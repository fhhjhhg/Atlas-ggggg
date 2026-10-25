package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.Biocontext
import com.example.data.Bioprompt
import com.example.data.Bioreactor
import com.example.data.Bioregion
import com.example.data.Biospec
import com.example.data.CodeDefinition
import com.example.data.DroneSpec
import com.example.data.EmergentPaper
import com.example.data.EvoChemistryPath
import com.example.data.EvoLink
import com.example.data.EvoMathFormula
import com.example.data.FrameworkToolkit
import com.example.data.HardwareReview
import com.example.data.PocketFlowTask
import com.example.data.ThoughtLeader
import com.example.data.TutorialDifficulty
import com.example.data.TutorialModule
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergentResearchDialog(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel,
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        "Emergent Research",
        "PocketFlow Tutorials",
        "Awesome Frameworks",
        "Hardware & Drones",
        "Heartbeat & Soul BPM",
        "Bio-Evo Ecosystem"
    )

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
                .testTag("dialog_emergent_research_hub"),
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
                                .background(NeonCyan.copy(alpha = 0.15f))
                                .border(1.dp, NeonCyan.copy(alpha = 0.4f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = "Research",
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "EMERGENT RESEARCH & POCKETFLOW HUB",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Works Cited • Zero to Hero • Hardware/Drones • Heartbeat BPM • Bio-Evo",
                                fontSize = 10.sp,
                                color = TextMuted
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("btn_close_emergent_research_hub")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextMuted
                        )
                    }
                }

                // Scrollable Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = CyberSurfaceVariant,
                    contentColor = TextPrimary,
                    edgePadding = 12.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = NeonCyan
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == index) NeonCyan else TextMuted
                                )
                            }
                        )
                    }
                }

                // Content View according to Selected Tab
                Box(modifier = Modifier.fillMaxSize()) {
                    when (selectedTab) {
                        0 -> EmergentPapersTab(state = state, viewModel = viewModel)
                        1 -> PocketFlowTutorialsTab(state = state, viewModel = viewModel)
                        2 -> FrameworksToolkitsTab(state = state)
                        3 -> HardwareDronesTab(state = state)
                        4 -> HeartbeatSoulBpmTab(state = state, viewModel = viewModel)
                        5 -> BioEvoEcosystemTab(state = state)
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------------------
// Tab 0: Emergent Research & Works Cited
// -----------------------------------------------------------------------------------------
@Composable
private fun EmergentPapersTab(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf(state.emergentSearchQuery) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.filterEmergentPapers(it)
                },
                placeholder = { Text("Search arXiv papers, thought leaders, topics...", fontSize = 12.sp, color = TextMuted) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = NeonCyan, modifier = Modifier.size(18.dp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_search_emergent_papers"),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = CyberSurfaceBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = CyberSurface,
                    unfocusedContainerColor = CyberSurface
                ),
                shape = RoundedCornerShape(10.dp)
            )
        }

        item {
            Text(
                text = "TRENDING PAPERS (EMERGENT MIND INDEX)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan,
                letterSpacing = 0.5.sp
            )
        }

        items(state.filteredEmergentPapers) { paper ->
            PaperCardItem(paper = paper)
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "THOUGHT LEADERS & WORKS CITED AUTHORS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonAmber,
                letterSpacing = 0.5.sp
            )
        }

        items(state.thoughtLeaders) { leader ->
            ThoughtLeaderCardItem(leader = leader)
        }
    }
}

