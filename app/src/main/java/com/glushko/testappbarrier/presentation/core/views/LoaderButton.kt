package com.glushko.testappbarrier.presentation.core.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.glushko.testappbarrier.R
import com.google.android.material.button.MaterialButton

class LoaderButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    private var textRemember = ""

    var isShowProgress = false
        private set

    init {
        textRemember = text.toString()
    }

    fun setTextWithRemember(text: String) {
        textRemember = text
        this.text = text
    }

    fun showProgress(isShow: Boolean) {
        isShowProgress = isShow
        isClickable = !isShow
        text = if (isShow) {
            ""
        } else {
            textRemember
        }
        setShowProgress(isShow)
    }

    private fun setShowProgress(showProgress: Boolean?) {
        iconGravity = ICON_GRAVITY_TEXT_END
        icon = if (showProgress == true) {
            CircularProgressDrawable(context).apply {
                setStyle(CircularProgressDrawable.DEFAULT)
                setColorSchemeColors(ContextCompat.getColor(context, R.color.white))
                start()
            }
        } else null
        if (icon != null) { // callback to redraw button icon
            icon.callback = object : Drawable.Callback {
                override fun unscheduleDrawable(who: Drawable, what: Runnable) {}

                override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {}

                override fun invalidateDrawable(who: Drawable) {
                    this@LoaderButton.invalidate()
                }
            }
        }
    }
}
