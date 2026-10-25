package com.example.data.firestore

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.DashboardWidget
import com.example.data.WidgetCategory
import com.example.data.WidgetType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WidgetConfigRepositoryRuleTest : FirestoreEmulatorTestBase() {

  private val aliceEmail = "alice@example.com"
  private val bobEmail = "bob@example.com"

  @Test
  fun saveAndObserveWidgetConfigs_authenticatedUser_succeeds() = runBlocking {
    val aliceUid = try {
      signInTestUser(aliceEmail)
    } catch (e: Exception) {
      org.junit.Assume.assumeNoException("Firebase Auth emulator not active in local JVM test environment", e)
      return@runBlocking
    }
    val repo = FirestoreWidgetConfigRepository(firestore, auth)

    val widget = DashboardWidget(
      id = "widget_exec_metrics",
      title = "Executive Metrics",
      subtitle = "Weekly Time-Saved",
      category = WidgetCategory.SUMMARY_CARDS,
      widgetType = WidgetType.EXECUTIVE_METRICS,
      isEnabled = true,
      order = 0
    )

    repo.saveWidgetConfig(widget)

    val configs = repo.observeWidgetConfigs().first()
    assertTrue(configs.isNotEmpty())
    val saved = configs.firstOrNull { it.widgetId == "widget_exec_metrics" }
    assertNotNull(saved)
    assertEquals("Executive Metrics", saved?.title)
    assertEquals(aliceUid, saved?.userId)
  }

  @Test
  fun saveWidgetConfigs_unauthenticatedUser_fails() = runBlocking {
    auth.signOut()
    val repo = FirestoreWidgetConfigRepository(firestore, auth)

    val widget = DashboardWidget(
      id = "widget_exec_metrics",
      title = "Executive Metrics",
      subtitle = "Weekly Time-Saved",
      category = WidgetCategory.SUMMARY_CARDS,
      widgetType = WidgetType.EXECUTIVE_METRICS,
      isEnabled = true,
      order = 0
    )

    var thrown = false
    try {
      repo.saveWidgetConfig(widget)
    } catch (e: IllegalStateException) {
      thrown = true
    }
    assertTrue("Expected IllegalStateException when unauthenticated", thrown)
  }
}
