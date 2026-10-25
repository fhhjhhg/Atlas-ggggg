package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberBg
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonRose
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.Fill
import java.util.Locale

@Composable
fun MetricDataStreamChart(
    title: String,
    streamValues: List<Double>,
    modifier: Modifier = Modifier,
    lineColor: Color = NeonCyan,
    unit: String = "",
    metricLabel: String = "LIVE STREAM",
    sampleRateMs: Long = 1000L
) {
    val cleanValues = remember(streamValues) {
        if (streamValues.isEmpty()) listOf(0.0, 0.0) else streamValues
    }

    val latestValue = cleanValues.lastOrNull() ?: 0.0
    val minValue = cleanValues.minOrNull() ?: 0.0
    val maxValue = cleanValues.maxOrNull() ?: 0.0
    val avgValue = if (cleanValues.isNotEmpty()) cleanValues.average() else 0.0

    // Build Vico chart model
    val chartModel = remember(cleanValues) {
        val lineLayerModel = LineCartesianLayerModel.build {
            series(cleanValues)
        }
        CartesianChartModel(lineLayerModel)
    }

    val line = rememberLine(
        fill = LineCartesianLayer.LineFill.single(Fill(lineColor.toArgb())),
        areaFill = LineCartesianLayer.AreaFill.single(Fill(lineColor.copy(alpha = 0.20f).toArgb()))
    )

    val lineLayer = rememberLineCartesianLayer(
        lineProvider = remember(line) { LineCartesianLayer.LineProvider.series(line) }
    )

    val labelComponent = rememberAxisLabelComponent(
        color = TextSecondary
    )
    val lineComponent = rememberAxisLineComponent(
        color = CyberSurfaceBorder
    )

    val startAxis = rememberStartAxis(
        label = labelComponent,
        line = lineComponent,
        guideline = null
    )
    val bottomAxis = rememberBottomAxis(
        label = labelComponent,
        line = lineComponent,
        guideline = null
    )

    val chart = rememberCartesianChart(
        lineLayer,
        startAxis = startAxis,
        bottomAxis = bottomAxis
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("metric_data_stream_chart")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Header: Title & Live pulse indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(lineColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShowChart,
                            contentDescription = "Chart Stream",
                            tint = lineColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Column {
                        Text(
                            text = title.uppercase(),
                            color = lineColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "$metricLabel • ${cleanValues.size} sample points",
                            color = TextSecondary,
                            fontSize = 9.sp
                        )
                    }
                }

                // Live status tag
                Surface(
                    color = CyberBg,
                    border = BorderStroke(1.dp, lineColor.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(NeonEmerald)
                        )
                        Text(
                            text = String.format(Locale.US, "%.1f%s", latestValue, unit),
                            color = TextPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Metrics Summary Strip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MetricSummaryPill("MIN", String.format(Locale.US, "%.1f%s", minValue, unit), NeonRose, Modifier.weight(1f))
                MetricSummaryPill("AVG", String.format(Locale.US, "%.1f%s", avgValue, unit), NeonCyan, Modifier.weight(1f))
                MetricSummaryPill("MAX", String.format(Locale.US, "%.1f%s", maxValue, unit), NeonEmerald, Modifier.weight(1f))
                MetricSummaryPill("RATE", "${sampleRateMs}ms", TextSecondary, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Vico Cartesian Chart Host
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(CyberBg, RoundedCornerShape(6.dp))
                    .padding(horizontal = 4.dp, vertical = 4.dp)
            ) {
                CartesianChartHost(
                    chart = chart,
                    model = chartModel,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun MetricSummaryPill(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CyberBg,
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                color = TextMuted,
                fontSize = 8.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                color = color,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
