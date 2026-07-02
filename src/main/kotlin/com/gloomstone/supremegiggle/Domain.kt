package com.gloomstone.supremegiggle

import java.time.Instant

data class Feature(
    val id: String,
    val name: String,
    val displayName: String,
    val description: String,
    val enabled: Boolean = false,
    val state: Boolean = false,

    val createdAt: Instant = Instant.now(),
)

data class CreateFeatureDto(
    val name: String,
    val displayName: String,
    val description: String,
)