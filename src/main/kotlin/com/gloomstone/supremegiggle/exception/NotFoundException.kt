package com.gloomstone.supremegiggle.exception

/**
 * Exception for 404 Not Found errors
 */
class NotFoundException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

