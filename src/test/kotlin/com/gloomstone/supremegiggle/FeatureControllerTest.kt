package com.gloomstone.supremegiggle

import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper
import java.time.Instant

@WebMvcTest(FeatureController::class)
class FeatureControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockitoBean
    lateinit var featureService: FeatureService

    @Test
    fun `get returns all features`() {
        whenever(featureService.getAll()).thenReturn(
            listOf(
                Feature(
                    id = "feature-1",
                    name = "feature1",
                    displayName = "Feature 1",
                    description = "Description 1",
                    createdAt = Instant.parse("2026-07-02T18:00:00Z")
                )
            )
        )

        mvc.perform(get("/features"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value("feature-1"))
            .andExpect(jsonPath("$[0].name").value("feature1"))
            .andExpect(jsonPath("$[0].displayName").value("Feature 1"))
            .andExpect(jsonPath("$[0].description").value("Description 1"))
    }

    @Test
    fun `get by id returns feature`() {
        whenever(featureService.get("feature-1")).thenReturn(
            Feature(
                id = "feature-1",
                name = "feature1",
                displayName = "Feature 1",
                description = "Description 1",
                createdAt = Instant.parse("2026-07-02T18:00:00Z")
            )
        )

        mvc.perform(get("/features/feature-1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("feature-1"))
            .andExpect(jsonPath("$.name").value("feature1"))
    }

    @Test
    fun `get by id returns not found when feature is missing`() {
        whenever(featureService.get("missing")).thenReturn(null)

        mvc.perform(get("/features/missing"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("Not Found"))
            .andExpect(jsonPath("$.message").value("Feature with id missing not found"))
            .andExpect(jsonPath("$.path").value("/features/missing"))
    }

    @Test
    fun `get state returns enabled and payload`() {
        whenever(featureService.getState("feature-1")).thenReturn(
            FeatureState(
                enabled = true,
                payload = PayloadDto(
                    body = """{"active":true}""",
                    format = "json",
                ),
            )
        )

        mvc.perform(get("/features/feature-1/state"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.enabled").value(true))
            .andExpect(jsonPath("$.payload.body").value("""{"active":true}"""))
            .andExpect(jsonPath("$.payload.format").value("json"))
    }

    @Test
    fun `get state returns not found when feature is missing`() {
        whenever(featureService.getState("missing")).thenReturn(null)

        mvc.perform(get("/features/missing/state"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.error").value("Not Found"))
            .andExpect(jsonPath("$.message").value("Feature with id missing not found"))
            .andExpect(jsonPath("$.path").value("/features/missing/state"))
    }

    @Test
    fun `post creates feature`() {
        val createFeatureDto = CreateFeatureDto(
            name = "feature1",
            displayName = "Feature 1",
            description = "Description 1"
        )
        whenever(featureService.create(createFeatureDto)).thenReturn(
            Feature(
                id = "generated-id",
                name = "feature1",
                displayName = "Feature 1",
                description = "Description 1",
                createdAt = Instant.parse("2026-07-02T18:00:00Z")
            )
        )

        mvc.perform(
            post("/features")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createFeatureDto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("generated-id"))
            .andExpect(jsonPath("$.name").value("feature1"))
            .andExpect(jsonPath("$.displayName").value("Feature 1"))
            .andExpect(jsonPath("$.description").value("Description 1"))
    }
}
