package com.example.ui.widgets

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.DashboardUiState
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
fun EmergentResearchWidget(
    state: DashboardUiState,
    onOpenHub: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "heartbeat")
    val heartbeatScale by infiniteTransition.animateFloat(
        initialValue = 0.90f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (60_000 / state.heartbeatSoul.currentBpm).coerceIn(300, 1500), easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartbeat_scale"
    )

    val topPaper = state.emergentPapers.firstOrNull()
    val topTutorial = state.tutorialModules.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CyberSurface)
            .border(BorderStroke(1.dp, CyberSurfaceBorder), RoundedCornerShape(14.dp))
            .padding(16.dp)
            .testTag("widget_emergent_research")
    ) {
        // Header Row with Heartbeat Pulse & Live BPM
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
                        .background(NeonViolet.copy(alpha = 0.15f))
                        .border(1.dp, NeonViolet.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "Research Hub",
                        tint = NeonViolet,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "EMERGENT RESEARCH & POCKETFLOW",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = "Works Cited • Zero-to-Hero • Bio-Evo • Drones",
                        fontSize = 10.sp,
                        color = TextMuted
                    )
                }
            }

            // Live Heartbeat Pulse Pill
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = NeonRose.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, NeonRose.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Heartbeat",
                        tint = NeonRose,
                        modifier = Modifier
                            .size(13.dp)
                            .scale(heartbeatScale)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${state.heartbeatSoul.currentBpm} BPM",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = NeonRose
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4 KPI Stat Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResearchStatCard(
                label = "Emergent Papers",
                value = "${state.emergentPapers.size}",
                subtext = "arXiv Cited",
                color = NeonCyan,
                modifier = Modifier.weight(1f)
            )
            ResearchStatCard(
                label = "Code Modules",
                value = "${state.tutorialModules.size}",
                subtext = "Zero to Hero",
                color = NeonEmerald,
                modifier = Modifier.weight(1f)
            )
            ResearchStatCard(
                label = "Frameworks",
                value = "${state.frameworkToolkits.size}",
                subtext = "Awesome SDKs",
                color = NeonAmber,
                modifier = Modifier.weight(1f)
            )
            ResearchStatCard(
                label = "Bioreactors",
                value = "${state.bioreactors.size}",
                subtext = "Evo Lab Active",
                color = NeonViolet,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Trending Paper Spotlight Card (Emergent Mind Inspired)
        if (topPaper != null) {
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = NeonCyan.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "TRENDING ON EMERGENT MIND",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = topPaper.arxivId,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = TextMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = topPaper.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = topPaper.abstractText,
                        fontSize = 10.sp,
                        color = TextSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            topPaper.topics.take(3).forEach { topic ->
                                Surface(
                                    shape = RoundedCornerShape(3.dp),
                                    color = CyberSurface,
                                    border = BorderStroke(0.5.dp, CyberSurfaceBorder)
                                ) {
                                    Text(
                                        text = topic,
                                        fontSize = 8.sp,
                                        color = TextMuted,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = "${topPaper.worksCitedCount} Works Cited",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = NeonAmber
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // PocketFlow Zero-to-Hero Quick Code Card
        if (topTutorial != null) {
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Code Tutorial",
                            tint = NeonEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = topTutorial.title,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${topTutorial.durationMinutes} min • ${topTutorial.tasks.size} steps • ${topTutorial.notebookFilename}",
                                fontSize = 9.sp,
                                color = TextMuted
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = NeonEmerald.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = topTutorial.level.label,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonEmerald,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Open Full Workstation Button
        Button(
            onClick = onOpenHub,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("btn_open_emergent_research_hub"),
            colors = ButtonDefaults.buttonColors(
                containerColor = NeonViolet.copy(alpha = 0.25f),
                contentColor = NeonViolet
            ),
            border = BorderStroke(1.dp, NeonViolet.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Launch,
                contentDescription = "Open Hub",
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "OPEN RESEARCH & POCKETFLOW WORKSTATION",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun ResearchStatCard(
    label: String,
    value: String,
    subtext: String,
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
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = color
            )
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtext,
                fontSize = 8.sp,
                color = TextMuted,
                maxLines = 1
            )
        }
    }
}
