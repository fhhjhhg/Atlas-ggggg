package com.example.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HistoricalMetricType
import com.example.data.HistoricalTimeRange
import com.example.ui.DashboardUiState
import com.example.ui.components.HistoricalTrendLineChart
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

@Composable
fun HistoricalTrendChartWidget(
    state: DashboardUiState,
    onSelectMetric: (HistoricalMetricType) -> Unit,
    onSelectRange: (HistoricalTimeRange) -> Unit,
    onToggleLive: () -> Unit,
    onSimulateTick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("historical_trend_chart_widget")
    ) {
        // Control Bar: Time Horizon Tabs & Live Stream Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time Horizon Range Selector Tabs
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(CyberSurfaceVariant)
                    .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(8.dp))
                    .padding(3.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                HistoricalTimeRange.entries.forEach { range ->
                    val isSelected = range == state.selectedHistoricalRange
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) NeonCyan.copy(alpha = 0.25f) else Color.Transparent)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) NeonCyan else Color.Transparent,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable { onSelectRange(range) }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .testTag("range_tab_${range.name}")
                    ) {
                        Text(
                            text = range.label,
                            color = if (isSelected) NeonCyan else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            // Real-time Controls: Simulate Point & Live Toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Simulate Tick Button (triggers instant real-time data point)
                Button(
                    onClick = onSimulateTick,
                    modifier = Modifier
                        .height(32.dp)
                        .testTag("simulate_trend_tick_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald.copy(alpha = 0.20f)),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, NeonEmerald.copy(alpha = 0.6f))
                ) {
                    Icon(
                        imageVector = Icons.Default.AddChart,
                        contentDescription = "Simulate Real-time Tick",
                        tint = NeonEmerald,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+1 Tick",
                        color = NeonEmerald,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Toggle Live Streaming Button
                IconButton(
                    onClick = onToggleLive,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(6.dp))
                        .testTag("toggle_trend_live_button")
                ) {
                    Icon(
                        imageVector = if (state.isHistoricalTrendLive) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (state.isHistoricalTrendLive) "Pause Stream" else "Resume Stream",
                        tint = if (state.isHistoricalTrendLive) NeonCyan else NeonAmber,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Metric Category Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            HistoricalMetricType.entries.forEach { metric ->
                val isSelected = metric == state.selectedHistoricalMetric
                val (chipColor, chipBg) = when (metric) {
                    HistoricalMetricType.GROSS_REVENUE -> Pair(NeonCyan, NeonCyan.copy(alpha = 0.15f))
                    HistoricalMetricType.HOURS_SAVED -> Pair(NeonEmerald, NeonEmerald.copy(alpha = 0.15f))
                    HistoricalMetricType.SWARM_THROUGHPUT -> Pair(NeonViolet, NeonViolet.copy(alpha = 0.15f))
                    HistoricalMetricType.ARENA_SCORE -> Pair(NeonAmber, NeonAmber.copy(alpha = 0.15f))
                }

                Surface(
                    color = if (isSelected) chipBg else CyberSurface,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, if (isSelected) chipColor else CyberSurfaceBorder),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelectMetric(metric) }
                        .testTag("metric_chip_${metric.name}")
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = metric.displayName,
                            color = if (isSelected) chipColor else TextSecondary,
                            fontSize = 9.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            maxLines = 1
                        )
                        Text(
                            text = "[${metric.unit}]",
                            color = if (isSelected) chipColor.copy(alpha = 0.8f) else TextMuted,
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // The Real-Time Vico Line Chart Component
        HistoricalTrendLineChart(
            points = state.historicalTrendPoints,
            metric = state.selectedHistoricalMetric,
            range = state.selectedHistoricalRange,
            isLive = state.isHistoricalTrendLive
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Macro Volatility & Projections Strip
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant.copy(alpha = 0.6f)),
            border = BorderStroke(1.dp, CyberSurfaceBorder),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timeline,
                        contentDescription = "Analysis",
                        tint = NeonCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "VOLATILITY: ±1.24% • TRAJECTORY: EXPONENTIAL",
                        color = TextSecondary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Text(
                    text = "${state.historicalTrendPoints.size} SAMPLES",
                    color = NeonEmerald,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
