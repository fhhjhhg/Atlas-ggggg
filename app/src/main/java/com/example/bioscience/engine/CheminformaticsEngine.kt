package com.example.bioscience.engine

import com.example.bioscience.model.ChemicalDescriptor
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.roundToInt

object CheminformaticsEngine {

    val PRESET_MOLECULES: List<ChemicalDescriptor> = listOf(
        ChemicalDescriptor(
            name = "Aspirin (Acetylsalicylic Acid)",
            smiles = "CC(=O)OC1=CC=CC=C1C(=O)O",
            formula = "C9H8O4",
            molecularWeight = 180.16,
            tpsa = 63.6,
            hbd = 1,
            hba = 4,
            logP = 1.19,
            rotatableBonds = 3,
            heavyAtomCount = 13,
            lipinskiPass = true,
            lipinskiViolations = emptyList(),
            drugClass = "NSAID / COX-1 & COX-2 Inhibitor",
            therapeuticApplication = "Analgesic, Antipyretic, Anti-platelet prophylaxis"
        ),
        ChemicalDescriptor(
            name = "Caffeine (1,3,7-Trimethylxanthine)",
            smiles = "CN1C=NC2=C1C(=O)N(C(=O)N2C)C",
            formula = "C8H10N4O2",
            molecularWeight = 194.19,
            tpsa = 58.4,
            hbd = 0,
            hba = 6,
            logP = -0.07,
            rotatableBonds = 0,
            heavyAtomCount = 14,
            lipinskiPass = true,
            lipinskiViolations = emptyList(),
            drugClass = "CNS Stimulant / Adenosine A1 & A2A Receptor Antagonist",
            therapeuticApplication = "Alertness, Bronchodilator, Fatigue reduction"
        ),
        ChemicalDescriptor(
            name = "Paracetamol (Acetaminophen)",
            smiles = "CC(=O)NC1=CC=C(O)C=C1",
            formula = "C8H9NO2",
            molecularWeight = 151.16,
            tpsa = 49.3,
            hbd = 2,
            hba = 2,
            logP = 0.91,
            rotatableBonds = 1,
            heavyAtomCount = 11,
            lipinskiPass = true,
            lipinskiViolations = emptyList(),
            drugClass = "Analgesic / Central Endocannabinoid Modulator",
            therapeuticApplication = "Pain relief, Fever suppression"
        ),
        ChemicalDescriptor(
            name = "Ibuprofen",
            smiles = "CC(C)CC1=CC=C(C=C1)C(C)C(=O)O",
            formula = "C13H18O2",
            molecularWeight = 206.28,
            tpsa = 37.3,
            hbd = 1,
            hba = 2,
            logP = 3.50,
            rotatableBonds = 4,
            heavyAtomCount = 15,
            lipinskiPass = true,
            lipinskiViolations = emptyList(),
            drugClass = "NSAID / Reversible COX Inhibitor",
            therapeuticApplication = "Anti-inflammatory, Osteoarthritis, Migraine"
        ),
        ChemicalDescriptor(
            name = "Metformin",
            smiles = "CN(C)C(=N)NC(=N)N",
            formula = "C4H11N5",
            molecularWeight = 129.16,
            tpsa = 89.0,
            hbd = 4,
            hba = 5,
            logP = -1.43,
            rotatableBonds = 2,
            heavyAtomCount = 9,
            lipinskiPass = true,
            lipinskiViolations = emptyList(),
            drugClass = "Biguanide / AMPK Activator & Mitochondrial Complex I Inhibitor",
            therapeuticApplication = "Type 2 Diabetes, Insulin sensitization, Longevity"
        ),
        ChemicalDescriptor(
            name = "Remdesivir (GS-5734)",
            smiles = "CCC(CC)COC(=O)C(C)NP(=O)(OCC1C(C(C(O1)C#N)O)O)OC2=CC=CC=C2",
            formula = "C27H35N6O8P",
            molecularWeight = 602.58,
            tpsa = 158.4,
            hbd = 4,
            hba = 12,
            logP = 1.95,
            rotatableBonds = 13,
            heavyAtomCount = 42,
            lipinskiPass = false,
            lipinskiViolations = listOf("MW > 500 Da (602.58)", "HBA > 10 (12)", "Rotatable bonds > 10 (13)"),
            drugClass = "Broad-Spectrum Antiviral / RNA-Dependent RNA Polymerase Inhibitor",
            therapeuticApplication = "SARS-CoV-2, Ebola, Filovirus therapy"
        ),
        ChemicalDescriptor(
            name = "Penicillin G (Benzylpenicillin)",
            smiles = "CC1(C(N2C(S1)C(C2=O)NC(=O)CC3=CC=CC=C3)C(=O)O)C",
            formula = "C16H18N2O4S",
            molecularWeight = 334.39,
            tpsa = 75.6,
            hbd = 2,
            hba = 5,
            logP = 1.83,
            rotatableBonds = 4,
            heavyAtomCount = 23,
            lipinskiPass = true,
            lipinskiViolations = emptyList(),
            drugClass = "Beta-Lactam Antibiotic / Penicillin-Binding Protein Inhibitor",
            therapeuticApplication = "Streptococcal, Pneumococcal & Syphilis bacterial infections"
        ),
        ChemicalDescriptor(
            name = "ATP (Adenosine Triphosphate)",
            smiles = "C1=NC(=C2C(=N1)N(C=N2)C3C(C(C(O3)COP(=O)(O)OP(=O)(O)OP(=O)(O)O)O)O)N",
            formula = "C10H16N5O13P3",
            molecularWeight = 507.18,
            tpsa = 233.9,
            hbd = 7,
            hba = 18,
            logP = -3.80,
            rotatableBonds = 7,
            heavyAtomCount = 31,
            lipinskiPass = false,
            lipinskiViolations = listOf("MW > 500 Da (507.18)", "HBD > 5 (7)", "HBA > 10 (18)", "tPSA > 140 (233.9)"),
            drugClass = "Universal Bioenergetic Coenzyme / Nucleoside Triphosphate",
            therapeuticApplication = "Cellular respiration, Active transport, Enzymatic Phosphorylation"
        ),
        ChemicalDescriptor(
            name = "Atorvastatin (Lipitor)",
            smiles = "CC(C)C1=C(C(=C(N1CCC(CC(CC(=O)O)O)O)C2=CC=C(C=C2)F)C3=CC=CC=C3)C(=O)NC4=CC=CC=C4",
            formula = "C33H35FN2O5",
            molecularWeight = 558.64,
            tpsa = 111.8,
            hbd = 4,
            hba = 7,
            logP = 5.39,
            rotatableBonds = 12,
            heavyAtomCount = 41,
            lipinskiPass = false,
            lipinskiViolations = listOf("MW > 500 Da (558.64)", "LogP > 5.0 (5.39)", "Rotatable bonds > 10 (12)"),
            drugClass = "HMG-CoA Reductase Inhibitor / Statin",
            therapeuticApplication = "Hypercholesterolemia, Cardiovascular disease prevention"
        )
    )

