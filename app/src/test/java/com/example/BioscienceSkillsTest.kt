package com.example

import com.example.bioscience.engine.BioAiStack
import com.example.bioscience.engine.BioDatabaseRegistry
import com.example.bioscience.engine.BioinformaticsEngine
import com.example.bioscience.engine.CheminformaticsEngine
import com.example.bioscience.engine.MolecularDynamicsEngine
import com.example.bioscience.model.BioDatabaseType
import com.example.bioscience.model.RamachandranRegion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BioscienceSkillsTest {

    @Test
    fun testBioinformaticsSequenceAnalysis() {
        val raw = "ATGGCCCTGTGGATGCGCCTCCTGCCCCTGCTGGCGCTGCTGGCCCTCTGGGGACCTGACCCAGCCGCAGCCTTTGTGAACCAACACCTGTGCGGCTCACACCTGGTGGAAGCTCTCTACCTAGTGTGCGGGGAACGAGGCTTCTTCTACACACCCAAGACCCGCCGGGAGGCAGAGGACCTGCAGGTGGGGCAGGTGGAGCTGGGCGGGGGCCCTGGTGCAGGCAGCCTGCAGCCCTTGGCCCTGGAGGGGTCCCTGCAGAAGCGTGGCATTGTGGAACAATGCTGTACCAGCATCTGCTCCCTCTACCAGCTGGAGAACTACTGCAACTAG"
        val analysis = BioinformaticsEngine.analyzeDnaSequence(raw)

        assertEquals(333, analysis.length)
        assertTrue(analysis.gcContentPercent > 50f)
        assertTrue(analysis.meltingTempTm > 60.0)
        assertFalse(analysis.reverseComplement.isEmpty())
        assertTrue(analysis.translatedProtein.startsWith("MALWM"))
        assertTrue(analysis.translatedProtein.endsWith("ENYCN*"))

        // Check reverse complement logic
        val testOligo = "ATGC"
        val revComp = BioinformaticsEngine.getReverseComplement(testOligo)
        assertEquals("GCAT", revComp)

        // Check ORF detection
        val orfs = BioinformaticsEngine.findOrfs(raw, minPeptideLength = 10)
        assertTrue(orfs.isNotEmpty())
        assertEquals(1, orfs.first().frame)
    }

    @Test
    fun testPairwiseSequenceAlignment() {
        val s1 = "ATGCGATCG"
        val s2 = "ATGCGATCG"
        val result = BioinformaticsEngine.alignSequences(s1, s2)

        assertEquals(100f, result.identityPercent, 0.01f)
        assertEquals(0, result.gaps)
        assertEquals(9, result.matches)
        assertTrue(result.score > 0)

        // Test with single mutation and gap
        val s3 = "ATG-GATCG"
        val s4 = "ATGCGATCG"
        val diffResult = BioinformaticsEngine.alignSequences(s3, s4)
        assertTrue(diffResult.identityPercent > 70f)
    }

    @Test
    fun testMutationPredictor() {
        val ref = "ATGTTT" // Met Phe
        val altSyn = "ATGTTTC" // Check indel detection
        val effects = BioinformaticsEngine.analyzeMutation(ref, altSyn)
        assertTrue(effects.any { it.effectType.contains("Frameshift") })

        val altSub = "ATGTTG" // TTT (Phe) -> TTG (Leu) = Missense
        val effects2 = BioinformaticsEngine.analyzeMutation(ref, altSub)
        assertTrue(effects2.any { it.effectType.contains("Missense") })
    }

    @Test
    fun testMolecularDynamicsPdbParsingAndAngles() {
        val atoms = MolecularDynamicsEngine.parsePdb(MolecularDynamicsEngine.PDB_1CRN_RAW)
        assertTrue("PDB atom count should be parsed", atoms.size > 20)
        assertEquals("THR", atoms.first().residueName)
        assertEquals('A', atoms.first().chainId)

        val residues = MolecularDynamicsEngine.groupIntoResidues(atoms)
        assertTrue("Residues should be grouped", residues.isNotEmpty())

        val ramachandranPoints = MolecularDynamicsEngine.calculateRamachandranPoints(residues)
        assertEquals(residues.size, ramachandranPoints.size)

        // RMSD test
        val selfRmsd = MolecularDynamicsEngine.calculateRmsd(atoms, atoms)
        assertEquals(0.0, selfRmsd, 1e-4)

        // Verlet MD steps
        val frames = MolecularDynamicsEngine.simulateMdSteps(stepCount = 10)
        assertEquals(10, frames.size)
        val lastFrame = frames.last()
        assertTrue(lastFrame.timePs > 0.0)
        assertTrue(lastFrame.temperatureK in 290.0..315.0)
        assertTrue(lastFrame.rmsdAngstrom > 0.0)
    }

    @Test
    fun testCheminformaticsSmilesAndLipinski() {
        // Aspirin test
        val aspirin = CheminformaticsEngine.parseSmilesAndCalculate("Aspirin", "CC(=O)OC1=CC=CC=C1C(=O)O")
        assertEquals(180.16, aspirin.molecularWeight, 0.1)
        assertTrue(aspirin.lipinskiPass)
        assertEquals(0, aspirin.lipinskiViolations.size)

        // Remdesivir (MW > 500 Da)
        val remdesivir = CheminformaticsEngine.parseSmilesAndCalculate("Remdesivir", "CCC(CC)COC(=O)C(C)NP(=O)(OCC1C(C(C(O1)C#N)O)O)OC2=CC=CC=C2")
        assertFalse(remdesivir.lipinskiPass)
        assertTrue(remdesivir.lipinskiViolations.any { it.contains("MW > 500") })

        // Thermodynamics solver test
        val (deltaG, keq) = CheminformaticsEngine.calculateThermodynamics(deltaH_kJ = -30.5, deltaS_J_K = 15.0)
        assertTrue(deltaG < 0.0) // Spontaneous exergonic reaction
        assertTrue(keq > 1.0)
    }

    @Test
    fun testBioDatabaseRegistry() {
        val allEntries = BioDatabaseRegistry.ENTRIES
        assertTrue(allEntries.size >= 8)

        val genbank = BioDatabaseRegistry.queryEntries("SARS-CoV-2", BioDatabaseType.GENBANK)
        assertTrue(genbank.isNotEmpty())
        assertEquals("NC_045512.2", genbank.first().accession)

        val uniprot = BioDatabaseRegistry.queryEntries("Insulin", BioDatabaseType.UNIPROT)
        assertTrue(uniprot.isNotEmpty())
        assertEquals("P01308", uniprot.first().accession)

        val pdb = BioDatabaseRegistry.queryEntries("Crambin", BioDatabaseType.PDB)
        assertTrue(pdb.isNotEmpty())
        assertEquals("1CRN", pdb.first().accession)
    }

    @Test
    fun testBioAiStackAndGodmodeInference() {
        assertTrue(BioAiStack.SKU_ATLAS.isNotEmpty())
        assertTrue(BioAiStack.ACTIVE_STACK_NODES.isNotEmpty())

        val result = BioAiStack.runGodmodeInference("Design OpenPlanter plant Rubisco metabolic pathway")
        assertNotNull(result)
        assertTrue(result.planSteps.size >= 3)
        assertTrue(result.confidenceScore > 0.9f)
        assertTrue(result.generatedSequenceOrCode.contains("pPLANTER") || result.generatedSequenceOrCode.contains("Evo"))
    }
}
