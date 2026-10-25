package com.example

import androidx.test.core.app.ApplicationProvider
import com.example.data.DashboardRepository
import com.example.data.TutorialDifficulty
import com.example.data.WidgetType
import com.example.ui.NexusDashboardViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class EmergentResearchKnowledgeTest {

    @Test
    fun `verify repository provides emergent research and papers data`() {
        val repo = DashboardRepository()
        val papers = repo.getEmergentPapers()

        assertTrue("Emergent papers should not be empty", papers.isNotEmpty())
        assertTrue("All papers should have arxivId", papers.all { it.arxivId.startsWith("arXiv:") })
        assertTrue("All papers should have non-empty topics", papers.all { it.topics.isNotEmpty() })
        assertTrue("All papers should have works cited count > 0", papers.all { it.worksCitedCount > 0 })
        assertTrue("All papers should have citations list", papers.all { it.worksCited.isNotEmpty() })

        val leaders = repo.getThoughtLeaders()
        assertTrue("Thought leaders should not be empty", leaders.isNotEmpty())
        assertTrue("All leaders should have h-index > 0", leaders.all { it.hIndex > 0 })
        assertTrue("All leaders should have sponsorships and affiliate links",
            leaders.all { it.sponsorships.isNotEmpty() && it.affiliateLinks.isNotEmpty() })
    }

    @Test
    fun `verify repository provides pocketflow tutorials and zero-to-hero modules`() {
        val repo = DashboardRepository()
        val tutorials = repo.getTutorialModules()

        assertTrue("Tutorials should not be empty", tutorials.isNotEmpty())
        assertTrue("Should contain Zero-to-Hero module", tutorials.any { it.level == TutorialDifficulty.ZERO_TO_HERO })
        assertTrue("Should contain Intermediate module", tutorials.any { it.level == TutorialDifficulty.INTERMEDIATE })
        assertTrue("Should contain Advanced module", tutorials.any { it.level == TutorialDifficulty.ADVANCED })

        tutorials.forEach { mod ->
            assertTrue("Module must have duration > 0", mod.durationMinutes > 0)
            assertTrue("Module must have non-empty code snippet", mod.codeSnippet.isNotBlank())
            assertTrue("Module must have definitions", mod.definitions.isNotEmpty())
            assertTrue("Module must have step tasks", mod.tasks.isNotEmpty())
            assertTrue("Module must have a notebook filename (.ipynb)", mod.notebookFilename.endsWith(".ipynb"))
        }
    }

    @Test
    fun `verify repository provides frameworks hardware and drones`() {
        val repo = DashboardRepository()

        val frameworks = repo.getFrameworkToolkits()
        assertTrue("Frameworks list should not be empty", frameworks.isNotEmpty())
        assertTrue("Should include PocketFlow", frameworks.any { it.name.contains("PocketFlow") })
        assertTrue("Should include Evo 2", frameworks.any { it.name.contains("Evo 2") })
        assertTrue("Frameworks should have artifact APK names", frameworks.all { it.artifactApkName.endsWith(".apk") })

        val reviews = repo.getHardwareReviews()
        assertTrue("Hardware reviews should not be empty", reviews.isNotEmpty())
        assertTrue("Reviews should have specs", reviews.all { it.hardwareSpecs.isNotEmpty() })
        assertTrue("Reviews should have affiliate URLs", reviews.all { it.affiliateUrl.isNotBlank() })

        val drones = repo.getDroneSpecs()
        assertTrue("Drone specs should not be empty", drones.isNotEmpty())
        assertTrue("Drones should have flight time > 0", drones.all { it.flightTimeMinutes > 0 })
        assertTrue("Drones should have PX4 firmware", drones.all { it.px4FirmwareVersion.startsWith("v") })
        assertTrue("Drones should have telemetry BPM", drones.all { it.telemetryBpm in 60..240 })
    }

    @Test
    fun `verify heartbeat soul telemetry and bio-intelligence data`() {
        val repo = DashboardRepository()

        val heartbeat = repo.getInitialHeartbeatSoul()
        assertTrue("Heartbeat BPM should be positive", heartbeat.currentBpm in 40..240)
        assertTrue("Live deltas should not be empty", heartbeat.liveDeltas.isNotEmpty())

        val bioprompts = repo.getBioprompts()
        assertTrue("Bioprompts should not be empty", bioprompts.isNotEmpty())
        assertTrue("Bioprompts should have expected outcome", bioprompts.all { it.expectedOutcome.isNotBlank() })

        val bioreactors = repo.getBioreactors()
        assertTrue("Bioreactors should not be empty", bioreactors.isNotEmpty())
        assertTrue("Bioreactors should have volume > 0", bioreactors.all { it.volumeLiters > 0.0 })

        val bioregions = repo.getBioregions()
        assertTrue("Bioregions should not be empty", bioregions.isNotEmpty())

        val biospecs = repo.getBiospecs()
        assertTrue("Biospecs should not be empty", biospecs.isNotEmpty())

        val biocontexts = repo.getBiocontexts()
        assertTrue("Biocontexts should not be empty", biocontexts.isNotEmpty())

        val evoMath = repo.getEvoMathFormulas()
        assertTrue("EvoMath formulas should not be empty", evoMath.isNotEmpty())
        assertTrue("EvoMath should contain Michaelis-Menten", evoMath.any { it.name.contains("Michaelis-Menten") })

        val evoChem = repo.getEvoChemistryPaths()
        assertTrue("EvoChemistry paths should not be empty", evoChem.isNotEmpty())
        assertTrue("EvoChemistry paths should have deltaG", evoChem.all { it.deltaGKcalPerMol != 0.0 })
    }

    @Test
    fun `verify ViewModel Emergent Research dialog actions and task completion`() {
        val viewModel = NexusDashboardViewModel()

        // Verify initial state
        val state = viewModel.uiState.value
        assertEquals(4, state.emergentPapers.size)
        assertEquals(3, state.tutorialModules.size)
        assertEquals(4, state.frameworkToolkits.size)
        assertEquals(3, state.hardwareReviews.size)
        assertEquals(2, state.droneSpecs.size)

        // Dialog toggle
        assertFalse(viewModel.uiState.value.isEmergentResearchDialogOpen)
        viewModel.openEmergentResearchDialog()
        assertTrue(viewModel.uiState.value.isEmergentResearchDialogOpen)
        viewModel.dismissEmergentResearchDialog()
        assertFalse(viewModel.uiState.value.isEmergentResearchDialogOpen)

        // Filter papers by search query
        viewModel.filterEmergentPapers("PocketFlow")
        assertEquals(1, viewModel.uiState.value.filteredEmergentPapers.size)
        assertTrue(viewModel.uiState.value.filteredEmergentPapers.first().title.contains("PocketFlow"))

        // Reset paper filter
        viewModel.filterEmergentPapers("")
        assertEquals(4, viewModel.uiState.value.filteredEmergentPapers.size)

        // Filter tutorials by level
        viewModel.filterTutorials(TutorialDifficulty.ZERO_TO_HERO, "")
        assertEquals(1, viewModel.uiState.value.filteredTutorialModules.size)
        assertEquals(TutorialDifficulty.ZERO_TO_HERO, viewModel.uiState.value.filteredTutorialModules.first().level)

        // Reset tutorial filter
        viewModel.filterTutorials(null, "")
        assertEquals(3, viewModel.uiState.value.filteredTutorialModules.size)

        // Complete a tutorial task step
        val targetMod = viewModel.uiState.value.tutorialModules.first()
        assertFalse(targetMod.tasks.first().isCompleted)
        viewModel.completeTutorialTask(targetMod.id, 1)
        val updatedMod = viewModel.uiState.value.tutorialModules.first { it.id == targetMod.id }
        assertTrue(updatedMod.tasks.first().isCompleted)

        // Adjust heartbeat BPM
        viewModel.adjustHeartbeatBpm(144)
        assertEquals(144, viewModel.uiState.value.heartbeatSoul.currentBpm)

        viewModel.stopTimersForTesting()
    }
}
