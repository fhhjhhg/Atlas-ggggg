package com.example.bioscience.engine

import com.example.bioscience.model.AlignmentResult
import com.example.bioscience.model.GeneticCode
import com.example.bioscience.model.MutationEffect
import com.example.bioscience.model.OrfInfo
import com.example.bioscience.model.SequenceAnalysisResult
import kotlin.math.max

object BioinformaticsEngine {

    // Curated high-impact real bioscience sequences
    const val SAMPLE_INSULIN_CDS =
        "ATGGCCCTGTGGATGCGCCTCCTGCCCCTGCTGGCGCTGCTGGCCCTCTGGGGACCTGACCCAGCCGCAGCCTTTGTGAACCAACACCTGTGCGGCTCACACCTGGTGGAAGCTCTCTACCTAGTGTGCGGGGAACGAGGCTTCTTCTACACACCCAAGACCCGCCGGGAGGCAGAGGACCTGCAGGTGGGGCAGGTGGAGCTGGGCGGGGGCCCTGGTGCAGGCAGCCTGCAGCCCTTGGCCCTGGAGGGGTCCCTGCAGAAGCGTGGCATTGTGGAACAATGCTGTACCAGCATCTGCTCCCTCTACCAGCTGGAGAACTACTGCAACTAG"

    const val SAMPLE_SPIKE_RBD =
        "AACCTGTGTCCATTTGGTGAAGTTTTTAACGCCACCAGATTTGCATCTGTTTATGCTTGGAACAGGAAGAGAATCAGCAACTGTGTTGCTGATTATTCTGTCCTATATAATTCCGCATCATTTTCCACTTTTAAGTGTTATGGAGTGTCTCCTACTAAATTAAATGATCTCTGCTTTACTAATGTCTATGCAGATTCATTTGTAATTAGAGGTGATGAAGTCAGACAAATCGCTCCAGGGCAAACTGGAAAGATTGCTGATTATAATTATAAATTACCAGATGATTTTACAGGCTGCGTTATAGCTTGGAATTCTAACAATCTTGATTCTAAGGTTGGTGGTAATTATAATTACCTGTATAGATTGTTTAGGAAGTCTAATCTCAAACCTTTTGAGAGAGATATTTCAACTGAAATCTATCAGGCCGGTAGCACACCTTGTAATGGTGTTAAAGGTTTTAATTGTTACTTTCCTTTACAATCATATGGTTTCCAACCCACTAATGGTGTTGGTTACCAACCATACAGAGTAGTAGTACTTTCTTTTGAACTTCTACATGCACCAGCAACTGTTTGTGGACCTAAAAAGTCTACTAATTTGGTTAAAAACAAATGTGTCAATTTCAACTTCAATGGTTTAACAGGCACAGGTGTTCTTACTGAGTCTAACAAAAAGTTTCTGCCTTTCCAACAATTTGGCAGAGACATTGCTGACACTACTGATGCTGTCCGTGATCCACAGACACTTGAGATTCTTGACATTACACCATGTTCTTTTGGTGGTGTCAGTGTTATAACACCAGGAACAAATACTTCTAACCAGGTTGCTGTTCTTTATCAGGATGTTAACTGCACAGAAGTCCCTGTTGCTATTCATGCAGATCAACTTACTCCTACTTGGCGTGTTTATTCTACAGGTTCTAATGTTTTTCAAACACGTGCAGGCTGTTTAATAGGGGCTGAACATGTCAACAACTCATATGAGTGTGACATACCCATTGGTGCAGGTATATGCGCTAGTTATCAGACTCAGACTAATTCT"

    const val SAMPLE_CAS9_GUIDE =
        "GGGAGTTCAGTAGACAGAGAGTTTTAGAGCTAGAAATAGCAAGTTAAAATAAGGCTAGTCCGTTATCAACTTGAAAAAGTGGCACCGAGTCGGTGCTTTTT"

