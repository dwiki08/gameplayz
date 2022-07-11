package com.dice.core.utils

import android.view.View

object ViewExtensions {
    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }
}