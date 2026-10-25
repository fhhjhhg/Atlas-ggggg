package com.example.ui.widgets

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.DashboardUiState
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
import java.util.Locale

@Composable
fun ArenaScoreWidget(
    state: DashboardUiState,
    modifier: Modifier = Modifier
) {
    val animatedScore by animateFloatAsState(
        targetValue = state.compositeArenaScore,
        animationSpec = tween(800),
        label = "arenaScoreAnim"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("arena_score_widget")
    ) {
        // Formula Banner
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = CyberSurfaceVariant.copy(alpha = 0.5f),
            border = androidx.compose.foundation.BorderStroke(1.dp, CyberSurfaceBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "FORMULA: 0.30·ROI + 0.25·Stab + 0.15·Safe + 0.15·Proof + 0.15·Ship",
                    color = NeonCyan,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "SYS: OPTIMAL",
                    color = NeonEmerald,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Large composite score display + 5 sub-pillars
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular Score Box
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(CyberSurfaceVariant)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format(Locale.US, "%.1f", animatedScore),
                        color = NeonCyan,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "ARENASCORE",
                        color = TextSecondary,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Sub-metrics Progress Bars
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ScoreBarItem(
                    label = "ROI (30%)",
                    score = state.arenaScoreRoi,
                    color = NeonEmerald
                )
                ScoreBarItem(
                    label = "Stability (25%)",
                    score = state.arenaScoreStability,
                    color = NeonCyan
                )
                ScoreBarItem(
                    label = "Safety (15%)",
                    score = state.arenaScoreSafety,
                    color = NeonViolet
                )
                ScoreBarItem(
                    label = "Proof (15%)",
                    score = state.arenaScoreProof,
                    color = NeonAmber
                )
                ScoreBarItem(
                    label = "Shipping (15%)",
                    score = state.arenaScoreShipping,
                    color = NeonRose
                )
            }
        }
    }
}

@Composable
private fun ScoreBarItem(
    label: String,
    score: Float,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.width(90.dp)
        )

        LinearProgressIndicator(
            progress = { score },
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = CyberSurfaceBorder
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "${(score * 100).toInt()}%",
            color = TextPrimary,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(32.dp)
        )
    }
}
