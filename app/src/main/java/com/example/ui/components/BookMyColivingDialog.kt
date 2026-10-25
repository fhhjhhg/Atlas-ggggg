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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.ColivingSpaceListing
import com.example.data.ColivingVibe
import com.example.data.DomainAsset
import com.example.data.DxiMetrics
import com.example.data.HardwareBomItem
import com.example.data.McpServerLink
import com.example.data.RagKnowledgeDocument
import com.example.data.SoftwareArtifact
import com.example.data.StandardOperatingProcedure
import com.example.data.StatementOfWorkPhase
import com.example.data.TokenomicsPool
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
fun BookMyColivingDialog(
    spaces: List<ColivingSpaceListing>,
    domains: List<DomainAsset>,
    artifacts: List<SoftwareArtifact>,
    hardwareBom: List<HardwareBomItem>,
    sowPhases: List<StatementOfWorkPhase>,
    operatingSops: List<StandardOperatingProcedure>,
    mcpServers: List<McpServerLink>,
    ragDocs: List<RagKnowledgeDocument>,
    tokenomics: TokenomicsPool,
    dxiMetrics: DxiMetrics,
    selectedVibe: ColivingVibe?,
    searchQuery: String,
    onFilterChange: (ColivingVibe?, String) -> Unit,
    onDirectInquire: (spaceId: String, email: String, residentName: String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Marketplace", "Domains & HTTPS", "APKs & SDKs", "BOM & SOW", "SOP & RAG", "MCP & Tokenomics", "1000x DXI")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.96f)
                .fillMaxHeight(0.94f)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, CyberSurfaceBorder), RoundedCornerShape(16.dp))
                .testTag("book_my_coliving_dialog"),
            color = CyberBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                                .size(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(NeonEmerald.copy(alpha = 0.15f))
                                .border(1.dp, NeonEmerald.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Apartment,
                                contentDescription = "BookMyColiving",
                                tint = NeonEmerald,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "BOOKMYCOLIVING.COM",
                                    color = TextPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.6.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(NeonCyan.copy(alpha = 0.2f))
                                        .padding(horizontal = 5.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "ZERO COMMISSION DIRECT",
                                        color = NeonCyan,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                            Text(
                                text = "Global Discovery Directory • Dual-LLC Master Leases • 1000x DXI Stack",
                                color = TextSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("close_book_my_coliving_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable Sub-Tabs Navigation
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurfaceVariant)
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(tabTitles.size) { index ->
                        val isSelected = activeSubTab == index
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) NeonEmerald.copy(alpha = 0.25f) else Color.Transparent)
                                .border(1.dp, if (isSelected) NeonEmerald else Color.Transparent, RoundedCornerShape(6.dp))
                                .clickable { activeSubTab = index }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("bookmycoliving_tab_$index")
                        ) {
                            Text(
                                text = tabTitles[index],
                                color = if (isSelected) NeonEmerald else TextSecondary,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Active Tab Content
                Box(modifier = Modifier.fillMaxSize()) {
                    when (activeSubTab) {
                        0 -> MarketplaceView(
                            spaces = spaces,
                            selectedVibe = selectedVibe,
                            searchQuery = searchQuery,
                            onFilterChange = onFilterChange,
                            onDirectInquire = onDirectInquire
                        )
                        1 -> DomainsHttpsView(domains = domains)
                        2 -> SoftwareStackView(artifacts = artifacts)
                        3 -> BomAndSowView(bom = hardwareBom, sow = sowPhases)
                        4 -> SopAndRagView(sops = operatingSops, rag = ragDocs)
                        5 -> McpAndTokenomicsView(mcp = mcpServers, tokenomics = tokenomics)
                        6 -> DxiMultiplierView(dxi = dxiMetrics)
                    }
                }
            }
        }
    }
}

