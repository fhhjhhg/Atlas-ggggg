package com.example.data

/**
 * Data models for the Emergent Mind research aggregator, PocketFlow tutorial codebase,
 * awesome lists, hardware/drone reviews, heartbeat & soul telemetry, and bio-intelligence ecosystem.
 */

data class EmergentPaper(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedDate: String,
    val arxivId: String,
    val abstractText: String,
    val worksCitedCount: Int,
    val emergentScore: Double,
    val thoughtLeader: String,
    val topics: List<String>,
    val directUrl: String = "https://www.emergentmind.com/papers/$id",
    val githubRepoUrl: String = "https://github.com/emergentmind/$id",
    val worksCited: List<String> = listOf("Attention Is All You Need (Vaswani et al.)", "PocketFlow Micro-Graph Architecture (2026)", "Evo 2 StripedHyena Genomic Foundation (2025)")
)

data class ThoughtLeader(
    val id: String,
    val name: String,
    val institution: String,
    val role: String,
    val hIndex: Int,
    val primaryDomain: String,
    val worksCitedCount: Int,
    val sponsorships: List<String>,
    val affiliateLinks: List<String>,
    val bio: String
)

enum class TutorialDifficulty(val label: String, val badgeColor: String) {
    ZERO_TO_HERO("Zero to Hero", "Emerald"),
    INTERMEDIATE("Intermediate", "Cyan"),
    ADVANCED("Advanced Master", "Violet")
}

data class CodeDefinition(
    val term: String,
    val category: String,
    val definition: String,
    val codeOrFormula: String
)

data class PocketFlowTask(
    val step: Int,
    val title: String,
    val description: String,
    val command: String,
    val stateDelta: String,
    var isCompleted: Boolean = false
)

data class TutorialModule(
    val id: String,
    val level: TutorialDifficulty,
    val title: String,
    val subtitle: String,
    val durationMinutes: Int,
    val topics: List<String>,
    val codeSnippet: String,
    val explanation: String,
    val definitions: List<CodeDefinition>,
    val tasks: List<PocketFlowTask>,
    val notebookFilename: String = "01_pocketflow_agentic_zero_to_hero.ipynb",
    val hackathonPrompt: String = "Build an autonomous agent with sub-20ms state convergence using PocketFlow AST routing"
)

data class FrameworkToolkit(
    val id: String,
    val name: String,
    val category: String, // "Agentic Swarm", "Bio-Intelligence", "Edge Hardware", "MCP & RAG"
    val stars: String,
    val repoUrl: String,
    val description: String,
    val coreFeatures: List<String>,
    val sdkImportExample: String,
    val artifactApkName: String = "framework-edge-runtime.apk",
    val isAwesomeListFeatured: Boolean = true
)

data class McpRagSkill(
    val id: String,
    val name: String,
    val toolFunctions: List<String>,
    val ragVectorDimensions: Int = 1536,
    val chunkOverlap: Int = 128,
    val status: String = "Active (gRPC/SSE)",
    val dataModels: List<String>
)

data class HardwareReview(
    val id: String,
    val name: String,
    val category: String, // "Autonomous Drone", "Edge Neural Compute", "Bioreactor Node", "Sensing Payload"
    val rating: Double,
    val priceUsd: Double,
    val sponsorName: String,
    val affiliateUrl: String,
    val pros: List<String>,
    val cons: List<String>,
    val hardwareSpecs: Map<String, String>,
    val telemetryBpmSupport: Boolean = true
)

data class DroneSpec(
    val id: String,
    val modelName: String,
    val flightTimeMinutes: Int,
    val companionComputer: String, // e.g. "Jetson Orin Nano 8GB"
    val px4FirmwareVersion: String = "v1.14.3",
    val maxPayloadGrams: Int,
    val edgeAiInferenceFps: Double,
    val telemetryBpm: Int = 120
)

data class HeartbeatSoulTelemetry(
    val currentBpm: Int = 128,
    val soulState: String = "Coherent Autonomous Alignment",
    val pulseTimestamp: Long = System.currentTimeMillis(),
    val deltaVelocity: Double = 94.6,
    val systemHealthScore: Int = 99,
    val liveDeltas: List<String> = listOf(
        "AST symbol tree converged: +14 deltas",
        "MCP tool handshake: 8/8 tools synchronized",
        "Bioreactor pH optimal at 7.21 (delta: -0.02)",
        "Drone PX4 companion telemetry: 120 BPM pulse confirmed"
    )
)

// Bio-Intelligence & Evo Ecosystem Models
data class Bioprompt(
    val id: String,
    val title: String,
    val category: String, // "Protein Folding", "Metabolic Engineering", "CRISPR Specificity", "Bioreactor Optimization"
    val promptTemplate: String,
    val expectedOutcome: String,
    val evovibeRating: Int = 98
)

data class Bioreactor(
    val id: String,
    val name: String,
    val bioregion: String,
    val volumeLiters: Double,
    val tempCelsius: Double,
    val phLevel: Double,
    val dissolvedOxygenPercent: Double,
    val agitationRpm: Int,
    val biomassGramsPerLiter: Double,
    val status: String = "Cultivating (Exponential Growth)"
)

data class Bioregion(
    val id: String,
    val name: String,
    val climateZone: String,
    val nativeStrainsCount: Int,
    val microbialDiversityIndex: Double,
    val conservationStatus: String = "Secured"
)

data class Biospec(
    val id: String,
    val organismName: String,
    val strainCode: String,
    val pathwayTarget: String,
    val biosafetyLevel: String = "BSL-1",
    val yieldEfficiency: Double
)

data class Biocontext(
    val id: String,
    val name: String,
    val environmentMatrix: String,
    val biovibe: String, // e.g. "Hyper-Anaerobic Deep Ferment", "Phototrophic Algal Canopy"
    val energySource: String,
    val growthMultiplier: Double
)

data class EvoLink(
    val fromNode: String,
    val toNode: String,
    val linkageType: String, // "Catalytic Dependency", "Transcriptional Cascade", "Markov Transition"
    val deltaG: String
)

data class EvoMathFormula(
    val id: String,
    val name: String,
    val latexFormula: String,
    val explanation: String,
    val variables: Map<String, String>,
    val applicationDomain: String
)

data class EvoChemistryPath(
    val id: String,
    val pathwayName: String,
    val reactants: String,
    val products: String,
    val enzymeCatalyst: String,
    val deltaGKcalPerMol: Double,
    val kineticsRateConstant: String
)
