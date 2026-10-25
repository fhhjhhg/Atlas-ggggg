package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.AssistantChatMessage
import com.example.data.SmartActionItem
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
fun AiAssistantChatSheet(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    val quickQuestions = listOf(
        "What is our gross revenue trajectory?",
        "Plot a linear regression forecasting chart",
        "Export an executive PDF performance audit",
        "Check live telemetry anomalies"
    )

    LaunchedEffect(state.assistantChatMessages.size) {
        if (state.assistantChatMessages.isNotEmpty()) {
            listState.animateScrollToItem(state.assistantChatMessages.size - 1)
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, CyberSurfaceBorder), RoundedCornerShape(16.dp))
                .testTag("dialog_ai_assistant_chat"),
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
                                .border(1.dp, NeonViolet, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SmartToy, contentDescription = "AI", tint = NeonViolet, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "PERSISTENT AI WORKSTATION ASSISTANT",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Powered by Firebase Gemini 3.8 Flash • Real-Time Metrics Context",
                                fontSize = 9.5.sp,
                                color = TextMuted
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("btn_close_ai_chat")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                // Quick Prompt Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CyberSurfaceVariant)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickQuestions.forEach { prompt ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CyberSurface,
                            border = BorderStroke(0.5.dp, CyberSurfaceBorder),
                            modifier = Modifier.clickable {
                                viewModel.sendChatMessage(prompt)
                            }
                        ) {
                            Text(
                                text = prompt,
                                fontSize = 9.sp,
                                color = NeonCyan,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Chat Messages List
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.assistantChatMessages, key = { it.id }) { msg ->
                        ChatMessageBubble(
                            message = msg,
                            onExecuteAction = { action -> viewModel.executeSmartAction(action, context) }
                        )
                    }

                    if (state.isAssistantThinking) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                CircularProgressIndicator(color = NeonViolet, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Gemini is analyzing workstation telemetry...",
                                    fontSize = 10.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }

                // Input Bar
                Surface(
                    color = CyberSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = { Text("Ask about metrics, forecasting, or commands...", fontSize = 11.sp, color = TextMuted) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_ai_chat_query"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonViolet,
                                unfocusedBorderColor = CyberSurfaceBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedContainerColor = CyberBg,
                                unfocusedContainerColor = CyberBg
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    viewModel.sendChatMessage(inputText)
                                    inputText = ""
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonViolet.copy(alpha = 0.25f),
                                contentColor = NeonViolet
                            ),
                            border = BorderStroke(1.dp, NeonViolet),
                            modifier = Modifier
                                .height(48.dp)
                                .testTag("btn_send_ai_chat")
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "Send", modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatMessageBubble(
    message: AssistantChatMessage,
    onExecuteAction: (SmartActionItem) -> Unit
) {
    val isUser = message.sender == "USER"

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 2.dp)
        ) {
            Text(
                text = if (isUser) "Operator (You)" else "Gemini 3.8 Flash",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (isUser) NeonCyan else NeonViolet
            )
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = if (isUser) 10.dp else 2.dp,
                bottomEnd = if (isUser) 2.dp else 10.dp
            ),
            color = if (isUser) CyberSurfaceVariant else CyberSurface,
            border = BorderStroke(1.dp, if (isUser) CyberSurfaceBorder else NeonViolet.copy(alpha = 0.3f)),
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = message.text,
                    fontSize = 11.5.sp,
                    color = TextPrimary,
                    lineHeight = 16.sp
                )

                // Optional Smart Action Attached to Bot Reply
                if (message.actionSuggestion != null) {
                    val action = message.actionSuggestion
                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = CyberBg,
                        border = BorderStroke(0.5.dp, NeonEmerald.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onExecuteAction(action) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = when (action.actionType) {
                                        com.example.data.VoiceActionType.SHOW_FORECASTING -> Icons.Default.ShowChart
                                        com.example.data.VoiceActionType.EXPORT_PDF_REPORT -> Icons.Default.PictureAsPdf
                                        else -> Icons.Default.PlayArrow
                                    },
                                    contentDescription = "Smart Action",
                                    tint = NeonEmerald,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = action.label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeonEmerald
                                )
                            }
                            Text(
                                text = "EXECUTE ➔",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonEmerald
                            )
                        }
                    }
                }
            }
        }
    }
}
