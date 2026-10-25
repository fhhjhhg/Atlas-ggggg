package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.filled.ViewKanban
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.content.Context
import android.hardware.SensorManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.runtime.DisposableEffect
import com.example.data.WidgetType
import com.example.ui.components.ActivityLogDialog
import com.example.ui.components.DiagnosticReportDialog
import com.example.ui.components.MetricDataStreamChart
import com.example.ui.components.MetricEditorDialog
import com.example.ui.components.TopBarHeader
import com.example.ui.components.WidgetContainer
import com.example.ui.components.WidgetCustomizerDialog
import com.example.util.ShakeDetector
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
import com.example.ui.widgets.ActionSimulatorWidget
import com.example.ui.widgets.AgentEnclaveWidget
import com.example.ui.widgets.ArenaScoreWidget
import com.example.ui.widgets.AssetRegistryTableWidget
import com.example.ui.widgets.BioCommonsWidget
import com.example.ui.widgets.CompoundingChartWidget
import com.example.ui.widgets.ExecutiveMetricsWidget
import com.example.ui.widgets.LiveTelemetryWidget
import com.example.ui.widgets.MegaPillarsWidget
import com.example.auth.GoogleAuthManager
import com.example.ui.widgets.DiffusersTransformersWidget
import com.example.ui.widgets.HaradaHabitWidget
import com.example.ui.widgets.HistoricalTrendChartWidget
import com.example.ui.widgets.ScientificProverWidget
import com.example.bioscience.ui.BioscienceHubScreen
import com.example.ui.widgets.BioscienceHubWidget
import com.example.ui.components.ColivingLeaseHubDialog
import com.example.ui.widgets.ColivingDigitalTwinWidget
import com.example.ui.components.BookMyColivingDialog
import com.example.ui.widgets.BookMyColivingWidget
import com.example.ui.components.EmergentResearchDialog
import com.example.ui.widgets.EmergentResearchWidget
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import java.text.NumberFormat
import java.util.Locale

import androidx.compose.foundation.focusable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalHapticFeedback
import com.example.data.WorkspaceType
import com.example.ui.components.AiAssistantChatSheet
import com.example.ui.components.DashboardFeatureTour
import com.example.ui.components.GeminiPromptSettingsDialog
import com.example.ui.components.KeyboardShortcutsDialog
import com.example.ui.components.PdfReportExportDialog
import com.example.ui.components.SyncDetailsDialog
import com.example.ui.components.ThresholdAlertsDialog
import com.example.ui.components.VoiceCommandRecorderDialog
import com.example.ui.components.WidgetFocusOverlay
import com.example.ui.components.WidgetStoreDialog
import com.example.ui.widgets.ForecastingWidget

