package com.pawga.radio.extensions

import android.content.Context
import com.pawga.radio.R
import com.pawga.radio.model.Language

/**
 * Created by sivannikov
 */

val Language.imageResource: Int
    get() {
        return when (this) {
            Language.RUSSIAN -> R.drawable.rus
            Language.ENGLISH -> R.drawable.eng
            Language.GERMAN -> R.drawable.de
            Language.FRENCH -> R.drawable.fra
            Language.SPANISH -> R.drawable.esp
            Language.CHINESE -> R.drawable.chn
            Language.JAPANESE -> R.drawable.jpn
        }
    }

fun Language.getTitle(context: Context): CharSequence {

    val idResource = when (this) {
        Language.RUSSIAN -> R.string.str_russian_language
        Language.ENGLISH -> R.string.str_english_language
        Language.GERMAN -> R.string.str_german_language
        Language.FRENCH -> R.string.str_french_language
        Language.SPANISH -> R.string.str_spanish_language
        Language.CHINESE -> R.string.str_chinese_language
        Language.JAPANESE -> R.string.str_japanese_language
    }

    return context.getText(idResource)
}
