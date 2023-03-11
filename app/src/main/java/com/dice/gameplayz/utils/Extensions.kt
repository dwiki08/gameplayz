package com.dice.gameplayz.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object Extensions {
    fun ImageView.loadUrl(url: String) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}