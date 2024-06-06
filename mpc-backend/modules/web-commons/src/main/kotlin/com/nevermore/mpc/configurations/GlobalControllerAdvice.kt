package com.nevermore.mpc.configurations

import com.nevermore.mpc.vos.ResponseStatus.BAD_REQUEST
import com.nevermore.mpc.vos.ResponseStatus.INTERNAL_SERVER_ERROR
import com.nevermore.mpc.vos.ResponseStatus.NOT_FOUND
import com.nevermore.mpc.vos.ResponseUtils.failure
import com.nevermore.mpc.vos.ResponseVo
import com.nevermore.mpc.vos.ResponseVoWithData
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException

/**
 * @author nevermore
 * @since
 */
@ControllerAdvice
class GlobalControllerAdvices {

    private val logger: Logger = LoggerFactory.getLogger(GlobalControllerAdvices::class.java)

    @ResponseBody
    @ExceptionHandler(Throwable::class)
    fun handleUncaughtException(e: Throwable): ResponseEntity<ResponseVo> {
        logger.error("unhandled exception", e)
        return ok(failure(INTERNAL_SERVER_ERROR))
    }

    @ResponseBody
    @ExceptionHandler(value = [NoHandlerFoundException::class, NoResourceFoundException::class])
    fun handleNotFoundException(e: Exception): ResponseEntity<ResponseVo> {
        val uri = when (e) {
            is NoHandlerFoundException -> e.requestURL
            is NoResourceFoundException -> e.resourcePath
            else -> "unknown uri"
        }
        logger.warn("no handler or resource found: {}", uri)
        return ok(failure(NOT_FOUND))
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleBindException(e: MethodArgumentNotValidException): ResponseEntity<ResponseVoWithData<List<String>>> =
        e.allErrors
            .mapNotNull { it.defaultMessage }
            .toList()
            .let { ok(ResponseVoWithData(BAD_REQUEST.code, BAD_REQUEST.message, it)) }

}
