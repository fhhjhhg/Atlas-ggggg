package com.example.data

/**
 * Data models for:
 * 1. Saved voice command triggers
 * 2. Smart Actions & Quick Add widget recommendations
 * 3. Custom Gemini system prompt management
 */

enum class VoiceActionType(val label: String, val targetWidget: WidgetType?) {
    RUN_WEEKLY_REPORT("Run Weekly Report", null),
    EXPORT_PDF_REPORT("Export Executive PDF", null),
    SHOW_FORECASTING("Plot Forecasting Chart", WidgetType.METRIC_FORECASTING),
    REFRESH_TELEMETRY("Refresh Live Telemetry", WidgetType.LIVE_TELEMETRY),
    OPEN_CUSTOMIZER("Open Widget Customizer", null),
    ANALYZE_WITH_AI("Analyze System with Gemini", null),
    TRIGGER_WIN("Execute 1-Minute HUD Win", WidgetType.ACTION_SIMULATOR)
}

data class VoiceCommandRecord(
    val id: String,
    val triggerPhrase: String,
    val actionType: VoiceActionType,
    val description: String,
    val lastTriggeredTimestamp: Long? = null,
    val isEnabled: Boolean = true,
    val executionCount: Int = 0
)

data class SmartActionItem(
    val id: String,
    val label: String,
    val actionType: VoiceActionType,
    val iconName: String = "AutoAwesome",
    val description: String,
    val targetWidgetType: WidgetType? = null
)

data class GeminiWidgetRecommendation(
    val id: String,
    val title: String,
    val reason: String,
    val recommendedWidgetType: WidgetType,
    val confidenceScore: Double = 0.96,
    val isAlreadyAdded: Boolean = false
)
