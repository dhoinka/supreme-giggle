package com.gloomstone.supremegiggle.exception

/**
 * Exception for 401 Unauthorized errors
 */
class UnauthorizedException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

