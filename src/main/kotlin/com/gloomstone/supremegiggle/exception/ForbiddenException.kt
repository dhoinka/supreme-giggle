package com.gloomstone.supremegiggle.exception

/**
 * Exception for 403 Forbidden errors
 */
class ForbiddenException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

