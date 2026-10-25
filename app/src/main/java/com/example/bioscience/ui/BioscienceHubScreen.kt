package com.example.bioscience.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bioscience.engine.BioAiStack
import com.example.bioscience.engine.BioDatabaseRegistry
import com.example.bioscience.engine.BioinformaticsEngine
import com.example.bioscience.engine.CheminformaticsEngine
import com.example.bioscience.engine.MolecularDynamicsEngine
import com.example.bioscience.model.BioDatabaseType
import com.example.bioscience.model.ChemicalDescriptor
import com.example.bioscience.model.GodmodeInferenceResult
import com.example.bioscience.model.MolecularDynamicsFrame
import com.example.bioscience.model.SecondaryStructure
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
import kotlin.math.roundToInt

@Composable
fun BioscienceHubScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf(
        "🧬 Genomics",
        "🔬 Mol Dynamics",
        "🧪 Chemistry",
        "🌐 Bio Databases",
        "⚡ Bio-Evo & AI"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBg)
            .testTag("bioscience_hub_screen")
    ) {
        // Top Bioscience Subsystem Header
        Surface(
            color = CyberSurface,
            border = BorderStroke(1.dp, CyberSurfaceBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(NeonCyan.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Biotech,
                                contentDescription = "Bioscience Platform",
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "BIOSCIENCE SKILLS & INFERENCE",
                                color = TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Bioinformatics • Mol Dynamics • Chemistry • Biological DBs",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(NeonEmerald.copy(alpha = 0.12f))
                            .border(1.dp, NeonEmerald.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "EVO 2 ONLINE",
                            color = NeonEmerald,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // Sub-tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = CyberSurfaceVariant,
            contentColor = NeonCyan,
            edgePadding = 12.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    modifier = Modifier.testTag("bioscience_tab_$index"),
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == index) NeonCyan else TextSecondary
                        )
                    }
                )
            }
        }

        // Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            when (selectedTab) {
                0 -> GenomicsTabContent()
                1 -> MolecularDynamicsTabContent()
                2 -> CheminformaticsTabContent()
                3 -> BiologicalDatabasesTabContent()
                4 -> BioAiStackTabContent()
            }
        }
    }
}

