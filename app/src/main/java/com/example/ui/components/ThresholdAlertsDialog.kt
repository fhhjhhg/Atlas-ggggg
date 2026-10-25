package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.AlertSeverity
import com.example.data.MetricThresholdRule
import com.example.data.TriggeredThresholdAlert
import com.example.ui.theme.CyberBg
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonRose
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ThresholdAlertsDialog(
    rules: List<MetricThresholdRule>,
    alerts: List<TriggeredThresholdAlert>,
    onToggleRule: (String) -> Unit,
    onTriggerTestFcm: (MetricThresholdRule) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val haptic = LocalHapticFeedback.current
    val timeFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(20.dp))
                .testTag("threshold_alerts_dialog"),
            color = CyberBg,
            border = BorderStroke(1.dp, CyberSurfaceBorder)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight()
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(NeonRose.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = "FCM Alerts",
                                tint = NeonRose,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "THRESHOLD ALERTS & FCM",
                                color = TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Firebase Cloud Messaging automated excursion alerts",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_threshold_alerts_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tabs: 0 -> Threshold Rules, 1 -> FCM Triggered Log
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = CyberSurface,
                    contentColor = NeonCyan,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = NeonCyan
                        )
                    }
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            selectedTab = 0
                        },
                        text = {
                            Text(
                                "Rules (${rules.count { it.isEnabled }}/${rules.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (selectedTab == 0) NeonCyan else TextSecondary
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            selectedTab = 1
                        },
                        text = {
                            Text(
                                "FCM Log (${alerts.size})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (selectedTab == 1) NeonRose else TextSecondary
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (selectedTab == 0) {
                    // Rules List
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(rules, key = { it.id }) { rule ->
                            RuleCard(
                                rule = rule,
                                onToggle = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onToggleRule(rule.id)
                                },
                                onTestFcm = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onTriggerTestFcm(rule)
                                }
                            )
                        }
                    }
                } else {
                    // Triggered FCM Alerts Log
                    if (alerts.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No threshold excursions recorded yet.",
                                color = TextMuted,
                                fontSize = 13.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(alerts, key = { it.id }) { alert ->
                                AlertLogCard(alert = alert, timeFormat = timeFormat)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RuleCard(
    rule: MetricThresholdRule,
    onToggle: () -> Unit,
    onTestFcm: () -> Unit
) {
    val severityColor = when (rule.severity) {
        AlertSeverity.EMERGENCY -> NeonRose
        AlertSeverity.CRITICAL -> NeonRose
        AlertSeverity.WARNING -> NeonAmber
        AlertSeverity.INFO -> NeonCyan
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("rule_card_${rule.id}"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CyberSurfaceBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
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
                            .background(if (rule.isEnabled) severityColor else TextMuted)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = rule.metricName,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Switch(
                    checked = rule.isEnabled,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = NeonCyan,
                        checkedTrackColor = CyberSurfaceVariant,
                        uncheckedThumbColor = TextMuted,
                        uncheckedTrackColor = CyberSurface
                    ),
                    modifier = Modifier.testTag("switch_rule_${rule.id}")
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Trigger Condition: ${rule.condition.symbol} ${rule.thresholdValue} ${rule.unit}",
                    color = severityColor,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Topic: ${rule.fcmTopic}",
                    color = TextMuted,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onTestFcm,
                enabled = rule.isEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberSurfaceVariant,
                    contentColor = NeonCyan
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("test_fcm_button_${rule.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Test FCM",
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Test Excursion & Dispatch FCM Push", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun AlertLogCard(
    alert: TriggeredThresholdAlert,
    timeFormat: SimpleDateFormat
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, NeonRose.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Warning",
                        tint = NeonRose,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = alert.metricName,
                        color = NeonRose,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = timeFormat.format(Date(alert.timestamp)),
                    color = TextMuted,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = alert.message,
                color = TextPrimary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "FCM ID: ${alert.fcmMessageId}",
                color = TextMuted,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
