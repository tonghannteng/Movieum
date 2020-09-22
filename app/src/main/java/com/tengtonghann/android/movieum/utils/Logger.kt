package com.tengtonghann.android.movieum.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * @author Tonghann Teng
 */
object Logger {

    fun d(tag: String, message: String) {
        Log.d(tag, message)
        FirebaseCrashlytics.getInstance().log(message)
        FirebaseCrashlytics.getInstance().setCustomKey(tag, message)
    }

    fun e(e: Exception) {
        e.message?.let { FirebaseCrashlytics.getInstance().log(it) }
        FirebaseCrashlytics.getInstance().recordException(e)

    }

    fun t(t: Throwable) {
        t.message?.let { FirebaseCrashlytics.getInstance().log(it) }
        FirebaseCrashlytics.getInstance().recordException(t)
    }
}