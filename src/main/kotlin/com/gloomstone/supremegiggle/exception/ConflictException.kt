package com.gloomstone.supremegiggle.exception

/**
 * Exception for 409 Conflict errors
 */
class ConflictException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

