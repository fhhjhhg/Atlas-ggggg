package com.example.ui.components

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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.window.Dialog
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
fun DashboardFeatureTour(
    currentStep: Int,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onDismiss: () -> Unit
) {
    val steps = listOf(
        TourStepInfo(
            title = "Modular Widget Store & Customizer",
            subtitle = "Step 1 of 4 • Tailor Your Workstation",
            description = "Toggle and customize over 18 production widgets across Executive KPIs, Room OLS Forecasting, Emergent Research, and Bio-Evo pipelines to match your cyber-physical workflow.",
            icon = Icons.Default.DashboardCustomize,
            color = NeonCyan,
            shortcut = "Tap 'Customize' in TopBar"
        ),
        TourStepInfo(
            title = "Drag-and-Drop & Workspaces",
            subtitle = "Step 2 of 4 • Instant Layout Presets",
            description = "Easily reorder widgets using the Move Up/Down controls or switch instantly between curated Workspaces ('Executive', 'Analyst', 'DevOps', 'FinOps', 'Bio-Evo').",
            icon = Icons.Default.DragHandle,
            color = NeonEmerald,
            shortcut = "Workspace Pills on Home Dashboard"
        ),
        TourStepInfo(
            title = "Voice Commands & Audio Triggers",
            subtitle = "Step 3 of 4 • Hands-Free Operation",
            description = "Speak or save short voice triggers like 'Run a weekly report', 'Plot forecasting chart', or 'Export executive PDF' to execute complex multi-step operations hands-free.",
            icon = Icons.Default.Mic,
            color = NeonRose,
            shortcut = "Tap Mic icon in TopBar"
        ),
        TourStepInfo(
            title = "Power-User Keyboard Shortcuts",
            subtitle = "Step 4 of 4 • Lightning-Fast Navigation",
            description = "Use hardware shortcuts: Ctrl+S (Save layout), Ctrl+F (Focus Search), Ctrl+P (PDF Report), Ctrl+R (Refresh), Ctrl+D (Demo Mode), Ctrl+Space (AI Assistant), and '?' for Cheat Sheet.",
            icon = Icons.Default.Keyboard,
            color = NeonAmber,
            shortcut = "Ctrl+S  |  Ctrl+F  |  Ctrl+P  |  Ctrl+R"
        )
    )

    val stepInfo = steps.getOrElse(currentStep) { steps.first() }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CyberBg),
            border = BorderStroke(1.dp, stepInfo.color.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("dialog_feature_tour")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header with Step Pill & Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = stepInfo.color.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, stepInfo.color.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = stepInfo.subtitle.uppercase(),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = stepInfo.color,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Icon + Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(stepInfo.color.copy(alpha = 0.15f))
                            .border(1.dp, stepInfo.color, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = stepInfo.icon,
                            contentDescription = stepInfo.title,
                            tint = stepInfo.color,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = stepInfo.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = stepInfo.shortcut,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = stepInfo.color
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stepInfo.description,
                    fontSize = 11.5.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Progress Dots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    steps.indices.forEach { index ->
                        Box(
                            modifier = Modifier
                                .size(if (index == currentStep) 8.dp else 6.dp)
                                .clip(CircleShape)
                                .background(if (index == currentStep) stepInfo.color else CyberSurfaceBorder)
                        )
                        if (index < steps.size - 1) {
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Bottom Navigation Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentStep > 0) {
                        OutlinedButton(
                            onClick = onPrevious,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, CyberSurfaceBorder),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("BACK", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CyberSurfaceVariant, contentColor = TextMuted),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("SKIP", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = {
                            if (currentStep < steps.size - 1) onNext()
                            else onDismiss()
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = stepInfo.color.copy(alpha = 0.25f),
                            contentColor = stepInfo.color
                        ),
                        border = BorderStroke(1.dp, stepInfo.color),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("btn_tour_next_or_finish")
                    ) {
                        Text(
                            text = if (currentStep < steps.size - 1) "NEXT ➔" else "GOT IT!",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private data class TourStepInfo(
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color,
    val shortcut: String
)