@Composable
private fun MarketplaceView(
    spaces: List<ColivingSpaceListing>,
    selectedVibe: ColivingVibe?,
    searchQuery: String,
    onFilterChange: (ColivingVibe?, String) -> Unit,
    onDirectInquire: (spaceId: String, email: String, residentName: String) -> Unit
) {
    var searchInput by remember(searchQuery) { mutableStateOf(searchQuery) }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        // Search & Filter Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchInput,
                onValueChange = {
                    searchInput = it
                    onFilterChange(selectedVibe, it)
                },
                placeholder = { Text("Search city, space name, SKU...", fontSize = 11.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = TextMuted, modifier = Modifier.size(16.dp))
                },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("coliving_search_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = CyberSurfaceBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Vibe Filters Row
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                FilterChip(
                    label = "All Vibes",
                    isSelected = selectedVibe == null,
                    onClick = { onFilterChange(null, searchInput) },
                    testTag = "vibe_chip_all"
                )
            }
            items(ColivingVibe.entries) { vibe ->
                FilterChip(
                    label = vibe.displayName,
                    isSelected = selectedVibe == vibe,
                    onClick = { onFilterChange(vibe, searchInput) },
                    testTag = "vibe_chip_${vibe.name}"
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Spaces List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("coliving_spaces_list"),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(spaces, key = { it.id }) { space ->
                SpaceCard(
                    space = space,
                    onInquire = { email, name ->
                        onDirectInquire(space.id, email, name)
                        Toast.makeText(context, "Direct inquiry sent to ${space.name}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
private fun SpaceCard(
    space: ColivingSpaceListing,
    onInquire: (email: String, name: String) -> Unit
) {
    var showInquiryForm by remember { mutableStateOf(false) }
    var emailInput by remember { mutableStateOf("guest@founder.io") }
    var nameInput by remember { mutableStateOf("Resident") }

    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("space_card_${space.id}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = space.name,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified Direct",
                            tint = NeonEmerald,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "${space.address} • ${space.city}",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                }

                Surface(
                    color = CyberBg,
                    border = BorderStroke(1.dp, NeonEmerald.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "$${space.monthlyRateUsd.toInt()}/mo",
                        color = NeonEmerald,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Badges & Vibe
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                TagPill(text = space.vibe.displayName, color = NeonCyan)
                TagPill(text = "${space.availableBeds} beds available (of ${space.totalBeds})", color = if (space.availableBeds > 0) NeonEmerald else NeonRose)
                TagPill(text = space.skuCode, color = TextMuted)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Amenities
            Text(
                text = "AMENITIES: " + space.amenities.joinToString(" • "),
                color = TextSecondary,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Actions: Book Direct (0% Commission)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Operator: ${space.operatorLlc}",
                    color = TextMuted,
                    fontSize = 9.sp
                )

                Button(
                    onClick = { showInquiryForm = !showInquiryForm },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald.copy(alpha = 0.20f)),
                    border = BorderStroke(1.dp, NeonEmerald),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .height(30.dp)
                        .testTag("inquire_button_${space.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Inquire",
                        tint = NeonEmerald,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (showInquiryForm) "Cancel" else "Book Direct (0% Fee)",
                        color = TextPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            AnimatedVisibility(visible = showInquiryForm) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "DIRECT OPERATOR INQUIRY DISPATCH (0% BOOKING FEE)",
                        color = NeonCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text("Your Name", fontSize = 9.sp) },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text("Your Email", fontSize = 9.sp) },
                            singleLine = true,
                            modifier = Modifier.weight(1.3f)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = {
                            onInquire(emailInput, nameInput)
                            showInquiryForm = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                    ) {
                        Text("Send Direct Booking Request", color = CyberBg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun DomainsHttpsView(domains: List<DomainAsset>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(domains) { item ->
            Surface(
                color = CyberSurface,
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Language, contentDescription = "Domain", tint = NeonCyan, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = item.domain, color = NeonCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        }
                        Text(text = item.purpose, color = TextSecondary, fontSize = 10.sp)
                        Text(text = "${item.status} • ${item.registrar}", color = TextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "${item.monthlyVisits} visits/mo", color = TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = "$${item.valuationUsd.toInt()} val", color = NeonEmerald, fontSize = 11.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}

@Composable
private fun SoftwareStackView(artifacts: List<SoftwareArtifact>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(artifacts) { artifact ->
            Surface(
                color = CyberSurface,
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Devices, contentDescription = "Artifact", tint = NeonViolet, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = artifact.name, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        TagPill(text = "${artifact.type} • ${artifact.version}", color = NeonViolet)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = artifact.description, color = TextSecondary, fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Arch: ${artifact.architecture} • Size: ${artifact.sizeMb}MB", color = TextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                }
            }
        }
    }
}

@Composable
private fun BomAndSowView(bom: List<HardwareBomItem>, sow: List<StatementOfWorkPhase>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "BILL OF MATERIALS (BOM) HARDWARE REGISTRY", color = NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
        items(bom) { item ->
            Surface(
                color = CyberSurface,
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.componentName, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        Text(text = "${item.sku} • Vendor: ${item.vendor} • Qty: ${item.quantity}", color = TextMuted, fontSize = 9.sp)
                    }
                    Text(text = "$${item.totalCostUsd.toInt()}", color = NeonEmerald, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "STATEMENT OF WORK (SOW) PHASES", color = NeonAmber, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
        items(sow) { phase ->
            Surface(
                color = CyberSurface,
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Phase ${phase.phaseNumber}: ${phase.title}", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${phase.progressPercent}% COMPLETE", color = if (phase.progressPercent == 100) NeonEmerald else NeonAmber, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                    Text(text = "Milestone: ${phase.milestone}", color = TextSecondary, fontSize = 9.sp)
                }
            }
        }
    }
}

@Composable
private fun SopAndRagView(sops: List<StandardOperatingProcedure>, rag: List<RagKnowledgeDocument>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(text = "STANDARD OPERATING PROCEDURES (SOP)", color = NeonEmerald, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
        items(sops) { sop ->
            Surface(
                color = CyberSurface,
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "${sop.code}: ${sop.title}", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        TagPill(text = sop.slaTarget, color = NeonCyan)
                    }
                    Text(text = sop.ruleSummary, color = TextSecondary, fontSize = 10.sp)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "RETRIEVAL-AUGMENTED GENERATION (RAG) VECTORS", color = NeonViolet, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
        items(rag) { doc ->
            Surface(
                color = CyberSurface,
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = doc.title, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        Text(text = "Chunks: ${doc.chunkCount} • Embedding Dim: ${doc.vectorDimension}", color = TextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                    }
                    TagPill(text = doc.category, color = NeonViolet)
                }
            }
        }
    }
}

@Composable
private fun McpAndTokenomicsView(mcp: List<McpServerLink>, tokenomics: TokenomicsPool) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(text = "MODEL CONTEXT PROTOCOL (MCP) SERVERS", color = NeonCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
        items(mcp) { server ->
            Surface(
                color = CyberSurface,
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = server.name, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        TagPill(text = server.status, color = NeonEmerald)
                    }
                    Text(text = "Tools: ${server.capabilities.joinToString(", ")}", color = TextSecondary, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "FRACTIONAL TOKENOMICS POOL ($${tokenomics.symbol})", color = NeonAmber, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "STAKED APY: ${tokenomics.apyPercent}%", color = NeonEmerald, fontSize = 13.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
                        Text(text = "DSCR RESERVE: ${tokenomics.dscrReserveRatio}x", color = NeonAmber, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Monthly Distributed Yield: $${tokenomics.monthlyYieldDistributedUsd.toInt()} USD", color = TextPrimary, fontSize = 11.sp)
                    Text(text = "Total Supply: ${tokenomics.totalFractionalUnits} units (${tokenomics.stakedUnits} staked in coliving cashflow pool)", color = TextMuted, fontSize = 9.sp)
                }
            }
        }
    }
}

@Composable
private fun DxiMultiplierView(dxi: DxiMetrics) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberSurface),
            border = BorderStroke(1.dp, NeonEmerald.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Speed, contentDescription = "1000x DXI", tint = NeonEmerald, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "1000X DOMAIN EXPERIENCE INDEX (DXI)", color = NeonEmerald, fontSize = 13.sp, fontWeight = FontWeight.Black, letterSpacing = 0.8.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Combines zero-commission marketplace direct routing, sub-20ms edge telemetry, automated by-the-bed Everything Coliving contracts, and MCP subagent orchestration to deliver 1000x developer and operator leverage.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DxiStatCard(label = "TIME SAVED", value = "${dxi.hoursSavedCumulative.toInt()} hrs", color = NeonCyan, modifier = Modifier.weight(1f))
            DxiStatCard(label = "SKILLS ARBITRAGE", value = "$${dxi.skillsArbitrageSavingsUsd.toInt()}/yr", color = NeonEmerald, modifier = Modifier.weight(1f))
            DxiStatCard(label = "EDGE VELOCITY", value = "${dxi.autonomousVelocityOpsPerSec} ops/s", color = NeonViolet, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun DxiStatCard(
    label: String,
    value: String,
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
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = label, color = TextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            Text(text = value, color = color, fontSize = 12.sp, fontWeight = FontWeight.Black, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) NeonCyan.copy(alpha = 0.25f) else CyberSurfaceVariant)
            .border(1.dp, if (isSelected) NeonCyan else Color.Transparent, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 5.dp)
            .testTag(testTag)
    ) {
        Text(
            text = label,
            color = if (isSelected) NeonCyan else TextSecondary,
            fontSize = 9.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun TagPill(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            .padding(horizontal = 5.dp, vertical = 2.dp)
    ) {
        Text(text = text, color = color, fontSize = 8.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
    }
}
