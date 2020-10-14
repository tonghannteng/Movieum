package com.tengtonghann.android.movieum.data.interceptor

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.tengtonghann.android.movieum.data.exception.ApiRequestTimeOutException
import com.tengtonghann.android.movieum.data.exception.ClientApiRequestException
import com.tengtonghann.android.movieum.data.exception.NoConnectivityException
import com.tengtonghann.android.movieum.data.exception.OfflineApiRequestException
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*
import java.util.regex.Pattern

object ApiErrorLogger {

    private val GUID_PATTERN =
        Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
    private val NUMBER_PATTERN = Pattern.compile("^[0-9]+")

    fun crashlyticsLogApiErrorResponse(request: Request, response: Response) {
        val responseCode = response.code
        val method = request.method
        val genericUrl = generifyHttpUrl(request.url)
        val exceptionMsg = String.format(Locale.US, "[%d] %s %s", responseCode, method, genericUrl)

        crashlyticsLogApiRequest(request)
        crashlyticsLogApiResponse(response)
        if (responseCode in 400..499) {
            crashlyticsLogException(ClientApiRequestException(exceptionMsg))
        } else if (responseCode in 500..599) {
            crashlyticsLogException(ServiceConfigurationError(exceptionMsg))
        }
    }

    fun crashlyticsLogFailedApiRequest(request: Request, ex: IOException) {
        crashlyticsLog("Url:", String.format(Locale.US, "%s:%s", ex.javaClass.name, ex.message))
        crashlyticsLogApiRequest(request)

        val genericUrl = generifyHttpUrl(request.url)
        when (ex) {
            is SocketTimeoutException -> {
                crashlyticsLogException(ApiRequestTimeOutException(genericUrl))
            }
            is NoConnectivityException -> {
                crashlyticsLogException(OfflineApiRequestException(genericUrl))
            }
            else -> {
                crashlyticsLogException(ApiRequestTimeOutException(genericUrl))
            }
        }
    }

    private fun crashlyticsLogApiResponse(response: Response) {
        val responseBody = extractResponseBody(response)
        crashlyticsLog("ResponseBody:", responseBody ?: "")
    }

    private fun crashlyticsLogApiRequest(request: Request) {
        val method = request.method

        // Request
        val actualUrl = request.url.toString()
        val requestHeaders = extractRequestHeaders(request)
        val payload = request.body?.let { extractRequestPayload(it) }
        crashlyticsLog("Url:", String.format(Locale.US, "%s %s", method, actualUrl))
        crashlyticsLog("RequestHeaders", requestHeaders)
        crashlyticsLog("Payload:", payload ?: "")

    }

    private fun crashlyticsLogException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    private fun extractRequestPayload(body: RequestBody): String? {
        var payload: String? = null
        try {
            val buffer = Buffer()
            body.writeTo(buffer)
            payload = buffer.readUtf8()
        } catch (ex: Exception) {
            // ignore
        }
        return payload
    }

    private fun extractResponseBody(response: Response): String? {
        var responseBody: String? = null
        try {
            val tempResponseBody = response.peekBody(Long.MAX_VALUE)
            responseBody = tempResponseBody.string()
        } catch (ex: Exception) {
            // ignore
        }
        return responseBody

    }

    private fun extractRequestHeaders(request: Request): String {
        return request.headers.toString()
    }

    private fun generifyHttpUrl(httpUrl: HttpUrl): String {
        val pathSegments = httpUrl.encodedPathSegments
        if (pathSegments.isEmpty()) {
            return httpUrl.toString()
        }
        val genericUrlBuilder = StringBuilder().append("/")
        for (j in pathSegments.indices) {
            val pathSegment = pathSegments[j]
            var path = pathSegment
            if (isDynamicUrlPathSegment(pathSegment)) {
                path = String.format(Locale.US, "{<%d>}", j)
            }
            genericUrlBuilder.append(path).append("/")
        }

        genericUrlBuilder.deleteCharAt(genericUrlBuilder.length - 1)
        return genericUrlBuilder.toString()

    }

    private fun isDynamicUrlPathSegment(pathSegment: String): Boolean {
        if (pathSegment.isEmpty()) {
            return false
        }
        return isNumber(pathSegment) || isGuid(pathSegment)
    }

    private fun crashlyticsLog(tag: String, msg: String) {
        FirebaseCrashlytics.getInstance().log(String.format(Locale.US, "%s/%s", tag, msg))
    }

    private fun isNumber(str: String): Boolean {
        if (str.isEmpty()) {
            return false
        }
        return NUMBER_PATTERN.matcher(str).find()
    }

    private fun isGuid(str: String): Boolean {
        if (str.isEmpty()) {
            return false
        }
        return GUID_PATTERN.matcher(str).find()
    }
}