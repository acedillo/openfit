package com.acedillo.openfit.util

import android.widget.ImageView
import com.acedillo.openfit.R
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object ImageLoader {

    private const val HOST = "https://wger.de"

    fun loadImage(view: ImageView, url: String?) {
        if (url == null || url.isEmpty()) {
            return
        }

        val options = RequestOptions()
            .placeholder(R.drawable.ic_android)
            .error(R.drawable.ic_android)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(view)
            .load(HOST + url)
            .apply(options)
            .into(view)

    }
}