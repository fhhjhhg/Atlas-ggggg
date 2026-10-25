package com.example.data

enum class WidgetCategory(val title: String) {
    SUMMARY_CARDS("Executive Summary"),
    CHARTS("Visual Analytics & Charts"),
    TABLES("Registries & Data Tables"),
    TELEMETRY("Live Feeds & Runtimes"),
    SIMULATOR("Action Simulators & Pipelines")
}

enum class WidgetType {
    EXECUTIVE_METRICS,
    ARENA_SCORE,
    LIVE_TELEMETRY,
    COMPOUNDING_CHART,
    ASSET_REGISTRY,
    MEGA_PILLARS,
    BIOCOMMONS_MATRIX,
    DIFFUSERS_TRANSFORMERS,
    SCIENTIFIC_PROVER,
    HARADA_HABIT_MATRIX,
    ACTION_SIMULATOR,
    AGENTIC_ENCLAVE,
    BIOSCIENCE_HUB,
    HISTORICAL_TREND_CHART,
    COLIVING_DIGITAL_TWIN,
    BOOK_MY_COLIVING,
    EMERGENT_RESEARCH_KNOWLEDGE,
    METRIC_FORECASTING
}

data class DashboardWidget(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: WidgetCategory,
    val widgetType: WidgetType,
    val isEnabled: Boolean = true,
    val order: Int
)

data class AssetSku(
    val id: String,
    val name: String,
    val assetType: String,
    val category: String,
    val monetization: String,
    val monthlyYieldUsd: Double,
    val valuationUsd: Double,
    val status: String
)

enum class TelemetrySeverity {
    INFO,
    YIELD,
    SYNC,
    AUDIT
}

data class TelemetryEvent(
    val id: String,
    val timestamp: Long,
    val timeLabel: String,
    val pillarId: String,
    val nodeName: String,
    val message: String,
    val deltaImpact: String,
    val severity: TelemetrySeverity
)

data class MegaPillarData(
    val id: String,
    val code: String,
    val name: String,
    val profilesRange: String,
    val activeNodes: Int,
    val weeklyHoursSaved: Double,
    val coreStack: String,
    val keyObjective: String,
    val healthPercentage: Int
)

data class ActionWin(
    val durationMinutes: Int,
    val operatorAction: String,
    val agentOutput: String,
    val skuCode: String,
    val roiTier: String
)

enum class BugReportSeverity(val label: String) {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    CRITICAL("Critical")
}

data class DiagnosticData(
    val deviceModel: String,
    val osVersion: String,
    val memoryUsage: String,
    val activeWidgetsCount: Int,
    val totalWidgetsCount: Int,
    val activeTabName: String,
    val autoRefreshStatus: String,
    val cloudSyncStatus: String,
    val currentUserEmail: String?,
    val timestamp: Long,
    val formattedTime: String,
    val recentTelemetry: List<TelemetryEvent>
)

data class BugReport(
    val id: String,
    val timestamp: Long,
    val formattedDate: String,
    val severity: BugReportSeverity,
    val userNotes: String,
    val diagnostics: DiagnosticData,
    val status: String = "QUEUED"
)

data class DashboardActivity(
    val id: String = java.util.UUID.randomUUID().toString().take(8),
    val action: String,
    val category: String, // "WIDGET", "SYNC", "SYSTEM", "SETTINGS", "DIAGNOSTIC", "AI"
    val timestamp: Long = System.currentTimeMillis(),
    val formattedTime: String
)

enum class HistoricalMetricType(
    val displayName: String,
    val unit: String,
    val description: String
) {
    GROSS_REVENUE("Gross Revenue", "$", "Compounding Annual Gross Trajectory"),
    HOURS_SAVED("Hours Saved", "h", "Autonomous Swarm Engineering Hours"),
    SWARM_THROUGHPUT("Swarm Ops", "ops/s", "Real-Time Subagent Transaction Velocity"),
    ARENA_SCORE("ArenaScore", "%", "Composite Five-Pillar Operational Quality")
}

enum class HistoricalTimeRange(
    val label: String,
    val intervalLabel: String,
    val samplePointsCount: Int
) {
    RANGE_1H("1H", "1-min intervals", 20),
    RANGE_24H("24H", "1-hour intervals", 24),
    RANGE_7D("7D", "Daily intervals", 7),
    RANGE_30D("30D", "3-day intervals", 10)
}

data class HistoricalTrendPoint(
    val id: String = java.util.UUID.randomUUID().toString().take(8),
    val timestamp: Long,
    val timeLabel: String,
    val grossRevenue: Double,
    val hoursSaved: Double,
    val swarmThroughput: Double,
    val arenaScore: Double
) {
    fun valueFor(metric: HistoricalMetricType): Double = when (metric) {
        HistoricalMetricType.GROSS_REVENUE -> grossRevenue
        HistoricalMetricType.HOURS_SAVED -> hoursSaved
        HistoricalMetricType.SWARM_THROUGHPUT -> swarmThroughput
        HistoricalMetricType.ARENA_SCORE -> arenaScore
    }
}

enum class ColivingLeaseStatus(val label: String) {
    ACTIVE("Active License"),
    RENEWAL_DUE("Renewal Window"),
    PENDING_SIGNATURE("Pending Signature"),
    VACANT("Vacant")
}

enum class ColivingLeaseType(val displayName: String, val description: String) {
    BED_LICENSE("By-The-Bed Private Room License", "Exclusive bedroom grant + shared common amenity easement without joint liability"),
    MASTER_SUBLEASE("Master Lease Subletting Agreement", "Operator Head Lease with explicit corporate sub-licensing authorization"),
    NOMAD_FLEX("Flexible 30-Day Rolling Stay", "Short/mid-term month-to-month agreement for digital nomads and traveling founders"),
    COMMUNITY_RULES("Community Code & House Rules Addendum", "Quiet hours, guest caps, fridge/pantry bin allocations, and chore SLAs"),
    UTILITY_SCHEDULE("All-Inclusive Utility & Amenity Schedule", "Gigabit Wi-Fi, bi-weekly cleanings, and pro-rata utility excess cap")
}

data class ColivingBedTwin(
    val bedId: String,
    val roomName: String,
    val occupantName: String,
    val monthlyRateUsd: Double = 830.0,
    val securityDepositUsd: Double = 830.0,
    val leaseStatus: ColivingLeaseStatus = ColivingLeaseStatus.ACTIVE,
    val leaseType: ColivingLeaseType = ColivingLeaseType.BED_LICENSE,
    val termMonths: Int = 12,
    val startDate: String = "2026-01-01",
    val endDate: String = "2026-12-31",
    val houseRulesScore: Int = 98,
    val utilityAllowanceUsd: Double = 120.0,
    val keylessAccessPin: String = "4819",
    val digitalHash: String = "ED25519-TWIN-VERIFIED"
)

data class ColivingContractConfig(
    val residentName: String = "Alex Vance",
    val bedId: String = "BED-A01",
    val propertyAddress: String = "Nexus Living Co-Op, 440 Innovation Blvd, Austin TX",
    val monthlyFee: Double = 830.0,
    val securityDeposit: Double = 830.0,
    val templateType: ColivingLeaseType = ColivingLeaseType.BED_LICENSE,
    val quietHoursStart: String = "22:00",
    val quietHoursEnd: String = "08:00",
    val guestConsecutiveNightsMax: Int = 3,
    val utilityCapUsd: Double = 120.0,
    val noticeDays: Int = 30
)


