package com.yma.banks.di

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yma.banks.BuildConfig.BASE_URL
import com.yma.banks.R

@BindingAdapter(value = ["imageUrl"], requireAll = false)
public fun loadImage(view: ImageView, url: String) {
    if (TextUtils.isEmpty(url)) {
        view.setImageResource(R.drawable.ic_placeholder)
    } else {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    view.setImageResource(R.drawable.ic_placeholder)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            }).into(view)
    }
}