    const val SAMPLE_GFP_FRAGMENT =
        "ATGAGTAAAGGAGAAGAACTTTTCACTGGAGTTGTCCCAATTCTTGTTGAATTAGATGGTGATGTTAATGGGCACAAATTTTCTGTCAGTGGAGAGGGTGAAGGTGATGCAACATACGGAAAACTTACCCTTAAATTTATTTGCACTACTGGAAAACTACCTGTTCCATGGCCAACACTTGTCACTACTTTCTCTTATGGTGTTCAATGCTTTTCAAGATACCCAGATCATATGAAACGGCATGACTTTTTCAAGAGTGCCATGCCCGAAGGTTATGTACAGGAAAGAACTATATTTTTCAAAGATGACGGGAACTACAAGACACGTGCTGAAGTCAAGTTTGAAGGTGATACCCTTGTTAATAGAATCGAGTTAAAAGGTATTGATTTTAAAGAAGATGGAAACATTCTTGGACACAAATTGGAATACAACTATAACTCACACAATGTATACATCATGGCAGACAAACAAAAGAATGGAATCAAAGTTAACTTCAAAATTAGACACAACATTGAAGATGGAAGCGTTCAACTAGCAGACCATTATCAACAAAATACTCCAATTGGCGATGGCCCTGTCCTTTTACCAGACAACCATTACCTGTCGACACAATCTGCCCTTTCGAAAGATCCCAACGAAAAGAGAGACCACATGGTCCTTCTTGAGTTTGTAACAGCTGCTGGGATTACACATGGCATGGATGAACTATACAAATAA"

    fun cleanSequence(input: String): String {
        return input.replace(Regex("[^a-zA-Z]"), "").uppercase()
    }

    fun analyzeDnaSequence(rawInput: String): SequenceAnalysisResult {
        val seq = cleanSequence(rawInput)
        val len = seq.length
        if (len == 0) {
            return SequenceAnalysisResult(
                cleanSequence = "",
                length = 0,
                gcContentPercent = 0f,
                atContentPercent = 0f,
                counts = emptyMap(),
                molecularWeightDa = 0.0,
                meltingTempTm = 0.0,
                reverseComplement = "",
                translatedProtein = "",
                orfs = emptyList(),
                gcSkewProfile = emptyList()
            )
        }

        val counts = mutableMapOf('A' to 0, 'C' to 0, 'G' to 0, 'T' to 0)
        for (c in seq) {
            if (counts.containsKey(c)) {
                counts[c] = counts[c]!! + 1
            } else if (c == 'U') {
                counts['T'] = (counts['T'] ?: 0) + 1
            }
        }

        val gCount = counts['G'] ?: 0
        val cCount = counts['C'] ?: 0
        val aCount = counts['A'] ?: 0
        val tCount = counts['T'] ?: 0
        val gcCount = gCount + cCount
        val atCount = aCount + tCount

        val gcPercent = if (len > 0) (gcCount.toFloat() / len.toFloat()) * 100f else 0f
        val atPercent = if (len > 0) (atCount.toFloat() / len.toFloat()) * 100f else 0f

        // Average single-stranded DNA nucleotide molecular weight ~330 Da
        val mw = (aCount * 313.21) + (tCount * 304.2) + (cCount * 289.18) + (gCount * 329.21) - 61.96

        // Melting temperature (Wallace rule for <20 nt, Nearest-Neighbor approximation for >=20 nt)
        val tm = if (len < 20) {
            (atCount * 2.0) + (gcCount * 4.0)
        } else {
            64.9 + 41.0 * (gcCount - 16.4) / len.toDouble()
        }

        val revComp = getReverseComplement(seq)
        val translated = translateToProtein(seq)
        val orfs = findOrfs(seq)
        val gcSkew = calculateWindowedGcSkew(seq, windowSize = max(10, len / 20))

        return SequenceAnalysisResult(
            cleanSequence = seq,
            length = len,
            gcContentPercent = gcPercent,
            atContentPercent = atPercent,
            counts = counts,
            molecularWeightDa = mw,
            meltingTempTm = tm,
            reverseComplement = revComp,
            translatedProtein = translated,
            orfs = orfs,
            gcSkewProfile = gcSkew
        )
    }

