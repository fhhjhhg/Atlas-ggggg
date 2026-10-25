package com.example.ui.widgets

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TelemetryEvent
import com.example.data.TelemetrySeverity
import com.example.ui.DashboardUiState
import com.example.ui.components.MetricDataStreamChart
import com.example.ui.theme.CyberBg
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
fun LiveTelemetryWidget(
    state: DashboardUiState,
    modifier: Modifier = Modifier
) {
    // Dynamic stream calculation derived from telemetry events and refresh ticks
    val throughputStream = remember(state.telemetryFeed, state.totalLiveEventsCount) {
        if (state.telemetryFeed.isEmpty()) {
            listOf(22.0, 26.0, 31.0, 28.0, 34.0, 42.0, 39.0, 47.0)
        } else {
            state.telemetryFeed.take(10).reversed().mapIndexed { idx, ev ->
                val base = when (ev.severity) {
                    TelemetrySeverity.YIELD -> 46.0
                    TelemetrySeverity.AUDIT -> 35.0
                    TelemetrySeverity.SYNC -> 28.0
                    TelemetrySeverity.INFO -> 22.0
                }
                base + ((idx * 3) % 7) * 1.5
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("live_telemetry_widget")
    ) {
        // Stream status header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(NeonEmerald)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "EVENT STREAM (${state.totalLiveEventsCount} EVENTS LOGGED)",
                    color = NeonEmerald,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Text(
                text = "SUB-20MS HANDSHAKE",
                color = TextSecondary,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Visual Data Stream Chart
        MetricDataStreamChart(
            title = "STREAM THROUGHPUT",
            streamValues = throughputStream,
            unit = "evt/s",
            lineColor = NeonCyan,
            metricLabel = "THROUGHPUT",
            sampleRateMs = (state.refreshIntervalSeconds * 1000L) / 10
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Display latest 5 telemetry events
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            state.telemetryFeed.take(5).forEach { event ->
                TelemetryEventRow(event = event)
            }
        }
    }
}

@Composable
private fun TelemetryEventRow(event: TelemetryEvent) {
    val (badgeColor, badgeText) = when (event.severity) {
        TelemetrySeverity.YIELD -> Pair(NeonEmerald, "YIELD")
        TelemetrySeverity.AUDIT -> Pair(NeonAmber, "AUDIT")
        TelemetrySeverity.SYNC -> Pair(NeonCyan, "SYNC")
        TelemetrySeverity.INFO -> Pair(NeonViolet, "INFO")
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = CyberBg,
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberSurfaceBorder.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = event.timeLabel,
                        color = TextMuted,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(badgeColor.copy(alpha = 0.15f))
                            .padding(horizontal = 5.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "[${event.pillarId}] ${event.nodeName}",
                            color = badgeColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = event.message,
                    color = TextPrimary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(CyberSurfaceVariant)
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Text(
                    text = event.deltaImpact,
                    color = badgeColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
