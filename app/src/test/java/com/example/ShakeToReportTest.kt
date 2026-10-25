package com.example

import com.example.data.BugReportSeverity
import com.example.data.DashboardRepository
import com.example.ui.NexusDashboardViewModel
import com.example.util.ShakeDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ShakeToReportTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `shake detector invokes callback on simulated shake with debouncing`() {
        var shakeCount = 0
        val detector = ShakeDetector(
            shakeThresholdGForce = 2.7f,
            debounceMillis = 800L,
            onShake = { shakeCount++ }
        )

        // First shake should trigger callback
        detector.simulateShake()
        assertEquals(1, shakeCount)

        // Rapid subsequent shake within debounce window should be ignored
        detector.simulateShake()
        assertEquals(1, shakeCount)
    }

    @Test
    fun `triggerShakeReport populates diagnostic payload and opens dialog`() = runTest {
        val viewModel = NexusDashboardViewModel(repository = DashboardRepository())
        runCurrent()

        // Initially report dialog is closed
        assertFalse(viewModel.uiState.value.isReportDialogOpen)
        assertNull(viewModel.uiState.value.capturedDiagnostics)

        // Trigger shake report
        viewModel.triggerShakeReport()
        runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state.isReportDialogOpen)
        assertNotNull(state.capturedDiagnostics)

        val diag = state.capturedDiagnostics!!
        assertTrue(diag.deviceModel.isNotBlank())
        assertTrue(diag.memoryUsage.contains("Free:"))
        assertTrue(diag.activeWidgetsCount > 0)
        assertTrue(diag.recentTelemetry.isNotEmpty())
        assertEquals("Dashboard (Widgets)", diag.activeTabName)

        // Dismiss report dialog
        viewModel.dismissReportDialog()
        assertFalse(viewModel.uiState.value.isReportDialogOpen)

        viewModel.stopTimersForTesting()
    }

    @Test
    fun `submitBugReport packages report and displays confirmation banner`() = runTest {
        val viewModel = NexusDashboardViewModel(repository = DashboardRepository())
        runCurrent()

        viewModel.triggerShakeReport()
        runCurrent()

        // Submit report with critical severity and user observation notes
        viewModel.submitBugReport(
            severity = BugReportSeverity.CRITICAL,
            userNotes = "Widget FPS dropped during swarm compile"
        )

        // Advance coroutines past upload delay
        advanceTimeBy(500)
        runCurrent()

        val state = viewModel.uiState.value
        assertFalse(state.isReportDialogOpen)
        assertFalse(state.isSubmittingReport)
        assertNotNull(state.lastSubmittedReportId)
        assertTrue(state.lastSubmittedReportId!!.startsWith("NX-RPT-"))
        assertNotNull(state.reportSubmitSuccessBanner)
        assertTrue(state.reportSubmitSuccessBanner!!.contains(state.lastSubmittedReportId!!))

        // Check that telemetry feed contains the report upload event
        val latestLog = state.telemetryFeed.first()
        assertTrue(latestLog.message.contains(state.lastSubmittedReportId!!))
        assertTrue(latestLog.message.contains("CRITICAL"))

        // Dismiss banner
        viewModel.dismissSuccessBanner()
        assertNull(viewModel.uiState.value.reportSubmitSuccessBanner)

        viewModel.stopTimersForTesting()
    }
}
