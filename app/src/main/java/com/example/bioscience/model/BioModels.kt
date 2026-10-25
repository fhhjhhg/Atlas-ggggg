package com.example.bioscience.model

enum class NucleotideType(val symbol: Char, val complementDna: Char, val complementRna: Char, val isPurine: Boolean) {
    ADENINE('A', 'T', 'U', true),
    THYMINE('T', 'A', 'A', false),
    GUANINE('G', 'C', 'C', true),
    CYTOSINE('C', 'G', 'G', false),
    URACIL('U', 'A', 'A', false);

    companion object {
        fun fromChar(c: Char): NucleotideType? = when (c.uppercaseChar()) {
            'A' -> ADENINE
            'T' -> THYMINE
            'G' -> GUANINE
            'C' -> CYTOSINE
            'U' -> URACIL
            else -> null
        }
    }
}

data class CodonInfo(
    val triplet: String,
    val aminoAcidCode: Char,
    val aminoAcidName: String,
    val threeLetterCode: String,
    val isStartCodon: Boolean = false,
    val isStopCodon: Boolean = false
)

object GeneticCode {
    val table: Map<String, CodonInfo> = mapOf(
        "TTT" to CodonInfo("TTT", 'F', "Phenylalanine", "Phe"),
        "TTC" to CodonInfo("TTC", 'F', "Phenylalanine", "Phe"),
        "TTA" to CodonInfo("TTA", 'L', "Leucine", "Leu"),
        "TTG" to CodonInfo("TTG", 'L', "Leucine", "Leu"),
        "CTT" to CodonInfo("CTT", 'L', "Leucine", "Leu"),
        "CTC" to CodonInfo("CTC", 'L', "Leucine", "Leu"),
        "CTA" to CodonInfo("CTA", 'L', "Leucine", "Leu"),
        "CTG" to CodonInfo("CTG", 'L', "Leucine", "Leu"),
        "ATT" to CodonInfo("ATT", 'I', "Isoleucine", "Ile"),
        "ATC" to CodonInfo("ATC", 'I', "Isoleucine", "Ile"),
        "ATA" to CodonInfo("ATA", 'I', "Isoleucine", "Ile"),
        "ATG" to CodonInfo("ATG", 'M', "Methionine", "Met", isStartCodon = true),
        "GTT" to CodonInfo("GTT", 'V', "Valine", "Val"),
        "GTC" to CodonInfo("GTC", 'V', "Valine", "Val"),
        "GTA" to CodonInfo("GTA", 'V', "Valine", "Val"),
        "GTG" to CodonInfo("GTG", 'V', "Valine", "Val"),
        "TCT" to CodonInfo("TCT", 'S', "Serine", "Ser"),
        "TCC" to CodonInfo("TCC", 'S', "Serine", "Ser"),
        "TCA" to CodonInfo("TCA", 'S', "Serine", "Ser"),
        "TCG" to CodonInfo("TCG", 'S', "Serine", "Ser"),
        "CCT" to CodonInfo("CCT", 'P', "Proline", "Pro"),
        "CCC" to CodonInfo("CCC", 'P', "Proline", "Pro"),
        "CCA" to CodonInfo("CCA", 'P', "Proline", "Pro"),
        "CCG" to CodonInfo("CCG", 'P', "Proline", "Pro"),
        "ACT" to CodonInfo("ACT", 'T', "Threonine", "Thr"),
        "ACC" to CodonInfo("ACC", 'T', "Threonine", "Thr"),
        "ACA" to CodonInfo("ACA", 'T', "Threonine", "Thr"),
        "ACG" to CodonInfo("ACG", 'T', "Threonine", "Thr"),
        "GCT" to CodonInfo("GCT", 'A', "Alanine", "Ala"),
        "GCC" to CodonInfo("GCC", 'A', "Alanine", "Ala"),
        "GCA" to CodonInfo("GCA", 'A', "Alanine", "Ala"),
        "GCG" to CodonInfo("GCG", 'A', "Alanine", "Ala"),
        "TAT" to CodonInfo("TAT", 'Y', "Tyrosine", "Tyr"),
        "TAC" to CodonInfo("TAC", 'Y', "Tyrosine", "Tyr"),
        "TAA" to CodonInfo("TAA", '*', "Stop (Ochre)", "Stp", isStopCodon = true),
        "TAG" to CodonInfo("TAG", '*', "Stop (Amber)", "Stp", isStopCodon = true),
        "CAT" to CodonInfo("CAT", 'H', "Histidine", "His"),
        "CAC" to CodonInfo("CAC", 'H', "Histidine", "His"),
        "CAA" to CodonInfo("CAA", 'Q', "Glutamine", "Gln"),
        "CAG" to CodonInfo("CAG", 'Q', "Glutamine", "Gln"),
        "AAT" to CodonInfo("AAT", 'N', "Asparagine", "Asn"),
        "AAC" to CodonInfo("AAC", 'N', "Asparagine", "Asn"),
        "AAA" to CodonInfo("AAA", 'K', "Lysine", "Lys"),
        "AAG" to CodonInfo("AAG", 'K', "Lysine", "Lys"),
        "GAT" to CodonInfo("GAT", 'D', "Aspartate", "Asp"),
        "GAC" to CodonInfo("GAC", 'D', "Aspartate", "Asp"),
        "GAA" to CodonInfo("GAA", 'E', "Glutamate", "Glu"),
        "GAG" to CodonInfo("GAG", 'E', "Glutamate", "Glu"),
        "TGT" to CodonInfo("TGT", 'C', "Cysteine", "Cys"),
        "TGC" to CodonInfo("TGC", 'C', "Cysteine", "Cys"),
        "TGA" to CodonInfo("TGA", '*', "Stop (Opal)", "Stp", isStopCodon = true),
        "TGG" to CodonInfo("TGG", 'W', "Tryptophan", "Trp"),
        "CGT" to CodonInfo("CGT", 'R', "Arginine", "Arg"),
        "CGC" to CodonInfo("CGC", 'R', "Arginine", "Arg"),
        "CGA" to CodonInfo("CGA", 'R', "Arginine", "Arg"),
        "CGG" to CodonInfo("CGG", 'R', "Arginine", "Arg"),
        "AGT" to CodonInfo("AGT", 'S', "Serine", "Ser"),
        "AGC" to CodonInfo("AGC", 'S', "Serine", "Ser"),
        "AGA" to CodonInfo("AGA", 'R', "Arginine", "Arg"),
        "AGG" to CodonInfo("AGG", 'R', "Arginine", "Arg"),
        "GGT" to CodonInfo("GGT", 'G', "Glycine", "Gly"),
        "GGC" to CodonInfo("GGC", 'G', "Glycine", "Gly"),
        "GGA" to CodonInfo("GGA", 'G', "Glycine", "Gly"),
        "GGG" to CodonInfo("GGG", 'G', "Glycine", "Gly")
    )
}

