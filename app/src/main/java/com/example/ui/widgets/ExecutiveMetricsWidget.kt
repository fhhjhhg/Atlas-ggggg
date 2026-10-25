package com.example.ui.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.DashboardUiState
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonViolet
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ExecutiveMetricsWidget(
    state: DashboardUiState,
    onOpenMetricEditor: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                label = "TIME-SAVED YIELD",
                value = "${state.totalWeeklyHoursSaved.toInt()} hrs/wk",
                subValue = "Equivalent to ${state.activeOperatorsCount} operators",
                icon = Icons.Default.Schedule,
                accentColor = NeonCyan,
                badge = "2,500 HRS CAP",
                modifier = Modifier
                    .weight(1f)
                    .testTag("metric_time_saved")
            )

            MetricCard(
                label = "GROSS REVENUE",
                value = currencyFormat.format(state.grossAnnualRevenue),
                subValue = "35 Beds @ $830/mo ($29k/mo)",
                icon = Icons.Default.TrendingUp,
                accentColor = NeonEmerald,
                badge = "+3.6% CAGR",
                modifier = Modifier
                    .weight(1f)
                    .testTag("metric_gross_rev")
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                label = "NET FREE CASH FLOW",
                value = currencyFormat.format(state.netFreeCashFlow),
                subValue = "Fixed Debt Service $33,564/yr",
                icon = Icons.Default.AccountBalance,
                accentColor = NeonViolet,
                badge = "DSCR >= 3.5x",
                modifier = Modifier
                    .weight(1f)
                    .testTag("metric_cash_flow")
            )

            MetricCard(
                label = "15-YR WEALTH VELOCITY",
                value = "$7.02M",
                subValue = "100% Cash Flow Reinvestment",
                icon = Icons.Default.AutoGraph,
                accentColor = NeonAmber,
                badge = "10.0% S&P",
                modifier = Modifier
                    .weight(1f)
                    .testTag("metric_wealth_velocity")
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Room Database User-Defined Performance Metrics Section
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("user_defined_metrics_section"),
            shape = RoundedCornerShape(10.dp),
            color = CyberSurface,
            border = BorderStroke(1.dp, CyberSurfaceBorder)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "USER PERFORMANCE METRICS",
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CyberSurfaceVariant)
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ROOM DB (${state.userPerformanceMetrics.size})",
                                color = NeonEmerald,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Button(
                        onClick = onOpenMetricEditor,
                        modifier = Modifier
                            .height(30.dp)
                            .testTag("add_user_metric_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan.copy(alpha = 0.20f)),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f)),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Metric",
                            tint = NeonCyan,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Manage",
                            color = NeonCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (state.userPerformanceMetrics.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        state.userPerformanceMetrics.take(4).forEach { metric ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(CyberSurfaceVariant)
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                                    .testTag("dashboard_metric_item_${metric.id}"),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = metric.name,
                                        color = TextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Category: ${metric.category}",
                                        color = TextMuted,
                                        fontSize = 9.sp
                                    )
                                }
                                Text(
                                    text = "${metric.currentValue} ${metric.unit}",
                                    color = NeonEmerald,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    label: String,
    value: String,
    subValue: String,
    icon: ImageVector,
    accentColor: Color,
    badge: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = CyberSurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberSurfaceBorder)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(accentColor.copy(alpha = 0.12f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badge,
                        color = accentColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = label,
                color = TextSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            AnimatedContent(
                targetState = value,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "metricValueAnim"
            ) { targetVal ->
                Text(
                    text = targetVal,
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subValue,
                color = TextMuted,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}
