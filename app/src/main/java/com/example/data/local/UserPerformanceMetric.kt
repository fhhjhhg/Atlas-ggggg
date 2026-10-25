package com.example.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room database entity representing a user-defined performance metric.
 * Stores metric attributes including name, current value, measurement unit, and timestamp.
 */
@Entity(tableName = "user_performance_metrics")
data class UserPerformanceMetric(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "current_value")
    val currentValue: Double,

    @ColumnInfo(name = "unit")
    val unit: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "category")
    val category: String = "CUSTOM",

    @ColumnInfo(name = "target_value")
    val targetValue: Double? = null
)