data class OrfInfo(
    val frame: Int, // +1, +2, +3, -1, -2, -3
    val startNt: Int,
    val endNt: Int,
    val lengthNt: Int,
    val aminoAcidLength: Int,
    val proteinSequence: String
)

data class SequenceAnalysisResult(
    val cleanSequence: String,
    val length: Int,
    val gcContentPercent: Float,
    val atContentPercent: Float,
    val counts: Map<Char, Int>,
    val molecularWeightDa: Double,
    val meltingTempTm: Double,
    val reverseComplement: String,
    val translatedProtein: String,
    val orfs: List<OrfInfo>,
    val gcSkewProfile: List<Float>
)

data class AlignmentResult(
    val queryAligned: String,
    val targetAligned: String,
    val alignmentMarkers: String,
    val score: Int,
    val identityPercent: Float,
    val similarityPercent: Float,
    val matches: Int,
    val mismatches: Int,
    val gaps: Int
)

data class MutationEffect(
    val position: Int,
    val refBase: Char,
    val altBase: Char,
    val refCodon: String,
    val altCodon: String,
    val refAa: Char,
    val altAa: Char,
    val effectType: String // "Synonymous", "Missense", "Nonsense (Early Stop)", "Frameshift"
)

enum class SecondaryStructure(val label: String) {
    HELIX("Alpha-Helix"),
    SHEET("Beta-Sheet"),
    COIL("Random Coil / Loop")
}

data class PdbAtom(
    val serial: Int,
    val atomName: String,
    val residueName: String,
    val chainId: Char,
    val resSeq: Int,
    val x: Float,
    val y: Float,
    val z: Float,
    val occupancy: Float = 1.0f,
    val tempFactor: Float = 20.0f,
    val element: String = "C"
)

