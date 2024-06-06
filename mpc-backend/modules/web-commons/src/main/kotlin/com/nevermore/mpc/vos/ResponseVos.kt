package com.nevermore.mpc.vos

/**
 * @author nevermore
 * @since
 */
open class ResponseVo(
    val status: Int, val message: String?
)

class ResponseVoWithData<out T>(
    status: Int,
    message: String,
    val data: T
) : ResponseVo(status, message)


object ResponseUtils {
    @JvmStatic
    fun success() = ResponseVo(ResponseStatus.SUCCESS.code, ResponseStatus.SUCCESS.message)

    @JvmStatic
    fun <D> success(data: D) = ResponseVoWithData(ResponseStatus.SUCCESS.code, ResponseStatus.SUCCESS.message, data)

    @JvmStatic
    fun failure(status: ResponseStatus) = ResponseVo(status.code, status.message)

    @JvmStatic
    fun <D> failure(status: ResponseStatus, data: D) = ResponseVoWithData(status.code, status.message, data)
}

enum class ResponseStatus(val code: Int, val message: String) {
    SUCCESS(0, "Success"),

    INTERNAL_SERVER_ERROR(1000, "internal server error"), //10xx: server faults
    NOT_FOUND(2000, "not found"), //20xx: client faults
    BAD_REQUEST(2001, "bad request"),
}
