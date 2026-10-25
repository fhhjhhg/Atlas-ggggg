package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("NexusForce", appName)
  }

  @Test
  fun `verify dashboard repository default widgets`() {
    val repo = com.example.data.DashboardRepository()
    val widgets = repo.getDefaultWidgets()
    assertEquals(18, widgets.size)
    org.junit.Assert.assertTrue(widgets.any { it.widgetType == com.example.data.WidgetType.BIOSCIENCE_HUB })
    org.junit.Assert.assertTrue(widgets.any { it.widgetType == com.example.data.WidgetType.HISTORICAL_TREND_CHART })
    org.junit.Assert.assertTrue(widgets.any { it.widgetType == com.example.data.WidgetType.COLIVING_DIGITAL_TWIN })
    org.junit.Assert.assertTrue(widgets.any { it.widgetType == com.example.data.WidgetType.BOOK_MY_COLIVING })
    org.junit.Assert.assertTrue(widgets.any { it.widgetType == com.example.data.WidgetType.EMERGENT_RESEARCH_KNOWLEDGE })
    org.junit.Assert.assertTrue(widgets.any { it.widgetType == com.example.data.WidgetType.METRIC_FORECASTING })
  }
}
