package com.example.ai

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout

private const val TAG = "GeminiAiService"

data class ActivityLogAnalysis(
    val summary: String,
    val healthRating: String, // "Optimal", "Degraded", "Critical"
    val detectedAnomalies: List<String>,
    val recommendations: List<String>
)

object GeminiAiService {

    // Mandated: initialize Firebase AI model lazily to prevent class-init crashes
    private val generalModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash"
        )
    }

    /**
     * Executes single-turn text inference using Gemini 3.8 Flash via Firebase AI Logic.
     */
    suspend fun generateContent(prompt: String): Result<String> {
        return try {
            val response = withTimeout(60_000L) {
                generalModel.generateContent(prompt)
            }
            val text = response.text
            if (!text.isNullOrBlank()) {
                Result.success(text)
            } else {
                Result.failure(IllegalStateException("Gemini returned an empty response."))
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Gemini generation timed out", e)
            Result.failure(IllegalStateException("AI analysis timed out after 60 seconds. Please try again."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Firebase AI generation failed", e)
            Result.failure(e)
        }
    }

    /**
     * Streams tokens in real-time from Gemini 3.8 Flash.
     */
    fun streamContent(prompt: String): Flow<String> = flow {
        withTimeout(60_000L) {
            generalModel.generateContentStream(prompt).collect { chunk ->
                chunk.text?.let { emit(it) }
            }
        }
    }

    /**
     * Performs automated diagnosis of the last 20 dashboard activities to assist troubleshooting.
     */
    suspend fun analyzeActivityLog(
        activityLogList: List<String>,
        systemMetricsSummary: String
    ): Result<ActivityLogAnalysis> {
        val prompt = """
You are a Site Reliability & Operations AI Diagnostics specialist.
Analyze the following recent 20 system actions and system metrics to provide a structured troubleshooting analysis:

SYSTEM METRICS:
$systemMetricsSummary

ACTIVITY LOG (Last 20 events, oldest to newest):
${activityLogList.joinToString("\n") { "- $it" }}

Provide your response in this exact format:
SUMMARY: <1-2 sentences summarizing operational status>
HEALTH: <Optimal | Degraded | Critical>
ANOMALIES:
- <Anomaly 1 or "No critical anomalies detected">
- <Anomaly 2>
RECOMMENDATIONS:
- <Specific troubleshooting recommendation 1>
- <Specific troubleshooting recommendation 2>
""".trimIndent()

        return try {
            val responseText = generateContent(prompt).getOrThrow()
            val parsed = parseDiagnosticOutput(responseText)
            Result.success(parsed)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Activity log analysis failed", e)
            Result.failure(e)
        }
    }

    private fun parseDiagnosticOutput(rawText: String): ActivityLogAnalysis {
        var summary = "Operations running normally."
        var health = "Optimal"
        val anomalies = mutableListOf<String>()
        val recommendations = mutableListOf<String>()

        var currentSection = ""

        rawText.lines().forEach { line ->
            val trimmed = line.trim()
            when {
                trimmed.startsWith("SUMMARY:", ignoreCase = true) -> {
                    summary = trimmed.substringAfter(":").trim()
                    currentSection = "SUMMARY"
                }
                trimmed.startsWith("HEALTH:", ignoreCase = true) -> {
                    health = trimmed.substringAfter(":").trim()
                    currentSection = "HEALTH"
                }
                trimmed.startsWith("ANOMALIES:", ignoreCase = true) -> {
                    currentSection = "ANOMALIES"
                }
                trimmed.startsWith("RECOMMENDATIONS:", ignoreCase = true) -> {
                    currentSection = "RECOMMENDATIONS"
                }
                trimmed.startsWith("-") || trimmed.startsWith("*") -> {
                    val item = trimmed.removePrefix("-").removePrefix("*").trim()
                    if (item.isNotEmpty()) {
                        when (currentSection) {
                            "ANOMALIES" -> anomalies.add(item)
                            "RECOMMENDATIONS" -> recommendations.add(item)
                        }
                    }
                }
            }
        }

        if (anomalies.isEmpty()) anomalies.add("Zero critical faults detected in the last 20 actions.")
        if (recommendations.isEmpty()) recommendations.add("Maintain scheduled auto-refresh telemetry.")

        return ActivityLogAnalysis(
            summary = summary,
            healthRating = health,
            detectedAnomalies = anomalies,
            recommendations = recommendations
        )
    }

    suspend fun auditColivingContract(contractText: String): Result<String> {
        val prompt = """
            You are a senior real estate attorney and coliving legal operations advisor.
            Review the following Coliving Lease / Room License Agreement generated according to Everything Coliving best practices:

            $contractText

            Provide an executive legal audit:
            1. RISK RATING: (Optimal / Low Risk / Moderate)
            2. EVERYTHING COLIVING COMPLIANCE EVALUATION: Evaluate by-the-bed licensing, joint-and-several liability avoidance, utility caps, and quiet hours.
            3. ENFORCEABILITY & EVICTION MECHANICS: Comment on the individual license revocation vs standard residential eviction laws.
            4. ACTIONABLE LEGAL RECOMMENDATIONS: Suggest any state-specific or municipal operational riders to include.
        """.trimIndent()
        return generateContent(prompt)
    }
}
