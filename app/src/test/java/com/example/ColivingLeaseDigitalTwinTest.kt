package com.example

import com.example.data.ColivingContractConfig
import com.example.data.ColivingLeaseStatus
import com.example.data.ColivingLeaseType
import com.example.data.DashboardRepository
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
class ColivingLeaseDigitalTwinTest {

    @Test
    fun `repository generates 35-bed digital twin portfolio`() {
        val repo = DashboardRepository()
        val beds = repo.getInitialColivingBedTwins()
        assertEquals(35, beds.size)

        // Check ID formatting and pricing
        assertTrue(beds.any { it.bedId == "BED-01" })
        assertTrue(beds.any { it.bedId == "BED-35" })
        assertTrue(beds.all { it.monthlyRateUsd == 830.0 })
        assertTrue(beds.all { it.securityDepositUsd == 830.0 })
    }

    @Test
    fun `Everything Coliving agreement generator outputs comprehensive legal clauses`() {
        val repo = DashboardRepository()

        // 1. By-The-Bed License
        val bedLicenseConfig = ColivingContractConfig(
            residentName = "Marcus Aurelius",
            bedId = "BED-07",
            monthlyFee = 830.0,
            templateType = ColivingLeaseType.BED_LICENSE
        )
        val bedLicense = repo.generateEverythingColivingLeaseAgreement(bedLicenseConfig)
        assertTrue(bedLicense.contains("EVERYTHING COLIVING™ COMPLIANT LEGAL FRAMEWORK"))
        assertTrue(bedLicense.contains("INDIVIDUAL OCCUPANCY LICENSE"))
        assertTrue(bedLicense.contains("NOT jointly liable"))
        assertTrue(bedLicense.contains("$830.00 USD"))
        assertTrue(bedLicense.contains("BED-07"))

        // 2. Master Lease Sublease
        val masterConfig = ColivingContractConfig(
            residentName = "Founders House LLC",
            templateType = ColivingLeaseType.MASTER_SUBLEASE
        )
        val masterLease = repo.generateEverythingColivingLeaseAgreement(masterConfig)
        assertTrue(masterLease.contains("Master Lease Subletting Agreement"))

        // 3. Community Rules Addendum
        val rulesConfig = ColivingContractConfig(
            templateType = ColivingLeaseType.COMMUNITY_RULES,
            quietHoursStart = "23:00",
            quietHoursEnd = "07:00",
            guestConsecutiveNightsMax = 2
        )
        val rules = repo.generateEverythingColivingLeaseAgreement(rulesConfig)
        assertTrue(rules.contains("23:00 and 07:00"))
        assertTrue(rules.contains("maximum of 2 consecutive nights"))
        assertTrue(rules.contains("Shared Kitchen & Fridge Allocation"))
    }

    @Test
    fun `deployColivingTwin updates bed state and occupant name`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        val bedId = "BED-34"
        val bedBefore = viewModel.uiState.value.colivingBeds.find { it.bedId == bedId }
        assertNotNull(bedBefore)
        assertEquals(ColivingLeaseStatus.VACANT, bedBefore?.leaseStatus)

        // Deploy twin license
        viewModel.deployColivingTwin(bedId, "Grace Hopper", ColivingLeaseType.BED_LICENSE)

        val bedAfter = viewModel.uiState.value.colivingBeds.find { it.bedId == bedId }
        assertEquals("Grace Hopper", bedAfter?.occupantName)
        assertEquals(ColivingLeaseStatus.ACTIVE, bedAfter?.leaseStatus)
        assertEquals(100, bedAfter?.houseRulesScore)
        assertTrue(viewModel.uiState.value.activityLog.first().action.contains("Deployed Coliving License"))
    }

    @Test
    fun `coliving dialog open and dismiss operations update state`() {
        val viewModel = NexusDashboardViewModel()
        viewModel.stopTimersForTesting()

        assertFalse(viewModel.uiState.value.isColivingLeaseDialogOpen)

        val targetBed = viewModel.uiState.value.colivingBeds.first()
        viewModel.openColivingLeaseDialog(targetBed)
        assertTrue(viewModel.uiState.value.isColivingLeaseDialogOpen)
        assertEquals(targetBed.bedId, viewModel.uiState.value.colivingContractConfig.bedId)

        viewModel.dismissColivingLeaseDialog()
        assertFalse(viewModel.uiState.value.isColivingLeaseDialogOpen)
    }

    @Test
    fun `coliving widget registered in default widgets list`() {
        val repo = DashboardRepository()
        val widgets = repo.getDefaultWidgets()
        val colivingWidget = widgets.find { it.widgetType == WidgetType.COLIVING_DIGITAL_TWIN }
        assertNotNull(colivingWidget)
        assertEquals("widget_coliving_digital_twin", colivingWidget?.id)
        assertTrue(colivingWidget?.isEnabled == true)
    }
}