@Composable
private fun PaperCardItem(paper: EmergentPaper) {
    var expandedWorksCited by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = NeonCyan.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = paper.arxivId,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = NeonCyan,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = NeonEmerald.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Emergent Score: ${paper.emergentScore}",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonEmerald,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = paper.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Authors: ${paper.authors.joinToString(", ")}",
                fontSize = 10.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = paper.abstractText,
                fontSize = 11.sp,
                color = TextSecondary,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    paper.topics.forEach { topic ->
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = CyberSurfaceVariant,
                            border = BorderStroke(0.5.dp, CyberSurfaceBorder)
                        ) {
                            Text(
                                text = topic,
                                fontSize = 8.sp,
                                color = TextMuted,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Button(
                    onClick = { expandedWorksCited = !expandedWorksCited },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonAmber.copy(alpha = 0.15f),
                        contentColor = NeonAmber
                    ),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(0.5.dp, NeonAmber.copy(alpha = 0.4f)),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text(
                        text = "${paper.worksCitedCount} Works Cited ${if (expandedWorksCited) "▲" else "▼"}",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            AnimatedVisibility(visible = expandedWorksCited) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurfaceVariant)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "WORKS CITED CITATION TREE:",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonAmber,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    paper.worksCited.forEachIndexed { idx, citation ->
                        Text(
                            text = "[$idx] $citation",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = TextSecondary,
                            lineHeight = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ThoughtLeaderCardItem(leader: ThoughtLeader) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = leader.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${leader.role} • ${leader.institution}",
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = NeonAmber.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "h-index: ${leader.hIndex}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = NeonAmber,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = leader.bio,
                fontSize = 10.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sponsors: ${leader.sponsorships.joinToString(", ")}",
                    fontSize = 9.sp,
                    color = TextMuted
                )
                Text(
                    text = "${leader.worksCitedCount} Total Citations",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = NeonEmerald
                )
            }
        }
    }
}

