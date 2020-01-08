package com.pawga.radio.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pawga.radio.R
import com.pawga.radio.model.RadioLanguage
import com.pawga.radio.model.RadioTheme

/**
 * Created by sivannikov
 */

@BindingAdapter("languageImage")
fun ImageView.setLanguageImage(item: RadioLanguage) {
    setImageResource(item.language.imageResource)
}

@BindingAdapter("themeImage")
fun ImageView.setThemeImage(item: RadioTheme) {
    setImageResource(item.theme.imageResource)
}

@BindingAdapter("localImage")
fun ImageView.loadLocalImage(imageResource: Int) {
    setImageResource(imageResource)
}

// important code for loading image here
@BindingAdapter("networkImage")
fun loadImage(imageView: ImageView, imageURL: String?) {
    Glide.with(imageView.context)
            .setDefaultRequestOptions(RequestOptions()
                    .circleCrop())
            .load(imageURL)
            .placeholder(R.drawable.stub_image_station)
            .into(imageView)
}