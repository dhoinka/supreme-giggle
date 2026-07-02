package com.gloomstone.supremegiggle.exception

/**
 * Exception for 500 Internal Server Error
 */
class InternalServerErrorException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

