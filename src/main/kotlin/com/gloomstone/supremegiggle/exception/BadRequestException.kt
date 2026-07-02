package com.gloomstone.supremegiggle.exception

/**
 * Exception for 400 Bad Request errors
 */
class BadRequestException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

