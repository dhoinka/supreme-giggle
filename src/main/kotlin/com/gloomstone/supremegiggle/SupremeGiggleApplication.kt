package com.gloomstone.supremegiggle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SupremeGiggleApplication

fun main(args: Array<String>) {
    runApplication<SupremeGiggleApplication>(*args)
}
