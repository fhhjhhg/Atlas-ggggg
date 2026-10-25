package com.example.bioscience.engine

import com.example.bioscience.model.BioDatabaseEntry
import com.example.bioscience.model.BioDatabaseType

object BioDatabaseRegistry {

    val ENTRIES: List<BioDatabaseEntry> = listOf(
        BioDatabaseEntry(
            id = "db_genbank_covid",
            database = BioDatabaseType.GENBANK,
            accession = "NC_045512.2",
            title = "Severe acute respiratory syndrome coronavirus 2 isolate Wuhan-Hu-1, complete genome",
            organism = "SARS-CoV-2 (Coronaviridae)",
            summary = "29,903 bp single-stranded positive-sense RNA viral genome encoding ORF1ab replicase polyprotein, Spike (S), Envelope (E), Membrane (M), and Nucleocapsid (N) structural proteins.",
            sequenceOrSmiles = "ATTAAAGGTTTATACCTTCCCAGGTAACAAACCAACCAACTTTCGATCTCTTGTAGATCTGTTCTCTAAACGAACTTTAAAATCTGTGTGGCTGTCACTCGGCTGCATGCTTAGTGCACTCACGCAGTATAATTAATAACTAATTACTGTCGTTGACAGGACACGAGTAACTCGTCTATCTTCTGCAGGCTGCTTACGGTTTCGTCCGTGTTGCAGCCGATCATCAGCACATCTAGGTTTCGTCCGGGTGTGACCGAAAGGTAAGATGGAGAGCCTTGTCCCTGGTTTCAACGAGAAAACACACGTCCAACTCAGTTTGCCTGTTTTACAGGTTCGCGAC",
            biologicalFunction = "Viral pathogen initiating COVID-19 pandemic through ACE2 cell surface receptor binding via S1 receptor-binding domain (RBD).",
            referenceUrl = "https://www.ncbi.nlm.nih.gov/nuccore/NC_045512.2"
        ),
        BioDatabaseEntry(
            id = "db_genbank_insulin",
            database = BioDatabaseType.GENBANK,
            accession = "NM_000207.3",
            title = "Homo sapiens insulin (INS), transcript variant 1, mRNA",
            organism = "Homo sapiens (Human)",
            summary = "469 bp mRNA transcript for preproinsulin composed of signal peptide (24 aa), B chain (30 aa), C-peptide (31 aa), and A chain (21 aa) connected via disulfide bridges.",
            sequenceOrSmiles = BioinformaticsEngine.SAMPLE_INSULIN_CDS,
            biologicalFunction = "Endocrine peptide hormone produced by pancreatic beta cells regulating systemic carbohydrate, lipid, and protein metabolism via glucose uptake through GLUT4 translocation.",
            referenceUrl = "https://www.ncbi.nlm.nih.gov/nuccore/NM_000207.3"
        ),
        BioDatabaseEntry(
            id = "db_uniprot_insulin",
            database = BioDatabaseType.UNIPROT,
            accession = "P01308",
            title = "Insulin [Homo sapiens]",
            organism = "Homo sapiens (Human)",
            summary = "110 aa precursor cleaved into Insulin B chain (FVNQHLCGSHLVEALYLVCGERGFFYTPKT) and A chain (GIVEQCCTSICSLYQLENYCN) linked by 2 interchain and 1 intrachain disulfide bonds (Cys7-Cys7, Cys19-Cys20, Cys6-Cys11).",
            sequenceOrSmiles = "MALWMRLLPLLALLALWGPDPAAAFVNQHLCGSHLVEALYLVCGERGFFYTPKTRREAEDLQVGQVELGGGPGAGSLQPLALEGSLQKRGIVEQCCTSICSLYQLENYCN",
            biologicalFunction = "Binds to INSR tyrosine kinase receptor; triggers IRS1/2 phosphorylation, PI3K/Akt pathway, and GSK3 inhibition to drive glycogen synthesis.",
            referenceUrl = "https://www.uniprot.org/uniprotkb/P01308"
        ),
        BioDatabaseEntry(
            id = "db_uniprot_p53",
            database = BioDatabaseType.UNIPROT,
            accession = "P04637",
            title = "Cellular tumor antigen p53 (Guardian of the Genome)",
            organism = "Homo sapiens (Human)",
            summary = "393 aa nuclear transcription factor that coordinates DNA repair, cell cycle arrest at G1/S, senescence, and apoptosis in response to genotoxic stress.",
            sequenceOrSmiles = "MEEPQSDPSVEPPLSQETFSDLWKLLPENNVLSPLPSQAMDDLMLSPDDIEQWFTEDPGPDEAPRMPEAAPPVAPAPAAPTPAAPAPAPSWPLSSSVPSQKTYQGSYGFRLGFLHSGTAKSVTCTYSPALNKMFCQLAKTCPVQLWVDSTPPPGTRVRAMAIYKQSQHMTEVVRRCPHHERCSDSDGLAPPQHLIRVEGNLRVEYLDDRNTFRHSVVVPYEPPEVGSDCTTIHYNYMCNSSCMGGMNRRPILTIITLEDSSGNLLGRNSFEVRVCACPGRDRRTEEENLRKKGEPHHELPPGSTKRALPNNTSSSPQPKKKPLDGEYFTLQIRGRERFEMFRELNEALELKDAQAGKEPGGSRAHSSHLKSKKGQSTSRHKKLMFKTEGPDSD",
            biologicalFunction = "Master tumor suppressor; mutated in over 50% of human cancers; activates p21/CDKN1A, BAX, PUMA, and MDM2 feedback loop.",
            referenceUrl = "https://www.uniprot.org/uniprotkb/P04637"
        ),
        BioDatabaseEntry(
            id = "db_pdb_crambin",
            database = BioDatabaseType.PDB,
            accession = "1CRN",
            title = "Water Structure of a Hydrophobic Protein at Atomic Resolution: Crambin at 0.54 Å",
            organism = "Crambe abyssinica (Abyssinian kale)",
            summary = "46 residue seed plant hydrophobic thionin folded into two amphipathic alpha-helices, a short two-stranded beta-sheet, and three disulfide crosslinks (Cys3-Cys40, Cys4-Cys32, Cys16-Cys26).",
            sequenceOrSmiles = "TTCCPSIVARSNFNVCRLPGTPEAICATYTGCIIIPGATCPGDYAN",
            biologicalFunction = "Plant defense toxin with antifungal and cell-membrane permeabilizing properties; reference benchmark for crystallography and molecular dynamics force fields.",
            referenceUrl = "https://www.rcsb.org/structure/1CRN"
        ),
        BioDatabaseEntry(
            id = "db_pdb_cas9",
            database = BioDatabaseType.PDB,
            accession = "7K43",
            title = "Structure of Streptococcus pyogenes Cas9 in complex with single-guide RNA and target DNA",
            organism = "Streptococcus pyogenes",
            summary = "Cryo-EM 2.80 Å complex of SpCas9 (1,368 aa) with 102 nt sgRNA targeting double-stranded protospacer DNA flanked by 5'-NGG-3' PAM motif.",
            sequenceOrSmiles = "MDKKYSIGLDIGTNSVGWAVITDEYKVPSKKFKVLGNTDRHSIKKNLIGALLFDSGETAEATRLKRTARRRYTRRKNRICYLQEIFSNEMAKVDDSFFHRLEESFLVEEDKKHERHPIFGNIVDEVAYHEKYPTIYHLRKKLVDSTDKADLRLIYLALAHMIKFRGHFLIEGDLNPDNSDVDKLFIQLVQTYNQLFEENPINASGVDAKAILSARLSKSRRLENLIAQLPGEKKNGLFGNLIALSLGLTPNFKSNFDLAEDAKLQLSKDTYDDDLDNLLAQIGDQYADLFLAAKNLSDAILLSDILRVNTEITKAPLSASMIKRYDEHHQDLTLLKALVRQQLPEKYKEIFFDQSKNGYAGYIDGGASQEEFYKFIKPILEKMDGTEELLVKLNREDLLRKQRTFDNGSIPHQIHLGELHAILRRQEDFYPFLKDNREKIEKILTFRIPYYVGPLARGNSRFAWMTRKSEETITPWNFEEVVDKGASAQSFIERMTNFDKNLPNEKVLPKHSLLYEYFTVYNELTKVKYVTEGMRKPAFLSGEQKKAIVDLLFKTNRKVTVKQLKEDYFKKIECFDSVEISGVEDRFNASLGTYHDLLKIIKDKDFLDNEENEDILEDIVLTLTLFEDREMIEERLKTYAHLFDDKVMKQLKRRRYTGWGRLSRKLINGIRDKQSGKTILDFLKSDGFANRNFMQLIHDDSLTFKEDIQKAQVSGQGDSLHEHIANLAGSPAIKKGILQTVKVVDELVKVMGRHKPENIVIEMARENQTTQKGQKNSRERMKRIEEGIKELGSQILKEHPVENTQLQNEKLYLYYLQNGRDMYVDQELDINRLSDYDVDHIVPQSFLKDDSIDNKVLTRSDKNRGKSDNVPSEEVVKKMKNYWRQLLNAKLITQRKFDNLTKAERGGLSELDKAGFIKRQLVETRQITKHVAQILDSRMNTKYDENDKLIREVKVITLKSKLVSDFRKDFQFYKVREINNYHHAHDAYLNAVVGTALIKKYPKLESEFVYGDYKVYDVRKMIAKSEQEIGKATAKYFFYSNIMNFFKTEITLANGEIRKRPLIETNGETGEIVWDKGRDFATVRKVLSMPQVNIVKKTEVQTGGFSKESILPKRNSDKLIARKKDWDPKKYGGFDSPTVAYSVLVVAKVEKGKSKKLKSVKELLGITIMERSSFEKNPIDFLEAKGYKEVKKDLIIKLPKYSLFELENGRKRMLASAGELQKGNELALPSKYVNFLYLASHYEKLKGSPEDNEQKQLFVEQHKHYLDEIIEQISEFSKRVILADANLDKVLSAYNKHRDKPIREQAENIIHLFTLTNLGAPAAFKYFDTTIDRKRYTSTKEVLDATLIHQSITGLYETRIDLSQLGGD",
            biologicalFunction = "CRISPR-associated RNA-guided endonuclease causing site-specific double-strand breaks for precision genome engineering and epigenetic editing.",
            referenceUrl = "https://www.rcsb.org/structure/7K43"
        ),
        BioDatabaseEntry(
            id = "db_chembl_remdesivir",
            database = BioDatabaseType.CHEMBL,
            accession = "CHEMBL4299092",
            title = "Remdesivir (Veklury / GS-5734)",
            organism = "Synthetic (Gilead Sciences)",
            summary = "Nucleotide analog prodrug metabolized intracellularly to remdesivir triphosphate (GS-443902), mimicking ATP to inhibit RdRp via delayed chain termination at position i+3.",
            sequenceOrSmiles = "CCC(CC)COC(=O)C(C)NP(=O)(OCC1C(C(C(O1)C#N)O)O)OC2=CC=CC=C2",
            biologicalFunction = "Potent antiviral agent (IC50 = 0.77 uM against SARS-CoV-2); binds catalytic core of viral RNA polymerase.",
            referenceUrl = "https://www.ebi.ac.uk/chembl/compound_report_card/CHEMBL4299092/"
        ),
        BioDatabaseEntry(
            id = "db_chembl_atorvastatin",
            database = BioDatabaseType.CHEMBL,
            accession = "CHEMBL1487",
            title = "Atorvastatin Calcium (Lipitor)",
            organism = "Synthetic (Pfizer / Viatris)",
            summary = "Competitive inhibitor of 3-hydroxy-3-methylglutaryl-coenzyme A (HMG-CoA) reductase, the rate-limiting enzyme in cholesterol de novo biosynthesis.",
            sequenceOrSmiles = "CC(C)C1=C(C(=C(N1CCC(CC(CC(=O)O)O)O)C2=CC=C(C=C2)F)C3=CC=CC=C3)C(=O)NC4=CC=CC=C4",
            biologicalFunction = "Lowers plasma cholesterol and lipoprotein levels, upregulates LDL receptor density on hepatocytes (Kd = 8 nM).",
            referenceUrl = "https://www.ebi.ac.uk/chembl/compound_report_card/CHEMBL1487/"
        ),
        BioDatabaseEntry(
            id = "db_kegg_glycolysis",
            database = BioDatabaseType.KEGG,
            accession = "map00010",
            title = "Glycolysis / Gluconeogenesis Metabolic Pathway",
            organism = "Ubiquitous (Prokaryotes & Eukaryotes)",
            summary = "10-step enzymatic cascade converting 1 mole D-glucose into 2 moles pyruvate, generating net 2 ATP and 2 NADH (Hexokinase, PFK-1, GAPDH, Pyruvate Kinase).",
            sequenceOrSmiles = "Glucose + 2 NAD+ + 2 ADP + 2 Pi -> 2 Pyruvate + 2 NADH + 2 H+ + 2 ATP + 2 H2O (Delta G = -85 kJ/mol)",
            biologicalFunction = "Primary anaerobic/aerobic energy catabolic pathway producing carbon backbones for citric acid cycle and fatty acid synthesis.",
            referenceUrl = "https://www.kegg.jp/pathway/map00010"
        ),
        BioDatabaseEntry(
            id = "db_kegg_insulin_signal",
            database = BioDatabaseType.KEGG,
            accession = "hsa04910",
            title = "Insulin Signaling Pathway Cascade",
            organism = "Homo sapiens (Human)",
            summary = "Canonical kinase transduction: INSR -> IRS1/2 -> PI3K (p85/p110) -> PIP3 -> PDK1 -> Akt/PKB -> AS160 -> GLUT4 exocytosis + FOXO1 phosphorylation.",
            sequenceOrSmiles = "INSR -> IRS1 [Tyr612] -> PIK3CA -> AKT1 [Ser473/Thr308] -> GSK3B [Ser9-P inactive] -> GYS1 activation",
            biologicalFunction = "Stimulates glucose entry into skeletal muscle and adipose tissue, promotes lipid storage, and halts hepatic gluconeogenesis.",
            referenceUrl = "https://www.kegg.jp/pathway/hsa04910"
        )
    )

    fun queryEntries(search: String, selectedDb: BioDatabaseType?): List<BioDatabaseEntry> {
        val q = search.trim().lowercase()
        return ENTRIES.filter { entry ->
            val matchesDb = selectedDb == null || entry.database == selectedDb
            val matchesSearch = q.isEmpty() ||
                    entry.accession.lowercase().contains(q) ||
                    entry.title.lowercase().contains(q) ||
                    entry.organism.lowercase().contains(q) ||
                    entry.biologicalFunction.lowercase().contains(q)
            matchesDb && matchesSearch
        }
    }
}
