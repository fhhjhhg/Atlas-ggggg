package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.ColivingVibe
import com.example.data.DashboardRepository
import com.example.data.local.AppDatabase
import com.example.data.local.Metric
import com.example.data.local.MetricDao
import com.example.data.local.UserPerformanceMetric
import com.example.ui.NexusDashboardViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class BookMyColivingTest {

    private lateinit var context: Context
    private lateinit var db: AppDatabase
    private lateinit var metricDao: MetricDao

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        metricDao = db.metricDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `verify repository provides BookMyColiving data structures`() {
        val repo = DashboardRepository()

        val spaces = repo.getBookMyColivingSpaces()
        assertTrue("Spaces should not be empty", spaces.isNotEmpty())
        assertTrue("All spaces should have a valid monthly rate", spaces.all { it.monthlyRateUsd > 0.0 })
        assertTrue("All spaces should have SKU code", spaces.all { it.skuCode.startsWith("SKU-") })

        val domains = repo.getDomainAssets()
        assertTrue("Domain assets should contain bookmycoliving.com", domains.any { it.domain == "bookmycoliving.com" })
        assertTrue("Domain assets should have httpsUrl", domains.all { it.httpsUrl.startsWith("https://") })

        val artifacts = repo.getSoftwareArtifacts()
        assertTrue("Artifacts should contain APKs and SDKs", artifacts.any { it.type == "APK" } && artifacts.any { it.type == "SDK" })

        val bom = repo.getHardwareBomItems()
        assertTrue("Hardware BOM should contain items with SKU", bom.all { it.sku.startsWith("BOM-") })
        val totalBomCost = bom.sumOf { it.totalCostUsd }
        assertTrue("BOM total cost should be positive", totalBomCost > 0.0)

        val sow = repo.getSowPhases()
        assertEquals("Should have 4 SOW phases", 4, sow.size)
        assertTrue("SOW progress should be within bounds", sow.all { it.progressPercent in 0..100 })

        val sops = repo.getStandardOperatingProcedures()
        assertTrue("SOPs should not be empty", sops.isNotEmpty())
        assertTrue("SOPs should have code", sops.all { it.code.startsWith("SOP-") })

        val mcps = repo.getMcpServerLinks()
        assertTrue("MCP servers should contain Coliving Telemetry", mcps.any { it.serverId == "mcp-coliving-telemetry" })

        val ragDocs = repo.getRagKnowledgeDocs()
        assertTrue("RAG docs should include lease templates", ragDocs.any { it.title.contains("Guide") || it.title.contains("Coliving") })

        val tokenomics = repo.getTokenomicsPool()
        assertEquals("NEXUS-PROP", tokenomics.symbol)
        assertTrue("APY should be positive", tokenomics.apyPercent > 0.0)

        val dxi = repo.getDxiMetrics()
        assertEquals(1000, dxi.score)
        assertTrue("Hours saved should be positive", dxi.hoursSavedCumulative > 0.0)
        assertTrue("Skills arbitrage should be positive", dxi.skillsArbitrageSavingsUsd > 0.0)
    }

    @Test
    fun `verify ViewModel filtering and direct inquiry interaction`() {
        val viewModel = NexusDashboardViewModel()

        val initialSpaces = viewModel.uiState.value.colivingListings
        val initialFiltered = viewModel.uiState.value.filteredColivingListings
        assertEquals(5, initialSpaces.size)
        assertEquals(5, initialFiltered.size)

        // Filter by vibe
        viewModel.filterBookMyColivingSpaces(vibe = ColivingVibe.AI_DEEP_WORK, query = "")
        assertEquals(ColivingVibe.AI_DEEP_WORK, viewModel.uiState.value.selectedColivingVibeFilter)
        assertTrue("Filtered spaces should only match AI_DEEP_WORK vibe",
            viewModel.uiState.value.filteredColivingListings.all { it.vibe == ColivingVibe.AI_DEEP_WORK })

        // Filter by text search
        viewModel.filterBookMyColivingSpaces(vibe = null, query = "Tokyo")
        assertTrue("Search for Tokyo should yield matches",
            viewModel.uiState.value.filteredColivingListings.any { it.city.contains("Tokyo", ignoreCase = true) })

        // Clear filter
        viewModel.filterBookMyColivingSpaces(vibe = null, query = "")
        assertEquals(5, viewModel.uiState.value.filteredColivingListings.size)

        // Dialog toggle
        assertFalse(viewModel.uiState.value.isBookMyColivingDialogOpen)
        viewModel.openBookMyColivingDialog()
        assertTrue(viewModel.uiState.value.isBookMyColivingDialogOpen)
        viewModel.dismissBookMyColivingDialog()
        assertFalse(viewModel.uiState.value.isBookMyColivingDialogOpen)

        // Direct inquiry submission
        val testSpace = initialSpaces.first()
        viewModel.submitDirectColivingInquiry(testSpace.id, "alice@example.com", "Resident Alice")
        assertNotNull(viewModel.uiState.value.reportSubmitSuccessBanner)
        assertNotNull(viewModel.uiState.value.dxiMetrics)

        viewModel.stopTimersForTesting()
    }

    @Test
    fun `verify MetricDao Room CRUD and telemetry queries`() = runBlocking {
        // 1. Initial count is 0
        assertEquals(0, metricDao.getMetricsCount())

        // 2. Insert metric
        val metric1 = Metric(
            name = "Direct Booking Throughput",
            currentValue = 184.5,
            timestamp = 1000L,
            category = "KPI",
            unit = "tx/hr"
        )
        val id1 = metricDao.insertMetric(metric1)
        assertTrue("Inserted ID should be positive", id1 > 0)
        assertEquals(1, metricDao.getMetricsCount())

        // 3. Insert Telemetry metrics
        val telemetry1 = Metric(
            name = "Gateway Latency",
            currentValue = 18.2,
            timestamp = 2000L,
            category = "TELEMETRY",
            unit = "ms"
        )
        val telemetry2 = Metric(
            name = "Edge Hop Latency",
            currentValue = 9.4,
            timestamp = 3000L,
            category = "TELEMETRY",
            unit = "ms"
        )
        metricDao.insertMetrics(listOf(telemetry1, telemetry2))
        assertEquals(3, metricDao.getMetricsCount())

        // 4. Query telemetry latest
        val latestTelemetry = metricDao.getLatestTelemetryMetric()
        assertNotNull(latestTelemetry)
        assertEquals("Edge Hop Latency", latestTelemetry?.name)
        assertEquals(3000L, latestTelemetry?.timestamp)

        // 5. Query recent telemetry
        val recentTelemetry = metricDao.getRecentTelemetryMetrics(10).first()
        assertEquals(2, recentTelemetry.size)
        assertEquals("Edge Hop Latency", recentTelemetry[0].name)
        assertEquals("Gateway Latency", recentTelemetry[1].name)

        // 6. Query by time range
        val rangeMetrics = metricDao.getTelemetryMetricsByTimeRange(1500L, 2500L).first()
        assertEquals(1, rangeMetrics.size)
        assertEquals("Gateway Latency", rangeMetrics[0].name)

        // 7. Search by name
        val searchResults = metricDao.searchMetricsByName("Booking").first()
        assertEquals(1, searchResults.size)
        assertEquals("Direct Booking Throughput", searchResults[0].name)

        // 8. Update metric
        val updatedMetric1 = metric1.copy(id = id1, currentValue = 220.0)
        metricDao.updateMetric(updatedMetric1)
        val fetchedUpdated = metricDao.getMetricByIdOnce(id1)
        assertEquals(220.0, fetchedUpdated?.currentValue ?: 0.0, 0.01)

        // 9. Delete by ID
        metricDao.deleteMetricById(id1)
        assertEquals(2, metricDao.getMetricsCount())
        assertNull(metricDao.getMetricByIdOnce(id1))

        // 10. Delete all
        metricDao.deleteAllMetrics()
        assertEquals(0, metricDao.getMetricsCount())
    }

    @Test
    fun `verify UserPerformanceMetricDao in AppDatabase`() = runBlocking {
        val perfDao = db.performanceMetricDao()
        assertEquals(0, perfDao.getMetricsCount())

        val perfMetric = UserPerformanceMetric(
            name = "Coliving DXI Velocity",
            currentValue = 1000.0,
            unit = "DXI",
            timestamp = System.currentTimeMillis()
        )
        val id = perfDao.insertMetric(perfMetric)
        assertTrue(id > 0)
        assertEquals(1, perfDao.getMetricsCount())

        val fetched = perfDao.getMetricByIdOnce(id)
        assertNotNull(fetched)
        assertEquals("Coliving DXI Velocity", fetched?.name)
    }
}
