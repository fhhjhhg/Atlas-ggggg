package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.PromptTestScenario
import com.example.data.SmartRecommendationCategory
import com.example.ui.DashboardUiState
import com.example.ui.NexusDashboardViewModel
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
fun GeminiPromptSettingsDialog(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel,
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("System Prompt Editor", "Testing Sandbox")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, CyberSurfaceBorder), RoundedCornerShape(16.dp))
                .testTag("dialog_gemini_prompt_settings"),
            color = CyberBg
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CyberSurface)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
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
                            Icon(Icons.Default.Tune, contentDescription = "Settings", tint = NeonViolet, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "GEMINI ENGINE SYSTEM PROMPT & SANDBOX",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Customize Widget Recommendation & Metric Analysis Logic",
                                fontSize = 10.sp,
                                color = TextMuted
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("btn_close_prompt_settings")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                // Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = CyberSurfaceVariant,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = NeonViolet
                        )
                    }
                ) {
                    tabs.forEachIndexed { idx, title ->
                        Tab(
                            selected = selectedTab == idx,
                            onClick = { selectedTab = idx },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = if (selectedTab == idx) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == idx) NeonViolet else TextMuted
                                )
                            }
                        )
                    }
                }

                // Tab Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    if (selectedTab == 0) {
                        // System Prompt Editor Tab
                        Text(
                            text = "SMART RECOMMENDATION CATEGORY FILTERING:",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SmartRecommendationCategory.values().forEach { cat ->
                                FilterChip(
                                    selected = state.recommendationFilter == cat,
                                    onClick = { viewModel.setRecommendationFilter(cat) },
                                    label = { Text(cat.label, fontSize = 10.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                                        selectedLabelColor = NeonCyan
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "CUSTOM GEMINI SYSTEM PROMPT:",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonViolet,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = state.customGeminiSystemPrompt,
                            onValueChange = { viewModel.updateCustomGeminiSystemPrompt(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .testTag("input_custom_system_prompt"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonViolet,
                                unfocusedBorderColor = CyberSurfaceBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedContainerColor = CyberSurface,
                                unfocusedContainerColor = CyberSurface
                            ),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.resetGeminiSystemPromptToDefault() },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, CyberSurfaceBorder)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.size(14.dp), tint = TextMuted)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("RESET TO DEFAULT", fontSize = 10.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { viewModel.applyPromptToProduction() },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = NeonEmerald.copy(alpha = 0.25f),
                                    contentColor = NeonEmerald
                                ),
                                border = BorderStroke(1.dp, NeonEmerald),
                                modifier = Modifier.testTag("btn_apply_prompt_to_production")
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Apply", modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("APPLY TO PRODUCTION", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        // Testing Sandbox Tab
                        Text(
                            text = "SELECT SIMULATION SCENARIO:",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonAmber,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        state.testScenarios.forEach { scenario ->
                            val isSelected = state.selectedTestScenario?.id == scenario.id
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) NeonAmber.copy(alpha = 0.12f) else CyberSurface,
                                border = BorderStroke(1.dp, if (isSelected) NeonAmber else CyberSurfaceBorder),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = scenario.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                        Text(text = scenario.description, fontSize = 9.5.sp, color = TextSecondary)
                                    }
                                    Button(
                                        onClick = { viewModel.selectTestScenario(scenario) },
                                        shape = RoundedCornerShape(6.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSelected) NeonAmber else CyberSurfaceVariant,
                                            contentColor = if (isSelected) Color.Black else TextPrimary
                                        ),
                                        modifier = Modifier.height(28.dp)
                                    ) {
                                        Text(if (isSelected) "SELECTED" else "SELECT", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { viewModel.runPromptTestSimulation() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_run_prompt_simulation"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonViolet.copy(alpha = 0.25f),
                                contentColor = NeonViolet
                            ),
                            border = BorderStroke(1.dp, NeonViolet)
                        ) {
                            if (state.isTestingPrompt) {
                                CircularProgressIndicator(color = NeonViolet, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("RUNNING GEMINI SIMULATION...", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            } else {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Run", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("RUN TEST SIMULATION ON SCENARIO", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Evaluation Results Card
                        if (state.lastPromptEvaluation != null) {
                            val eval = state.lastPromptEvaluation
                            Spacer(modifier = Modifier.height(14.dp))

                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                                border = BorderStroke(1.dp, NeonEmerald.copy(alpha = 0.4f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "SIMULATION RESULTS: ${eval.scenarioName}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = NeonEmerald
                                        )
                                        Text(
                                            text = "${eval.executionLatencyMs}ms • ~${eval.estimatedTokens} tokens",
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily.Monospace,
                                            color = TextMuted
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = eval.analysisExecutiveSummary,
                                        fontSize = 10.sp,
                                        color = TextSecondary,
                                        lineHeight = 14.sp
                                    )

                                    if (eval.identifiedAnomalies.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(text = "Detected Anomalies: ${eval.identifiedAnomalies.joinToString()}", fontSize = 9.sp, color = NeonRose)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
