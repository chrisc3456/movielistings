package com.examples.movielistings.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle

/**
 * Extension method for activity to allow parameters to be passed in more easily
 */
inline fun <reified T : Any> Activity.launch(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = intent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        startActivityForResult(intent, requestCode, options)
    else
        startActivityForResult(intent, requestCode)
}

/**
 * Extension method for context to allow parameters to be passed in more easily
 */
inline fun <reified T : Any> Context.launch(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = intent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        startActivity(intent, options)
    else
        startActivity(intent)
}

inline fun <reified T : Any> intent(context: Context): Intent = Intent(context, T::class.java)