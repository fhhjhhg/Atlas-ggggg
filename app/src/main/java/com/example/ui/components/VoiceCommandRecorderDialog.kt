package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.VoiceActionType
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
fun VoiceCommandRecorderDialog(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var newTriggerPhrase by remember { mutableStateOf("") }
    var selectedActionType by remember { mutableStateOf(VoiceActionType.RUN_WEEKLY_REPORT) }
    var customDescription by remember { mutableStateOf("") }
    var testSimulatedSpeech by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val micScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mic_scale"
    )

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
                .testTag("dialog_voice_commands_recorder"),
            color = CyberBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
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
                                .scale(micScale)
                                .clip(CircleShape)
                                .background(NeonRose.copy(alpha = 0.15f))
                                .border(1.dp, NeonRose.copy(alpha = 0.5f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Mic, contentDescription = "Voice", tint = NeonRose, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "GEMINI VOICE COMMAND RECORDER & TRIGGERS",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Execute Complex Workflows through Audio Commands",
                                fontSize = 10.sp,
                                color = TextMuted
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("btn_close_voice_dialog")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quick Speech Test Section
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    border = BorderStroke(1.dp, NeonRose.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "SPEAK OR SIMULATE AUDIO COMMAND:",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonRose,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = testSimulatedSpeech,
                            onValueChange = { testSimulatedSpeech = it },
                            placeholder = { Text("e.g. 'run a weekly report' or 'plot forecasting chart'", fontSize = 11.sp, color = TextMuted) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_speech_command"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonRose,
                                unfocusedBorderColor = CyberSurfaceBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedContainerColor = CyberBg,
                                unfocusedContainerColor = CyberBg
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf("run a weekly report", "plot forecasting chart", "export executive pdf", "refresh live telemetry").forEach { chip ->
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = CyberSurfaceVariant,
                                        border = BorderStroke(0.5.dp, CyberSurfaceBorder),
                                        modifier = Modifier.clickable {
                                            testSimulatedSpeech = chip
                                            viewModel.executeVoiceCommand(chip, context)
                                        }
                                    ) {
                                        Text(
                                            text = "\"$chip\"",
                                            fontSize = 9.sp,
                                            fontFamily = FontFamily.Monospace,
                                            color = NeonCyan,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    if (testSimulatedSpeech.isNotBlank()) {
                                        viewModel.executeVoiceCommand(testSimulatedSpeech, context)
                                    }
                                },
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = NeonRose.copy(alpha = 0.25f),
                                    contentColor = NeonRose
                                ),
                                border = BorderStroke(1.dp, NeonRose),
                                modifier = Modifier
                                    .height(30.dp)
                                    .testTag("btn_execute_voice_trigger")
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Run", modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("TRIGGER", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Saved Voice Commands List
                Text(
                    text = "SAVED VOICE TRIGGERS (${state.voiceCommands.size}):",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                state.voiceCommands.forEach { cmd ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CyberSurface,
                        border = BorderStroke(0.5.dp, CyberSurfaceBorder),
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
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.RecordVoiceOver, contentDescription = "Voice", tint = NeonCyan, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "\"${cmd.triggerPhrase}\"",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${cmd.actionType.label} • Executed ${cmd.executionCount}x",
                                        fontSize = 9.sp,
                                        color = TextSecondary
                                    )
                                    Text(
                                        text = cmd.description,
                                        fontSize = 8.5.sp,
                                        color = TextMuted
                                    )
                                }
                            }

                            Button(
                                onClick = { viewModel.executeVoiceCommand(cmd.triggerPhrase, context) },
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = NeonEmerald.copy(alpha = 0.2f),
                                    contentColor = NeonEmerald
                                ),
                                modifier = Modifier.height(28.dp)
                            ) {
                                Text("RUN", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Record New Voice Trigger Form
                Text(
                    text = "RECORD NEW VOICE TRIGGER:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonAmber,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    border = BorderStroke(1.dp, CyberSurfaceBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        OutlinedTextField(
                            value = newTriggerPhrase,
                            onValueChange = { newTriggerPhrase = it },
                            placeholder = { Text("Spoken Trigger Phrase (e.g. 'lock the doors')", fontSize = 11.sp, color = TextMuted) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_new_voice_phrase"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonAmber,
                                unfocusedBorderColor = CyberSurfaceBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedContainerColor = CyberBg,
                                unfocusedContainerColor = CyberBg
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "ASSIGN TO WORKFLOW ACTION:", fontSize = 9.sp, color = TextMuted)
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            VoiceActionType.values().forEach { action ->
                                FilterChip(
                                    selected = selectedActionType == action,
                                    onClick = { selectedActionType = action },
                                    label = { Text(action.label, fontSize = 9.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = NeonAmber.copy(alpha = 0.2f),
                                        selectedLabelColor = NeonAmber
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                if (newTriggerPhrase.isNotBlank()) {
                                    viewModel.addCustomVoiceCommand(
                                        phrase = newTriggerPhrase,
                                        actionType = selectedActionType,
                                        description = "Custom voice trigger mapped to ${selectedActionType.label}"
                                    )
                                    newTriggerPhrase = ""
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonAmber.copy(alpha = 0.25f),
                                contentColor = NeonAmber
                            ),
                            border = BorderStroke(1.dp, NeonAmber),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_save_voice_trigger")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Save", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("SAVE VOICE COMMAND", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
