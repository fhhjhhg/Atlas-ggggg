package com.example.data

enum class ThresholdCondition(val symbol: String, val label: String) {
    GREATER_THAN(">", "Greater Than"),
    LESS_THAN("<", "Less Than")
}

enum class AlertSeverity(val label: String) {
    INFO("Info"),
    WARNING("Warning"),
    CRITICAL("Critical"),
    EMERGENCY("Emergency")
}

data class MetricThresholdRule(
    val id: String,
    val metricName: String,
    val condition: ThresholdCondition,
    val thresholdValue: Double,
    val unit: String,
    val severity: AlertSeverity,
    val isEnabled: Boolean = true,
    val fcmTopic: String = "telemetry_alerts"
)

data class TriggeredThresholdAlert(
    val id: String,
    val ruleId: String,
    val metricName: String,
    val currentValue: Double,
    val thresholdValue: Double,
    val unit: String,
    val severity: AlertSeverity,
    val timestamp: Long = System.currentTimeMillis(),
    val message: String,
    val fcmMessageId: String = "fcm_msg_${System.currentTimeMillis()}"
)

enum class StoreCategory(val label: String) {
    ALL("All Categories"),
    AI_AGENTS("AI & Autonomous Agents"),
    FINOPS("FinOps & Wealth Velocity"),
    DEVOPS("DevOps & Edge Telemetry"),
    BIOSCIENCE("Bioscience & Synthetic"),
    DEPIN_WEB3("DePIN & Distributed Tech")
}

data class StoreWidget(
    val id: String,
    val name: String,
    val description: String,
    val category: StoreCategory,
    val author: String,
    val version: String,
    val downloadsCount: Int,
    val rating: Double,
    val tags: List<String>,
    val isInstalled: Boolean = false,
    val targetWidgetType: WidgetType
)

enum class DashboardTemplate(
    val title: String,
    val description: String,
    val iconName: String,
    val widgetTypes: List<WidgetType>
) {
    ANALYST(
        title = "Analyst View",
        description = "Optimized for quantitative modeling, linear regression forecasting, and asset velocity.",
        iconName = "ShowChart",
        widgetTypes = listOf(
            WidgetType.METRIC_FORECASTING,
            WidgetType.EXECUTIVE_METRICS,
            WidgetType.HISTORICAL_TREND_CHART,
            WidgetType.COMPOUNDING_CHART,
            WidgetType.ASSET_REGISTRY
        )
    ),
    DEVELOPER(
        title = "Developer View",
        description = "Edge telemetry streams, agentic enclave orchestration, and scientific prover algorithms.",
        iconName = "Terminal",
        widgetTypes = listOf(
            WidgetType.LIVE_TELEMETRY,
            WidgetType.AGENTIC_ENCLAVE,
            WidgetType.DIFFUSERS_TRANSFORMERS,
            WidgetType.SCIENTIFIC_PROVER,
            WidgetType.ACTION_SIMULATOR
        )
    ),
    EXECUTIVE(
        title = "Executive View",
        description = "High-level arena benchmarks, ARR KPIs, coliving twin occupancy, and compounding equity.",
        iconName = "Analytics",
        widgetTypes = listOf(
            WidgetType.EXECUTIVE_METRICS,
            WidgetType.ARENA_SCORE,
            WidgetType.COMPOUNDING_CHART,
            WidgetType.COLIVING_DIGITAL_TWIN,
            WidgetType.BOOK_MY_COLIVING
        )
    ),
    BIO_EVO(
        title = "Bio-Evo View",
        description = "Bioreactors, genomic matrices, emergent research knowledge, and Harada habit execution.",
        iconName = "Biotech",
        widgetTypes = listOf(
            WidgetType.BIOSCIENCE_HUB,
            WidgetType.BIOCOMMONS_MATRIX,
            WidgetType.EMERGENT_RESEARCH_KNOWLEDGE,
            WidgetType.HARADA_HABIT_MATRIX,
            WidgetType.SCIENTIFIC_PROVER
        )
    ),
    MINIMALIST(
        title = "Minimalist View",
        description = "Distraction-free high-density view with executive metrics and telemetry streaming.",
        iconName = "Dashboard",
        widgetTypes = listOf(
            WidgetType.EXECUTIVE_METRICS,
            WidgetType.LIVE_TELEMETRY,
            WidgetType.METRIC_FORECASTING
        )
    )
}

enum class DatabaseSyncState(val label: String) {
    SYNCED("Synced with Firestore"),
    SYNCING("Syncing Local Room ➔ Firestore..."),
    LOCAL_CACHE("Local Room DB Ready (Offline)"),
    SYNC_ERROR("Sync Alert")
}
