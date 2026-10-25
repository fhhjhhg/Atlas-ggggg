package com.example.data

/**
 * Data models for the BookMyColiving digital platform, marketplace,
 * and enterprise technology stack (Domains, APKs, SDKs, BOM, SOW, SOP, MCP, RAG, Tokenomics, 1000x DXI).
 */

enum class ColivingVibe(val displayName: String, val icon: String) {
    DIGITAL_NOMAD("Digital Nomad & Remote Work", "Nomad"),
    AI_DEEP_WORK("AI & Deep Tech Founders", "Tech"),
    BIOSCIENCE_RESEARCH("BioScience & Lab Co-Op", "Bio"),
    STUDENT_SCHOLAR("Student & Scholar Residency", "Academic"),
    CREATIVE_STUDIO("Creative & Media Enclave", "Studio")
}

data class ColivingSpaceListing(
    val id: String,
    val name: String,
    val city: String,
    val country: String,
    val address: String,
    val vibe: ColivingVibe,
    val monthlyRateUsd: Double,
    val totalBeds: Int,
    val availableBeds: Int,
    val directInquiryEmail: String,
    val directUrl: String = "https://bookmycoliving.com/space/",
    val rating: Double = 4.9,
    val amenities: List<String> = listOf("Gigabit Mesh Wi-Fi", "Keyless Access", "Bi-Weekly Cleanings", "24/7 Coworking"),
    val skuCode: String,
    val operatorLlc: String = "Nexus Living Operator LLC",
    val isVerifiedDirect: Boolean = true
)

data class DomainAsset(
    val domain: String,
    val httpsUrl: String,
    val status: String = "Active / Secured (TLS 1.3)",
    val registrar: String = "Google Cloud Domains / Cloudflare",
    val purpose: String,
    val monthlyVisits: Int,
    val valuationUsd: Double
)

data class SoftwareArtifact(
    val name: String,
    val type: String, // "APK", "SDK", "MCP"
    val version: String,
    val sizeMb: Double,
    val description: String,
    val architecture: String = "arm64-v8a / JVM",
    val repoUrl: String
)

data class HardwareBomItem(
    val sku: String,
    val componentName: String,
    val category: String, // "NETWORKING", "ACCESS_CONTROL", "POWER_BACKUP", "WORKSTATION"
    val unitCostUsd: Double,
    val quantity: Int,
    val vendor: String,
    val status: String = "Deployed / Operational"
) {
    val totalCostUsd: Double get() = unitCostUsd * quantity
}

data class StatementOfWorkPhase(
    val phaseNumber: Int,
    val title: String,
    val milestone: String,
    val progressPercent: Int, // 0 - 100
    val owner: String,
    val deliverables: List<String>
)

data class StandardOperatingProcedure(
    val code: String,
    val title: String,
    val category: String,
    val executionFrequency: String,
    val slaTarget: String,
    val ruleSummary: String
)

data class McpServerLink(
    val serverId: String,
    val name: String,
    val protocolVersion: String = "2024-11-05 (MCP)",
    val toolsCount: Int,
    val status: String = "Connected (gRPC/SSE)",
    val capabilities: List<String>
)

data class RagKnowledgeDocument(
    val docId: String,
    val title: String,
    val vectorDimension: Int = 768,
    val chunkCount: Int,
    val category: String,
    val lastIndexedTimestamp: Long = System.currentTimeMillis()
)

data class TokenomicsPool(
    val symbol: String = "NEXUS-PROP",
    val totalFractionalUnits: Long = 10_000_000L,
    val stakedUnits: Long = 6_840_000L,
    val apyPercent: Double = 14.8,
    val monthlyYieldDistributedUsd: Double = 27_390.0,
    val dscrReserveRatio: Double = 3.65
)

data class DxiMetrics(
    val score: Int = 1000,
    val label: String = "1000x Domain Experience Index",
    val autonomousVelocityOpsPerSec: Double = 142.0,
    val latencyMs: Double = 18.2,
    val hoursSavedCumulative: Double = 2548.5,
    val skillsArbitrageSavingsUsd: Double = 34860.0
)
