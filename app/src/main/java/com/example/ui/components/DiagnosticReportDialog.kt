package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.BugReportSeverity
import com.example.data.DiagnosticData
import com.example.data.TelemetrySeverity
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiagnosticReportDialog(
    diagnostics: DiagnosticData,
    isSubmitting: Boolean,
    onSubmit: (BugReportSeverity, String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSeverity by remember { mutableStateOf(BugReportSeverity.MEDIUM) }
    var userNotes by remember { mutableStateOf("") }
    var logsExpanded by remember { mutableStateOf(false) }

    // Pulsing animation for the shake indicator badge
    val infiniteTransition = rememberInfiniteTransition(label = "shake_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Dialog(
        onDismissRequest = { if (!isSubmitting) onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.96f)
                .fillMaxHeight(0.92f)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, NeonRose.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                .testTag("diagnostic_report_dialog"),
            color = CyberSurface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(NeonRose.copy(alpha = 0.15f))
                                .border(1.dp, NeonRose.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.BugReport,
                                contentDescription = "Bug Report",
                                tint = NeonRose,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "DIAGNOSTIC REPORT",
                                    color = TextPrimary,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                // Shake pulse badge
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(NeonRose.copy(alpha = 0.2f * pulseAlpha))
                                        .border(1.dp, NeonRose.copy(alpha = 0.6f * pulseAlpha), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Vibration,
                                            contentDescription = "Shake Event",
                                            tint = NeonRose,
                                            modifier = Modifier.size(10.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = "SHAKE DETECTED",
                                            color = NeonRose,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "Real-time state & telemetry capture at ${diagnostics.formattedTime}",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        enabled = !isSubmitting,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(CyberSurfaceVariant)
                            .testTag("close_diagnostic_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Dialog",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Scrollable Body Content
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // System Snapshot Metrics Card
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = CyberBg,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, CyberSurfaceBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PhoneAndroid,
                                        contentDescription = "Hardware Info",
                                        tint = NeonCyan,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "ENVIRONMENT & RUNTIME METRICS",
                                        color = NeonCyan,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    MetricPill(label = "DEVICE", value = diagnostics.deviceModel)
                                    MetricPill(label = "OS", value = diagnostics.osVersion)
                                    MetricPill(label = "ACTIVE TAB", value = diagnostics.activeTabName)
                                    MetricPill(label = "WIDGETS", value = "${diagnostics.activeWidgetsCount} / ${diagnostics.totalWidgetsCount}")
                                    MetricPill(label = "REFRESH", value = diagnostics.autoRefreshStatus)
                                    MetricPill(label = "SYNC", value = diagnostics.cloudSyncStatus)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Memory,
                                        contentDescription = "Memory Usage",
                                        tint = TextMuted,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "JVM Memory: ${diagnostics.memoryUsage}",
                                        color = TextSecondary,
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }

                    // Severity Selector
                    item {
                        Column {
                            Text(
                                text = "INCIDENT SEVERITY",
                                color = TextPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                BugReportSeverity.values().forEach { severity ->
                                    val isSelected = selectedSeverity == severity
                                    val tintColor = when (severity) {
                                        BugReportSeverity.LOW -> NeonEmerald
                                        BugReportSeverity.MEDIUM -> NeonAmber
                                        BugReportSeverity.HIGH -> NeonRose
                                        BugReportSeverity.CRITICAL -> Color(0xFFFF2A6D)
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) tintColor.copy(alpha = 0.2f) else CyberSurfaceVariant)
                                            .border(
                                                1.dp,
                                                if (isSelected) tintColor else CyberSurfaceBorder,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable { selectedSeverity = severity }
                                            .padding(vertical = 10.dp)
                                            .testTag("severity_${severity.name.lowercase()}"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = severity.label.uppercase(),
                                                color = if (isSelected) tintColor else TextSecondary,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 0.5.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // User Notes / Observations Input
                    item {
                        Column {
                            Text(
                                text = "WHAT HAPPENED? (USER OBSERVATIONS)",
                                color = TextPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = userNotes,
                                onValueChange = { userNotes = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("bug_report_notes_input"),
                                placeholder = {
                                    Text(
                                        text = "Describe the steps taken before shaking, unexpected behavior, or anomalies observed...",
                                        color = TextMuted,
                                        fontSize = 12.sp
                                    )
                                },
                                minLines = 3,
                                maxLines = 5,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NeonCyan,
                                    unfocusedBorderColor = CyberSurfaceBorder,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary,
                                    focusedContainerColor = CyberBg,
                                    unfocusedContainerColor = CyberBg,
                                    cursorColor = NeonCyan
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }

                    // Captured Telemetry Logs Snapshot (Collapsible)
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = CyberBg,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, CyberSurfaceBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { logsExpanded = !logsExpanded },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Terminal,
                                            contentDescription = "Telemetry Logs",
                                            tint = NeonAmber,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "CAPTURED TELEMETRY SNAPSHOT (${diagnostics.recentTelemetry.size} EVENTS)",
                                            color = NeonAmber,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.5.sp
                                        )
                                    }

                                    Icon(
                                        imageVector = if (logsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = "Toggle logs",
                                        tint = TextMuted,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                AnimatedVisibility(visible = logsExpanded) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 10.dp)
                                    ) {
                                        diagnostics.recentTelemetry.forEach { event ->
                                            val badgeColor = when (event.severity) {
                                                TelemetrySeverity.INFO -> NeonCyan
                                                TelemetrySeverity.YIELD -> NeonEmerald
                                                TelemetrySeverity.SYNC -> NeonAmber
                                                TelemetrySeverity.AUDIT -> NeonRose
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 3.dp),
                                                verticalAlignment = Alignment.Top
                                            ) {
                                                Text(
                                                    text = event.timeLabel,
                                                    color = TextMuted,
                                                    fontSize = 10.sp,
                                                    fontFamily = FontFamily.Monospace,
                                                    modifier = Modifier.width(55.dp)
                                                )
                                                Text(
                                                    text = "[${event.pillarId}]",
                                                    color = badgeColor,
                                                    fontSize = 10.sp,
                                                    fontFamily = FontFamily.Monospace,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.width(42.dp)
                                                )
                                                Text(
                                                    text = event.message,
                                                    color = TextPrimary,
                                                    fontSize = 10.sp,
                                                    fontFamily = FontFamily.Monospace,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Footer Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        enabled = !isSubmitting,
                        modifier = Modifier.testTag("cancel_report_button"),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextSecondary
                        ),
                        border = BorderStroke(1.dp, CyberSurfaceBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            onSubmit(selectedSeverity, userNotes)
                        },
                        enabled = !isSubmitting,
                        modifier = Modifier.testTag("submit_bug_report_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonRose,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Uploading...")
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Submit Report",
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Upload Diagnostic Report",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricPill(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(CyberSurfaceVariant)
            .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$label: ",
                color = TextMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = value,
                color = TextPrimary,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
