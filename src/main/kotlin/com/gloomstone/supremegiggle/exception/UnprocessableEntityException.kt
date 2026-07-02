package com.gloomstone.supremegiggle.exception

/**
 * Exception for 422 Unprocessable Entity errors
 */
class UnprocessableEntityException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

