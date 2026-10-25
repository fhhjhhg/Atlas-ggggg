package com.example

import com.example.ai.GeminiAiService
import com.example.ui.NexusDashboardViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ActivityLogTest {

    @Test
    fun `initial activity log is populated with bootstrap events`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        val state = viewModel.uiState.value
        assertFalse(state.activityLog.isEmpty())
        assertTrue(state.activityLog.size <= 20)
        assertTrue(state.activityLog.any { it.action.contains("Dashboard session initialized") })
    }

    @Test
    fun `recordActivity limits list to last 20 actions`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        // Add 25 actions
        for (i in 1..25) {
            viewModel.recordActivity("Test action #$i", "TEST")
        }

        val activities = viewModel.uiState.value.activityLog
        assertEquals(20, activities.size)
        // Most recent should be action #25
        assertEquals("Test action #25", activities.first().action)
        assertEquals("TEST", activities.first().category)
    }

    @Test
    fun `user actions correctly record activity entries`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        // 1. Toggle widget
        viewModel.toggleWidget("widget-telemetry")
        val afterToggle = viewModel.uiState.value.activityLog.first()
        assertTrue(afterToggle.action.contains("widget-telemetry"))
        assertEquals("WIDGET", afterToggle.category)

        // 2. Change refresh interval
        viewModel.setRefreshInterval(15)
        val afterInterval = viewModel.uiState.value.activityLog.first()
        assertEquals("Refresh interval set to 15s", afterInterval.action)
        assertEquals("SETTINGS", afterInterval.category)

        // 3. Toggle auto-refresh
        viewModel.toggleAutoRefresh()
        val afterAutoRefresh = viewModel.uiState.value.activityLog.first()
        assertTrue(afterAutoRefresh.action.contains("Auto-refresh toggled"))
        assertEquals("SETTINGS", afterAutoRefresh.category)

        // 4. Trigger manual refresh
        viewModel.triggerManualRefresh()
        val afterManualRefresh = viewModel.uiState.value.activityLog.first()
        assertEquals("Manual refresh triggered", afterManualRefresh.action)
        assertEquals("SYSTEM", afterManualRefresh.category)
    }

    @Test
    fun `activity log dialog open and dismiss state updates`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        assertFalse(viewModel.uiState.value.isActivityLogDialogOpen)

        viewModel.openActivityLog()
        assertTrue(viewModel.uiState.value.isActivityLogDialogOpen)

        viewModel.dismissActivityLog()
        assertFalse(viewModel.uiState.value.isActivityLogDialogOpen)
    }
}
