package com.example.ui.widgets

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.DashboardUiState
import com.example.ui.theme.CyberBg
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonViolet
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AgentEnclaveWidget(
    state: DashboardUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("agent_enclave_widget"),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Enclave Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "EDGE RUNTIMES & SOUL ARCHITECTURE",
                color = NeonCyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "SANDBOX: ISOLATED",
                color = NeonEmerald,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }

        // Kai 9000 Android APK Node
        EnclaveNodeItem(
            nodeTitle = "Kai 9000 Android Enclave",
            runtime = "Termux / Alpine Minirootfs (3MB)",
            metrics = "LiteRT + llama.cpp • whisper.cpp Offline Audio",
            status = "ARM64 Running",
            accentColor = NeonCyan
        )

        // OpenClaw SOUL Persona
        EnclaveNodeItem(
            nodeTitle = "OpenClaw & GhostWork SOUL.md",
            runtime = "Persona Architecture v6.0",
            metrics = "WAL Protocol Buffer • ADR-010 Memory Isolation",
            status = "Zero Loss Synced",
            accentColor = NeonViolet
        )

        // OpenLobster Hardened Go Node
        EnclaveNodeItem(
            nodeTitle = "OpenLobster Runtime (Go Core)",
            runtime = "Cold Start: <200ms • RAM: <30MB",
            metrics = "GraphQL Endpoints • Neo4j Spatial Graph Engine",
            status = "Low Latency",
            accentColor = NeonEmerald
        )
    }
}

@Composable
private fun EnclaveNodeItem(
    nodeTitle: String,
    runtime: String,
    metrics: String,
    status: String,
    accentColor: androidx.compose.ui.graphics.Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = CyberBg,
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberSurfaceBorder.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = nodeTitle,
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = status,
                    color = accentColor,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = runtime,
                color = NeonCyan,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )

            Text(
                text = metrics,
                color = TextSecondary,
                fontSize = 9.sp
            )
        }
    }
}
