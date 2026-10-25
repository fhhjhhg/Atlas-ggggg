package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.ViewQuilt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DashboardTemplate
import com.example.data.DatabaseSyncState
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
fun TopBarHeader(
    activeWidgetsCount: Int,
    totalWidgetsCount: Int,
    secondsUntilNextRefresh: Int,
    refreshIntervalSeconds: Int,
    isAutoRefreshEnabled: Boolean,
    isRefreshing: Boolean,
    currentUserEmail: String? = null,
    cloudSyncStatus: String = "Local Storage",
    databaseSyncState: DatabaseSyncState = DatabaseSyncState.SYNCED,
    onSignInGoogle: () -> Unit = {},
    onSignOut: () -> Unit = {},
    onTriggerRefresh: () -> Unit,
    onToggleAutoRefresh: () -> Unit,
    onSelectInterval: (Int) -> Unit,
    onOpenCustomizer: () -> Unit,
    onTriggerShakeReport: () -> Unit = {},
    onOpenActivityLog: () -> Unit = {},
    onOpenSyncDetails: () -> Unit = {},
    onApplyTemplate: (DashboardTemplate) -> Unit = {},
    onOpenWidgetStore: () -> Unit = {},
    onOpenThresholdAlerts: () -> Unit = {},
    onOpenVoiceCommands: () -> Unit = {},
    onOpenPdfExport: () -> Unit = {},
    onOpenAiAssistant: () -> Unit = {},
    onOpenShortcutsCheatSheet: () -> Unit = {},
    isDemoModeActive: Boolean = false,
    onToggleDemoMode: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var intervalMenuOpen by remember { mutableStateOf(false) }
    var templateMenuOpen by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    // Pulsating animation for live telemetry indicator
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val rotation by animateFloatAsState(
        targetValue = if (isRefreshing) 360f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "refreshRotation"
    )

    val syncColor = when (databaseSyncState) {
        DatabaseSyncState.SYNCED -> NeonEmerald
        DatabaseSyncState.SYNCING -> NeonAmber
        DatabaseSyncState.LOCAL_CACHE -> NeonCyan
        DatabaseSyncState.SYNC_ERROR -> NeonRose
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = CyberSurface,
        border = BorderStroke(1.dp, CyberSurfaceBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            // Upper Row: Title + Live Status + Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Title with live pulse dot
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (isAutoRefreshEnabled) NeonEmerald.copy(alpha = pulseAlpha) else NeonAmber)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "NEXUS",
                                color = TextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 17.sp,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "FORCE",
                                color = NeonCyan,
                                fontWeight = FontWeight.Black,
                                fontSize = 17.sp,
                                letterSpacing = 1.sp
                            )
                        }
                        Text(
                            text = "ATLAS GODMODE • 1000x ENGINE",
                            color = TextSecondary,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.6.sp
                        )
                    }
                }

                // Action controls: Sync Icon, Interval, Refresh, Customizer
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Connectivity feedback: Status icon that changes color based on Room <-> Firestore sync
                    IconButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenSyncDetails()
                        },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(syncColor.copy(alpha = 0.15f))
                            .border(1.dp, syncColor.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .testTag("sync_status_icon")
                    ) {
                        Icon(
                            imageVector = when (databaseSyncState) {
                                DatabaseSyncState.SYNCED -> Icons.Default.CloudDone
                                DatabaseSyncState.SYNCING -> Icons.Default.CloudSync
                                else -> Icons.Default.Cloud
                            },
                            contentDescription = "Sync Status: ${databaseSyncState.label}",
                            tint = syncColor,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // Auto-refresh countdown badge
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(CyberSurfaceVariant)
                                .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(8.dp))
                                .clickable { intervalMenuOpen = true }
                                .padding(horizontal = 7.dp, vertical = 5.dp)
                                .testTag("interval_selector_button")
                        ) {
                            Icon(
                                imageVector = if (isAutoRefreshEnabled) Icons.Default.Timer else Icons.Default.Pause,
                                contentDescription = "Auto Refresh Timer",
                                tint = if (isAutoRefreshEnabled) NeonCyan else NeonAmber,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isAutoRefreshEnabled) "${secondsUntilNextRefresh}s" else "PAUSED",
                                color = TextPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        DropdownMenu(
                            expanded = intervalMenuOpen,
                            onDismissRequest = { intervalMenuOpen = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("5 Seconds (Ultra-Fast)") },
                                onClick = {
                                    onSelectInterval(5)
                                    intervalMenuOpen = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("15 Seconds (Fast)") },
                                onClick = {
                                    onSelectInterval(15)
                                    intervalMenuOpen = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("30 Seconds (Default)") },
                                onClick = {
                                    onSelectInterval(30)
                                    intervalMenuOpen = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("60 Seconds (Standard)") },
                                onClick = {
                                    onSelectInterval(60)
                                    intervalMenuOpen = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(if (isAutoRefreshEnabled) "Pause Auto-Refresh" else "Resume Auto-Refresh")
                                },
                                onClick = {
                                    onToggleAutoRefresh()
                                    intervalMenuOpen = false
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // Manual refresh button
                    IconButton(
                        onClick = onTriggerRefresh,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CyberSurfaceVariant)
                            .testTag("manual_refresh_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Manual Refresh",
                            tint = NeonCyan,
                            modifier = Modifier
                                .size(17.dp)
                                .rotate(rotation)
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // Customize widgets button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(NeonViolet.copy(alpha = 0.15f))
                            .border(1.dp, NeonViolet.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .clickable(onClick = onOpenCustomizer)
                            .padding(horizontal = 7.dp, vertical = 5.dp)
                            .testTag("customize_widgets_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DashboardCustomize,
                            contentDescription = "Customize Widgets",
                            tint = NeonViolet,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$activeWidgetsCount/$totalWidgetsCount",
                            color = NeonViolet,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    // Shake / Diagnostic Bug Report Button
                    IconButton(
                        onClick = onTriggerShakeReport,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(NeonRose.copy(alpha = 0.15f))
                            .border(1.dp, NeonRose.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .testTag("shake_report_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.BugReport,
                            contentDescription = "Shake to Report Bug",
                            tint = NeonRose,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Ribbon Toolbar: Predefined Templates, Widget Store, FCM Alerts, Voice, PDF, AI Assistant, Demo Mode, Shortcuts
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 1-Click Layout Templates Dropdown
                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(NeonCyan.copy(alpha = 0.15f))
                            .border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .clickable { templateMenuOpen = true }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("layout_templates_button")
                    ) {
                        Icon(Icons.Default.ViewQuilt, contentDescription = "Templates", tint = NeonCyan, modifier = Modifier.size(13.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Templates", color = NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }

                    DropdownMenu(
                        expanded = templateMenuOpen,
                        onDismissRequest = { templateMenuOpen = false }
                    ) {
                        DashboardTemplate.values().forEach { template ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(template.title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Text(template.description, color = TextMuted, fontSize = 9.sp)
                                    }
                                },
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    templateMenuOpen = false
                                    onApplyTemplate(template)
                                }
                            )
                        }
                    }
                }

                // Widget Store Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(NeonViolet.copy(alpha = 0.15f))
                        .border(1.dp, NeonViolet.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenWidgetStore()
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("open_widget_store_button")
                ) {
                    Icon(Icons.Default.Storefront, contentDescription = "Store", tint = NeonViolet, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Store", color = NeonViolet, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                // FCM Threshold Alerts Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(NeonRose.copy(alpha = 0.15f))
                        .border(1.dp, NeonRose.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenThresholdAlerts()
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("open_threshold_alerts_button")
                ) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = "FCM Alerts", tint = NeonRose, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("FCM Alerts", color = NeonRose, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                // AI Assistant Persistent Chat
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(NeonCyan.copy(alpha = 0.15f))
                        .border(1.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenAiAssistant()
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("open_ai_assistant_button")
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = NeonCyan, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("AI Chat", color = NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                // Voice Commands Recorder Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(6.dp))
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenVoiceCommands()
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("open_voice_commands_button")
                ) {
                    Icon(Icons.Default.Mic, contentDescription = "Voice", tint = NeonAmber, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Voice", color = TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                // PDF Export Report Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(6.dp))
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenPdfExport()
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("open_pdf_export_button")
                ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF", tint = NeonRose, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("PDF Report", color = TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                // Demo Mode Toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isDemoModeActive) NeonAmber.copy(alpha = 0.25f) else CyberSurfaceVariant)
                        .border(1.dp, if (isDemoModeActive) NeonAmber else CyberSurfaceBorder, RoundedCornerShape(6.dp))
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onToggleDemoMode()
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("toggle_demo_mode_button")
                ) {
                    Icon(
                        Icons.Default.Science,
                        contentDescription = "Demo Mode",
                        tint = if (isDemoModeActive) NeonAmber else TextSecondary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        if (isDemoModeActive) "DEMO ON" else "Demo",
                        color = if (isDemoModeActive) NeonAmber else TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Activity Log Troubleshooting
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .clickable(onClick = onOpenActivityLog)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("activity_log_button")
                ) {
                    Icon(Icons.Default.History, contentDescription = "Activity Log", tint = NeonCyan, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Activity", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                // Shortcuts Cheat Sheet Button (?)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenShortcutsCheatSheet()
                        }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("keyboard_shortcuts_button")
                ) {
                    Icon(Icons.Default.Keyboard, contentDescription = "Shortcuts", tint = NeonCyan, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("?", color = NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Sub-status bar: Google Auth & Telemetry status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "WS: CONNECTED",
                        color = NeonEmerald,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = " • 18ms",
                        color = TextMuted,
                        fontSize = 9.5.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = " • ${databaseSyncState.label}",
                        color = syncColor,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                if (currentUserEmail != null) {
                    Text(
                        text = currentUserEmail.take(18) + (if (currentUserEmail.length > 18) "..." else ""),
                        color = NeonCyan,
                        fontSize = 9.5.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "Sign in with Google",
                        color = NeonCyan,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable(onClick = onSignInGoogle)
                            .testTag("google_sign_in_button")
                    )
                }
            }
        }
    }
}
