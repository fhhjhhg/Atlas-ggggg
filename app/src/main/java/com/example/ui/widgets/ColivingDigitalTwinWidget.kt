package com.example.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Shield
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
import com.example.data.ColivingBedTwin
import com.example.data.ColivingLeaseStatus
import com.example.ui.DashboardUiState
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
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ColivingDigitalTwinWidget(
    state: DashboardUiState,
    onOpenBedContract: (ColivingBedTwin) -> Unit,
    onOpenHub: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }

    val occupiedCount = state.colivingBeds.count { it.leaseStatus == ColivingLeaseStatus.ACTIVE || it.leaseStatus == ColivingLeaseStatus.RENEWAL_DUE }
    val totalBeds = state.colivingBeds.size.coerceAtLeast(35)
    val occupancyRate = if (totalBeds > 0) (occupiedCount.toDouble() / totalBeds.toDouble()) * 100.0 else 0.0

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("coliving_digital_twin_widget"),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // High-Level Portfolio & Legal Twin Metrics Strip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TwinSummaryCard(
                label = "OCCUPANCY",
                value = "$occupiedCount / $totalBeds",
                subText = String.format(Locale.US, "%.1f%% Trajectory", occupancyRate),
                color = NeonEmerald,
                modifier = Modifier.weight(1f)
            )

            TwinSummaryCard(
                label = "MONTHLY GROSS",
                value = currencyFormat.format(occupiedCount * 830.0),
                subText = "$830/bed all-in",
                color = NeonCyan,
                modifier = Modifier.weight(1f)
            )

            TwinSummaryCard(
                label = "LEGAL FRAMEWORK",
                value = "By-The-Bed",
                subText = "0% Joint Liability",
                color = NeonAmber,
                modifier = Modifier.weight(1f)
            )
        }

        // Everything Coliving Legal Architecture Banner
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
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Protection",
                            tint = NeonCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "EVERYTHING COLIVING™ COMPLIANT SOP",
                            color = NeonCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Button(
                        onClick = onOpenHub,
                        modifier = Modifier
                            .height(28.dp)
                            .testTag("open_coliving_hub_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan.copy(alpha = 0.20f)),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Open Hub",
                            tint = NeonCyan,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Lease Generator",
                            color = NeonCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Recommended: Individual By-The-Bed Occupancy Licenses + House Rules Addendum (Curfew 22:00-08:00, 3-night guest cap, $120 utility cap). Protects cashflow by isolating non-payment risk.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }

        // 35-Bed Twin Matrix Roster
        Text(
            text = "DIGITAL TWIN BED MATRIX (TAP BED FOR LEGAL CONTRACT):",
            color = TextSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            // Show first 6 beds with quick tap, and a button to view all in hub
            state.colivingBeds.take(6).forEach { bed ->
                BedRowItem(
                    bed = bed,
                    onClick = { onOpenBedContract(bed) }
                )
            }
        }

        // View All 35 Beds Button
        Button(
            onClick = onOpenHub,
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .testTag("view_all_coliving_beds_button"),
            colors = ButtonDefaults.buttonColors(containerColor = CyberSurfaceVariant),
            border = BorderStroke(1.dp, CyberSurfaceBorder),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MeetingRoom,
                contentDescription = "All Beds",
                tint = TextPrimary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Open Full 35-Bed Digital Twin Roster & Contracts (${state.colivingBeds.size} Beds)",
                color = TextPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun TwinSummaryCard(
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

@Composable
private fun BedRowItem(
    bed: ColivingBedTwin,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = when (bed.leaseStatus) {
        ColivingLeaseStatus.ACTIVE -> NeonEmerald
        ColivingLeaseStatus.RENEWAL_DUE -> NeonAmber
        ColivingLeaseStatus.PENDING_SIGNATURE -> NeonCyan
        ColivingLeaseStatus.VACANT -> TextMuted
    }

    Surface(
        color = CyberSurface,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("bed_twin_row_${bed.bedId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(statusColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = bed.bedId,
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = bed.occupantName,
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "${bed.roomName} • ${bed.leaseType.displayName}",
                        color = TextMuted,
                        fontSize = 9.sp
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$${bed.monthlyRateUsd.toInt()}/mo",
                        color = NeonEmerald,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = bed.leaseStatus.label,
                        color = statusColor,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
