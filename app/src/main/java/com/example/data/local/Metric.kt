package com.example.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room database entity 'Metric' to store KPI data points.
 * Contains ID, name, current value, timestamp, and category as required.
 */
@Entity(tableName = "metrics")
data class Metric(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "current_value")
    val currentValue: Double,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "unit")
    val unit: String = ""
)
