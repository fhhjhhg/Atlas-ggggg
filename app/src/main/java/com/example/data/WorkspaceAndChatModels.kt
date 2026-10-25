package com.example.data

/**
 * Data models for:
 * 1. Workspaces (Executive, Analyst, DevOps, FinOps, Bio-Evo)
 * 2. Persistent AI Assistant Chat
 * 3. Focus Mode full-screen inspection & notes
 * 4. Recommendation smart filtering
 */

enum class WorkspaceType(val id: String, val title: String, val description: String, val includedWidgetTypes: Set<WidgetType>) {
    val label: String get() = title
    ALL(
        id = "ws-all",
        title = "All Workstations",
        description = "Unfiltered master control center with all operational widgets",
        includedWidgetTypes = WidgetType.values().toSet()
    ),
    EXECUTIVE(
        id = "ws-executive",
        title = "Executive",
        description = "High-level yield, ArenaScore, compounding trajectory & portfolio metrics",
        includedWidgetTypes = setOf(
            WidgetType.EXECUTIVE_METRICS,
            WidgetType.ARENA_SCORE,
            WidgetType.COMPOUNDING_CHART,
            WidgetType.METRIC_FORECASTING,
            WidgetType.ASSET_REGISTRY
        )
    ),
    ANALYST(
        id = "ws-analyst",
        title = "Analyst",
        description = "Historical trends, OLS linear regression forecasting & research citations",
        includedWidgetTypes = setOf(
            WidgetType.HISTORICAL_TREND_CHART,
            WidgetType.METRIC_FORECASTING,
            WidgetType.EMERGENT_RESEARCH_KNOWLEDGE,
            WidgetType.EXECUTIVE_METRICS
        )
    ),
    DEVOPS(
        id = "ws-devops",
        title = "DevOps",
        description = "Sub-20ms live telemetry, Agentic Enclave AST, and SRE anomaly audit",
        includedWidgetTypes = setOf(
            WidgetType.LIVE_TELEMETRY,
            WidgetType.AGENTIC_ENCLAVE,
            WidgetType.ACTION_SIMULATOR,
            WidgetType.MEGA_PILLARS
        )
    ),
    FINOPS(
        id = "ws-finops",
        title = "FinOps",
        description = "Real estate tokenomics, 35-bed digital twins & BookMyColiving marketplace",
        includedWidgetTypes = setOf(
            WidgetType.COMPOUNDING_CHART,
            WidgetType.COLIVING_DIGITAL_TWIN,
            WidgetType.BOOK_MY_COLIVING,
            WidgetType.ASSET_REGISTRY
        )
    ),
    BIO_EVO(
        id = "ws-bio",
        title = "Bio-Evo",
        description = "BioCommons, PDB molecular dynamics, bioreactor kinetics & EvoMath",
        includedWidgetTypes = setOf(
            WidgetType.BIOSCIENCE_HUB,
            WidgetType.BIOCOMMONS_MATRIX,
            WidgetType.DIFFUSERS_TRANSFORMERS,
            WidgetType.SCIENTIFIC_PROVER
        )
    )
}

data class AssistantChatMessage(
    val id: String = java.util.UUID.randomUUID().toString().take(8),
    val sender: String, // "USER" or "GEMINI"
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val actionSuggestion: SmartActionItem? = null
)

enum class SmartRecommendationCategory(val label: String) {
    ALL("All Categories"),
    EFFICIENCY("Efficiency & Speed"),
    SECURITY("Security & SASE"),
    SYSTEM_HEALTH("System Health & SLAs"),
    FINANCIAL_YIELD("Financial & Yield")
}
