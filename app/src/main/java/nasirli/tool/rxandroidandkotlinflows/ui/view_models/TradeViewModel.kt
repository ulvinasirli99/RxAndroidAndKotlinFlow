package nasirli.tool.rxandroidandkotlinflows.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nasirli.tool.rxandroidandkotlinflows.domain.models.TradeResponse
import nasirli.tool.rxandroidandkotlinflows.utils.helpers.WebSocketMessageHolder
import okhttp3.WebSocket
import javax.inject.Inject

@HiltViewModel
class TradeViewModel @Inject constructor(
    private val webSocket: WebSocket,
    private val messageHolder: WebSocketMessageHolder,
) : ViewModel() {

    private val _tradeResponse = MutableStateFlow<TradeResponse?>(null)
    val tradeResponse: StateFlow<TradeResponse?> = _tradeResponse.asStateFlow()

    private val _barData = MutableStateFlow<BarData?>(null)
    val barData: StateFlow<BarData?> = _barData

    private val gson = Gson()


    init {

        listenToWebSocket()
        // Observe messages from WebSocketMessageHolder

    }

    override fun onCleared() {
        super.onCleared()
        closeConnection()
    }

    private fun listenToWebSocket() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                messageHolder.messages.collect { message ->
                    try {
                        val response = gson.fromJson(message, TradeResponse::class.java)
                        if (response != null) {
                            updateTradeResponse(response)
                        } else {
                            Log.e("TradeViewModel", "Invalid TradeResponse received")
                        }
                    } catch (e: Exception) {
                        Log.e("TradeViewModel", "Error parsing WebSocket message: ${e.message}", e)
                    }
                }
            }
        }
    }

    private fun updateTradeResponse(response: TradeResponse) {
        _tradeResponse.update { currentResponse ->
            currentResponse?.copy(
                data = currentResponse.data + response.data
            ) ?: response
        }

        // Convert response data to chart entries
        val entries = response.data.mapIndexed { index, trade ->
            BarEntry(index.toFloat(), trade.v.toFloat(),0) // Index as X-axis and price as Y-axis
        }

        val dataSet = BarDataSet(entries, "Price Data")
        dataSet.color = android.graphics.Color.BLUE // Set line color
        dataSet.valueTextColor = android.graphics.Color.RED // Set value text color

        _barData.value = BarData(dataSet)
    }

    fun sendSubscriptionEvent() {
        val subscribeEvent = """
            {
                "type": "subscribe",
                "symbol": "BINANCE:BTCUSDT"
            }
        """.trimIndent()
        webSocket.send(subscribeEvent)
    }

    fun closeConnection() {
        webSocket.close(1000, "Closing connection")
    }

}