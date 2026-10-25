package com.example.data

/**
 * Data models for:
 * 1. Historical metric linear regression forecasting (Recharts-inspired Compose curve)
 * 2. PDF report generation and summaries
 * 3. Feature onboarding tour
 * 4. Gemini prompt sandbox testing interface
 */

data class ForecastPoint(
    val stepIndex: Int,
    val timeLabel: String,
    val predictedValue: Double,
    val upperConfidenceBound: Double,
    val lowerConfidenceBound: Double
)

data class MetricHistoryPoint(
    val index: Int,
    val timestamp: Long,
    val timeLabel: String,
    val metricValue: Double
)

enum class TrendDirection(val label: String, val badgeColor: String) {
    BULLISH("Upward Momentum (Bullish)", "NeonEmerald"),
    BEARISH("Downward Declining (Bearish)", "NeonRose"),
    STABLE("Steady Neutral Equilibrium", "NeonCyan")
}

data class LinearRegressionForecast(
    val metricName: String,
    val slope: Double,
    val intercept: Double,
    val rSquared: Double,
    val standardError: Double,
    val trendDirection: TrendDirection,
    val historicalData: List<MetricHistoryPoint>,
    val forecastedPoints: List<ForecastPoint>,
    val projectedChangePercent: Double
)

data class GeneratedPdfReport(
    val reportId: String,
    val generatedTimestamp: Long,
    val formattedDateTime: String,
    val fileName: String,
    val absoluteFilePath: String,
    val fileSizeBytes: Long,
    val totalRevenue: Double,
    val weeklyHoursSaved: Double,
    val arenaScore: Float,
    val activeWidgetsCount: Int,
    val userEmail: String? = null
)

data class FeatureTourStep(
    val stepIndex: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val highlightCategory: String,
    val keyboardShortcut: String? = null
)

data class PromptTestScenario(
    val id: String,
    val name: String,
    val description: String,
    val sampleMetricsSummary: String,
    val sampleActivityLogs: List<String> = emptyList(),
    val simulatedAnomaly: String? = null
)

data class PromptTestEvaluation(
    val scenarioId: String,
    val scenarioName: String,
    val systemPromptTested: String,
    val timestamp: Long,
    val executionLatencyMs: Long,
    val estimatedTokens: Int,
    val healthRating: String, // "OPTIMAL", "WARNING", "CRITICAL"
    val analysisExecutiveSummary: String,
    val identifiedAnomalies: List<String>,
    val recommendedInterventions: List<String>,
    val confidenceScore: Double
)
