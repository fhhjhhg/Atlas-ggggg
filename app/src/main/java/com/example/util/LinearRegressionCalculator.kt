package com.example.util

import com.example.data.ForecastPoint
import com.example.data.LinearRegressionForecast
import com.example.data.MetricHistoryPoint
import com.example.data.TrendDirection
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

object LinearRegressionCalculator {

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    /**
     * Calculates linear regression trend line and projects future values
     * using ordinary least squares (OLS) regression with confidence intervals.
     */
    fun calculateForecast(
        metricName: String,
        historicalData: List<MetricHistoryPoint>,
        forecastStepsCount: Int = 6,
        stepIntervalMs: Long = 60_000L
    ): LinearRegressionForecast {
        if (historicalData.isEmpty()) {
            return fallbackEmptyForecast(metricName)
        }

        val n = historicalData.size
        if (n < 2) {
            val single = historicalData.first()
            val dummyForecasts = (1..forecastStepsCount).map { step ->
                ForecastPoint(
                    stepIndex = step,
                    timeLabel = timeFormat.format(Date(single.timestamp + (step * stepIntervalMs))),
                    predictedValue = single.metricValue,
                    upperConfidenceBound = single.metricValue * 1.05,
                    lowerConfidenceBound = single.metricValue * 0.95
                )
            }
            return LinearRegressionForecast(
                metricName = metricName,
                slope = 0.0,
                intercept = single.metricValue,
                rSquared = 1.0,
                standardError = 0.0,
                trendDirection = TrendDirection.STABLE,
                historicalData = historicalData,
                forecastedPoints = dummyForecasts,
                projectedChangePercent = 0.0
            )
        }

        var sumX = 0.0
        var sumY = 0.0
        var sumXY = 0.0
        var sumX2 = 0.0

        for (i in 0 until n) {
            val x = i.toDouble()
            val y = historicalData[i].metricValue
            sumX += x
            sumY += y
            sumXY += x * y
            sumX2 += x * x
        }

        val denominator = (n * sumX2) - (sumX * sumX)
        val slope = if (denominator != 0.0) {
            ((n * sumXY) - (sumX * sumY)) / denominator
        } else 0.0

        val intercept = (sumY - (slope * sumX)) / n
        val meanY = sumY / n
        val meanX = sumX / n

        // Compute residuals for R^2 and Standard Error
        var ssTot = 0.0
        var ssRes = 0.0
        var sumXDiffSq = 0.0

        for (i in 0 until n) {
            val x = i.toDouble()
            val actualY = historicalData[i].metricValue
            val predY = (slope * x) + intercept
            ssTot += (actualY - meanY).pow(2)
            ssRes += (actualY - predY).pow(2)
            sumXDiffSq += (x - meanX).pow(2)
        }

        val rSquared = if (ssTot > 0.0) {
            (1.0 - (ssRes / ssTot)).coerceIn(0.0, 1.0)
        } else 1.0

        val degreesOfFreedom = max(1, n - 2)
        val standardError = sqrt(ssRes / degreesOfFreedom)

        val lastTimestamp = historicalData.last().timestamp
        val lastValue = historicalData.last().metricValue

        // Generate Forecasted Points with 95% Confidence Interval
        val forecastedPoints = (1..forecastStepsCount).map { step ->
            val futureX = (n - 1) + step
            val rawPredicted = (slope * futureX) + intercept
            val predicted = rawPredicted.coerceAtLeast(0.0)

            // Leverage factor for confidence band widening into future
            val leverage = if (sumXDiffSq > 0.0) {
                1.0 + (1.0 / n) + ((futureX - meanX).pow(2) / sumXDiffSq)
            } else 1.0 + (1.0 / n)

            val marginOfError = 1.96 * standardError * sqrt(leverage)
            val upperBound = (predicted + marginOfError).coerceAtLeast(predicted)
            val lowerBound = (predicted - marginOfError).coerceAtLeast(0.0)

            val futureTime = lastTimestamp + (step * stepIntervalMs)
            ForecastPoint(
                stepIndex = step,
                timeLabel = timeFormat.format(Date(futureTime)),
                predictedValue = (predicted * 100.0).toInt() / 100.0,
                upperConfidenceBound = (upperBound * 100.0).toInt() / 100.0,
                lowerConfidenceBound = (lowerBound * 100.0).toInt() / 100.0
            )
        }

        val finalPredicted = forecastedPoints.lastOrNull()?.predictedValue ?: lastValue
        val projectedChangePercent = if (lastValue > 0.0) {
            ((finalPredicted - lastValue) / lastValue) * 100.0
        } else 0.0

        val trendDirection = when {
            slope > 0.15 -> TrendDirection.BULLISH
            slope < -0.15 -> TrendDirection.BEARISH
            else -> TrendDirection.STABLE
        }

        return LinearRegressionForecast(
            metricName = metricName,
            slope = (slope * 1000.0).toInt() / 1000.0,
            intercept = (intercept * 100.0).toInt() / 100.0,
            rSquared = (rSquared * 1000.0).toInt() / 1000.0,
            standardError = (standardError * 100.0).toInt() / 100.0,
            trendDirection = trendDirection,
            historicalData = historicalData,
            forecastedPoints = forecastedPoints,
            projectedChangePercent = (projectedChangePercent * 10.0).toInt() / 10.0
        )
    }

    private fun fallbackEmptyForecast(metricName: String): LinearRegressionForecast {
        val now = System.currentTimeMillis()
        val dummyHistorical = (0..5).map { i ->
            MetricHistoryPoint(
                index = i,
                timestamp = now - ((5 - i) * 60_000L),
                timeLabel = timeFormat.format(Date(now - ((5 - i) * 60_000L))),
                metricValue = 100.0 + (i * 5.0)
            )
        }
        return calculateForecast(metricName, dummyHistorical)
    }
}