    fun getReverseComplement(seq: String): String {
        val sb = StringBuilder(seq.length)
        for (i in seq.length - 1 downTo 0) {
            val c = seq[i]
            val comp = when (c) {
                'A' -> 'T'
                'T' -> 'A'
                'U' -> 'A'
                'G' -> 'C'
                'C' -> 'G'
                else -> 'N'
            }
            sb.append(comp)
        }
        return sb.toString()
    }

    fun translateToProtein(seq: String): String {
        val protein = StringBuilder()
        var i = 0
        while (i + 2 < seq.length) {
            val codon = seq.substring(i, i + 3).replace('U', 'T')
            val info = GeneticCode.table[codon]
            val aa = info?.aminoAcidCode ?: 'X'
            protein.append(aa)
            i += 3
        }
        return protein.toString()
    }

    fun findOrfs(seq: String, minPeptideLength: Int = 15): List<OrfInfo> {
        val orfs = mutableListOf<OrfInfo>()

        // 3 Forward frames
        for (frame in 0..2) {
            searchOrfsInFrame(seq, frame, isReverse = false, minPeptideLength, orfs)
        }

        // 3 Reverse complement frames
        val revComp = getReverseComplement(seq)
        for (frame in 0..2) {
            searchOrfsInFrame(revComp, frame, isReverse = true, minPeptideLength, orfs)
        }

        return orfs.sortedByDescending { it.aminoAcidLength }
    }

    private fun searchOrfsInFrame(
        seq: String,
        frameOffset: Int,
        isReverse: Boolean,
        minPeptideLength: Int,
        outList: MutableList<OrfInfo>
    ) {
        var openStart: Int? = null
        var i = frameOffset
        val currentPeptide = StringBuilder()

        while (i + 2 < seq.length) {
            val codon = seq.substring(i, i + 3).replace('U', 'T')
            val info = GeneticCode.table[codon]

            if (openStart == null) {
                if (info?.isStartCodon == true) {
                    openStart = i
                    currentPeptide.clear()
                    currentPeptide.append(info.aminoAcidCode)
                }
            } else {
                if (info?.isStopCodon == true) {
                    val endNt = i + 3
                    val lengthNt = endNt - openStart
                    val aaLen = currentPeptide.length
                    if (aaLen >= minPeptideLength) {
                        outList.add(
                            OrfInfo(
                                frame = if (isReverse) -(frameOffset + 1) else (frameOffset + 1),
                                startNt = openStart,
                                endNt = endNt,
                                lengthNt = lengthNt,
                                aminoAcidLength = aaLen,
                                proteinSequence = currentPeptide.toString()
                            )
                        )
                    }
                    openStart = null
                } else {
                    currentPeptide.append(info?.aminoAcidCode ?: 'X')
                }
            }
            i += 3
        }
    }

    fun calculateWindowedGcSkew(seq: String, windowSize: Int): List<Float> {
        val results = mutableListOf<Float>()
        if (seq.isEmpty() || windowSize <= 0) return results

        var i = 0
        while (i < seq.length) {
            val end = (i + windowSize).coerceAtMost(seq.length)
            val window = seq.substring(i, end)
            var g = 0
            var c = 0
            for (ch in window) {
                if (ch == 'G') g++
                if (ch == 'C') c++
            }
            val sum = g + c
            val skew = if (sum > 0) (g - c).toFloat() / sum.toFloat() else 0f
            results.add(skew)
            i += windowSize
        }
        return results
    }

