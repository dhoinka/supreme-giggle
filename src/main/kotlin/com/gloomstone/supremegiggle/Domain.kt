package com.gloomstone.supremegiggle

import java.time.Instant

data class Feature(
    val id: String,
    val name: String,
    val displayName: String,
    val description: String,
    val enabled: Boolean = false,

    val payload: Payload? = null,
    val createdAt: Instant = Instant.now(),
)

data class FeatureState(
    val enabled: Boolean,
    val payload: PayloadDto? = null,
)

data class Payload(
    val body: String,
    val format: PayloadFormat
)

data class PayloadDto(
    val body: String,
    val format: String
)

enum class PayloadFormat(
    val value: String,
) {
    PLAIN_TEXT("text"),
    JSON("json"),
    YAML("yaml"),
    XML("xml"),
    MARKDOWN("markdown");

    companion object {
        fun fromValue(value: String): PayloadFormat {
            val normalizedValue = value.trim()

            return entries.firstOrNull {
                it.value.equals(normalizedValue, ignoreCase = true) ||
                        it.name.equals(normalizedValue, ignoreCase = true)
            }
                ?: error("Unsupported payload format '$value'")
        }
    }
}


data class CreateFeatureDto(
    val name: String,
    val displayName: String,
    val description: String,
    val payload: PayloadDto? = null,
)