package com.example.data.firestore

import android.content.Context
import com.example.R
import com.example.data.DashboardWidget
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreWidgetConfigRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    // CRITICAL: Always initialize with R.string.firestore_database_id
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing Firestore.")
    }

    /**
     * Observes the user's dashboard widget configurations in real time.
     */
    fun observeWidgetConfigs(): Flow<List<FirestoreWidgetConfig>> = flow {
        val uid = requireUserId()
        val path = "users/$uid/widgetConfigs"
        val collectionRef = db.collection("users").document(uid).collection("widgetConfigs")

        collectionRef.snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestoreWidgetConfig::class.java).sortedBy { it.order }
            }
            .catch { error ->
                if (error is Exception) {
                    handleFirestoreError(error, OperationType.LIST, path)
                }
                throw error
            }
            .collect { emit(it) }
    }

    /**
     * Saves or updates a single widget configuration.
     */
    suspend fun saveWidgetConfig(widget: DashboardWidget) {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid).collection("widgetConfigs").document(widget.id)

        try {
            val payload = mapOf(
                "widgetId" to widget.id,
                "title" to widget.title,
                "subtitle" to widget.subtitle,
                "category" to widget.category.name,
                "widgetType" to widget.widgetType.name,
                "isEnabled" to widget.isEnabled,
                "order" to widget.order,
                "userId" to uid,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload, com.google.firebase.firestore.SetOptions.merge()).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            throw e
        }
    }

    /**
     * Saves all widget configurations using a single atomic batch write.
     */
    suspend fun saveAllWidgetConfigs(widgets: List<DashboardWidget>) {
        val uid = requireUserId()
        val batch = db.batch()
        val collectionRef = db.collection("users").document(uid).collection("widgetConfigs")

        widgets.forEachIndexed { index, widget ->
            val docRef = collectionRef.document(widget.id)
            val payload = mapOf(
                "widgetId" to widget.id,
                "title" to widget.title,
                "subtitle" to widget.subtitle,
                "category" to widget.category.name,
                "widgetType" to widget.widgetType.name,
                "isEnabled" to widget.isEnabled,
                "order" to index,
                "userId" to uid,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            batch.set(docRef, payload, com.google.firebase.firestore.SetOptions.merge())
        }

        try {
            batch.commit().await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, "users/$uid/widgetConfigs/batch")
            throw e
        }
    }

    /**
     * Initializes default widget configurations in Firestore for a new user.
     */
    suspend fun seedDefaultConfigsIfEmpty(defaultWidgets: List<DashboardWidget>) {
        val uid = requireUserId()
        val collectionRef = db.collection("users").document(uid).collection("widgetConfigs")
        try {
            val existing = collectionRef.limit(1).get().await()
            if (existing.isEmpty) {
                saveAllWidgetConfigs(defaultWidgets)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.GET, collectionRef.path)
            throw e
        }
    }
}
