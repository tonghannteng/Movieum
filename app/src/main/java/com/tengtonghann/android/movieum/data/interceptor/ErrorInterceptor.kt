package com.tengtonghann.android.movieum.data.interceptor

import com.tengtonghann.android.movieum.data.exception.AuthenticationException
import com.tengtonghann.android.movieum.data.exception.InternalServerErrorException
import com.tengtonghann.android.movieum.data.exception.NoConnectivityException
import com.tengtonghann.android.movieum.data.exception.PageNotFoundException
import com.tengtonghann.android.movieum.utils.Logger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.net.SocketTimeoutException

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        try {
            val response = chain.proceed(request)
            crashlyticsLogApiError(chain, response)

            when (response.code) {
                401, 403 -> {
                    throw AuthenticationException()
                }
                404 -> {
                    throw PageNotFoundException()
                }
                500 -> {
                    throw InternalServerErrorException()
                }
            }
            return response

        } catch (error: SocketTimeoutException) {
            crashlyticsLogFailedApiRequest(request, error)
            throw error
        } catch (error: NoConnectivityException) {
            crashlyticsLogFailedApiRequest(request, error)
            throw error
        }
    }

    private fun crashlyticsLogApiError(chain: Interceptor.Chain, response: Response) {
        if (response.isSuccessful) {
            return
        }
        try {
            ApiErrorLogger.crashlyticsLogApiErrorResponse(chain.request(), response)
        } catch (ex: Exception) {
            Logger.e(ex)
        }
    }

    private fun crashlyticsLogFailedApiRequest(request: Request, error: IOException) {
        try {
            ApiErrorLogger.crashlyticsLogFailedApiRequest(request, error)
        } catch (ex: Exception) {
            Logger.e(ex)
        }
    }
}