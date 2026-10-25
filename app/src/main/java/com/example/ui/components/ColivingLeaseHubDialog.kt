package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.ColivingBedTwin
import com.example.data.ColivingContractConfig
import com.example.data.ColivingLeaseType
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
fun ColivingLeaseHubDialog(
    selectedBed: ColivingBedTwin?,
    config: ColivingContractConfig,
    contractText: String?,
    isAuditing: Boolean,
    auditSummary: String?,
    onConfigChange: (ColivingContractConfig) -> Unit,
    onDeployTwin: (bedId: String, residentName: String, leaseType: ColivingLeaseType) -> Unit,
    onAuditWithGemini: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var residentNameState by remember(config.residentName) { mutableStateOf(config.residentName) }
    var bedIdState by remember(config.bedId) { mutableStateOf(config.bedId) }
    var feeState by remember(config.monthlyFee) { mutableStateOf(config.monthlyFee.toString()) }
    var depositState by remember(config.securityDeposit) { mutableStateOf(config.securityDeposit.toString()) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, CyberSurfaceBorder), RoundedCornerShape(16.dp))
                .testTag("coliving_lease_hub_dialog"),
            color = CyberBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Top Navigation Bar
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
                                .background(NeonCyan.copy(alpha = 0.15f))
                                .border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.HomeWork,
                                contentDescription = "Everything Coliving Hub",
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "EVERYTHING COLIVING™ DIGITAL TWIN",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.6.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(CyberSurfaceVariant)
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "35-BED TWIN",
                                        color = NeonEmerald,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                            Text(
                                text = "By-The-Bed Licensing • Master Leases • House Rules SOPs",
                                color = TextSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("close_coliving_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Recommendation Banner: Everything Coliving Legal Architecture
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberSurface),
                        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Gavel,
                                    contentDescription = "Legal Recommendation",
                                    tint = NeonAmber,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "EVERYTHING COLIVING LEGAL RECOMMENDATION",
                                    color = NeonAmber,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Standard residential joint-and-several leases fail in shared housing because non-paying occupants jeopardize the entire unit. Everything Coliving recommends By-The-Bed Individual Room Licenses with dedicated common area easements, an enforceable House Rules Addendum (quiet hours, fridge tags), and All-Inclusive Pricing with a utility cap.",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // Template Type Selector
                    Column {
                        Text(
                            text = "SELECT COLIVING LEGAL TEMPLATE:",
                            color = TextSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            ColivingLeaseType.entries.forEach { type ->
                                val isSelected = config.templateType == type
                                Surface(
                                    color = if (isSelected) NeonCyan.copy(alpha = 0.15f) else CyberSurface,
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, if (isSelected) NeonCyan else CyberSurfaceBorder),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onConfigChange(config.copy(templateType = type)) }
                                        .testTag("template_chip_${type.name}")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = type.displayName,
                                                color = if (isSelected) NeonCyan else TextPrimary,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = type.description,
                                                color = TextMuted,
                                                fontSize = 9.sp
                                            )
                                        }
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.VerifiedUser,
                                                contentDescription = "Selected",
                                                tint = NeonCyan,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Interactive Digital Twin Parameters
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberSurface),
                        border = BorderStroke(1.dp, CyberSurfaceBorder),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "DIGITAL TWIN LICENSE PARAMETERS",
                                color = NeonEmerald,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = residentNameState,
                                    onValueChange = {
                                        residentNameState = it
                                        onConfigChange(config.copy(residentName = it))
                                    },
                                    label = { Text("Resident Name", fontSize = 10.sp) },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1.3f)
                                        .testTag("coliving_resident_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = NeonCyan,
                                        unfocusedBorderColor = CyberSurfaceBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    )
                                )

                                OutlinedTextField(
                                    value = bedIdState,
                                    onValueChange = {
                                        bedIdState = it
                                        onConfigChange(config.copy(bedId = it))
                                    },
                                    label = { Text("Bed ID", fontSize = 10.sp) },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(0.9f)
                                        .testTag("coliving_bed_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = NeonCyan,
                                        unfocusedBorderColor = CyberSurfaceBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = feeState,
                                    onValueChange = {
                                        feeState = it
                                        it.toDoubleOrNull()?.let { fee ->
                                            onConfigChange(config.copy(monthlyFee = fee))
                                        }
                                    },
                                    label = { Text("Monthly Fee ($)", fontSize = 10.sp) },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("coliving_fee_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = NeonCyan,
                                        unfocusedBorderColor = CyberSurfaceBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    )
                                )

                                OutlinedTextField(
                                    value = depositState,
                                    onValueChange = {
                                        depositState = it
                                        it.toDoubleOrNull()?.let { dep ->
                                            onConfigChange(config.copy(securityDeposit = dep))
                                        }
                                    },
                                    label = { Text("Deposit ($)", fontSize = 10.sp) },
                                    singleLine = true,
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("coliving_deposit_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = NeonCyan,
                                        unfocusedBorderColor = CyberSurfaceBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    )
                                )
                            }
                        }
                    }

                    // Gemini AI Legal Audit Action
                    Button(
                        onClick = onAuditWithGemini,
                        enabled = !isAuditing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .testTag("audit_coliving_lease_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonViolet.copy(alpha = 0.25f)),
                        border = BorderStroke(1.dp, NeonViolet),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (isAuditing) {
                            CircularProgressIndicator(
                                color = NeonViolet,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Evaluating Enforceability via Gemini 3.8 Flash...",
                                color = NeonViolet,
                                fontSize = 11.sp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Gemini Audit",
                                tint = NeonViolet,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Run Gemini 3.8 Flash Legal Compliance Audit",
                                color = TextPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Audit Result Card
                    AnimatedVisibility(visible = auditSummary != null) {
                        auditSummary?.let { summary ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                                border = BorderStroke(1.dp, NeonViolet.copy(alpha = 0.5f)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.VerifiedUser,
                                            contentDescription = "Audit Verified",
                                            tint = NeonEmerald,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "GEMINI 3.8 FLASH LEGAL ANALYSIS",
                                            color = NeonViolet,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = summary,
                                        color = TextPrimary,
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    // Formatted Contract Preview
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
                                Text(
                                    text = "GENERATED DIGITAL TWIN AGREEMENT",
                                    color = NeonCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )

                                Button(
                                    onClick = {
                                        contractText?.let {
                                            clipboardManager.setText(AnnotatedString(it))
                                            Toast.makeText(context, "Legal Agreement copied to clipboard", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = CyberSurfaceVariant),
                                    border = BorderStroke(1.dp, CyberSurfaceBorder),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier
                                        .height(28.dp)
                                        .testTag("copy_coliving_lease_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = NeonCyan,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Copy Text",
                                        color = NeonCyan,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(CyberBg)
                                    .border(1.dp, CyberSurfaceBorder, RoundedCornerShape(6.dp))
                                    .padding(8.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = contractText ?: "Generating contract...",
                                    color = TextSecondary,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }

                    // Deploy Action Button
                    Button(
                        onClick = {
                            onDeployTwin(config.bedId, config.residentName, config.templateType)
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("deploy_coliving_twin_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald.copy(alpha = 0.25f)),
                        border = BorderStroke(1.dp, NeonEmerald),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Deploy Digital Twin Agreement to ${config.bedId}",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
