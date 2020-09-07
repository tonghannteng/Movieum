package com.tengtonghann.android.movieum.utils

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Returns Color from resource
 * @param id Color Resource ID
 */
fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)