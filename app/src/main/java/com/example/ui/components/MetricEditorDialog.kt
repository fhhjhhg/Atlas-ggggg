package com.example.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.UserPerformanceMetric
import com.example.ui.theme.CyberBg
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceBorder
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonRose
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MetricEditorDialog(
    metrics: List<UserPerformanceMetric>,
    onAddMetric: (name: String, currentValue: Double, unit: String, category: String) -> Unit,
    onDeleteMetric: (Long) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nameInput by remember { mutableStateOf("") }
    var valueInput by remember { mutableStateOf("") }
    var unitInput by remember { mutableStateOf("") }
    var categoryInput by remember { mutableStateOf("CUSTOM") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                .testTag("metric_editor_dialog"),
            color = CyberBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(NeonEmerald.copy(alpha = 0.15f))
                                .border(1.dp, NeonEmerald.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "Performance Metrics",
                                tint = NeonEmerald,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "USER PERFORMANCE METRICS",
                                color = TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.6.sp
                            )
                            Text(
                                text = "Local Room SQLite Persistent Storage",
                                color = NeonEmerald,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("close_metric_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Input Form: Name, Current Value, Unit
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    border = BorderStroke(1.dp, CyberSurfaceBorder),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "CREATE NEW METRIC",
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Metric Name Input
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = {
                                nameInput = it
                                errorMessage = null
                            },
                            label = { Text("Metric Name (e.g., Cluster Latency)") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("metric_name_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonCyan,
                                unfocusedBorderColor = CyberSurfaceBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedLabelColor = NeonCyan,
                                unfocusedLabelColor = TextSecondary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Current Value Input
                            OutlinedTextField(
                                value = valueInput,
                                onValueChange = {
                                    valueInput = it
                                    errorMessage = null
                                },
                                label = { Text("Current Value") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier
                                    .weight(1.2f)
                                    .testTag("metric_value_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NeonCyan,
                                    unfocusedBorderColor = CyberSurfaceBorder,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary,
                                    focusedLabelColor = NeonCyan,
                                    unfocusedLabelColor = TextSecondary
                                )
                            )

                            // Unit Input
                            OutlinedTextField(
                                value = unitInput,
                                onValueChange = {
                                    unitInput = it
                                    errorMessage = null
                                },
                                label = { Text("Unit (%, ms, $)") },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("metric_unit_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NeonCyan,
                                    unfocusedBorderColor = CyberSurfaceBorder,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary,
                                    focusedLabelColor = NeonCyan,
                                    unfocusedLabelColor = TextSecondary
                                )
                            )
                        }

                        if (errorMessage != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = errorMessage!!,
                                color = NeonRose,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Submit Button
                        Button(
                            onClick = {
                                val valParsed = valueInput.toDoubleOrNull()
                                if (nameInput.isBlank()) {
                                    errorMessage = "Please enter a valid metric name"
                                } else if (valParsed == null) {
                                    errorMessage = "Please enter a valid numeric value"
                                } else {
                                    onAddMetric(
                                        nameInput.trim(),
                                        valParsed,
                                        unitInput.trim().ifEmpty { "units" },
                                        categoryInput
                                    )
                                    nameInput = ""
                                    valueInput = ""
                                    unitInput = ""
                                    errorMessage = null
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .testTag("save_metric_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald.copy(alpha = 0.25f)),
                            border = BorderStroke(1.dp, NeonEmerald),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Save",
                                tint = NeonEmerald,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Save Metric to Room Database",
                                color = TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // List of Persisted Metrics
                Text(
                    text = "STORED METRICS (${metrics.size} ENTRIES)",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (metrics.isEmpty()) {
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
                            text = "No user-defined metrics in database yet.",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .testTag("stored_metrics_list"),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(metrics, key = { it.id }) { item ->
                            MetricRowItem(
                                metric = item,
                                onDelete = { onDeleteMetric(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricRowItem(
    metric: UserPerformanceMetric,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }
    val formattedDate = remember(metric.timestamp) { dateFormat.format(Date(metric.timestamp)) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("metric_item_${metric.id}"),
        color = CyberSurface,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, CyberSurfaceBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = metric.name,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(CyberSurfaceVariant)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = metric.category,
                            color = NeonCyan,
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Saved: $formattedDate",
                    color = TextMuted,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Value & Unit Badge
                Surface(
                    color = CyberBg,
                    border = BorderStroke(1.dp, NeonEmerald.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "${metric.currentValue} ${metric.unit}",
                        color = NeonEmerald,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Delete Button
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("delete_metric_${metric.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Metric",
                        tint = NeonRose,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
