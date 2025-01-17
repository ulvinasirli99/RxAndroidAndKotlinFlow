package nasirli.tool.rxandroidandkotlinflows.ui.screens.views.trade

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import nasirli.tool.rxandroidandkotlinflows.ui.view_models.TradeViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TradeSocketScreen(
    tradeViewModel: TradeViewModel = hiltViewModel()
) {
    val tradeResponse by tradeViewModel.tradeResponse.collectAsState()
    val lineData by tradeViewModel.lineData.collectAsState()

    // LazyListState to control scroll position
    val listState = rememberLazyListState()

    LaunchedEffect(tradeResponse) {
        // Scroll to the bottom when the data changes
        listState.animateScrollToItem(tradeResponse?.data?.size?.minus(1) ?: 0)
    }

    LaunchedEffect(Unit) {
        tradeViewModel.sendSubscriptionEvent()
    }

    DisposableEffect(Unit) {
        onDispose {
            tradeViewModel.closeConnection()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp)
    ) {
        Text(
            text = "Trade WebSocket Data",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display Chart
        if (lineData != null) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                factory = { ctx ->
                    LineChart(ctx).apply {
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)

                        axisRight.isEnabled = false

                        // X-Axis Configuration
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            granularity = 1f
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    val index = value.toInt()
                                    val trade = tradeResponse?.data?.getOrNull(index)
                                    return trade?.let {
                                        val date = Date(it.t)
                                        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(
                                            date
                                        )
                                    } ?: ""
                                }
                            }
                        }

                        // Y-Axis Configuration
                        axisLeft.apply {
                            setDrawGridLines(true)
                            gridColor = Color.LTGRAY
                            textColor = Color.DKGRAY
                            granularity = 0.1f
                        }

                        legend.isEnabled = true
                        legend.textSize = 14f

                        animateX(1500) // Smooth animations
                    }
                },
                update = { chart ->
                    lineData?.let { data ->
                        val dataSet = data.getDataSetByIndex(0) as? LineDataSet
                        dataSet?.apply {
                            setDrawFilled(true)
                            fillColor = Color.RED // Optional gradient fill
                            color = Color.BLUE
                            setDrawCircles(false)
                            lineWidth = 2f
                            mode = LineDataSet.Mode.CUBIC_BEZIER
                        }

                        chart.data = data
                        chart.invalidate()
                        chart.moveViewToX(data.entryCount.toFloat() - 10) // Keep the last 10 entries in view
                    }
                }
            )
        } else {
            Text("Loading chart data...", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Raw Trade Data
        Text(
            text = "Raw Trade Data:",
            style = MaterialTheme.typography.headlineSmall
        )

        if (tradeResponse == null || tradeResponse?.data.isNullOrEmpty()) {
            Text(
                text = "No trade data available.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
                items(tradeResponse?.data ?: emptyList()) { trade ->
                    Text(
                        text = "Symbol: ${trade.s}, Price: ${trade.p}, Volume: ${trade.v}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
        tradeResponse?.data?.forEach { trade ->
            Text(
                text = "Symbol: ${trade.s}, Price: ${trade.p}, Volume: ${trade.v}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
