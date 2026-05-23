package com.example.cryptoapp.presentation.coin_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.fixed
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@SuppressLint("DefaultLocale")
@Composable
fun CryptoLineChart(
    viewModel: DetailViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(state.history) {
        if (state.history.isNotEmpty()){
            modelProducer.runTransaction {
                lineSeries {
                    series(
                        x = state.history.indices.toList(),
                        y = state.history.map { it.price.toFloat() }
                    )
                }
            }
        }

    }


    val bottomAxisFormatter = remember(state.history, state.selectedRange) {
        CartesianValueFormatter { _, value, _ ->
            val index = kotlin.math.round(value).toInt()

            if (state.history.isNotEmpty() && index in state.history.indices) {
                val unixTime = state.history[index].timestamp

                val timeInMillis = if (unixTime < 99999999999L) unixTime * 1000 else unixTime
                val date = Date(timeInMillis)

                val pattern = when (state.selectedRange) {
                    "1D" -> "HH:mm"
                    "1W" -> "E"
                    "1M" -> "dd"
                    "1Y", "All" -> "MMM"
                    else -> "dd.MM.yyyy"
                }

                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                sdf.format(date)
            } else {
                ""
            }
        }
    }

    val startAxisFormatter = remember {
        CartesianValueFormatter { _, value, _ ->
            val priceFloat = value.toString().toFloatOrNull() ?: 0f
            when {
                priceFloat >= 1000f -> String.format("$%.0f", priceFloat)
                priceFloat < 1f -> String.format("$%.4f", priceFloat)
                else -> String.format("$%.2f", priceFloat)
            }
        }
    }

    val bottomSpacing = remember(state.selectedRange, state.history.size) {
        when (state.selectedRange) {
            "1D" -> maxOf(1, state.history.size / 6)
            "1W" -> maxOf(1, state.history.size / 7)
            "1M" -> maxOf(1, state.history.size / 8)
            "1Y", "All" -> maxOf(1, state.history.size / 12)
            else -> maxOf(1, state.history.size / 6)
        }
    }

    val markerIndicator = rememberShapeComponent(
        fill = fill(Color(0xFF0066FF)),
        shape = CorneredShape.Pill,
    )

    val marker = rememberDefaultCartesianMarker(
        label = rememberTextComponent(
            color = Color.White,
            padding = insets(8.dp, 4.dp, 8.dp, 4.dp),
            background = rememberShapeComponent(
                fill = fill(Color(0xFF1C1C2E)),
                shape = CorneredShape.rounded(allPercent = 8),
            )
        ),
        indicator = { _ -> markerIndicator },
        indicatorSize = 10.dp,
        labelPosition = DefaultCartesianMarker.LabelPosition.AbovePoint
    )

    if (state.history.isNotEmpty()) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(Color(0xFF0066FF))),
                            areaFill = LineCartesianLayer.AreaFill.single(
                                fill = fill(
                                    ShaderProvider.verticalGradient(
                                        Color(0xFF0066FF).copy(alpha = 0.35f).toArgb(),
                                        Color(0xFF0066FF).copy(alpha = 0.0f).toArgb()
                                    )
                                )
                            )
                        )
                    ),
                    rangeProvider = remember {
                        object : CartesianLayerRangeProvider {
                            override fun getMinY(minY: Double, maxY: Double, extraStore: ExtraStore) = minY
                            override fun getMaxY(minY: Double, maxY: Double, extraStore: ExtraStore) = maxY
                        }
                    }
                ),
                startAxis = VerticalAxis.rememberStart(
                    label = rememberTextComponent(
                        color = Color.Gray
                    ),
                    valueFormatter = startAxisFormatter,
                    guideline = null,
                    itemPlacer = VerticalAxis.ItemPlacer.count(count = { 5 })
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    label = rememberTextComponent(
                        color = Color.Gray
                    ),
                    valueFormatter = bottomAxisFormatter,
                    guideline = null,
                    itemPlacer = HorizontalAxis.ItemPlacer.aligned(spacing = { bottomSpacing })
                ),
                marker = marker
            ),
            modelProducer = modelProducer,
            modifier = Modifier.fillMaxSize()
        )
    }

}






