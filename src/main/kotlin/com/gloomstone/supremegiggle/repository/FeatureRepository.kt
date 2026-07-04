package com.gloomstone.supremegiggle.repository

import com.gloomstone.supremegiggle.CreateFeatureDto
import com.gloomstone.supremegiggle.Feature
import com.gloomstone.supremegiggle.Payload
import com.gloomstone.supremegiggle.PayloadFormat
import com.gloomstone.supremegiggle.config.AppProperties
import com.google.cloud.Timestamp
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.temporal.ChronoUnit

@Repository
class FeatureRepository(
    private val firestore: Firestore,
    private val appProperties: AppProperties
) {

    fun save(feature: CreateFeatureDto): Feature {
        val documentReference = firestore.collection(appProperties.featureCollection)
            .document()

        return writeFeature(
            documentReference = documentReference,
            name = feature.name,
            displayName = feature.displayName,
            description = feature.description,
            enabled = false,
            payload = feature.payload?.let { Payload(it.body, PayloadFormat.fromValue(it.format)) },
            createdAt = Instant.now(),
        )
    }

    fun getById(id: String): Feature? {
        val snapshot = firestore.collection(appProperties.featureCollection)
            .document(id)
            .get()
            .get()

        return snapshot.toFeature()
    }

    fun getAll(): List<Feature> {
        return firestore.collection(appProperties.featureCollection)
            .get()
            .get()
            .documents
            .mapNotNull { it.toFeature() }
    }

    fun update(feature: Feature): Feature {
        val documentReference = firestore.collection(appProperties.featureCollection)
            .document(feature.id)

        return writeFeature(
            documentReference = documentReference,
            name = feature.name,
            displayName = feature.displayName,
            description = feature.description,
            enabled = feature.enabled,
            payload = feature.payload,
            createdAt = feature.createdAt,
        )
    }

    fun delete(id: String) {
        firestore.collection(appProperties.featureCollection)
            .document(id)
            .delete()
            .get()
    }

    private fun DocumentSnapshot.toFeature(): Feature? {
        if (!exists()) {
            return null
        }

        val name = getString("name") ?: return null
        val displayName = getString("displayName") ?: return null
        val description = getString("description") ?: return null
        val enabled = getBoolean("enabled") ?: return null
        val payload = get("payload")?.toPayload()
        val createdAt = getTimestamp("createdAt")?.toInstant() ?: return null

        return Feature(
            id = id,
            name = name,
            displayName = displayName,
            description = description,
            enabled = enabled,
            payload = payload,
            createdAt = createdAt
        )
    }


    private fun writeFeature(
        documentReference: DocumentReference,
        name: String,
        displayName: String,
        description: String,
        enabled: Boolean,
        payload: Payload?,
        createdAt: Instant,
    ): Feature {
        documentReference.set(
            mapOf(
                "name" to name,
                "displayName" to displayName,
                "description" to description,
                "enabled" to enabled,
                "payload" to payload?.toDocument(),
                "createdAt" to createdAt.toTimestamp(),
                "expireAt" to Instant.now().plus(7, ChronoUnit.DAYS).toTimestamp(),
            )
        ).get()

        return documentReference.get()
            .get()
            .toFeature()
            ?: error("Saved feature document '${documentReference.id}' could not be mapped back to Feature")
    }
}

private fun Instant.toTimestamp(): Timestamp {
    return Timestamp.ofTimeSecondsAndNanos(epochSecond, nano)
}

private fun Timestamp.toInstant(): Instant {
    return Instant.ofEpochSecond(seconds, nanos.toLong())
}

private fun Any.toPayload(): Payload? {
    val payloadDocument = this as? Map<*, *> ?: return null
    val body = payloadDocument["body"] as? String ?: return null
    val formatName = payloadDocument["format"] as? String ?: return null
    val format = runCatching { PayloadFormat.fromValue(formatName) }.getOrNull() ?: return null

    return Payload(
        body = body,
        format = format
    )
}

private fun Payload.toDocument(): Map<String, String> {
    return mapOf(
        "body" to body,
        "format" to format.value,
    )
}
