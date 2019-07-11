package com.example.pocapplication

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso



    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(url: String?) {
//        val url = "https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png"
        Picasso.get().load(url).into(this)
    }