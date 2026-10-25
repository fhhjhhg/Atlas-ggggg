package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AlertSeverity
import com.example.data.DashboardRepository
import com.example.data.DashboardTemplate
import com.example.data.DatabaseSyncState
import com.example.data.MetricThresholdRule
import com.example.data.ThresholdCondition
import com.example.data.VoiceActionType
import com.example.data.VoiceCommandRecord
import com.example.data.WidgetType
import com.example.data.WorkspaceType
import com.example.ui.NexusDashboardViewModel
import com.example.util.LinearRegressionCalculator
import com.example.util.PdfReportGenerator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class NexusPowerFeaturesTest {

    @Test
    fun testLinearRegressionCalculator() {
        val historicalPoints = listOf(
            10.0 to 100.0,
            20.0 to 120.0,
            30.0 to 140.0,
            40.0 to 160.0,
            50.0 to 180.0
        ).mapIndexed { idx, pair ->
            com.example.data.MetricHistoryPoint(
                index = idx,
                xValue = pair.first,
                yValue = pair.second,
                timestamp = System.currentTimeMillis() + (idx * 60000L),
                timeLabel = "T+$idx"
            )
        }

        val forecast = LinearRegressionCalculator.calculateForecast(
            points = historicalPoints,
            futureSteps = 5,
            stepSize = 10.0,
            metricName = "Compounding Yield",
            unit = "$"
        )

        // Perfect linear line y = 2x + 80, slope should be 2.0, intercept 80.0, R2 = 1.0
        assertEquals(2.0, forecast.slope, 0.01)
        assertEquals(80.0, forecast.intercept, 0.01)
        assertEquals(1.0, forecast.rSquared, 0.01)
        assertEquals(com.example.data.TrendDirection.UPWARD, forecast.trendDirection)
        assertEquals(5, forecast.projectedPoints.size)

        // For first projected point x = 60, projected y = 2*60 + 80 = 200
        assertEquals(200.0, forecast.projectedPoints.first().projectedY, 0.1)
        assertTrue(forecast.projectedPoints.first().upperConfidenceBound >= forecast.projectedPoints.first().projectedY)
        assertTrue(forecast.projectedPoints.first().lowerConfidenceBound <= forecast.projectedPoints.first().projectedY)
    }

    @Test
    fun testPdfReportGenerator() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = DashboardRepository()
        val widgets = repo.getDefaultWidgets()

        val report = PdfReportGenerator.generateDashboardPerformancePdf(
            context = context,
            widgets = widgets,
            grossAnnualRevenue = 348600.0,
            netCashFlow = 175596.0,
            weeklyHoursSaved = 2500.0,
            arenaScore = 95.8f,
            telemetryEventsCount = 142
        )

        assertNotNull(report)
        assertTrue(report.fileSizeBytes > 0)
        assertTrue(report.filePath.endsWith(".pdf"))
        assertEquals("NEXUS_Performance_Report.pdf", report.fileName)
    }

    @Test
    fun testDashboardTemplatesApplication() = runTest {
        val vm = NexusDashboardViewModel()
        vm.stopTimersForTesting()

        // Apply ANALYST template
        vm.applyDashboardTemplate(DashboardTemplate.ANALYST)
        val analystState = vm.uiState.value
        val enabledWidgets = analystState.widgets.filter { it.isEnabled }.map { it.widgetType }

        assertTrue(enabledWidgets.contains(WidgetType.METRIC_FORECASTING))
        assertTrue(enabledWidgets.contains(WidgetType.EXECUTIVE_METRICS))
        assertTrue(enabledWidgets.contains(WidgetType.COMPOUNDING_CHART))

        // Apply DEVELOPER template
        vm.applyDashboardTemplate(DashboardTemplate.DEVELOPER)
        val devState = vm.uiState.value
        val devWidgets = devState.widgets.filter { it.isEnabled }.map { it.widgetType }

        assertTrue(devWidgets.contains(WidgetType.LIVE_TELEMETRY))
        assertTrue(devWidgets.contains(WidgetType.AGENTIC_ENCLAVE))
        assertTrue(devWidgets.contains(WidgetType.SCIENTIFIC_PROVER))
    }

    @Test
    fun testFcmThresholdAlertsTrigger() = runTest {
        val vm = NexusDashboardViewModel()
        vm.stopTimersForTesting()

        val rule = MetricThresholdRule(
            id = "test-rule-cpu",
            metricName = "Cluster CPU",
            condition = ThresholdCondition.GREATER_THAN,
            thresholdValue = 90.0,
            unit = "%",
            severity = AlertSeverity.CRITICAL,
            fcmTopic = "test_topic"
        )

        val initialAlertsCount = vm.uiState.value.triggeredAlerts.size
        vm.triggerSimulatedFcmAlert(rule)

        val updatedState = vm.uiState.value
        assertEquals(initialAlertsCount + 1, updatedState.triggeredAlerts.size)
        assertNotNull(updatedState.lastFcmAlertBanner)
        assertTrue(updatedState.lastFcmAlertBanner!!.contains("Cluster CPU"))
    }

    @Test
    fun testWidgetStoreInstallation() = runTest {
        val vm = NexusDashboardViewModel()
        vm.stopTimersForTesting()

        val storeWidget = vm.uiState.value.storeWidgets.first()
        vm.installStoreWidget(storeWidget.id)

        val updated = vm.uiState.value
        val installed = updated.storeWidgets.first { it.id == storeWidget.id }
        assertTrue(installed.isInstalled)
        assertTrue(updated.widgets.any { it.widgetType == storeWidget.targetWidgetType && it.isEnabled })
    }

    @Test
    fun testWorkspacesAndVoiceCommands() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val vm = NexusDashboardViewModel()
        vm.stopTimersForTesting()

        // Workspaces filtering
        vm.setWorkspace(WorkspaceType.EXECUTIVE)
        assertEquals(WorkspaceType.EXECUTIVE, vm.uiState.value.currentWorkspace)

        // Voice Command execution
        val voiceCmd = VoiceCommandRecord(
            id = "vc-test",
            triggerPhrase = "open widget store",
            actionType = VoiceActionType.OPEN_CUSTOMIZER,
            description = "Opens the widget customizer"
        )
        vm.executeVoiceCommand(voiceCmd, context)
        assertTrue(vm.uiState.value.isCustomizerOpen)

        // Demo Mode toggle
        vm.toggleDemoMode()
        assertTrue(vm.uiState.value.isDemoModeActive)
        vm.toggleDemoMode()
        org.junit.Assert.assertFalse(vm.uiState.value.isDemoModeActive)
    }

    @Test
    fun testDatabaseSyncState() = runTest {
        val vm = NexusDashboardViewModel()
        vm.stopTimersForTesting()

        assertEquals(DatabaseSyncState.SYNCED, vm.uiState.value.databaseSyncState)
        assertEquals(42, vm.uiState.value.roomRecordsCount)
    }
}
