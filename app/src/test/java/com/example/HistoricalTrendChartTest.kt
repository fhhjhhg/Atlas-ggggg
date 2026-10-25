package com.example

import com.example.data.DashboardRepository
import com.example.data.HistoricalMetricType
import com.example.data.HistoricalTimeRange
import com.example.data.WidgetType
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
class HistoricalTrendChartTest {

    @Test
    fun `historical trend widget is present in repository default widgets`() {
        val repo = DashboardRepository()
        val widgets = repo.getDefaultWidgets()
        val trendWidget = widgets.find { it.widgetType == WidgetType.HISTORICAL_TREND_CHART }
        assertNotNull(trendWidget)
        assertEquals("widget_historical_trend", trendWidget?.id)
        assertTrue(trendWidget?.isEnabled == true)
    }

    @Test
    fun `initial historical trend data is loaded in viewmodel`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        val state = viewModel.uiState.value
        assertFalse(state.historicalTrendPoints.isEmpty())
        assertTrue(state.historicalTrendPoints.size >= 20)

        val firstPoint = state.historicalTrendPoints.first()
        assertTrue(firstPoint.grossRevenue > 300000.0)
        assertTrue(firstPoint.hoursSaved > 2000.0)
        assertTrue(firstPoint.swarmThroughput > 50.0)
        assertTrue(firstPoint.arenaScore in 80.0..100.0)
    }

    @Test
    fun `selecting different metrics updates state and records activity`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        assertEquals(HistoricalMetricType.GROSS_REVENUE, viewModel.uiState.value.selectedHistoricalMetric)

        // Select HOURS_SAVED
        viewModel.setSelectedHistoricalMetric(HistoricalMetricType.HOURS_SAVED)
        assertEquals(HistoricalMetricType.HOURS_SAVED, viewModel.uiState.value.selectedHistoricalMetric)
        assertTrue(viewModel.uiState.value.activityLog.first().action.contains("Hours Saved"))

        // Select SWARM_THROUGHPUT
        viewModel.setSelectedHistoricalMetric(HistoricalMetricType.SWARM_THROUGHPUT)
        assertEquals(HistoricalMetricType.SWARM_THROUGHPUT, viewModel.uiState.value.selectedHistoricalMetric)

        // Select ARENA_SCORE
        viewModel.setSelectedHistoricalMetric(HistoricalMetricType.ARENA_SCORE)
        assertEquals(HistoricalMetricType.ARENA_SCORE, viewModel.uiState.value.selectedHistoricalMetric)
    }

    @Test
    fun `selecting different time ranges updates state`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        assertEquals(HistoricalTimeRange.RANGE_1H, viewModel.uiState.value.selectedHistoricalRange)

        viewModel.setSelectedHistoricalRange(HistoricalTimeRange.RANGE_24H)
        assertEquals(HistoricalTimeRange.RANGE_24H, viewModel.uiState.value.selectedHistoricalRange)

        viewModel.setSelectedHistoricalRange(HistoricalTimeRange.RANGE_7D)
        assertEquals(HistoricalTimeRange.RANGE_7D, viewModel.uiState.value.selectedHistoricalRange)

        viewModel.setSelectedHistoricalRange(HistoricalTimeRange.RANGE_30D)
        assertEquals(HistoricalTimeRange.RANGE_30D, viewModel.uiState.value.selectedHistoricalRange)
    }

    @Test
    fun `simulating trend tick appends new real-time historical data point`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        val initialCount = viewModel.uiState.value.historicalTrendPoints.size
        viewModel.simulateHistoricalTrendTick()

        val updatedPoints = viewModel.uiState.value.historicalTrendPoints
        assertEquals(initialCount + 1, updatedPoints.size)

        val latestPoint = updatedPoints.last()
        assertNotNull(latestPoint.timeLabel)
        assertTrue(latestPoint.grossRevenue > 300000.0)
        assertTrue(viewModel.uiState.value.activityLog.first().action.contains("Real-time trend tick simulated"))
    }

    @Test
    fun `toggling historical trend live state updates flag`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        assertTrue(viewModel.uiState.value.isHistoricalTrendLive)

        viewModel.toggleHistoricalTrendLive()
        assertFalse(viewModel.uiState.value.isHistoricalTrendLive)

        viewModel.toggleHistoricalTrendLive()
        assertTrue(viewModel.uiState.value.isHistoricalTrendLive)
    }

    @Test
    fun `HistoricalTrendPoint valueFor returns correct metric value`() {
        val repo = DashboardRepository()
        val points = repo.getInitialHistoricalTrends(5)
        val sample = points.first()

        assertEquals(sample.grossRevenue, sample.valueFor(HistoricalMetricType.GROSS_REVENUE), 0.001)
        assertEquals(sample.hoursSaved, sample.valueFor(HistoricalMetricType.HOURS_SAVED), 0.001)
        assertEquals(sample.swarmThroughput, sample.valueFor(HistoricalMetricType.SWARM_THROUGHPUT), 0.001)
        assertEquals(sample.arenaScore, sample.valueFor(HistoricalMetricType.ARENA_SCORE), 0.001)
    }
}