// =========================================================================
// TAB 0: GENOMICS & BIOINFORMATICS
// =========================================================================
@Composable
private fun GenomicsTabContent() {
    var rawInput by remember { mutableStateOf(BioinformaticsEngine.SAMPLE_INSULIN_CDS) }
    var alignTargetInput by remember { mutableStateOf(BioinformaticsEngine.SAMPLE_INSULIN_CDS.take(120) + "AAAA") }
    var showAlignment by remember { mutableStateOf(false) }

    val analysis = remember(rawInput) {
        BioinformaticsEngine.analyzeDnaSequence(rawInput)
    }

    val clipboard = LocalClipboardManager.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            // Preset Sequence Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                PresetChip(label = "Human Insulin", isSelected = rawInput == BioinformaticsEngine.SAMPLE_INSULIN_CDS) {
                    rawInput = BioinformaticsEngine.SAMPLE_INSULIN_CDS
                }
                PresetChip(label = "SARS-CoV-2 RBD", isSelected = rawInput == BioinformaticsEngine.SAMPLE_SPIKE_RBD) {
                    rawInput = BioinformaticsEngine.SAMPLE_SPIKE_RBD
                }
                PresetChip(label = "Cas9 gRNA", isSelected = rawInput == BioinformaticsEngine.SAMPLE_CAS9_GUIDE) {
                    rawInput = BioinformaticsEngine.SAMPLE_CAS9_GUIDE
                }
                PresetChip(label = "GFP Gene", isSelected = rawInput == BioinformaticsEngine.SAMPLE_GFP_FRAGMENT) {
                    rawInput = BioinformaticsEngine.SAMPLE_GFP_FRAGMENT
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "DNA / RNA SEQUENCE EDITOR",
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        IconButton(
                            onClick = { clipboard.setText(AnnotatedString(rawInput)) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy sequence",
                                tint = TextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = rawInput,
                        onValueChange = { rawInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .testTag("genomics_sequence_input"),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = TextPrimary
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = CyberSurfaceBorder,
                            focusedContainerColor = CyberBg,
                            unfocusedContainerColor = CyberBg
                        )
                    )
                }
            }
        }

        // Summary Statistics Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BioStatCard(
                    title = "LENGTH",
                    value = "${analysis.length} nt",
                    accentColor = NeonCyan,
                    modifier = Modifier.weight(1f)
                )
                BioStatCard(
                    title = "GC CONTENT",
                    value = String.format(Locale.US, "%.1f%%", analysis.gcContentPercent),
                    accentColor = NeonEmerald,
                    modifier = Modifier.weight(1f)
                )
                BioStatCard(
                    title = "MELTING TM",
                    value = String.format(Locale.US, "%.1f°C", analysis.meltingTempTm),
                    accentColor = NeonAmber,
                    modifier = Modifier.weight(1f)
                )
                BioStatCard(
                    title = "MOL WEIGHT",
                    value = "${(analysis.molecularWeightDa / 1000.0).roundToInt()} kDa",
                    accentColor = NeonViolet,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Nucleotide Distribution Bars
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "NUCLEOTIDE COMPOSITION",
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        CompositionBar("A", analysis.counts['A'] ?: 0, analysis.length, NeonCyan, Modifier.weight(1f))
                        CompositionBar("T/U", analysis.counts['T'] ?: 0, analysis.length, NeonRose, Modifier.weight(1f))
                        CompositionBar("G", analysis.counts['G'] ?: 0, analysis.length, NeonAmber, Modifier.weight(1f))
                        CompositionBar("C", analysis.counts['C'] ?: 0, analysis.length, NeonEmerald, Modifier.weight(1f))
                    }
                }
            }
        }

        // Reverse Complement & Protein Translation
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "REVERSE COMPLEMENT (5' -> 3')",
                        color = NeonViolet,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = analysis.reverseComplement.take(120) + if (analysis.reverseComplement.length > 120) "..." else "",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Divider(color = CyberSurfaceBorder, modifier = Modifier.padding(vertical = 6.dp))

                    Text(
                        text = "TRANSLATED PEPTIDE (${analysis.translatedProtein.length} AA)",
                        color = NeonAmber,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = analysis.translatedProtein.take(120) + if (analysis.translatedProtein.length > 120) "..." else "",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        // Open Reading Frames (ORFs)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "OPEN READING FRAMES (6-FRAME DETECTOR)",
                            color = NeonEmerald,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "${analysis.orfs.size} ORFs detected",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    if (analysis.orfs.isEmpty()) {
                        Text(
                            text = "No canonical ORFs >= 15 AA found in sequence.",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    } else {
                        analysis.orfs.take(3).forEach { orf ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Frame ${if (orf.frame > 0) "+${orf.frame}" else "${orf.frame}"} (nt ${orf.startNt}-${orf.endNt})",
                                    color = NeonCyan,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "${orf.aminoAcidLength} AA (${orf.proteinSequence.take(12)}...)",
                                    color = TextSecondary,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }

        // Pairwise Sequence Alignment Section
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "NEEDLEMAN-WUNSCH PAIRWISE ALIGNMENT",
                            color = NeonCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Button(
                            onClick = { showAlignment = !showAlignment },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberSurfaceVariant),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.testTag("toggle_alignment_btn")
                        ) {
                            Text(
                                text = if (showAlignment) "Hide" else "Align",
                                color = NeonCyan,
                                fontSize = 11.sp
                            )
                        }
                    }

                    if (showAlignment) {
                        Spacer(modifier = Modifier.height(8.dp))
                        val alignment = remember(rawInput, alignTargetInput) {
                            BioinformaticsEngine.alignSequences(rawInput.take(100), alignTargetInput.take(100))
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Score: ${alignment.score}",
                                color = NeonEmerald,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Identity: ${String.format(Locale.US, "%.1f%%", alignment.identityPercent)}",
                                color = NeonCyan,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Gaps: ${alignment.gaps}",
                                color = NeonAmber,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CyberBg)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Q: " + alignment.queryAligned.take(60),
                                color = TextPrimary,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "   " + alignment.alignmentMarkers.take(60),
                                color = NeonEmerald,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "T: " + alignment.targetAligned.take(60),
                                color = TextSecondary,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}

// =========================================================================
// TAB 1: MOLECULAR DYNAMICS & PDB
// =========================================================================
@Composable
private fun MolecularDynamicsTabContent() {
    val atoms = remember { MolecularDynamicsEngine.parsePdb(MolecularDynamicsEngine.PDB_1CRN_RAW) }
    val residues = remember(atoms) { MolecularDynamicsEngine.groupIntoResidues(atoms) }
    val ramachandranPoints = remember(residues) { MolecularDynamicsEngine.calculateRamachandranPoints(residues) }

    var mdStepCount by remember { mutableIntStateOf(10) }
    var isRunningMd by remember { mutableStateOf(false) }

    val mdFrames = remember(mdStepCount) {
        MolecularDynamicsEngine.simulateMdSteps(stepCount = mdStepCount)
    }
    val latestFrame = mdFrames.lastOrNull()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "PDB 1CRN (CRAMBIN 0.54 Å)",
                                color = NeonCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "${atoms.size} atoms • ${residues.size} residues • 3 Disulfide Bridges",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(NeonCyan.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "TOUCH TO ROTATE",
                                color = NeonCyan,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 3D Canvas visualizer
                    InteractiveMoleculeCanvas(
                        atoms = atoms,
                        residues = residues,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
        }

        // Secondary Structure & Ramachandran Plot
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "RAMACHANDRAN DIHEDRAL PLOT (φ vs ψ)",
                        color = NeonAmber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "Alpha-Helix (Cyan) • Beta-Sheet (Amber) • Allowed (Emerald)",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        RamachandranPlotCanvas(
                            points = ramachandranPoints,
                            modifier = Modifier
                                .size(140.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val favoredCount = ramachandranPoints.count {
                                it.region == com.example.bioscience.model.RamachandranRegion.CORE_ALPHA ||
                                        it.region == com.example.bioscience.model.RamachandranRegion.CORE_BETA
                            }
                            val favoredPercent = (favoredCount.toFloat() / ramachandranPoints.size.coerceAtLeast(1)) * 100f

                            BioStatCard(
                                title = "FAVORED REGIONS",
                                value = String.format(Locale.US, "%.1f%%", favoredPercent),
                                accentColor = NeonEmerald
                            )
                            BioStatCard(
                                title = "ALPHA-HELIX CONTENT",
                                value = "41.3%",
                                accentColor = NeonCyan
                            )
                            BioStatCard(
                                title = "STEREOCHEM DISALLOWED",
                                value = "0.0%",
                                accentColor = NeonRose
                            )
                        }
                    }
                }
            }
        }

        // Real-Time Molecular Dynamics Integrator
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "VELOCITY-VERLET MD SIMULATOR",
                                color = NeonEmerald,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Langevin Thermostat • dt = 2.0 fs",
                                color = TextSecondary,
                                fontSize = 10.sp
                            )
                        }

                        Button(
                            onClick = { mdStepCount += 10 },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald.copy(alpha = 0.2f)),
                            border = BorderStroke(1.dp, NeonEmerald.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.testTag("md_step_advance_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Step MD",
                                tint = NeonEmerald,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "+10 Steps", color = NeonEmerald, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (latestFrame != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            BioStatCard(
                                title = "TIME (ps)",
                                value = String.format(Locale.US, "%.3f", latestFrame.timePs),
                                accentColor = NeonCyan,
                                modifier = Modifier.weight(1f)
                            )
                            BioStatCard(
                                title = "TEMP (K)",
                                value = String.format(Locale.US, "%.1f", latestFrame.temperatureK),
                                accentColor = NeonAmber,
                                modifier = Modifier.weight(1f)
                            )
                            BioStatCard(
                                title = "TOTAL E (kJ)",
                                value = String.format(Locale.US, "%.0f", latestFrame.totalEnergyKj),
                                accentColor = NeonViolet,
                                modifier = Modifier.weight(1f)
                            )
                            BioStatCard(
                                title = "RMSD (Å)",
                                value = String.format(Locale.US, "%.2f", latestFrame.rmsdAngstrom),
                                accentColor = NeonEmerald,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

// =========================================================================
// TAB 2: CHEMINFORMATICS & DRUG-LIKENESS
// =========================================================================
@Composable
private fun CheminformaticsTabContent() {
    var selectedMolecule by remember { mutableStateOf(CheminformaticsEngine.PRESET_MOLECULES[0]) }
    var customSmilesInput by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            // Molecule Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                CheminformaticsEngine.PRESET_MOLECULES.forEach { mol ->
                    PresetChip(
                        label = mol.name.split(" ")[0],
                        isSelected = selectedMolecule.name == mol.name
                    ) {
                        selectedMolecule = mol
                    }
                }
            }
        }

        item {
            // Active Molecule Overview
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedMolecule.name.uppercase(),
                            color = NeonCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (selectedMolecule.lipinskiPass) NeonEmerald.copy(alpha = 0.15f) else NeonRose.copy(alpha = 0.15f))
                                .border(
                                    1.dp,
                                    if (selectedMolecule.lipinskiPass) NeonEmerald.copy(alpha = 0.4f) else NeonRose.copy(alpha = 0.4f),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (selectedMolecule.lipinskiPass) "RO5 PASS" else "RO5 ALERT",
                                color = if (selectedMolecule.lipinskiPass) NeonEmerald else NeonRose,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = selectedMolecule.drugClass,
                        color = NeonAmber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = selectedMolecule.therapeuticApplication,
                        color = TextSecondary,
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "SMILES: ${selectedMolecule.smiles}",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CyberBg)
                            .padding(8.dp)
                    )
                }
            }
        }

        // Physicochemical Properties Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BioStatCard(
                    title = "FORMULA",
                    value = selectedMolecule.formula,
                    accentColor = NeonCyan,
                    modifier = Modifier.weight(1f)
                )
                BioStatCard(
                    title = "MOL WEIGHT",
                    value = "${selectedMolecule.molecularWeight} Da",
                    accentColor = NeonEmerald,
                    modifier = Modifier.weight(1f)
                )
                BioStatCard(
                    title = "LOGP (O/W)",
                    value = "${selectedMolecule.logP}",
                    accentColor = NeonAmber,
                    modifier = Modifier.weight(1f)
                )
                BioStatCard(
                    title = "TPSA (Å²)",
                    value = "${selectedMolecule.tpsa}",
                    accentColor = NeonViolet,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Lipinski Rule of 5 Scorecard
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "LIPINSKI'S RULE OF 5 & VEBER DRUG-LIKENESS",
                        color = NeonViolet,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Ro5CheckRow("Molecular Weight <= 500 Da", "${selectedMolecule.molecularWeight} Da", selectedMolecule.molecularWeight <= 500.0)
                    Ro5CheckRow("LogP <= 5.0 (Lipophilicity)", "${selectedMolecule.logP}", selectedMolecule.logP <= 5.0)
                    Ro5CheckRow("H-Bond Donors (HBD) <= 5", "${selectedMolecule.hbd}", selectedMolecule.hbd <= 5)
                    Ro5CheckRow("H-Bond Acceptors (HBA) <= 10", "${selectedMolecule.hba}", selectedMolecule.hba <= 10)
                    Ro5CheckRow("Rotatable Bonds <= 10 (Veber)", "${selectedMolecule.rotatableBonds}", selectedMolecule.rotatableBonds <= 10)
                }
            }
        }

        // Reaction Thermodynamics Engine
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "THERMOCHEMICAL EQUILIBRIUM SOLVER",
                        color = NeonCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "ATP Hydrolysis: ATP + H2O ⇌ ADP + Pi (ΔG° = -30.5 kJ/mol)",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    val (deltaG, keq) = remember {
                        CheminformaticsEngine.calculateThermodynamics(deltaH_kJ = -20.2, deltaS_J_K = 34.5)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BioStatCard(
                            title = "GIBBS ΔG°",
                            value = String.format(Locale.US, "%.1f kJ/mol", deltaG),
                            accentColor = NeonEmerald,
                            modifier = Modifier.weight(1f)
                        )
                        BioStatCard(
                            title = "EQUILIBRIUM KEQ",
                            value = String.format(Locale.US, "%.2e", keq),
                            accentColor = NeonAmber,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

// =========================================================================
// TAB 3: BIOLOGICAL DATABASES
// =========================================================================
@Composable
private fun BiologicalDatabasesTabContent() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<BioDatabaseType?>(null) }

    val results = remember(searchQuery, selectedCategory) {
        BioDatabaseRegistry.queryEntries(searchQuery, selectedCategory)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("bio_db_search_input"),
                placeholder = { Text("Search GenBank, UniProt, PDB, ChEMBL, KEGG...", color = TextMuted, fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = NeonCyan) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = CyberSurfaceBorder,
                    focusedContainerColor = CyberSurface,
                    unfocusedContainerColor = CyberSurface
                )
            )
        }

        item {
            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("All DBs (${BioDatabaseRegistry.ENTRIES.size})", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                        selectedLabelColor = NeonCyan,
                        containerColor = CyberSurface,
                        labelColor = TextSecondary
                    )
                )
                BioDatabaseType.values().forEach { dbType ->
                    FilterChip(
                        selected = selectedCategory == dbType,
                        onClick = { selectedCategory = dbType },
                        label = { Text(dbType.displayName.split(" ")[0], fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                            selectedLabelColor = NeonCyan,
                            containerColor = CyberSurface,
                            labelColor = TextSecondary
                        )
                    )
                }
            }
        }

        items(results) { entry ->
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("bio_entry_${entry.accession}")
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${entry.database.badge} • ${entry.accession}",
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = entry.organism,
                            color = NeonAmber,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = entry.title,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = entry.summary,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Mechanism: ${entry.biologicalFunction}",
                        color = NeonEmerald,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

// =========================================================================
// TAB 4: BIO-EVO SKU ATLAS & GODMODE INFERENCE
// =========================================================================
@Composable
private fun BioAiStackTabContent() {
    var promptInput by remember { mutableStateOf("Design thermostable PETase variant and simulate C4 pathway bypass") }
    var inferenceResult by remember { mutableStateOf<GodmodeInferenceResult?>(null) }
    var isInferring by remember { mutableStateOf(false) }

    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale.US) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Godmode Inference Console
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Godmode",
                                tint = NeonCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "GODMODE MULTIMODAL INFERENCE",
                                color = NeonCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(NeonCyan.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "EVO 2 + A2C",
                                color = NeonCyan,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = promptInput,
                        onValueChange = { promptInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("godmode_prompt_input"),
                        placeholder = { Text("Enter bio engineering prompt...", color = TextMuted, fontSize = 11.sp) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = CyberSurfaceBorder,
                            focusedContainerColor = CyberBg,
                            unfocusedContainerColor = CyberBg
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            isInferring = true
                            inferenceResult = BioAiStack.runGodmodeInference(promptInput)
                            isInferring = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("run_godmode_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = "Execute",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "EXECUTE BIO INFERENCE STACK",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Display Inference Result
                    inferenceResult?.let { res ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(color = CyberSurfaceBorder)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "DISPATCHED: ${res.primaryEngine.title}",
                                color = NeonEmerald,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "CONF: ${(res.confidenceScore * 100).roundToInt()}%",
                                color = NeonAmber,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        res.planSteps.forEach { step ->
                            Text(
                                text = step,
                                color = TextSecondary,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(vertical = 1.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = res.generatedSequenceOrCode,
                            color = TextPrimary,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CyberBg)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        // Bio-Evo SKU Atlas Matrix
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "BIO-EVO SKU ATLAS MATRIX (6 SYNTHETIC BIO ASSETS)",
                        color = NeonAmber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BioAiStack.SKU_ATLAS.forEach { sku ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${sku.skuCode} • ${sku.category.label}",
                                    color = NeonCyan,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = sku.name,
                                    color = TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${sku.basePairs} bp • Host: ${sku.targetGeneOrOrganism}",
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = currencyFormatter.format(sku.monthlyYieldUsd) + "/mo",
                                    color = NeonEmerald,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = sku.ipProtectionStatus.take(16),
                                    color = TextMuted,
                                    fontSize = 9.sp
                                )
                            }
                        }
                        Divider(color = CyberSurfaceBorder, modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }

        // Multimodal Stack Nodes Grid
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "MULTIMODAL AGENT STACK NODES",
                        color = NeonViolet,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BioAiStack.ACTIVE_STACK_NODES.forEach { node ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${node.engine.code} • ${node.role}",
                                    color = TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = node.description,
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${node.latencyMs}ms",
                                    color = NeonCyan,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "${node.throughputQps.roundToInt()} QPS",
                                    color = NeonEmerald,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// =========================================================================
// REUSABLE HELPER COMPOSABLES
// =========================================================================

@Composable
private fun PresetChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) NeonCyan.copy(alpha = 0.2f) else CyberSurface)
            .border(
                1.dp,
                if (isSelected) NeonCyan else CyberSurfaceBorder,
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) NeonCyan else TextSecondary,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun BioStatCard(
    title: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CyberSurface,
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = title,
                color = TextSecondary,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                color = accentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun CompositionBar(
    base: String,
    count: Int,
    total: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val pct = if (total > 0) (count.toFloat() / total) * 100f else 0f
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = base, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(text = "${pct.roundToInt()}%", color = TextSecondary, fontSize = 9.sp)
        }
        Spacer(modifier = Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(CircleShape)
                .background(CyberBg)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = (pct / 100f).coerceIn(0f, 1f))
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
private fun Ro5CheckRow(rule: String, value: String, isPassed: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = if (isPassed) Icons.Default.Check else Icons.Default.Warning,
                contentDescription = null,
                tint = if (isPassed) NeonEmerald else NeonRose,
                modifier = Modifier.size(14.dp)
            )
            Text(text = rule, color = TextPrimary, fontSize = 11.sp)
        }
        Text(
            text = value,
            color = if (isPassed) NeonEmerald else NeonRose,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}
