package com.gloomstone.supremegiggle

import com.gloomstone.supremegiggle.exception.NotFoundException
import org.springframework.web.bind.annotation.*

@RestController
class FeatureController(private val featureService: FeatureService) {

    @GetMapping("/features")
    fun get(): List<Feature> {
        return featureService.getAll()
    }

    @GetMapping("/features/{id}")
    fun getById(@PathVariable id: String): Feature? {
        return featureService.get(id) ?: throw NotFoundException("Feature with id $id not found")
    }

    @PostMapping("/features")
    fun post(@RequestBody feature: CreateFeatureDto): Feature {
        return featureService.create(feature)
    }
}