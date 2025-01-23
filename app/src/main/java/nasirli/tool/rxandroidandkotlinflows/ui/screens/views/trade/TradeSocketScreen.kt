package nasirli.tool.rxandroidandkotlinflows.ui.screens.views.trade

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import io.github.cdimascio.dotenv.dotenv
import nasirli.tool.rxandroidandkotlinflows.ui.theme.Purple80
import nasirli.tool.rxandroidandkotlinflows.ui.view_models.TradeViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TradeSocketScreen(
    tradeViewModel: TradeViewModel = hiltViewModel(),
    navCtrl: NavHostController,
) {

    val tradeResponse by tradeViewModel.tradeResponse.collectAsState()
    val barData by tradeViewModel.barData.collectAsState()


    // LazyListState for smooth scrolling
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

    Scaffold(
        topBar = {
            CustomAppBar(navController = navCtrl, screenTitle = "Trade Screen", backFunction = {
                tradeViewModel.closeConnection()
                navCtrl.popBackStack()
            })
        }
    ) {
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

            // Display BarChart
            if (barData != null) {
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

                            axisRight.isEnabled = false // Disable right axis

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
                                            SimpleDateFormat(
                                                "HH:mm:ss",
                                                Locale.getDefault()
                                            ).format(
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
                                textColor = Color.WHITE
                                granularity = 0.1f
                            }

                            legend.isEnabled = true
                            legend.textSize = 14f

                            animateX(1500) // Smooth animations

                            // Set background color to dark
                            setBackgroundColor(Color.BLUE)
                        }
                    },
                    update = { chart ->
                        tradeResponse?.data?.let { data ->
                            // Create a LineDataSet with the required properties
                            val entries = data.mapIndexed { index, trade ->
                                Entry(index.toFloat(), trade.v.toFloat())
                            }
                            val dataSet = LineDataSet(entries, "Trade Values")
                            dataSet.color = Color.YELLOW
                            dataSet.setCircleColor(Color.YELLOW)

                            // Create LineData and set it to the chart
                            chart.data = LineData(dataSet)
                            chart.notifyDataSetChanged()
                            chart.invalidate()
                            chart.moveViewToX(data.size.toFloat() - 10) // Keep the last 10 entries in view
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(navController: NavController, screenTitle: String, backFunction: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(screenTitle) },
        navigationIcon = {
            IconButton(onClick = {

                backFunction.invoke()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

