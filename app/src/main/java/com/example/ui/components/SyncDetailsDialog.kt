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
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Storage
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.DatabaseSyncState
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
fun SyncDetailsDialog(
    syncState: DatabaseSyncState,
    lastSyncTimestamp: Long,
    roomRecordsCount: Int,
    currentUserEmail: String?,
    onForceSync: () -> Unit,
    onDismiss: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    val statusColor = when (syncState) {
        DatabaseSyncState.SYNCED -> NeonEmerald
        DatabaseSyncState.SYNCING -> NeonAmber
        DatabaseSyncState.LOCAL_CACHE -> NeonCyan
        DatabaseSyncState.SYNC_ERROR -> NeonRose
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(20.dp))
                .testTag("sync_details_dialog"),
            color = CyberBg,
            border = BorderStroke(1.dp, CyberSurfaceBorder)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
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
                                .background(statusColor.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (syncState) {
                                    DatabaseSyncState.SYNCED -> Icons.Default.CloudDone
                                    DatabaseSyncState.SYNCING -> Icons.Default.CloudSync
                                    else -> Icons.Default.Cloud
                                },
                                contentDescription = "Sync Status",
                                tint = statusColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "ROOM ➔ FIRESTORE SYNC",
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = syncState.label,
                                color = statusColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Diagnostic Specs Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, CyberSurfaceBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        SyncMetricRow(
                            label = "Local Storage Engine",
                            value = "Android Room SQLite (DAO)",
                            icon = Icons.Default.Storage,
                            accentColor = NeonCyan
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SyncMetricRow(
                            label = "Cached Telemetry Records",
                            value = "$roomRecordsCount records",
                            icon = Icons.Default.Storage,
                            accentColor = NeonEmerald
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SyncMetricRow(
                            label = "Cloud Backend",
                            value = "Firebase Cloud Firestore",
                            icon = Icons.Default.Cloud,
                            accentColor = NeonCyan
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SyncMetricRow(
                            label = "Last Synchronized",
                            value = timeFormat.format(Date(lastSyncTimestamp)),
                            icon = Icons.Default.CloudDone,
                            accentColor = statusColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SyncMetricRow(
                            label = "Authenticated Operator",
                            value = currentUserEmail ?: "Guest / Local Offline Session",
                            icon = Icons.Default.Cloud,
                            accentColor = if (currentUserEmail != null) NeonEmerald else TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Force Sync Button
                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onForceSync()
                    },
                    enabled = syncState != DatabaseSyncState.SYNCING,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("force_sync_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Sync",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (syncState == DatabaseSyncState.SYNCING) "Synchronizing..." else "Force Cloud Sync Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SyncMetricRow(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(label, color = TextSecondary, fontSize = 11.sp)
        }
        Text(
            value,
            color = TextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
