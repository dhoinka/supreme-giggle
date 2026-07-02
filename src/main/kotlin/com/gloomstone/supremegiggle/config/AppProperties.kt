package com.gloomstone.supremegiggle.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val baseUrl: String,
    val featureCollection: String
)

