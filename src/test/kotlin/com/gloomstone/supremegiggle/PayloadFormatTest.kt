package com.gloomstone.supremegiggle

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PayloadFormatTest {

    @Test
    fun `fromValue accepts lowercase wire value`() {
        assertEquals(PayloadFormat.PLAIN_TEXT, PayloadFormat.fromValue("text"))
    }

    @Test
    fun `fromValue accepts legacy enum name`() {
        assertEquals(PayloadFormat.PLAIN_TEXT, PayloadFormat.fromValue("PLAIN_TEXT"))
    }
}
