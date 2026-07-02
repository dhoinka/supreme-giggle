package com.gloomstone.supremegiggle

import com.gloomstone.supremegiggle.repository.FeatureRepository
import org.springframework.stereotype.Service

@Service
class FeatureService(private val featureRepository: FeatureRepository) {

    fun getAll(): List<Feature> {
        return featureRepository.getAll()
    }

    fun get(id: String): Feature? {
        return featureRepository.getById(id)
    }

    fun create(feature: CreateFeatureDto): Feature {
        return featureRepository.save(feature)
    }

    fun update(feature: Feature): Feature {
        return featureRepository.update(feature)
    }

    fun delete(id: String) {
        featureRepository.delete(id)
        return
    }
}