@Composable
fun DashboardScreen(
    viewModel: NexusDashboardViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember { CredentialManager.create(context) }
    val focusRequester = remember { FocusRequester() }
    val haptic = LocalHapticFeedback.current

    // Register hardware accelerometer shake detector with lifecycle cleanup
    DisposableEffect(context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        val shakeDetector = ShakeDetector(
            shakeThresholdGForce = 2.7f,
            debounceMillis = 800L,
            onShake = {
                viewModel.triggerShakeReport(context)
            }
        )
        if (sensorManager != null) {
            shakeDetector.start(sensorManager)
        }
        onDispose {
            shakeDetector.stop()
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        viewModel.initLocalMetrics(context)
        GoogleAuthManager.attemptAutoSignIn(
            context = context,
            credentialManager = credentialManager,
            onAuthSuccess = { user ->
                viewModel.initFirestore(context, user)
            },
            onUnauthenticated = {
                viewModel.initFirestore(context, null)
            },
            scope = coroutineScope
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .focusRequester(focusRequester)
            .focusable()
            .onPreviewKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    val isCtrl = event.isCtrlPressed || event.isMetaPressed
                    when {
                        isCtrl && event.key == Key.S -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.saveLayoutShortcut(context)
                            true
                        }
                        isCtrl && event.key == Key.F -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.openWidgetStore()
                            true
                        }
                        isCtrl && event.key == Key.P -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.openPdfExportDialog()
                            true
                        }
                        isCtrl && event.key == Key.R -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.triggerManualRefresh()
                            true
                        }
                        isCtrl && event.key == Key.T -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.startFeatureTour()
                            true
                        }
                        isCtrl && event.key == Key.D -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.toggleDemoMode()
                            true
                        }
                        isCtrl && event.key == Key.Spacebar -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.openAiAssistant()
                            true
                        }
                        event.key == Key.Slash || event.key == Key.Backslash -> {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.openKeyboardShortcutsCheatSheet()
                            true
                        }
                        else -> false
                    }
                } else false
            },
        containerColor = CyberBg,
        topBar = {
            TopBarHeader(
                activeWidgetsCount = state.widgets.count { it.isEnabled },
                totalWidgetsCount = state.widgets.size,
                secondsUntilNextRefresh = state.secondsUntilNextRefresh,
                refreshIntervalSeconds = state.refreshIntervalSeconds,
                isAutoRefreshEnabled = state.isAutoRefreshEnabled,
                isRefreshing = state.isRefreshing,
                currentUserEmail = state.currentUserEmail,
                cloudSyncStatus = state.cloudSyncStatus,
                databaseSyncState = state.databaseSyncState,
                onSignInGoogle = {
                    GoogleAuthManager.signInWithGoogle(
                        context = context,
                        credentialManager = credentialManager,
                        onAuthSuccess = { user ->
                            viewModel.initFirestore(context, user)
                        },
                        onAuthError = { /* non-fatal, logged */ },
                        scope = coroutineScope
                    )
                },
                onSignOut = {
                    GoogleAuthManager.signOut(
                        context = context,
                        credentialManager = credentialManager,
                        scope = coroutineScope,
                        onSignOutComplete = {
                            viewModel.initFirestore(context, null)
                        }
                    )
                },
                onTriggerRefresh = { viewModel.triggerManualRefresh() },
                onToggleAutoRefresh = { viewModel.toggleAutoRefresh() },
                onSelectInterval = { viewModel.setRefreshInterval(it) },
                onOpenCustomizer = { viewModel.setCustomizerOpen(true) },
                onTriggerShakeReport = { viewModel.triggerShakeReport(context) },
                onOpenActivityLog = { viewModel.openActivityLog() },
                onOpenSyncDetails = { viewModel.openSyncDetailsDialog() },
                onApplyTemplate = { viewModel.applyDashboardTemplate(it) },
                onOpenWidgetStore = { viewModel.openWidgetStore() },
                onOpenThresholdAlerts = { viewModel.openAlertThresholdsDialog() },
                onOpenVoiceCommands = { viewModel.openVoiceRecordingDialog() },
                onOpenPdfExport = { viewModel.openPdfExportDialog() },
                onOpenAiAssistant = { viewModel.openAiAssistant() },
                onOpenShortcutsCheatSheet = { viewModel.openKeyboardShortcutsCheatSheet() },
                isDemoModeActive = state.isDemoModeActive,
                onToggleDemoMode = { viewModel.toggleDemoMode() }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = CyberSurface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_nav_bar")
            ) {
                NavigationBarItem(
                    selected = state.activeTab == 0,
                    onClick = { viewModel.setActiveTab(0) },
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                    label = { Text("Dashboard", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
                    colors = navigationBarItemColors()
                )
                NavigationBarItem(
                    selected = state.activeTab == 1,
                    onClick = { viewModel.setActiveTab(1) },
                    icon = { Icon(Icons.Default.Biotech, contentDescription = "Bioscience Hub") },
                    label = { Text("Bioscience", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
                    colors = navigationBarItemColors()
                )
                NavigationBarItem(
                    selected = state.activeTab == 2,
                    onClick = { viewModel.setActiveTab(2) },
                    icon = { Icon(Icons.Default.Dataset, contentDescription = "Asset Registry") },
                    label = { Text("Registry", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
                    colors = navigationBarItemColors()
                )
                NavigationBarItem(
                    selected = state.activeTab == 3,
                    onClick = { viewModel.setActiveTab(3) },
                    icon = { Icon(Icons.Default.ShowChart, contentDescription = "Compounding Engine") },
                    label = { Text("Compound", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
                    colors = navigationBarItemColors()
                )
                NavigationBarItem(
                    selected = state.activeTab == 4,
                    onClick = { viewModel.setActiveTab(4) },
                    icon = { Icon(Icons.Default.Terminal, contentDescription = "Telemetry Stream") },
                    label = { Text("Telemetry", fontSize = 10.sp, fontWeight = FontWeight.SemiBold) },
                    colors = navigationBarItemColors()
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CyberBg)
        ) {
            when (state.activeTab) {
                0 -> CustomizableWidgetsView(
                    state = state,
                    viewModel = viewModel
                )
                1 -> BioscienceHubScreen()
                2 -> FullAssetRegistryView(
                    state = state,
                    viewModel = viewModel
                )
                3 -> InteractiveCompoundingView(
                    state = state,
                    viewModel = viewModel
                )
                4 -> FullTelemetryLogView(
                    state = state
                )
            }

            // Widget Customizer Dialog
            if (state.isCustomizerOpen) {
                WidgetCustomizerDialog(
                    widgets = state.widgets,
                    onToggleWidget = { viewModel.toggleWidget(it) },
                    onMoveUp = { viewModel.moveWidgetUp(it) },
                    onMoveDown = { viewModel.moveWidgetDown(it) },
                    onResetToDefault = { viewModel.resetWidgetsToDefault() },
                    onDismiss = { viewModel.setCustomizerOpen(false) }
                )
            }

            // Diagnostic Shake-to-Report Dialog
            if (state.isReportDialogOpen && state.capturedDiagnostics != null) {
                DiagnosticReportDialog(
                    diagnostics = state.capturedDiagnostics!!,
                    isSubmitting = state.isSubmittingReport,
                    onSubmit = { severity, userNotes ->
                        viewModel.submitBugReport(severity, userNotes)
                    },
                    onDismiss = { viewModel.dismissReportDialog() }
                )
            }

            // Activity Log Troubleshooting Dialog
            if (state.isActivityLogDialogOpen) {
                ActivityLogDialog(
                    activities = state.activityLog,
                    isAnalyzing = state.isAnalyzingActivityLog,
                    analysis = state.activityLogAnalysis,
                    analysisError = state.activityLogAnalysisError,
                    onAnalyzeWithGemini = { viewModel.analyzeActivityLogWithGemini() },
                    onDismiss = { viewModel.dismissActivityLog() }
                )
            }

            // User-Defined Performance Metrics Editor Dialog (Room Database)
            if (state.isMetricEditorDialogOpen) {
                MetricEditorDialog(
                    metrics = state.userPerformanceMetrics,
                    onAddMetric = { name, value, unit, cat ->
                        viewModel.addUserPerformanceMetric(name, value, unit, cat)
                    },
                    onDeleteMetric = { id ->
                        viewModel.deleteUserPerformanceMetric(id)
                    },
                    onDismiss = { viewModel.dismissMetricEditor() }
                )
            }

            // Everything Coliving Legal Hub & Digital Twin Dialog
            if (state.isColivingLeaseDialogOpen) {
                ColivingLeaseHubDialog(
                    selectedBed = state.selectedColivingBed,
                    config = state.colivingContractConfig,
                    contractText = state.generatedColivingLease,
                    isAuditing = state.isAuditingColivingLease,
                    auditSummary = state.colivingLegalAuditSummary,
                    onConfigChange = { viewModel.updateColivingContractConfig(it) },
                    onDeployTwin = { bedId, resident, type ->
                        viewModel.deployColivingTwin(bedId, resident, type)
                    },
                    onAuditWithGemini = { viewModel.auditColivingLeaseWithGemini() },
                    onDismiss = { viewModel.dismissColivingLeaseDialog() }
                )
            }

            // BookMyColiving Global Platform & Stack Workstation Dialog
            if (state.isBookMyColivingDialogOpen) {
                BookMyColivingDialog(
                    spaces = state.filteredColivingListings,
                    domains = state.domainAssets,
                    artifacts = state.softwareArtifacts,
                    hardwareBom = state.hardwareBom,
                    sowPhases = state.sowPhases,
                    operatingSops = state.operatingSops,
                    mcpServers = state.mcpServers,
                    ragDocs = state.ragDocs,
                    tokenomics = state.tokenomics,
                    dxiMetrics = state.dxiMetrics,
                    selectedVibe = state.selectedColivingVibeFilter,
                    searchQuery = state.bookMyColivingSearchQuery,
                    onFilterChange = { vibe, q -> viewModel.filterBookMyColivingSpaces(vibe, q) },
                    onDirectInquire = { id, email, name -> viewModel.submitDirectColivingInquiry(id, email, name) },
                    onDismiss = { viewModel.dismissBookMyColivingDialog() }
                )
            }

            // Emergent Research & PocketFlow Workstation Dialog
            if (state.isEmergentResearchDialogOpen) {
                EmergentResearchDialog(
                    state = state,
                    viewModel = viewModel,
                    onDismiss = { viewModel.dismissEmergentResearchDialog() }
                )
            }

            // Report submission confirmation feedback banner
            AnimatedVisibility(
                visible = state.reportSubmitSuccessBanner != null,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                state.reportSubmitSuccessBanner?.let { message ->
                    Surface(
                        color = CyberSurface,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, NeonEmerald),
                        shadowElevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .testTag("report_success_banner")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Success",
                                    tint = NeonEmerald,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = message,
                                    color = TextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            IconButton(
                                onClick = { viewModel.dismissSuccessBanner() },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss",
                                    tint = TextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // FCM Excursion Alert Banner
            AnimatedVisibility(
                visible = state.lastFcmAlertBanner != null,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 70.dp, start = 16.dp, end = 16.dp)
            ) {
                state.lastFcmAlertBanner?.let { alertMsg ->
                    Surface(
                        color = CyberSurface,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, NeonRose),
                        shadowElevation = 10.dp,
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .testTag("fcm_alert_banner")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NotificationsActive,
                                    contentDescription = "FCM Alert",
                                    tint = NeonRose,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = alertMsg,
                                    color = TextPrimary,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 15.sp
                                )
                            }
                            IconButton(
                                onClick = { viewModel.dismissFcmAlertBanner() },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss",
                                    tint = TextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // PDF Report Export Dialog
            if (state.isPdfExportDialogOpen) {
                PdfReportExportDialog(
                    isGenerating = state.isGeneratingPdf,
                    report = state.lastGeneratedPdf,
                    onGenerateReport = { viewModel.generatePdfReport(context) },
                    onDismiss = { viewModel.dismissPdfExportDialog() }
                )
            }

            // Multi-Step Onboarding Feature Tour
            if (state.isFeatureTourActive) {
                DashboardFeatureTour(
                    currentStepIndex = state.featureTourStepIndex,
                    onNextStep = { viewModel.nextTourStep() },
                    onPreviousStep = { viewModel.previousTourStep() },
                    onDismiss = { viewModel.dismissFeatureTour() }
                )
            }

            // Custom Gemini System Prompt & Testing Sandbox Dialog
            if (state.isGeminiPromptSettingsOpen) {
                GeminiPromptSettingsDialog(
                    currentPrompt = state.customGeminiSystemPrompt,
                    selectedCategory = state.recommendationFilter,
                    testScenarios = state.testScenarios,
                    selectedScenario = state.selectedTestScenario,
                    evaluation = state.lastPromptEvaluation,
                    isTesting = state.isTestingPrompt,
                    onUpdatePrompt = { viewModel.updateCustomGeminiSystemPrompt(it) },
                    onResetPrompt = { viewModel.resetGeminiSystemPromptToDefault() },
                    onSelectCategory = { viewModel.setRecommendationFilter(it) },
                    onSelectScenario = { viewModel.selectTestScenario(it) },
                    onRunSimulation = { viewModel.runPromptTestSimulation() },
                    onApplyToProduction = { viewModel.applyPromptToProduction() },
                    onDismiss = { viewModel.dismissGeminiPromptSettings() }
                )
            }

            // Voice Command Recorder & Audio Trigger Dialog
            if (state.isVoiceRecordingDialogOpen) {
                VoiceCommandRecorderDialog(
                    commands = state.voiceCommands,
                    isListening = state.isListeningToVoice,
                    recordedPhrase = state.lastRecordedVoicePhrase,
                    onExecuteCommand = { viewModel.executeVoiceCommand(it, context) },
                    onSaveCustomCommand = { phrase, action -> viewModel.addCustomVoiceCommand(phrase, action) },
                    onDismiss = { viewModel.dismissVoiceRecordingDialog() }
                )
            }

            // Persistent AI Assistant Natural Language Chat Sheet
            if (state.isAiAssistantOpen) {
                AiAssistantChatSheet(
                    messages = state.assistantChatMessages,
                    isThinking = state.isAssistantThinking,
                    onSendMessage = { viewModel.sendChatMessage(it) },
                    onExecuteSmartAction = { viewModel.executeSmartAction(it, context) },
                    onDismiss = { viewModel.dismissAiAssistant() }
                )
            }

            // Power-User Keyboard Shortcuts Dialog
            if (state.isKeyboardShortcutsCheatSheetOpen) {
                KeyboardShortcutsDialog(
                    onDismiss = { viewModel.dismissKeyboardShortcutsCheatSheet() }
                )
            }

            // Threshold Alerts & Firebase Cloud Messaging Dialog
            if (state.isAlertThresholdsDialogOpen) {
                ThresholdAlertsDialog(
                    rules = state.thresholdRules,
                    alerts = state.triggeredAlerts,
                    onToggleRule = { viewModel.toggleThresholdRule(it) },
                    onTriggerTestFcm = { viewModel.triggerSimulatedFcmAlert(it) },
                    onDismiss = { viewModel.dismissAlertThresholdsDialog() }
                )
            }

            // Internal 'Widget Store' Marketplace Dialog
            if (state.isWidgetStoreDialogOpen) {
                WidgetStoreDialog(
                    widgets = state.storeWidgets,
                    selectedCategory = state.selectedStoreCategory,
                    searchQuery = state.storeSearchQuery,
                    onCategorySelect = { viewModel.setStoreCategory(it) },
                    onSearchChange = { viewModel.setStoreSearchQuery(it) },
                    onInstallWidget = { viewModel.installStoreWidget(it) },
                    onDismiss = { viewModel.dismissWidgetStore() }
                )
            }

            // Database Connectivity & Room <-> Firestore Sync Details Dialog
            if (state.isSyncDetailsDialogOpen) {
                SyncDetailsDialog(
                    syncState = state.databaseSyncState,
                    lastSyncTimestamp = state.lastSyncTimestamp,
                    roomRecordsCount = state.roomRecordsCount,
                    currentUserEmail = state.currentUserEmail,
                    onForceSync = { viewModel.forceCloudSync() },
                    onDismiss = { viewModel.dismissSyncDetailsDialog() }
                )
            }

            // Focus Mode Full-Screen Overlay
            state.focusedWidget?.let { focusedWidget ->
                WidgetFocusOverlay(
                    widget = focusedWidget,
                    state = state,
                    existingNote = state.widgetNotes[focusedWidget.id] ?: "",
                    onSaveNote = { note -> viewModel.updateWidgetNote(focusedWidget.id, note) },
                    onDismiss = { viewModel.dismissWidgetFocus() }
                )
            }
        }
    }
}

@Composable
private fun CustomizableWidgetsView(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel
) {
    val haptic = LocalHapticFeedback.current
    val activeWidgets = state.widgets.filter {
        it.isEnabled && (state.currentWorkspace == WorkspaceType.ALL || it.widgetType in state.currentWorkspace.includedWidgetTypes)
    }.sortedBy { it.order }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("customizable_widgets_list"),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                // Workspace Selector Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "WORKSPACE:",
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    WorkspaceType.values().forEach { ws ->
                        FilterChip(
                            selected = state.currentWorkspace == ws,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.setWorkspace(ws)
                            },
                            label = { Text(ws.label, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                                selectedLabelColor = NeonCyan,
                                containerColor = CyberSurface,
                                labelColor = TextSecondary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = state.currentWorkspace == ws,
                                borderColor = CyberSurfaceBorder,
                                selectedBorderColor = NeonCyan
                            ),
                            modifier = Modifier.testTag("workspace_chip_${ws.name}")
                        )
                    }
                }

                if (state.isDemoModeActive) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("demo_mode_banner"),
                        shape = RoundedCornerShape(8.dp),
                        color = NeonAmber.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, NeonAmber.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Science, contentDescription = null, tint = NeonAmber, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "DEMO SIMULATION ACTIVE (Randomized Synthetic Data)",
                                    color = NeonAmber,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Exit Demo",
                                color = TextPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.toggleDemoMode()
                                }
                            )
                        }
                    }
                }
            }
        }

        itemsIndexed(activeWidgets, key = { _, item -> item.id }) { index, widget ->
            val isFirst = index == 0
            val isLast = index == activeWidgets.size - 1

            when (widget.widgetType) {
                WidgetType.EXECUTIVE_METRICS -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Analytics,
                        accentColor = NeonCyan,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        ExecutiveMetricsWidget(
                            state = state,
                            onOpenMetricEditor = { viewModel.openMetricEditor() }
                        )
                    }
                }
                WidgetType.ARENA_SCORE -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.AutoAwesome,
                        accentColor = NeonEmerald,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        ArenaScoreWidget(state = state)
                    }
                }
                WidgetType.LIVE_TELEMETRY -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Terminal,
                        accentColor = NeonCyan,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        LiveTelemetryWidget(state = state)
                    }
                }
                WidgetType.COMPOUNDING_CHART -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.ShowChart,
                        accentColor = NeonAmber,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        CompoundingChartWidget(
                            state = state,
                            onUpdateInputs = { beds, rent, cagr, market ->
                                viewModel.updateCompoundingInputs(beds, rent, cagr, market)
                            }
                        )
                    }
                }
                WidgetType.HISTORICAL_TREND_CHART -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.TrendingUp,
                        accentColor = NeonCyan,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        HistoricalTrendChartWidget(
                            state = state,
                            onSelectMetric = { viewModel.setSelectedHistoricalMetric(it) },
                            onSelectRange = { viewModel.setSelectedHistoricalRange(it) },
                            onToggleLive = { viewModel.toggleHistoricalTrendLive() },
                            onSimulateTick = { viewModel.simulateHistoricalTrendTick() }
                        )
                    }
                }
                WidgetType.ASSET_REGISTRY -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Dataset,
                        accentColor = NeonViolet,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        AssetRegistryTableWidget(
                            state = state,
                            onSearchChange = { viewModel.filterAssets(it) }
                        )
                    }
                }
                WidgetType.MEGA_PILLARS -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.ViewKanban,
                        accentColor = NeonCyan,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        MegaPillarsWidget(state = state)
                    }
                }
                WidgetType.BIOCOMMONS_MATRIX -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Biotech,
                        accentColor = NeonEmerald,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        BioCommonsWidget(state = state)
                    }
                }
                WidgetType.DIFFUSERS_TRANSFORMERS -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Science,
                        accentColor = NeonCyan,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        DiffusersTransformersWidget(state = state)
                    }
                }
                WidgetType.SCIENTIFIC_PROVER -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Calculate,
                        accentColor = NeonAmber,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        ScientificProverWidget(state = state)
                    }
                }
                WidgetType.HARADA_HABIT_MATRIX -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.CheckCircle,
                        accentColor = NeonEmerald,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        HaradaHabitWidget(state = state)
                    }
                }
                WidgetType.ACTION_SIMULATOR -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.PlayCircle,
                        accentColor = NeonRose,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        ActionSimulatorWidget(
                            state = state,
                            onExecuteWin = { viewModel.executeActionWin(it) }
                        )
                    }
                }
                WidgetType.AGENTIC_ENCLAVE -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Hub,
                        accentColor = NeonViolet,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        AgentEnclaveWidget(state = state)
                    }
                }
                WidgetType.BIOSCIENCE_HUB -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Biotech,
                        accentColor = NeonCyan,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        BioscienceHubWidget(
                            state = state,
                            onOpenBioscience = { viewModel.setActiveTab(1) }
                        )
                    }
                }
                WidgetType.COLIVING_DIGITAL_TWIN -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.HomeWork,
                        accentColor = NeonCyan,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        ColivingDigitalTwinWidget(
                            state = state,
                            onOpenBedContract = { bed -> viewModel.openColivingLeaseDialog(bed) },
                            onOpenHub = { viewModel.openColivingLeaseDialog(null) }
                        )
                    }
                }
                WidgetType.BOOK_MY_COLIVING -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.Apartment,
                        accentColor = NeonEmerald,
                        onMoveUp = { viewModel.moveWidgetUp(widget.id) },
                        onMoveDown = { viewModel.moveWidgetDown(widget.id) },
                        onHide = { viewModel.toggleWidget(widget.id) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        BookMyColivingWidget(
                            state = state,
                            onOpenHub = { viewModel.openBookMyColivingDialog() }
                        )
                    }
                }
                WidgetType.EMERGENT_RESEARCH_KNOWLEDGE -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.MenuBook,
                        accentColor = NeonCyan,
                        onMoveUp = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.moveWidgetUp(widget.id)
                        },
                        onMoveDown = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.moveWidgetDown(widget.id)
                        },
                        onHide = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.toggleWidget(widget.id)
                        },
                        onFocus = { viewModel.openWidgetFocus(widget) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        EmergentResearchWidget(
                            state = state,
                            onOpenHub = { viewModel.openEmergentResearchDialog() }
                        )
                    }
                }
                WidgetType.METRIC_FORECASTING -> {
                    WidgetContainer(
                        widget = widget,
                        icon = Icons.Default.TrendingUp,
                        accentColor = NeonCyan,
                        onMoveUp = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.moveWidgetUp(widget.id)
                        },
                        onMoveDown = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.moveWidgetDown(widget.id)
                        },
                        onHide = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.toggleWidget(widget.id)
                        },
                        onFocus = { viewModel.openWidgetFocus(widget) },
                        isFirst = isFirst,
                        isLast = isLast
                    ) {
                        ForecastingWidget(state = state)
                    }
                }
            }
        }
    }
}

