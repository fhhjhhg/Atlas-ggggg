package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for [UserPerformanceMetric] table.
 * Adheres to reactive Flow queries and asynchronous suspend write operations.
 */
@Dao
interface PerformanceMetricDao {

    @Query("SELECT * FROM user_performance_metrics ORDER BY timestamp DESC")
    fun getAllMetrics(): Flow<List<UserPerformanceMetric>>

    @Query("SELECT * FROM user_performance_metrics WHERE id = :id")
    fun getMetricById(id: Long): Flow<UserPerformanceMetric?>

    @Query("SELECT * FROM user_performance_metrics WHERE id = :id")
    suspend fun getMetricByIdOnce(id: Long): UserPerformanceMetric?

    @Query("SELECT * FROM user_performance_metrics WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    fun searchMetricsByName(searchQuery: String): Flow<List<UserPerformanceMetric>>

    @Query("SELECT * FROM user_performance_metrics WHERE category = :category ORDER BY timestamp DESC")
    fun getMetricsByCategory(category: String): Flow<List<UserPerformanceMetric>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetric(metric: UserPerformanceMetric): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetrics(metrics: List<UserPerformanceMetric>): List<Long>

    @Update
    suspend fun updateMetric(metric: UserPerformanceMetric)

    @Delete
    suspend fun deleteMetric(metric: UserPerformanceMetric)

    @Query("DELETE FROM user_performance_metrics WHERE id = :id")
    suspend fun deleteMetricById(id: Long)

    @Query("DELETE FROM user_performance_metrics")
    suspend fun deleteAllMetrics()

    @Query("SELECT COUNT(*) FROM user_performance_metrics")
    suspend fun getMetricsCount(): Int
}
