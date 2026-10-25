package com.example.data.local

import kotlinx.coroutines.flow.Flow

/**
 * Repository abstracting data access for [UserPerformanceMetric] from Room DAO.
 */
class PerformanceMetricRepository(
    private val dao: PerformanceMetricDao
) {
    val allMetrics: Flow<List<UserPerformanceMetric>> = dao.getAllMetrics()

    fun getMetricById(id: Long): Flow<UserPerformanceMetric?> = dao.getMetricById(id)

    suspend fun getMetricByIdOnce(id: Long): UserPerformanceMetric? = dao.getMetricByIdOnce(id)

    fun searchMetrics(query: String): Flow<List<UserPerformanceMetric>> = dao.searchMetricsByName(query)

    fun getMetricsByCategory(category: String): Flow<List<UserPerformanceMetric>> = dao.getMetricsByCategory(category)

    suspend fun insertMetric(metric: UserPerformanceMetric): Long = dao.insertMetric(metric)

    suspend fun insertMetrics(metrics: List<UserPerformanceMetric>): List<Long> = dao.insertMetrics(metrics)

    suspend fun updateMetric(metric: UserPerformanceMetric) = dao.updateMetric(metric)

    suspend fun deleteMetric(metric: UserPerformanceMetric) = dao.deleteMetric(metric)

    suspend fun deleteMetricById(id: Long) = dao.deleteMetricById(id)

    suspend fun deleteAllMetrics() = dao.deleteAllMetrics()

    suspend fun getMetricsCount(): Int = dao.getMetricsCount()

    suspend fun seedDefaultMetricsIfEmpty() {
        if (dao.getMetricsCount() == 0) {
            val defaults = listOf(
                UserPerformanceMetric(
                    name = "GPU Compute Cluster Utilization",
                    currentValue = 88.4,
                    unit = "%",
                    category = "INFRASTRUCTURE"
                ),
                UserPerformanceMetric(
                    name = "Subagent Round-Trip Latency",
                    currentValue = 18.2,
                    unit = "ms",
                    category = "NETWORK"
                ),
                UserPerformanceMetric(
                    name = "Swarm Yield Velocity",
                    currentValue = 142.0,
                    unit = "ops/s",
                    category = "OPERATIONS"
                ),
                UserPerformanceMetric(
                    name = "Compounding Free Cash Flow",
                    currentValue = 175596.0,
                    unit = "$/yr",
                    category = "FINANCIAL"
                )
            )
            dao.insertMetrics(defaults)
        }
    }
}
