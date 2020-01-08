package com.pawga.radio.model

import com.pawga.radio.common.ISelect
import java.io.Serializable

/**
 * Created by sivannikov
 */


enum class Language(value: String) {
    RUSSIAN("ru_RU"),
    ENGLISH("en_EN"),
    GERMAN("de_DE"),
    FRENCH("fr_FR"),
    SPANISH("es_ES"),
    CHINESE("zh_CN"),
    JAPANESE("ja_JP")
}

interface IRadioLanguage : ISelect {
    val language: Language
    override var select: Boolean
}

data class RadioLanguage(override val language: Language,
                         override var select: Boolean = false) : IRadioLanguage, Serializable