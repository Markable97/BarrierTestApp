package com.glushko.testappbarrier.utils.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import timber.log.Timber

private var toast: Toast? = null
fun toast(context: Context, message: String) {
    toast?.apply { cancel() }
    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT).apply { show() }
}

fun toast(context: Context, @StringRes message: Int) {
    toast?.apply { cancel() }
    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT).apply { show() }
}

fun toastLong(context: Context, message: String) {
    toast?.apply { cancel() }
    toast = Toast.makeText(context, message, Toast.LENGTH_LONG).apply { show() }
}

fun snackbar(view: View, @StringRes message: Int) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun snackbar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun snackbarLong(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}

fun inDevelopment(context: Context) {
    toast(context, "In Development")
}

fun toastClicked(context: Context, msg: String) {
    toast(context, "$msg Clicked")
}

fun loge(message: String, tag: String = "RRR") {
    Timber.tag(tag).e(message)
}

fun logi(message: String, tag: String = "RRR") {
    Timber.tag(tag).i(message)
}

fun loge(clazz: Any, tag: String = "RRR") {
    Timber.tag(tag).e(Gson().toJson(clazz))
}

fun logw(message: String, tag: String = "RRR") {
    Timber.tag(tag).w(message)
}
