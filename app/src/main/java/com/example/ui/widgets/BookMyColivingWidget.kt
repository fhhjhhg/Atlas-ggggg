package com.example.ui.widgets

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.data.ColivingSpaceListing
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

@Composable
fun BookMyColivingWidget(
    state: DashboardUiState,
    onOpenHub: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("book_my_coliving_widget"),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // High-Level Global Metric Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ColivStatCard(
                label = "GLOBAL SPACES",
                value = "${state.colivingListings.size}",
                subText = "5 Hubs Worldwide",
                color = NeonCyan,
                modifier = Modifier.weight(1f)
            )

            ColivStatCard(
                label = "COMMISSION",
                value = "0.0%",
                subText = "Direct Operator",
                color = NeonEmerald,
                modifier = Modifier.weight(1f)
            )

            ColivStatCard(
                label = "DXI MULTIPLIER",
                value = "1000x",
                subText = "Autonomous Stack",
                color = NeonViolet,
                modifier = Modifier.weight(1f)
            )
        }

        // Feature Spotlight Card
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberSurface),
            border = BorderStroke(1.dp, CyberSurfaceBorder),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Apartment,
                            contentDescription = "BookMyColiving",
                            tint = NeonEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "BOOKMYCOLIVING DIRECT DISCOVERY",
                            color = NeonEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(NeonCyan.copy(alpha = 0.2f))
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "https://bookmycoliving.com",
                            color = NeonCyan,
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "A zero-commission global coliving directory connecting digital nomads, AI founders, and researchers directly with verified property operators. Integrated with our Dual-LLC Master Leases, Hardware BOM, and MCP Subagent pipelines.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Top Spaces Preview
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    state.colivingListings.take(3).forEach { space ->
                        MiniSpaceRow(space = space)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onOpenHub,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald.copy(alpha = 0.22f)),
                    border = BorderStroke(1.dp, NeonEmerald),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .testTag("open_book_my_coliving_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Launch,
                        contentDescription = "Launch",
                        tint = NeonEmerald,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Open BookMyColiving Workstation & 1000x DXI Stack",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun MiniSpaceRow(space: ColivingSpaceListing) {
    Surface(
        color = CyberSurfaceVariant,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = space.name,
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified",
                        tint = NeonEmerald,
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    text = "${space.city} • ${space.vibe.displayName}",
                    color = TextMuted,
                    fontSize = 9.sp
                )
            }

            Text(
                text = "$${space.monthlyRateUsd.toInt()}/mo",
                color = NeonCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun ColivStatCard(
    label: String,
    value: String,
    subText: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CyberSurface,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                color = TextMuted,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = value,
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = subText,
                color = TextSecondary,
                fontSize = 8.sp,
                maxLines = 1
            )
        }
    }
}
