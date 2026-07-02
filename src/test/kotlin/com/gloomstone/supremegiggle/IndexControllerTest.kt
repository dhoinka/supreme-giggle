package com.gloomstone.supremegiggle

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@WebMvcTest(IndexController::class)
class IndexControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `index returns service status`() {
        mvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(
                jsonPath("$.status").value("UP")
            )
    }
}