@Composable
private fun FullAssetRegistryView(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("full_asset_registry_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "MASTER ASSET & SKU REGISTRY",
                color = TextPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )
            Text(
                text = "${state.assets.size} Assets • Real Estate, APIs, Mobile APKs & Tool SDKs",
                color = TextSecondary,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            AssetRegistryTableWidget(
                state = state,
                onSearchChange = { viewModel.filterAssets(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(state.filteredAssets) { asset ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = CyberSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberSurfaceBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = asset.id,
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Box(
                            modifier = Modifier
                                .background(NeonEmerald.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = asset.status,
                                color = NeonEmerald,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = asset.name,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${asset.assetType} • ${asset.category}",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("MONETIZATION STREAM", color = TextMuted, fontSize = 9.sp)
                            Text(asset.monetization, color = NeonViolet, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("MONTHLY YIELD / VAL", color = TextMuted, fontSize = 9.sp)
                            Text(
                                text = "${currencyFormat.format(asset.monthlyYieldUsd)} / ${currencyFormat.format(asset.valuationUsd)}",
                                color = TextPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InteractiveCompoundingView(
    state: DashboardUiState,
    viewModel: NexusDashboardViewModel
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("interactive_compounding_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "15-YEAR WEALTH VELOCITY ENGINE",
                color = TextPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )
            Text(
                text = "Dual-LLC AssetCo & OpCo Compounding Cash Flow Simulator",
                color = TextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            CompoundingChartWidget(
                state = state,
                onUpdateInputs = { beds, rent, cagr, market ->
                    viewModel.updateCompoundingInputs(beds, rent, cagr, market)
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Interactive Sliders for Sensitivity Analysis
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = CyberSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberSurfaceBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "SENSITIVITY PARAMETERS",
                        color = NeonCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Beds slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Co-Living Beds Count", color = TextSecondary, fontSize = 11.sp)
                        Text("${state.bedCount} Beds", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                    Slider(
                        value = state.bedCount.toFloat(),
                        onValueChange = { viewModel.updateCompoundingInputs(beds = it.toInt()) },
                        valueRange = 10f..80f,
                        steps = 14,
                        colors = sliderColors()
                    )

                    // Rent per bed slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Monthly Rent Per Bed", color = TextSecondary, fontSize = 11.sp)
                        Text("$${state.rentPerBed.toInt()}/bed", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                    Slider(
                        value = state.rentPerBed.toFloat(),
                        onValueChange = { viewModel.updateCompoundingInputs(rent = it.toDouble()) },
                        valueRange = 500f..1500f,
                        steps = 20,
                        colors = sliderColors()
                    )
                }
            }
        }

        item {
            Text(
                text = "15-YEAR MILESTONE SCHEDULE",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        items(state.compoundingYears) { milestone ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = CyberSurfaceVariant,
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberSurfaceBorder.copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "YEAR ${milestone.year}",
                            color = NeonAmber,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "Gross: ${currencyFormat.format(milestone.grossRevenue)}",
                            color = TextMuted,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = currencyFormat.format(milestone.liquidPortfolio),
                            color = NeonCyan,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "Net: ${currencyFormat.format(milestone.netCashFlow)}/yr",
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

@Composable
private fun FullTelemetryLogView(state: DashboardUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("full_telemetry_log_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "LIVE SYSTEM TELEMETRY LEDGER",
                color = TextPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
            )
            Text(
                text = "Real-time subagent task traces, Ed25519 commercial signatures, and WAL checkpoints",
                color = TextSecondary,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            val streamValues = remember(state.telemetryFeed) {
                if (state.telemetryFeed.isEmpty()) {
                    listOf(20.0, 24.0, 28.0, 32.0, 36.0, 42.0)
                } else {
                    state.telemetryFeed.take(15).reversed().mapIndexed { i, ev ->
                        30.0 + (i * 2.5) + (if (ev.severity == com.example.data.TelemetrySeverity.YIELD) 12.0 else 0.0)
                    }
                }
            }

            MetricDataStreamChart(
                title = "TELEMETRY METRIC INGESTION STREAM",
                streamValues = streamValues,
                unit = "events/s",
                lineColor = NeonEmerald,
                metricLabel = "RATE",
                sampleRateMs = (state.refreshIntervalSeconds * 1000L) / 10
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(state.telemetryFeed) { event ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = CyberSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberSurfaceBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = event.timeLabel,
                                color = TextMuted,
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "[${event.pillarId}]",
                                color = NeonViolet,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = event.nodeName,
                                color = NeonCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Text(
                            text = event.deltaImpact,
                            color = NeonEmerald,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = event.message,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun navigationBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Color.Black,
    selectedTextColor = NeonCyan,
    indicatorColor = NeonCyan,
    unselectedIconColor = TextSecondary,
    unselectedTextColor = TextSecondary
)

@Composable
private fun sliderColors() = SliderDefaults.colors(
    thumbColor = NeonCyan,
    activeTrackColor = NeonCyan,
    inactiveTrackColor = CyberSurfaceBorder
)