    /**
     * Pairwise Global Sequence Alignment using Needleman-Wunsch Dynamic Programming.
     */
    fun alignSequences(
        seq1Raw: String,
        seq2Raw: String,
        matchScore: Int = 2,
        mismatchScore: Int = -1,
        gapScore: Int = -2
    ): AlignmentResult {
        val s1 = cleanSequence(seq1Raw)
        val s2 = cleanSequence(seq2Raw)

        val n = s1.length
        val m = s2.length

        if (n == 0 || m == 0) {
            return AlignmentResult(
                queryAligned = s1,
                targetAligned = s2,
                alignmentMarkers = "",
                score = 0,
                identityPercent = 0f,
                similarityPercent = 0f,
                matches = 0,
                mismatches = 0,
                gaps = 0
            )
        }

        val dp = Array(n + 1) { IntArray(m + 1) }

        for (i in 0..n) dp[i][0] = i * gapScore
        for (j in 0..m) dp[0][j] = j * gapScore

        for (i in 1..n) {
            for (j in 1..m) {
                val match = dp[i - 1][j - 1] + if (s1[i - 1] == s2[j - 1]) matchScore else mismatchScore
                val delete = dp[i - 1][j] + gapScore
                val insert = dp[i][j - 1] + gapScore
                dp[i][j] = max(match, max(delete, insert))
            }
        }

        // Traceback
        val aligned1 = StringBuilder()
        val aligned2 = StringBuilder()
        val markers = StringBuilder()

        var i = n
        var j = m
        var matches = 0
        var mismatches = 0
        var gaps = 0

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + if (s1[i - 1] == s2[j - 1]) matchScore else mismatchScore) {
                val c1 = s1[i - 1]
                val c2 = s2[j - 1]
                aligned1.append(c1)
                aligned2.append(c2)
                if (c1 == c2) {
                    markers.append('|')
                    matches++
                } else {
                    markers.append('.')
                    mismatches++
                }
                i--
                j--
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + gapScore) {
                aligned1.append(s1[i - 1])
                aligned2.append('-')
                markers.append(' ')
                gaps++
                i--
            } else {
                aligned1.append('-')
                aligned2.append(s2[j - 1])
                markers.append(' ')
                gaps++
                j--
            }
        }

        val a1 = aligned1.reverse().toString()
        val a2 = aligned2.reverse().toString()
        val mk = markers.reverse().toString()
        val totalLength = a1.length
        val identity = if (totalLength > 0) (matches.toFloat() / totalLength) * 100f else 0f
        val similarity = if (totalLength > 0) ((matches + mismatches).toFloat() / totalLength) * 100f else 0f

        return AlignmentResult(
            queryAligned = a1,
            targetAligned = a2,
            alignmentMarkers = mk,
            score = dp[n][m],
            identityPercent = identity,
            similarityPercent = similarity,
            matches = matches,
            mismatches = mismatches,
            gaps = gaps
        )
    }

    /**
     * Variant and Mutation Effect Predictor
     */
    fun analyzeMutation(refSeq: String, altSeq: String): List<MutationEffect> {
        val sRef = cleanSequence(refSeq)
        val sAlt = cleanSequence(altSeq)
        val effects = mutableListOf<MutationEffect>()

        val minLen = minOf(sRef.length, sAlt.length)
        for (i in 0 until minLen) {
            if (sRef[i] != sAlt[i]) {
                val codonIndex = (i / 3) * 3
                val refCodon = if (codonIndex + 3 <= sRef.length) sRef.substring(codonIndex, codonIndex + 3) else ""
                val altCodon = if (codonIndex + 3 <= sAlt.length) sAlt.substring(codonIndex, codonIndex + 3) else ""

                val refAa = GeneticCode.table[refCodon]?.aminoAcidCode ?: 'X'
                val altAa = GeneticCode.table[altCodon]?.aminoAcidCode ?: 'X'

                val effectType = when {
                    refAa == altAa -> "Synonymous (Silent)"
                    altAa == '*' -> "Nonsense (Premature Stop)"
                    refAa == '*' -> "Readthrough Stop Loss"
                    else -> "Missense ($refAa -> $altAa)"
                }

                effects.add(
                    MutationEffect(
                        position = i + 1,
                        refBase = sRef[i],
                        altBase = sAlt[i],
                        refCodon = refCodon,
                        altCodon = altCodon,
                        refAa = refAa,
                        altAa = altAa,
                        effectType = effectType
                    )
                )
            }
        }

        if (sRef.length != sAlt.length) {
            effects.add(
                MutationEffect(
                    position = minLen + 1,
                    refBase = '-',
                    altBase = '+',
                    refCodon = "INDEL",
                    altCodon = "INDEL",
                    refAa = '-',
                    altAa = '+',
                    effectType = "Frameshift Indel (Length diff: ${sAlt.length - sRef.length} nt)"
                )
            )
        }

        return effects
    }
}
