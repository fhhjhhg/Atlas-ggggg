package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.local.Metric
import com.example.data.local.MetricDao
import com.example.data.local.MetricRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
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
class MetricEntityRoomTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: MetricDao
    private lateinit var repository: MetricRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.metricDao()
        repository = MetricRepository(dao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `Metric entity stores ID, name, current value, timestamp, and category`() = runBlocking {
        val testTime = 1729685000000L
        val kpiMetric = Metric(
            name = "Weekly Active Operators",
            currentValue = 48.0,
            timestamp = testTime,
            category = "OPERATIONAL"
        )

        val insertedId = repository.insertMetric(kpiMetric)
        assertTrue(insertedId > 0L)

        val retrieved = repository.getMetricByIdOnce(insertedId)
        assertNotNull(retrieved)
        assertEquals(insertedId, retrieved?.id)
        assertEquals("Weekly Active Operators", retrieved?.name)
        assertEquals(48.0, retrieved?.currentValue ?: 0.0, 0.001)
        assertEquals(testTime, retrieved?.timestamp)
        assertEquals("OPERATIONAL", retrieved?.category)
    }

    @Test
    fun `query metrics by category returns filtered items`() = runBlocking {
        val metric1 = Metric(name = "Gross Revenue", currentValue = 348600.0, category = "FINANCIAL")
        val metric2 = Metric(name = "Free Cash Flow", currentValue = 175596.0, category = "FINANCIAL")
        val metric3 = Metric(name = "Swarm Ops", currentValue = 142.0, category = "TELEMETRY")

        dao.insertMetrics(listOf(metric1, metric2, metric3))

        val financialMetrics = repository.getMetricsByCategory("FINANCIAL").first()
        assertEquals(2, financialMetrics.size)
        assertTrue(financialMetrics.all { it.category == "FINANCIAL" })

        val telemetryMetrics = repository.getMetricsByCategory("TELEMETRY").first()
        assertEquals(1, telemetryMetrics.size)
        assertEquals("Swarm Ops", telemetryMetrics[0].name)
    }

    @Test
    fun `update and delete operations on Metric entity`() = runBlocking {
        val id = repository.insertMetric(
            Metric(name = "Initial KPI", currentValue = 100.0, category = "INITIAL")
        )

        val item = repository.getMetricByIdOnce(id)!!
        val updated = item.copy(name = "Updated KPI", currentValue = 250.0, category = "UPDATED")
        repository.updateMetric(updated)

        val afterUpdate = repository.getMetricByIdOnce(id)
        assertEquals("Updated KPI", afterUpdate?.name)
        assertEquals(250.0, afterUpdate?.currentValue ?: 0.0, 0.001)
        assertEquals("UPDATED", afterUpdate?.category)

        repository.deleteMetricById(id)
        val afterDelete = repository.getMetricByIdOnce(id)
        assertNull(afterDelete)
    }

    @Test
    fun `searchMetricsByName returns matching results`() = runBlocking {
        repository.insertMetric(Metric(name = "CPU Core Temp", currentValue = 62.0, category = "HARDWARE"))
        repository.insertMetric(Metric(name = "CPU Core Load", currentValue = 84.0, category = "HARDWARE"))
        repository.insertMetric(Metric(name = "RAM Usage", currentValue = 14.5, category = "HARDWARE"))

        val cpuMetrics = repository.searchMetricsByName("CPU").first()
        assertEquals(2, cpuMetrics.size)
    }
}