data class PdbResidue(
    val resSeq: Int,
    val name: String,
    val chainId: Char,
    val atoms: List<PdbAtom>,
    val secondaryStructure: SecondaryStructure = SecondaryStructure.COIL,
    val phiDeg: Float? = null,
    val psiDeg: Float? = null
)

enum class RamachandranRegion(val label: String) {
    CORE_ALPHA("Favored Alpha-Helix"),
    CORE_BETA("Favored Beta-Sheet"),
    ALLOWED("Allowed Secondary"),
    DISALLOWED("Disallowed Steric Clash")
}

data class RamachandranPoint(
    val residueName: String,
    val resSeq: Int,
    val phiDeg: Float,
    val psiDeg: Float,
    val region: RamachandranRegion
)

data class MolecularDynamicsFrame(
    val step: Int,
    val timePs: Double,
    val temperatureK: Double,
    val kineticEnergyKj: Double,
    val potentialEnergyKj: Double,
    val totalEnergyKj: Double,
    val pressureBar: Double,
    val rmsdAngstrom: Double
)

data class ChemicalDescriptor(
    val name: String,
    val smiles: String,
    val formula: String,
    val molecularWeight: Double,
    val tpsa: Double,
    val hbd: Int,
    val hba: Int,
    val logP: Double,
    val rotatableBonds: Int,
    val heavyAtomCount: Int,
    val lipinskiPass: Boolean,
    val lipinskiViolations: List<String>,
    val drugClass: String,
    val therapeuticApplication: String
)

enum class BioDatabaseType(val displayName: String, val badge: String) {
    GENBANK("NCBI GenBank", "DNA/RNA"),
    UNIPROT("UniProtKB / Swiss-Prot", "PROTEIN"),
    PDB("RCSB Protein Data Bank", "3D STRUCTURE"),
    CHEMBL("ChEMBL / PubChem", "BIOACTIVE"),
    KEGG("KEGG Pathway DB", "PATHWAY")
}

data class BioDatabaseEntry(
    val id: String,
    val database: BioDatabaseType,
    val accession: String,
    val title: String,
    val organism: String,
    val summary: String,
    val sequenceOrSmiles: String,
    val biologicalFunction: String,
    val referenceUrl: String
)

enum class SkuCategory(val label: String) {
    CRISPR_GUIDE("CRISPR-Cas9 / Cas12"),
    SYNTHETIC_PROMOTER("Mammalian Promoter"),
    METABOLIC_CHASSIS("Metabolic Pathway"),
    DE_NOVO_PROTEIN("De Novo Binder"),
    NANOBODY_VHH("Nanobody / VHH CDR"),
    EXPRESSION_VECTOR("Viral Vector")
}

data class BioEvoSku(
    val skuCode: String,
    val name: String,
    val category: SkuCategory,
    val targetGeneOrOrganism: String,
    val basePairs: Int,
    val evo2PerplexityScore: Float,
    val estimatedTiterMgL: Double,
    val synthesisVendor: String,
    val monthlyYieldUsd: Double,
    val ipProtectionStatus: String
)

enum class BioAiAgentEngine(val code: String, val title: String) {
    EVO2_STRIPED_HYENA("Evo2", "Evo 2 StripedHyena 131k"),
    OPEN_PLANTER("OpenPlanter", "Plant Synthetic Biology"),
    MCP_SCIENCE("MCP-Sci", "Model Context Protocol Tool Server"),
    RAG_EMBEDDING("Bio-RAG", "Dense Scientific Vector Embeddings"),
    OPEN_CLAW("OpenClaw", "Biochemical Web Scraper"),
    OPEN_CODE("OpenCode", "BioPython & PyMOL Generator"),
    OPEN_SHELL("OpenShell", "Sandboxed Scientific CLI"),
    OPEN_HANDS("OpenHands", "Autonomous Protocol Executor"),
    A2C_CLOUD("A2C", "Actor-to-Cloud Distributed Dispatcher")
}

data class BioAiStackNode(
    val engine: BioAiAgentEngine,
    val role: String,
    val status: String,
    val latencyMs: Int,
    val throughputQps: Double,
    val activeInferenceThreads: Int,
    val description: String
)

data class GodmodeInferenceResult(
    val query: String,
    val primaryEngine: BioAiAgentEngine,
    val planSteps: List<String>,
    val generatedSequenceOrCode: String,
    val confidenceScore: Float,
    val safetyStatus: String = "CLEARED (Dual-use Screening Passed)"
)