// -----------------------------------------------------------------------------------------
// Tab 1: PocketFlow Code Tutorials & Zero-to-Hero
// -----------------------------------------------------------------------------------------
@Composable
private fun PocketFlowTutorialsTab(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var selectedLevel by remember { mutableStateOf<TutorialDifficulty?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Level Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedLevel == null,
                    onClick = {
                        selectedLevel = null
                        viewModel.filterTutorials(null, "")
                    },
                    label = { Text("All Modules (${state.tutorialModules.size})", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                        selectedLabelColor = NeonCyan
                    )
                )
                TutorialDifficulty.values().forEach { diff ->
                    FilterChip(
                        selected = selectedLevel == diff,
                        onClick = {
                            selectedLevel = diff
                            viewModel.filterTutorials(diff, "")
                        },
                        label = { Text(diff.label, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NeonEmerald.copy(alpha = 0.2f),
                            selectedLabelColor = NeonEmerald
                        )
                    )
                }
            }
        }

        items(state.filteredTutorialModules) { module ->
            TutorialModuleCardItem(
                module = module,
                onToggleTask = { step -> viewModel.completeTutorialTask(module.id, step) },
                onCopyCode = { code ->
                    clipboardManager.setText(AnnotatedString(code))
                    Toast.makeText(context, "Code copied to clipboard!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
private fun TutorialModuleCardItem(
    module: TutorialModule,
    onToggleTask: (Int) -> Unit,
    onCopyCode: (String) -> Unit
) {
    var expandedCode by remember { mutableStateOf(true) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        border = BorderStroke(1.dp, CyberSurfaceBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = NeonEmerald.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = module.level.label.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonEmerald,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = "${module.durationMinutes} min • ${module.notebookFilename}",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = module.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = module.subtitle,
                fontSize = 10.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = module.explanation,
                fontSize = 11.sp,
                color = TextSecondary,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Code Snippet Box with Copy Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PYTHON/POCKETFLOW SOURCE CODE:",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 0.5.sp
                )
                IconButton(
                    onClick = { onCopyCode(module.codeSnippet) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Code",
                        tint = NeonCyan,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CyberBg,
                border = BorderStroke(0.5.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = module.codeSnippet,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = NeonCyan,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Step-by-Step Interactive Tasks
            Text(
                text = "EXECUTION TASKS & STATE DELTAS (${module.tasks.count { it.isCompleted }}/${module.tasks.size}):",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = NeonAmber,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            module.tasks.forEach { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .clickable { onToggleTask(task.step) }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Toggle",
                        tint = if (task.isCompleted) NeonEmerald else TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${task.step}. ${task.title}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (task.isCompleted) NeonEmerald else TextPrimary
                        )
                        Text(
                            text = "Cmd: ${task.command}  |  Delta: ${task.stateDelta}",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Definitions glossary
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                module.definitions.forEach { def ->
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = CyberSurfaceVariant,
                        border = BorderStroke(0.5.dp, CyberSurfaceBorder)
                    ) {
                        Column(modifier = Modifier.padding(6.dp)) {
                            Text(
                                text = def.term,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonViolet
                            )
                            Text(
                                text = def.codeOrFormula,
                                fontSize = 8.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------------------
// Tab 2: Awesome Frameworks & Toolkits
// -----------------------------------------------------------------------------------------
@Composable
private fun FrameworksToolkitsTab(state: DashboardUiState) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "CURATED AGENTIC & BIO-INTELLIGENCE FRAMEWORKS (AWESOME LIST)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan,
                letterSpacing = 0.5.sp
            )
        }

        items(state.frameworkToolkits) { fw ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = fw.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = fw.category,
                                fontSize = 9.sp,
                                color = NeonCyan
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = NeonAmber.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Star, contentDescription = "Stars", tint = NeonAmber, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = fw.stars,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = NeonAmber
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = fw.description,
                        fontSize = 11.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "CORE CAPABILITIES:",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonEmerald
                    )
                    fw.coreFeatures.forEach { feat ->
                        Text(
                            text = "• $feat",
                            fontSize = 9.sp,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // SDK Import snippet
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = CyberBg,
                        border = BorderStroke(0.5.dp, CyberSurfaceBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = fw.sdkImportExample,
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                color = NeonEmerald
                            )
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(fw.sdkImportExample))
                                    Toast.makeText(context, "SDK import copied!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextMuted, modifier = Modifier.size(12.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // APK Artifact Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Download, contentDescription = "APK", tint = NeonViolet, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = fw.artifactApkName,
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                color = NeonViolet
                            )
                        }

                        Text(
                            text = "Awesome Curated ✓",
                            fontSize = 9.sp,
                            color = NeonEmerald
                        )
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------------------
// Tab 3: Hardware Reviews, Drones & Affiliates
// -----------------------------------------------------------------------------------------
@Composable
private fun HardwareDronesTab(state: DashboardUiState) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "AUTONOMOUS DRONES & COMPANION EDGE SENSORS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan,
                letterSpacing = 0.5.sp
            )
        }

        items(state.droneSpecs) { drone ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.FlightTakeoff, contentDescription = "Drone", tint = NeonCyan, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = drone.modelName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = NeonRose.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${drone.telemetryBpm} BPM Pulse",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonRose,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Flight Time: ${drone.flightTimeMinutes} min", fontSize = 10.sp, color = TextSecondary)
                        Text(text = "Max Payload: ${drone.maxPayloadGrams}g", fontSize = 10.sp, color = TextSecondary)
                        Text(text = "Inference: ${drone.edgeAiInferenceFps} FPS", fontSize = 10.sp, color = NeonEmerald)
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Companion AI: ${drone.companionComputer} (${drone.px4FirmwareVersion})",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextMuted
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "HARDWARE REVIEWS, BENCHMARKS & SPONSORSHIPS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonAmber,
                letterSpacing = 0.5.sp
            )
        }

        items(state.hardwareReviews) { hw ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = hw.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = hw.sponsorName,
                                fontSize = 9.sp,
                                color = NeonAmber
                            )
                        }

                        Text(
                            text = "$${hw.priceUsd}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = NeonEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Specs Grid
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(CyberBg)
                            .padding(8.dp)
                    ) {
                        hw.hardwareSpecs.forEach { (k, v) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = k, fontSize = 9.sp, color = TextMuted)
                                Text(text = v, fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = TextSecondary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rating: ★ ${hw.rating} / 5.0",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonAmber
                        )

                        Button(
                            onClick = { Toast.makeText(context, "Affiliate link logged: ${hw.affiliateUrl}", Toast.LENGTH_SHORT).show() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan.copy(alpha = 0.2f),
                                contentColor = NeonCyan
                            ),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text("View Sponsor Deal ↗", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------------------
// Tab 4: Heartbeat & Soul BPM Telemetry
// -----------------------------------------------------------------------------------------
@Composable
private fun HeartbeatSoulBpmTab(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel
) {
    val infiniteTransition = rememberInfiniteTransition(label = "heartbeat_dialog")
    val heartbeatScale by infiniteTransition.animateFloat(
        initialValue = 0.90f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (60_000 / state.heartbeatSoul.currentBpm).coerceIn(250, 1500), easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heartbeat_scale_dialog"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, NeonRose.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "AGENTIC SOUL & HEARTBEAT CADENCE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonRose,
                        letterSpacing = 0.8.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .scale(heartbeatScale)
                            .clip(CircleShape)
                            .background(NeonRose.copy(alpha = 0.15f))
                            .border(2.dp, NeonRose, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Pulsing Soul",
                            tint = NeonRose,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "${state.heartbeatSoul.currentBpm} BPM",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = NeonRose
                    )

                    Text(
                        text = state.heartbeatSoul.soulState,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // BPM Slider Control
                    Text(
                        text = "ADJUST AGENT HEARTBEAT CADENCE (40 - 220 BPM):",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )

                    Slider(
                        value = state.heartbeatSoul.currentBpm.toFloat(),
                        onValueChange = { viewModel.adjustHeartbeatBpm(it.toInt()) },
                        valueRange = 40f..220f,
                        colors = SliderDefaults.colors(
                            thumbColor = NeonRose,
                            activeTrackColor = NeonRose,
                            inactiveTrackColor = CyberSurfaceVariant
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("slider_heartbeat_bpm")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Resting: 60 BPM", fontSize = 9.sp, color = TextMuted)
                        Text(text = "PocketFlow Sync: 120 BPM", fontSize = 9.sp, color = NeonEmerald)
                        Text(text = "High Burst: 200 BPM", fontSize = 9.sp, color = NeonAmber)
                    }
                }
            }
        }

        item {
            Text(
                text = "LIVE SYSTEM STATE DELTAS & ALGORITHMIC PULSES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan,
                letterSpacing = 0.5.sp
            )
        }

        items(state.heartbeatSoul.liveDeltas) { delta ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CyberSurface,
                border = BorderStroke(0.5.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = "Delta",
                        tint = NeonCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = delta,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------------------
// Tab 5: Bio-Intelligence & Evo Ecosystem
// -----------------------------------------------------------------------------------------
@Composable
private fun BioEvoEcosystemTab(state: DashboardUiState) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "BIOPROMPTS CATALOG & EVOVIBES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonViolet,
                letterSpacing = 0.5.sp
            )
        }

        items(state.bioprompts) { bp ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = bp.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = NeonViolet.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "EvoVibe: ${bp.evovibeRating}%",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonViolet,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Category: ${bp.category}", fontSize = 9.sp, color = NeonCyan)

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = CyberBg,
                        border = BorderStroke(0.5.dp, CyberSurfaceBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = bp.promptTemplate,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Expected Outcome: ${bp.expectedOutcome}",
                        fontSize = 9.sp,
                        color = NeonEmerald
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "ACTIVE BIOREACTORS & BIOREGIONS TELEMETRY",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonEmerald,
                letterSpacing = 0.5.sp
            )
        }

        items(state.bioreactors) { br ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                border = BorderStroke(1.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = br.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "${br.volumeLiters} Liters",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = NeonEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Bioregion: ${br.bioregion}", fontSize = 9.sp, color = TextMuted)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Temp: ${br.tempCelsius}°C", fontSize = 9.sp, color = TextSecondary)
                        Text(text = "pH: ${br.phLevel}", fontSize = 9.sp, color = TextSecondary)
                        Text(text = "DO: ${br.dissolvedOxygenPercent}%", fontSize = 9.sp, color = TextSecondary)
                        Text(text = "Biomass: ${br.biomassGramsPerLiter} g/L", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = NeonEmerald)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "EVOMATH & EVOCHEMISTRY REACTION PATHWAYS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonAmber,
                letterSpacing = 0.5.sp
            )
        }

        items(state.evoMathFormulas) { math ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CyberSurface,
                border = BorderStroke(0.5.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = math.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonAmber)
                    Text(
                        text = math.latexFormula,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = NeonCyan,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(text = math.explanation, fontSize = 9.sp, color = TextSecondary)
                }
            }
        }

        items(state.evoChemistryPaths) { chem ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CyberSurface,
                border = BorderStroke(0.5.dp, CyberSurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = chem.pathwayName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonRose)
                        Text(text = "ΔG: ${chem.deltaGKcalPerMol} kcal/mol", fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = NeonEmerald)
                    }
                    Text(
                        text = "Reactants: ${chem.reactants}",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextMuted
                    )
                    Text(
                        text = "Products: ${chem.products}",
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextSecondary
                    )
                    Text(
                        text = "Enzyme: ${chem.enzymeCatalyst} (${chem.kineticsRateConstant})",
                        fontSize = 9.sp,
                        color = NeonAmber
                    )
                }
            }
        }
    }
}
