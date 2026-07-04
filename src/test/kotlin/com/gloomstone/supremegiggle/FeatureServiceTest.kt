package com.gloomstone.supremegiggle

import com.gloomstone.supremegiggle.repository.FeatureRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FeatureServiceTest {

    private val featureRepository = mock<FeatureRepository>()
    private val featureService = FeatureService(featureRepository)

    @Test
    fun `getAll returns repository features`() {
        val expected = listOf(
            Feature(
                id = "feature-1",
                name = "feature1",
                displayName = "Feature 1",
                description = "Description 1",
                createdAt = Instant.parse("2026-07-02T18:00:00Z")
            )
        )
        whenever(featureRepository.getAll()).thenReturn(expected)

        val actual = featureService.getAll()

        assertEquals(expected, actual)
    }

    @Test
    fun `get returns repository feature`() {
        val expected = Feature(
            id = "feature-1",
            name = "feature1",
            displayName = "Feature 1",
            description = "Description 1",
            createdAt = Instant.parse("2026-07-02T18:00:00Z")
        )
        whenever(featureRepository.getById("feature-1")).thenReturn(expected)

        val actual = featureService.get("feature-1")

        assertEquals(expected, actual)
    }

    @Test
    fun `get returns null when repository misses feature`() {
        whenever(featureRepository.getById("missing")).thenReturn(null)

        val actual = featureService.get("missing")

        assertNull(actual)
    }

    @Test
    fun `getState returns enabled and payload`() {
        val expectedFeature = Feature(
            id = "feature-1",
            name = "feature1",
            displayName = "Feature 1",
            description = "Description 1",
            enabled = true,
            payload = Payload(
                body = """{"active":true}""",
                format = PayloadFormat.JSON,
            ),
            createdAt = Instant.parse("2026-07-02T18:00:00Z")
        )
        whenever(featureRepository.getById("feature-1")).thenReturn(expectedFeature)

        val actual = featureService.getState("feature-1")

        assertEquals(
            FeatureState(
                enabled = true,
                payload = PayloadDto(
                    body = """{"active":true}""",
                    format = "json",
                ),
            ),
            actual
        )
    }

    @Test
    fun `getState returns null when repository misses feature`() {
        whenever(featureRepository.getById("missing")).thenReturn(null)

        val actual = featureService.getState("missing")

        assertNull(actual)
    }

    @Test
    fun `create saves create dto`() {
        val createFeatureDto = CreateFeatureDto(
            name = "feature1",
            displayName = "Feature 1",
            description = "Description 1"
        )
        val savedFeature = Feature(
            id = "generated-id",
            name = "feature1",
            displayName = "Feature 1",
            description = "Description 1",
            createdAt = Instant.parse("2026-07-02T18:00:00Z")
        )
        whenever(featureRepository.save(createFeatureDto)).thenReturn(savedFeature)

        val actual = featureService.create(createFeatureDto)

        assertEquals(savedFeature, actual)
    }

    @Test
    fun `update saves feature`() {
        val feature = Feature(
            id = "feature-1",
            name = "feature1",
            displayName = "Feature 1",
            description = "Description 1",
            enabled = true,
            createdAt = Instant.parse("2026-07-02T18:00:00Z")
        )
        whenever(featureRepository.update(feature)).thenReturn(feature)

        val actual = featureService.update(feature)

        assertEquals(feature, actual)
    }

    @Test
    fun `delete delegates to repository`() {
        featureService.delete("feature-1")

        verify(featureRepository).delete("feature-1")
    }
}
