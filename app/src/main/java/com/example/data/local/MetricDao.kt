package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the [Metric] entity.
 * Provides reactive Flow queries and suspend functions for storing and managing KPI data points.
 */
@Dao
interface MetricDao {

    @Query("SELECT * FROM metrics ORDER BY timestamp DESC")
    fun getAllMetrics(): Flow<List<Metric>>

    @Query("SELECT * FROM metrics WHERE id = :id")
    fun getMetricById(id: Long): Flow<Metric?>

    @Query("SELECT * FROM metrics WHERE id = :id")
    suspend fun getMetricByIdOnce(id: Long): Metric?

    @Query("SELECT * FROM metrics WHERE category = :category ORDER BY timestamp DESC")
    fun getMetricsByCategory(category: String): Flow<List<Metric>>

    @Query("SELECT * FROM metrics WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchMetricsByName(query: String): Flow<List<Metric>>

    // Telemetry Queries
    @Query("SELECT * FROM metrics WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp ASC")
    fun getTelemetryMetricsByTimeRange(startTime: Long, endTime: Long): Flow<List<Metric>>

    @Query("SELECT * FROM metrics WHERE category = 'TELEMETRY' ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentTelemetryMetrics(limit: Int = 30): Flow<List<Metric>>

    @Query("SELECT * FROM metrics WHERE category = 'TELEMETRY' ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestTelemetryMetric(): Metric?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetric(metric: Metric): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetrics(metrics: List<Metric>): List<Long>

    @Update
    suspend fun updateMetric(metric: Metric)

    @Delete
    suspend fun deleteMetric(metric: Metric)

    @Query("DELETE FROM metrics WHERE id = :id")
    suspend fun deleteMetricById(id: Long)

    @Query("DELETE FROM metrics")
    suspend fun deleteAllMetrics()

    @Query("SELECT COUNT(*) FROM metrics")
    suspend fun getMetricsCount(): Int
}
