package com.demoachitecture.remote.base

import com.demoachitecture.remote.ApiConstants
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.regex.Pattern

@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            Timber.d("error message: %s", error.message);
            val message = when (error) {
                is ConnectException -> {
                    ApiConstants.NetworkMessages.CONNECT_EXCEPTION
                }
                is UnknownHostException -> {
                    ApiConstants.NetworkMessages.UNKNOWN_HOST_EXCEPTION
                }
                is SocketTimeoutException -> {
                    ApiConstants.NetworkMessages.SOCKET_TIME_OUT_EXCEPTION
                }
                is HttpException -> {
                    ApiConstants.NetworkMessages.UNKNOWN_NETWORK_EXCEPTION
                }
                else -> {
                    ApiConstants.NetworkMessages.UNKNOWN_NETWORK_EXCEPTION
                }
            }
            return ApiErrorResponse(message)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                Timber.d("status code: %s", response.code());
                Timber.d("body: %s", response.body());
                Timber.d("message: %s", response.message());
                val body = response.body()
                when {
                    body == null || response.code() == 204 -> ApiEmptyResponse()
//                    response.code() == 401 || response.code() == 403 -> ApiErrorResponse("Token expired")
                    else -> ApiSuccessResponse(
                        body = body,
                        linkHeader = response.headers()?.get("link")
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T,
    val links: Map<String, String>
) : ApiResponse<T>() {
    constructor(body: T, linkHeader: String?) : this(
        body = body,
        links = linkHeader?.extractLinks() ?: emptyMap()
    )

    val nextPage: Int? by lazy(LazyThreadSafetyMode.NONE) {
        links[NEXT_LINK]?.let { next ->
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else {
                try {
                    Integer.parseInt(matcher.group(1))
                } catch (ex: NumberFormatException) {
                    Timber.w("cannot parse next page from %s", next)
                    null
                }
            }
        }
    }

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
