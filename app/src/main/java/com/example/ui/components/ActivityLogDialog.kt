package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ai.ActivityLogAnalysis
import com.example.data.DashboardActivity
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
fun ActivityLogDialog(
    activities: List<DashboardActivity>,
    isAnalyzing: Boolean,
    analysis: ActivityLogAnalysis?,
    analysisError: String?,
    onAnalyzeWithGemini: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.90f)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, CyberSurfaceBorder), RoundedCornerShape(16.dp))
                .testTag("activity_log_dialog"),
            color = CyberBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(NeonCyan.copy(alpha = 0.15f))
                                .border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "Activity Log",
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "ACTIVITY LOG",
                                    color = TextPrimary,
                                    fontSize = 15.sp,
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
                                        text = "${activities.size} / 20",
                                        color = NeonEmerald,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                            Text(
                                text = "Recent dashboard actions for troubleshooting",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Copy to clipboard button
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val logText = activities.joinToString("\n") {
                                    "[${it.formattedTime}] [${it.category}] ${it.action}"
                                }
                                val clip = ClipData.newPlainText("Nexus Activity Log", logText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Activity log copied to clipboard", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("copy_activity_log_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy Activity Log",
                                tint = TextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Close Dialog button
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("close_activity_log_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Activity Log",
                                tint = TextPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Gemini AI Troubleshooter Banner / Action
                GeminiTroubleshooterCard(
                    isAnalyzing = isAnalyzing,
                    analysis = analysis,
                    analysisError = analysisError,
                    onAnalyze = onAnalyzeWithGemini
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Activities List (Last 20 actions)
                Text(
                    text = "ACTION STREAM (NEWEST FIRST)",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 0.8.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (activities.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CyberSurface)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No activities recorded yet.",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .testTag("activity_log_list"),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(activities, key = { it.id }) { activity ->
                            ActivityRowItem(activity = activity)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom Dismiss Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("dismiss_activity_log_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = CyberSurfaceVariant),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, CyberSurfaceBorder)
                ) {
                    Text(
                        text = "Close Activity Log",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityRowItem(
    activity: DashboardActivity,
    modifier: Modifier = Modifier
) {
    val (badgeColor, badgeBg) = when (activity.category.uppercase()) {
        "WIDGET" -> Pair(NeonCyan, NeonCyan.copy(alpha = 0.12f))
        "SYNC" -> Pair(NeonEmerald, NeonEmerald.copy(alpha = 0.12f))
        "SETTINGS" -> Pair(NeonViolet, NeonViolet.copy(alpha = 0.12f))
        "DIAGNOSTIC" -> Pair(NeonRose, NeonRose.copy(alpha = 0.12f))
        "AI" -> Pair(NeonAmber, NeonAmber.copy(alpha = 0.12f))
        else -> Pair(TextSecondary, CyberSurfaceVariant)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("activity_item_${activity.id}"),
        color = CyberSurface,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, CyberSurfaceBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(badgeBg)
                    .border(1.dp, badgeColor.copy(alpha = 0.35f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = activity.category.uppercase(),
                    color = badgeColor,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Action Text
            Text(
                text = activity.action,
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Formatted Time
            Text(
                text = activity.formattedTime,
                color = TextSecondary,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun GeminiTroubleshooterCard(
    isAnalyzing: Boolean,
    analysis: ActivityLogAnalysis?,
    analysisError: String?,
    onAnalyze: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, NeonViolet.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Gemini AI",
                        tint = NeonViolet,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "GEMINI 3.8 FLASH DIAGNOSTICS",
                        color = NeonViolet,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp
                    )
                }

                Button(
                    onClick = onAnalyze,
                    enabled = !isAnalyzing,
                    modifier = Modifier
                        .height(32.dp)
                        .testTag("analyze_activity_log_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonViolet.copy(alpha = 0.25f),
                        disabledContainerColor = CyberSurfaceVariant
                    ),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, NeonViolet.copy(alpha = 0.6f))
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(
                            color = NeonViolet,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Analyzing...",
                            color = NeonViolet,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = if (analysis != null) "Re-Analyze" else "Troubleshoot",
                            color = TextPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Error display
            if (analysisError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(NeonRose.copy(alpha = 0.15f))
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Analysis Error",
                        tint = NeonRose,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = analysisError,
                        color = NeonRose,
                        fontSize = 11.sp
                    )
                }
            }

            // Analysis Result
            if (analysis != null) {
                Spacer(modifier = Modifier.height(10.dp))

                // Health rating banner
                val (ratingColor, ratingBg) = when (analysis.healthRating.uppercase()) {
                    "OPTIMAL" -> Pair(NeonEmerald, NeonEmerald.copy(alpha = 0.15f))
                    "DEGRADED" -> Pair(NeonAmber, NeonAmber.copy(alpha = 0.15f))
                    "CRITICAL" -> Pair(NeonRose, NeonRose.copy(alpha = 0.15f))
                    else -> Pair(NeonCyan, NeonCyan.copy(alpha = 0.15f))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(ratingBg)
                        .border(1.dp, ratingColor.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "HEALTH: ${analysis.healthRating.uppercase()}",
                        color = ratingColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "AI Verified",
                        color = ratingColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Summary
                Text(
                    text = analysis.summary,
                    color = TextPrimary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )

                // Anomalies
                if (analysis.detectedAnomalies.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ANOMALIES DETECTED:",
                        color = NeonAmber,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    analysis.detectedAnomalies.forEach { anomaly ->
                        Text(
                            text = "• $anomaly",
                            color = TextSecondary,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }
                }

                // Recommendations
                if (analysis.recommendations.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "RECOMMENDED ACTIONS:",
                        color = NeonCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    analysis.recommendations.forEach { rec ->
                        Text(
                            text = "✓ $rec",
                            color = TextPrimary,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }
                }
            } else if (!isAnalyzing && analysisError == null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tap 'Troubleshoot' to invoke Gemini 3.8 Flash for instant automated root-cause analysis of your last 20 dashboard activities.",
                    color = TextMuted,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}