    fun parseSmilesAndCalculate(name: String, rawSmiles: String): ChemicalDescriptor {
        val smiles = rawSmiles.trim()
        val found = PRESET_MOLECULES.find { it.smiles.equals(smiles, ignoreCase = true) || it.name.contains(name, ignoreCase = true) }
        if (found != null) return found

        // Heuristic analysis of custom SMILES
        var carbonCount = 0
        var oxygenCount = 0
        var nitrogenCount = 0
        var sulfurCount = 0
        var phosphorusCount = 0
        var fluorineCount = 0
        var chlorineCount = 0
        var bromineCount = 0
        var iodineCount = 0
        var doubleBonds = 0
        var rings = 0

        var i = 0
        while (i < smiles.length) {
            val c = smiles[i]
            when (c) {
                'C', 'c' -> carbonCount++
                'O', 'o' -> oxygenCount++
                'N', 'n' -> nitrogenCount++
                'S', 's' -> sulfurCount++
                'P', 'p' -> phosphorusCount++
                'F' -> fluorineCount++
                'B' -> if (i + 1 < smiles.length && smiles[i + 1] == 'r') { bromineCount++; i++ }
                'I' -> iodineCount++
                '=' -> doubleBonds++
                in '1'..'9' -> rings++
                else -> {
                    if (c == 'C' && i + 1 < smiles.length && smiles[i + 1] == 'l') {
                        chlorineCount++
                        carbonCount-- // undo carbon
                        i++
                    }
                }
            }
            i++
        }

        rings /= 2 // each ring has start and end digits

        // Approximate hydrogens
        val estimatedHydrogens = (carbonCount * 2 + 2 - (doubleBonds * 2) - (rings * 2) + nitrogenCount)
            .coerceAtLeast(carbonCount / 2)

        val mw = (carbonCount * 12.011) +
                (estimatedHydrogens * 1.008) +
                (oxygenCount * 15.999) +
                (nitrogenCount * 14.007) +
                (sulfurCount * 32.06) +
                (phosphorusCount * 30.974) +
                (fluorineCount * 18.998) +
                (chlorineCount * 35.45) +
                (bromineCount * 79.904) +
                (iodineCount * 126.90)

        val heavyAtomCount = carbonCount + oxygenCount + nitrogenCount + sulfurCount + phosphorusCount +
                fluorineCount + chlorineCount + bromineCount + iodineCount

        // Approximate HBD (OH, NH) and HBA (O, N)
        val hba = oxygenCount + nitrogenCount
        val hbd = (oxygenCount / 2 + nitrogenCount / 2).coerceAtLeast(if (oxygenCount > 0 || nitrogenCount > 0) 1 else 0)

        // Topological Polar Surface Area: O ~ 20.2 Å², N ~ 12.5 Å²
        val tpsa = (oxygenCount * 20.2) + (nitrogenCount * 12.5) + (sulfurCount * 28.2)

        // Wildman-Crippen LogP estimation
        val logP = (carbonCount * 0.28) + (fluorineCount * 0.35) + (chlorineCount * 0.65) -
                (oxygenCount * 0.38) - (nitrogenCount * 0.52) - (tpsa * 0.015)

        val rotatableBonds = (carbonCount / 3).coerceAtLeast(1)

        val violations = mutableListOf<String>()
        if (mw > 500.0) violations.add("MW > 500 Da (${mw.roundToInt()})")
        if (logP > 5.0) violations.add("LogP > 5.0 (${String.format("%.2f", logP)})")
        if (hbd > 5) violations.add("HBD > 5 ($hbd)")
        if (hba > 10) violations.add("HBA > 10 ($hba)")
        if (rotatableBonds > 10) violations.add("Rotatable bonds > 10 ($rotatableBonds)")

        val formula = "C${carbonCount}H${estimatedHydrogens}" +
                (if (nitrogenCount > 0) "N${nitrogenCount}" else "") +
                (if (oxygenCount > 0) "O${oxygenCount}" else "") +
                (if (sulfurCount > 0) "S${sulfurCount}" else "")

        return ChemicalDescriptor(
            name = if (name.isNotBlank()) name else "Custom Ligand ($formula)",
            smiles = smiles,
            formula = formula,
            molecularWeight = (mw * 100).roundToInt() / 100.0,
            tpsa = (tpsa * 10).roundToInt() / 10.0,
            hbd = hbd,
            hba = hba,
            logP = (logP * 100).roundToInt() / 100.0,
            rotatableBonds = rotatableBonds,
            heavyAtomCount = heavyAtomCount,
            lipinskiPass = violations.isEmpty(),
            lipinskiViolations = violations,
            drugClass = "Synthetic Chemical Scaffold",
            therapeuticApplication = "Computational Screening Candidate"
        )
    }

    /**
     * Calculates reaction thermodynamics (Gibbs Free Energy delta G and Equilibrium Constant Keq).
     * ΔG° = ΔH° - T * ΔS°
     * Keq = exp(-ΔG° / (R * T))
     */
    fun calculateThermodynamics(
        deltaH_kJ: Double,
        deltaS_J_K: Double,
        temperatureK: Double = 298.15
    ): Pair<Double, Double> {
        val r = 8.314462 // J/(mol·K)
        val deltaS_kJ = deltaS_J_K / 1000.0
        val deltaG_kJ = deltaH_kJ - (temperatureK * deltaS_kJ)

        val exponent = (-deltaG_kJ * 1000.0) / (r * temperatureK)
        val clampedExp = exponent.coerceIn(-50.0, 50.0)
        val keq = exp(clampedExp)

        return Pair(deltaG_kJ, keq)
    }
}
