package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.HistoricalMetricType
import com.example.data.HistoricalTimeRange
import com.example.data.HistoricalTrendPoint
import com.example.ui.theme.CyberBg
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonRose
import com.example.ui.theme.NeonViolet
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

/**
 * Reusable real-time updating Line Chart component using Vico library.
 * Visualizes historical data trends with dynamic multi-metric scaling, area gradient,
 * delta trajectory metrics, and responsive axis styling.
 */
@Composable
fun HistoricalTrendLineChart(
    points: List<HistoricalTrendPoint>,
    metric: HistoricalMetricType,
    range: HistoricalTimeRange,
    modifier: Modifier = Modifier,
    isLive: Boolean = true
) {
    val metricColor = when (metric) {
        HistoricalMetricType.GROSS_REVENUE -> NeonCyan
        HistoricalMetricType.HOURS_SAVED -> NeonEmerald
        HistoricalMetricType.SWARM_THROUGHPUT -> NeonViolet
        HistoricalMetricType.ARENA_SCORE -> NeonAmber
    }

    // Filter points to time horizon window
    val windowPoints = remember(points, range) {
        val sampleSize = range.samplePointsCount
        if (points.size <= sampleSize) points else points.takeLast(sampleSize)
    }

    // Extract numeric series
    val seriesValues = remember(windowPoints, metric) {
        if (windowPoints.isEmpty()) {
            listOf(0.0, 0.0)
        } else {
            windowPoints.map { it.valueFor(metric) }
        }
    }

    // Trend Statistics
    val latestValue = seriesValues.lastOrNull() ?: 0.0
    val firstValue = seriesValues.firstOrNull() ?: latestValue
    val minValue = seriesValues.minOrNull() ?: 0.0
    val maxValue = seriesValues.maxOrNull() ?: 0.0
    val avgValue = if (seriesValues.isNotEmpty()) seriesValues.average() else 0.0

    val deltaAbsolute = latestValue - firstValue
    val deltaPercent = if (firstValue > 0.0) (deltaAbsolute / firstValue) * 100.0 else 0.0
    val isPositiveTrend = deltaAbsolute >= 0.0

    // Pulsing animation for live state
    val infiniteTransition = rememberInfiniteTransition(label = "live_trend_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    // Build Vico chart model
    val chartModel = remember(seriesValues) {
        val lineLayerModel = LineCartesianLayerModel.build {
            series(seriesValues)
        }
        CartesianChartModel(lineLayerModel)
    }

    // Vico Line Layer with glowing area fill
    val line = rememberLine(
        fill = LineCartesianLayer.LineFill.single(Fill(metricColor.toArgb())),
        areaFill = LineCartesianLayer.AreaFill.single(Fill(metricColor.copy(alpha = 0.22f).toArgb()))
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
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("historical_trend_line_chart")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header: Metric Name, Range & Live Pulse
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (isLive) NeonEmerald.copy(alpha = pulseAlpha) else NeonAmber)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = metric.displayName.uppercase(),
                                color = metricColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.8.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(CyberSurfaceVariant)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = range.label,
                                    color = TextPrimary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                        Text(
                            text = "${metric.description} • ${seriesValues.size} data points",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }

                // Delta Trajectory Badge
                Surface(
                    color = CyberBg,
                    border = BorderStroke(1.dp, if (isPositiveTrend) NeonEmerald.copy(alpha = 0.5f) else NeonRose.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.testTag("historical_trend_delta_badge")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (isPositiveTrend) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                            contentDescription = if (isPositiveTrend) "Positive Trend" else "Negative Trend",
                            tint = if (isPositiveTrend) NeonEmerald else NeonRose,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = String.format(Locale.US, "%s%.2f%%", if (isPositiveTrend) "+" else "", deltaPercent),
                            color = if (isPositiveTrend) NeonEmerald else NeonRose,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Summary Stats Pill Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                StatPill(
                    label = "CURRENT",
                    value = formatMetricValue(latestValue, metric),
                    color = metricColor,
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    label = "MIN",
                    value = formatMetricValue(minValue, metric),
                    color = NeonRose,
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    label = "AVG",
                    value = formatMetricValue(avgValue, metric),
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    label = "MAX",
                    value = formatMetricValue(maxValue, metric),
                    color = NeonEmerald,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Vico Cartesian Chart Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CyberBg)
                    .border(BorderStroke(1.dp, CyberSurfaceBorder), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .testTag("vico_chart_host_container")
            ) {
                CartesianChartHost(
                    chart = chart,
                    model = chartModel,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Footer: Time horizon interval information & latest timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "INTERVAL: ${range.intervalLabel.uppercase()}",
                    color = TextMuted,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = if (isLive) "LIVE STREAM • AUTO-APPENDING" else "STREAM PAUSED",
                    color = if (isLive) NeonEmerald else NeonAmber,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

private fun formatMetricValue(value: Double, metric: HistoricalMetricType): String {
    return when (metric) {
        HistoricalMetricType.GROSS_REVENUE -> {
            if (value >= 1_000_000.0) {
                String.format(Locale.US, "$%.2fM", value / 1_000_000.0)
            } else if (value >= 1_000.0) {
                String.format(Locale.US, "$%.1fK", value / 1_000.0)
            } else {
                String.format(Locale.US, "$%.0f", value)
            }
        }
        HistoricalMetricType.HOURS_SAVED -> {
            String.format(Locale.US, "%.1fh", value)
        }
        HistoricalMetricType.SWARM_THROUGHPUT -> {
            String.format(Locale.US, "%.1f/s", value)
        }
        HistoricalMetricType.ARENA_SCORE -> {
            String.format(Locale.US, "%.1f%%", value)
        }
    }
}

@Composable
private fun StatPill(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CyberBg,
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
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
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
