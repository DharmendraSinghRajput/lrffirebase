package com.example.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.lrffirebase.R
@SuppressLint("SimpleDateFormat")

object GeneralFunctions {


    fun loadImage(context: Context, url: String, imageView: ImageView, changeScale: Boolean = true) {
        Glide.with(context).load(url).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                if (changeScale)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                return false
            }
        }).error(R.drawable.ic_profile_discover)
            .placeholder(R.drawable.ic_profile_discover).into(imageView)
    }



/*    fun loadImage(context: Context, url: String, imageView: ImageView, changeScale: Boolean = true) {
        Glide.with(context).load(url).listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                if (changeScale)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                return false
            }
        }).error(R.drawable.ic_profile_discover)
            .placeholder(R.drawable.ic_profile_discover).into(imageView)
    }*/



}