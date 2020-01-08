package com.pawga.radio.extensions

import android.content.Context
import com.pawga.radio.R
import com.pawga.radio.model.Theme

/**
 * Created by sivannikov
 */

val Theme.styleResource: Int
    get() {
        return when (this) {
            Theme.FROG -> R.style.AppThemeFrog
            Theme.LION -> R.style.AppThemeLion
            Theme.CHICKEN -> R.style.AppThemeChicken
            Theme.SANTACLAUS -> R.style.AppThemeSantaClaus
            Theme.MAIDEN -> R.style.AppThemeMaiden
            Theme.OWLET -> R.style.AppThemeOwlet
        }
    }

val Theme.imageResource: Int
    get() {
        return when (this) {
            Theme.FROG -> R.drawable.frog_small
            Theme.LION -> R.drawable.lion_small
            Theme.CHICKEN -> R.drawable.chicken_small
            Theme.SANTACLAUS -> R.drawable.santaclaus_small
            Theme.MAIDEN -> R.drawable.maiden_small
            Theme.OWLET -> R.drawable.owlet_small
        }
    }

fun Theme.getTitle(context: Context): CharSequence {

    val idResource = when (this) {
        Theme.FROG -> R.string.theme_frog
        Theme.LION -> R.string.theme_lion
        Theme.CHICKEN -> R.string.theme_chicken
        Theme.SANTACLAUS -> R.string.theme_santaclaus
        Theme.MAIDEN -> R.string.theme_maiden
        Theme.OWLET -> R.string.theme_owlet
    }

    return context.getText(idResource)
}