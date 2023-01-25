package com.glushko.testappbarrier.utils.extensions

import android.os.SystemClock
import android.view.View

private const val MIN_CLICK_DELAY = 700L

fun View.setSafeOnClickListener(action: (View) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View) {
            if (lastClickTime + MIN_CLICK_DELAY < SystemClock.elapsedRealtime()) {
                lastClickTime = SystemClock.elapsedRealtime()
                action(v)
            }
        }
    })
}