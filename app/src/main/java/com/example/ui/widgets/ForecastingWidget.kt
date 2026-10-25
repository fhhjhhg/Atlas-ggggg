package com.example.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingFlat
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MetricHistoryPoint
import com.example.data.TrendDirection
import com.example.ui.DashboardUiState
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
import com.example.util.LinearRegressionCalculator
import kotlin.math.max
import kotlin.math.min

@Composable
fun ForecastingWidget(
    state: DashboardUiState,
    modifier: Modifier = Modifier
) {
    val metricOptions = listOf("Gross Revenue", "Weekly Hours Saved", "ArenaScore Quality", "Swarm Throughput")
    var selectedMetricName by remember { mutableStateOf("Gross Revenue") }

    // Map historical points from trend data or user metrics
    val historicalData = remember(selectedMetricName, state.historicalTrendPoints, state.grossAnnualRevenue, state.totalWeeklyHoursSaved) {
        val points = state.historicalTrendPoints
        if (points.isNotEmpty()) {
            points.takeLast(12).mapIndexed { idx, pt ->
                val value = when (selectedMetricName) {
                    "Gross Revenue" -> pt.grossRevenue
                    "Weekly Hours Saved" -> pt.hoursSaved
                    "ArenaScore Quality" -> pt.arenaScore
                    "Swarm Throughput" -> pt.swarmThroughput
                    else -> pt.grossRevenue
                }
                MetricHistoryPoint(
                    index = idx,
                    timestamp = pt.timestamp,
                    timeLabel = pt.timeLabel,
                    metricValue = value
                )
            }
        } else {
            val now = System.currentTimeMillis()
            (0..9).map { i ->
                val base = when (selectedMetricName) {
                    "Gross Revenue" -> state.grossAnnualRevenue
                    "Weekly Hours Saved" -> state.totalWeeklyHoursSaved
                    "ArenaScore Quality" -> state.compositeArenaScore.toDouble()
                    else -> 142.0
                }
                MetricHistoryPoint(
                    index = i,
                    timestamp = now - ((9 - i) * 60_000L),
                    timeLabel = "T-$i",
                    metricValue = base + (i * 3.5)
                )
            }
        }
    }

    val forecast = remember(historicalData, selectedMetricName) {
        LinearRegressionCalculator.calculateForecast(
            metricName = selectedMetricName,
            historicalData = historicalData,
            forecastStepsCount = 6
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CyberSurface)
            .border(BorderStroke(1.dp, CyberSurfaceBorder), RoundedCornerShape(14.dp))
            .padding(16.dp)
            .testTag("widget_forecasting_linear_regression")
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(NeonCyan.copy(alpha = 0.15f))
                        .border(1.dp, NeonCyan.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoGraph,
                        contentDescription = "Forecast",
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "ROOM HISTORICAL FORECASTING ENGINE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "OLS Linear Regression • Future Projections • Confidence Bounds",
                        fontSize = 10.sp,
                        color = TextMuted
                    )
                }
            }

            // Trend Badge
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = when (forecast.trendDirection) {
                    TrendDirection.BULLISH -> NeonEmerald.copy(alpha = 0.15f)
                    TrendDirection.BEARISH -> NeonRose.copy(alpha = 0.15f)
                    TrendDirection.STABLE -> NeonCyan.copy(alpha = 0.15f)
                },
                border = BorderStroke(
                    1.dp,
                    when (forecast.trendDirection) {
                        TrendDirection.BULLISH -> NeonEmerald.copy(alpha = 0.4f)
                        TrendDirection.BEARISH -> NeonRose.copy(alpha = 0.4f)
                        TrendDirection.STABLE -> NeonCyan.copy(alpha = 0.4f)
                    }
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (forecast.trendDirection) {
                            TrendDirection.BULLISH -> Icons.Default.TrendingUp
                            TrendDirection.BEARISH -> Icons.Default.TrendingDown
                            TrendDirection.STABLE -> Icons.Default.TrendingFlat
                        },
                        contentDescription = "Trend Direction",
                        tint = when (forecast.trendDirection) {
                            TrendDirection.BULLISH -> NeonEmerald
                            TrendDirection.BEARISH -> NeonRose
                            TrendDirection.STABLE -> NeonCyan
                        },
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${if (forecast.projectedChangePercent >= 0) "+" else ""}${forecast.projectedChangePercent}% Projected",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = when (forecast.trendDirection) {
                            TrendDirection.BULLISH -> NeonEmerald
                            TrendDirection.BEARISH -> NeonRose
                            TrendDirection.STABLE -> NeonCyan
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Metric Selectors
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            metricOptions.forEach { name ->
                FilterChip(
                    selected = selectedMetricName == name,
                    onClick = { selectedMetricName = name },
                    label = { Text(name, fontSize = 10.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                        selectedLabelColor = NeonCyan
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Interactive Recharts-Style Canvas Chart
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = CyberBg),
            border = BorderStroke(1.dp, CyberSurfaceBorder),
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .padding(12.dp)
            ) {
                ForecastCurveCanvas(forecast = forecast)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Regression Statistics Summary Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatItem(
                label = "Slope (m)",
                value = "${if (forecast.slope >= 0) "+" else ""}${forecast.slope}",
                color = if (forecast.slope >= 0) NeonEmerald else NeonRose,
                modifier = Modifier.weight(1f)
            )
            StatItem(
                label = "R² Fit",
                value = "${(forecast.rSquared * 100).toInt()}%",
                color = NeonCyan,
                modifier = Modifier.weight(1f)
            )
            StatItem(
                label = "Std Error",
                value = "±${forecast.standardError}",
                color = NeonAmber,
                modifier = Modifier.weight(1f)
            )
            StatItem(
                label = "Horizon",
                value = "+6 Steps",
                color = NeonViolet,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ForecastCurveCanvas(
    forecast: com.example.data.LinearRegressionForecast,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxWidth().height(145.dp)) {
        val historical = forecast.historicalData
        val future = forecast.forecastedPoints

        val allHistoricalValues = historical.map { it.metricValue }
        val allFutureValues = future.map { it.predictedValue }
        val allUpper = future.map { it.upperConfidenceBound }
        val allLower = future.map { it.lowerConfidenceBound }

        val minY = (allHistoricalValues + allLower).minOrNull() ?: 0.0
        val maxY = (allHistoricalValues + allUpper).maxOrNull() ?: 100.0
        val ySpan = max(1.0, maxY - minY)

        val totalPointsCount = historical.size + future.size
        val widthPerPoint = size.width / max(1, totalPointsCount - 1)

        // Helper to convert data (index, value) to Canvas Offset
        fun toOffset(index: Int, value: Double): Offset {
            val x = index * widthPerPoint
            val normalizedY = ((value - minY) / ySpan).toFloat()
            val y = size.height - (normalizedY * (size.height - 20f)) - 10f
            return Offset(x, y)
        }

        // 1. Draw Grid Lines
        val gridPaints = 4
        for (i in 0..gridPaints) {
            val y = (size.height / gridPaints) * i
            drawLine(
                color = Color(0xFF1E293B),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
        }

        // 2. Draw Forecast Confidence Band (Shaded Violet)
        if (future.isNotEmpty()) {
            val bandPath = Path()
            val startIndex = historical.size - 1
            val startHistVal = historical.last().metricValue
            val startPt = toOffset(startIndex, startHistVal)

            bandPath.moveTo(startPt.x, startPt.y)
            future.forEachIndexed { i, pt ->
                val fIndex = startIndex + 1 + i
                val upOffset = toOffset(fIndex, pt.upperConfidenceBound)
                bandPath.lineTo(upOffset.x, upOffset.y)
            }
            future.reversed().forEachIndexed { i, pt ->
                val fIndex = startIndex + future.size - i
                val lowOffset = toOffset(fIndex, pt.lowerConfidenceBound)
                bandPath.lineTo(lowOffset.x, lowOffset.y)
            }
            bandPath.close()

            drawPath(
                path = bandPath,
                color = Color(0xFF8B5CF6).copy(alpha = 0.15f)
            )
        }

        // 3. Draw Historical Line (Solid Cyan)
        if (historical.size > 1) {
            val histPath = Path()
            histPath.moveTo(toOffset(0, historical[0].metricValue).x, toOffset(0, historical[0].metricValue).y)
            for (i in 1 until historical.size) {
                val pt = toOffset(i, historical[i].metricValue)
                histPath.lineTo(pt.x, pt.y)
            }
            drawPath(
                path = histPath,
                color = Color(0xFF00F0FF),
                style = Stroke(width = 2.5f)
            )

            // Draw historical data dots
            historical.forEachIndexed { i, pt ->
                val offset = toOffset(i, pt.metricValue)
                drawCircle(
                    color = Color(0xFF00F0FF),
                    radius = 3f,
                    center = offset
                )
            }
        }

        // 4. Draw Projected Regression Line (Dashed Emerald)
        if (future.isNotEmpty() && historical.isNotEmpty()) {
            val regPath = Path()
            val startPt = toOffset(historical.size - 1, historical.last().metricValue)
            regPath.moveTo(startPt.x, startPt.y)

            future.forEachIndexed { i, pt ->
                val futureIndex = historical.size + i
                val offset = toOffset(futureIndex, pt.predictedValue)
                regPath.lineTo(offset.x, offset.y)
            }

            drawPath(
                path = regPath,
                color = Color(0xFF10B981),
                style = Stroke(
                    width = 2.5f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
                )
            )

            // Future projected dots
            future.forEachIndexed { i, pt ->
                val futureIndex = historical.size + i
                val offset = toOffset(futureIndex, pt.predictedValue)
                drawCircle(
                    color = Color(0xFF10B981),
                    radius = 3.5f,
                    center = offset
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
        border = BorderStroke(0.5.dp, CyberSurfaceBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = color
            )
            Text(
                text = label,
                fontSize = 9.sp,
                color = TextMuted
            )
        }
    }
}
