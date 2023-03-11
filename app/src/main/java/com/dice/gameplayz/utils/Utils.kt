package com.dice.gameplayz.utils

import android.app.Activity
import androidx.core.view.WindowInsetsCompat

object Utils {
    fun onKeyboardListener(
        activity: Activity,
        action: (isKeyboardShowing: Boolean) -> Unit
    ) {
        activity.window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            val insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets, view)
            val isKeyboardShowing = insetsCompat.isVisible(WindowInsetsCompat.Type.ime())
            action(isKeyboardShowing)
            view.onApplyWindowInsets(insets)
        }
    }
}