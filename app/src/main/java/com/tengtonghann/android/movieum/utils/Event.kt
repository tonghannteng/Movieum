package com.tengtonghann.android.movieum.utils

import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Tonghann Teng
 * Used as a wrapper for data that is exposed via a LiveData that represents as event.
 */
data class Event<out T>(private val content: T) {

    private var hasBeenHandled = AtomicBoolean(false)

    /**
     * Returns the content and prevents its use again.
     */
    fun getIfNotHandled(): T? = if (hasBeenHandled.getAndSet(true)) null else content

    /**
     * Returns the content, even if is is already been handled
     */
    fun peek(): T = content
}