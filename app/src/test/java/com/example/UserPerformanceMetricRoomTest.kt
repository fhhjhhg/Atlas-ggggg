package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.local.PerformanceMetricDao
import com.example.data.local.PerformanceMetricRepository
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
class UserPerformanceMetricRoomTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: PerformanceMetricDao
    private lateinit var repository: PerformanceMetricRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.performanceMetricDao()
        repository = PerformanceMetricRepository(dao)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `insert and retrieve user-defined performance metric with name, value, unit, timestamp`() = runBlocking {
        val now = System.currentTimeMillis()
        val metric = UserPerformanceMetric(
            name = "Cluster GPU Load",
            currentValue = 92.4,
            unit = "%",
            timestamp = now,
            category = "INFRASTRUCTURE"
        )

        val insertedId = dao.insertMetric(metric)
        assertTrue(insertedId > 0L)

        val retrieved = dao.getMetricByIdOnce(insertedId)
        assertNotNull(retrieved)
        assertEquals("Cluster GPU Load", retrieved?.name)
        assertEquals(92.4, retrieved?.currentValue ?: 0.0, 0.001)
        assertEquals("%", retrieved?.unit)
        assertEquals(now, retrieved?.timestamp)
        assertEquals("INFRASTRUCTURE", retrieved?.category)
    }

    @Test
    fun `getAllMetrics reactive flow returns all inserted items ordered by timestamp DESC`() = runBlocking {
        val metric1 = UserPerformanceMetric(
            name = "Metric 1",
            currentValue = 10.0,
            unit = "ms",
            timestamp = 1000L
        )
        val metric2 = UserPerformanceMetric(
            name = "Metric 2",
            currentValue = 20.0,
            unit = "ops/s",
            timestamp = 2000L
        )

        dao.insertMetric(metric1)
        dao.insertMetric(metric2)

        val all = dao.getAllMetrics().first()
        assertEquals(2, all.size)
        // Ordered by timestamp DESC
        assertEquals("Metric 2", all[0].name)
        assertEquals("Metric 1", all[1].name)
    }

    @Test
    fun `searchMetricsByName filters results correctly`() = runBlocking {
        dao.insertMetric(UserPerformanceMetric(name = "Network Latency", currentValue = 12.0, unit = "ms"))
        dao.insertMetric(UserPerformanceMetric(name = "Network Throughput", currentValue = 140.0, unit = "MB/s"))
        dao.insertMetric(UserPerformanceMetric(name = "Database QPS", currentValue = 5000.0, unit = "qps"))

        val searchResults = dao.searchMetricsByName("Network").first()
        assertEquals(2, searchResults.size)
        assertTrue(searchResults.all { it.name.contains("Network") })
    }

    @Test
    fun `update and delete operations in Room database`() = runBlocking {
        val id = dao.insertMetric(
            UserPerformanceMetric(name = "Initial Metric", currentValue = 5.0, unit = "ms")
        )

        val original = dao.getMetricByIdOnce(id)!!
        val updated = original.copy(name = "Updated Metric", currentValue = 15.0)
        dao.updateMetric(updated)

        val afterUpdate = dao.getMetricByIdOnce(id)
        assertEquals("Updated Metric", afterUpdate?.name)
        assertEquals(15.0, afterUpdate?.currentValue ?: 0.0, 0.001)

        dao.deleteMetricById(id)
        val afterDelete = dao.getMetricByIdOnce(id)
        assertNull(afterDelete)
    }

    @Test
    fun `repository seedDefaultMetricsIfEmpty populates initial metrics`() = runBlocking {
        assertEquals(0, repository.getMetricsCount())
        repository.seedDefaultMetricsIfEmpty()
        val count = repository.getMetricsCount()
        assertTrue(count >= 4)

        // Seeding again does not duplicate
        repository.seedDefaultMetricsIfEmpty()
        assertEquals(count, repository.getMetricsCount())
    }

    @Test
    fun `viewModel integrates with performance metric repository`() = runBlocking {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()
        viewModel.setMetricRepositoryForTesting(repository)

        viewModel.addUserPerformanceMetric("Custom Swarm Metric", 77.5, "nodes", "TEST")

        // Wait for flow collection to emit
        val metrics = repository.allMetrics.first { list: List<UserPerformanceMetric> ->
            list.any { m: UserPerformanceMetric -> m.name == "Custom Swarm Metric" }
        }
        assertTrue(metrics.any { it.name == "Custom Swarm Metric" && it.currentValue == 77.5 })

        // Check dialog open/dismiss
        viewModel.openMetricEditor()
        assertTrue(viewModel.uiState.value.isMetricEditorDialogOpen)

        viewModel.dismissMetricEditor()
        assertFalse(viewModel.uiState.value.isMetricEditorDialogOpen)
    }
}
