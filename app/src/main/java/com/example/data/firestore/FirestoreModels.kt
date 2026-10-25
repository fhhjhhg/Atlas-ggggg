package com.example.data.firestore

import com.example.data.DashboardWidget
import com.example.data.WidgetCategory
import com.example.data.WidgetType
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FirestoreWidgetConfig(
    @DocumentId val id: String = "",
    val widgetId: String = "",
    val title: String = "",
    val subtitle: String = "",
    val category: String = "",
    val widgetType: String = "",
    val isEnabled: Boolean = true,
    val order: Int = 0,
    val userId: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    fun toDomainWidget(): DashboardWidget {
        val cat = try {
            WidgetCategory.valueOf(category)
        } catch (e: Exception) {
            WidgetCategory.SUMMARY_CARDS
        }

        val type = try {
            WidgetType.valueOf(widgetType)
        } catch (e: Exception) {
            WidgetType.EXECUTIVE_METRICS
        }

        return DashboardWidget(
            id = widgetId.ifBlank { id },
            title = title,
            subtitle = subtitle,
            category = cat,
            widgetType = type,
            isEnabled = isEnabled,
            order = order
        )
    }

    companion object {
        fun fromDomainWidget(widget: DashboardWidget, userId: String): FirestoreWidgetConfig {
            return FirestoreWidgetConfig(
                id = widget.id,
                widgetId = widget.id,
                title = widget.title,
                subtitle = widget.subtitle,
                category = widget.category.name,
                widgetType = widget.widgetType.name,
                isEnabled = widget.isEnabled,
                order = widget.order,
                userId = userId
            )
        }
    }
}
