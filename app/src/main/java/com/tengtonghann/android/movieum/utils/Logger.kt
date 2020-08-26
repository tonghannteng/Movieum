package com.tengtonghann.android.movieum.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * @author Tonghann Teng
 */
object Logger {

    fun d(tag: String, message: String) =
        FirebaseCrashlytics.getInstance().setCustomKey(tag, message)

    fun e(e: Exception) =
        FirebaseCrashlytics.getInstance().recordException(e)

    fun t(t: Throwable) =
        FirebaseCrashlytics.getInstance().recordException(t)
}