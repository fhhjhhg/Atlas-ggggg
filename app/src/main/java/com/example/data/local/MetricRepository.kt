package com.example.data.local

import kotlinx.coroutines.flow.Flow

/**
 * Repository pattern implementation for the [Metric] entity.
 */
class MetricRepository(
    private val dao: MetricDao
) {
    val allMetrics: Flow<List<Metric>> = dao.getAllMetrics()

    fun getMetricById(id: Long): Flow<Metric?> = dao.getMetricById(id)

    suspend fun getMetricByIdOnce(id: Long): Metric? = dao.getMetricByIdOnce(id)

    fun getMetricsByCategory(category: String): Flow<List<Metric>> = dao.getMetricsByCategory(category)

    fun searchMetricsByName(query: String): Flow<List<Metric>> = dao.searchMetricsByName(query)

    fun getTelemetryMetricsByTimeRange(startTime: Long, endTime: Long): Flow<List<Metric>> =
        dao.getTelemetryMetricsByTimeRange(startTime, endTime)

    fun getRecentTelemetryMetrics(limit: Int = 30): Flow<List<Metric>> =
        dao.getRecentTelemetryMetrics(limit)

    suspend fun getLatestTelemetryMetric(): Metric? = dao.getLatestTelemetryMetric()

    suspend fun insertMetric(metric: Metric): Long = dao.insertMetric(metric)

    suspend fun insertMetrics(metrics: List<Metric>): List<Long> = dao.insertMetrics(metrics)

    suspend fun updateMetric(metric: Metric) = dao.updateMetric(metric)

    suspend fun deleteMetric(metric: Metric) = dao.deleteMetric(metric)

    suspend fun deleteMetricById(id: Long) = dao.deleteMetricById(id)

    suspend fun deleteAllMetrics() = dao.deleteAllMetrics()

    suspend fun getMetricsCount(): Int = dao.getMetricsCount()
}
