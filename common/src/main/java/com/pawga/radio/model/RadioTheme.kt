package com.pawga.radio.model

import com.pawga.radio.common.ISelect
import java.io.Serializable

/**
 * Created by sivannikov
 */


enum class Theme {
    FROG,
    LION,
    CHICKEN,
    SANTACLAUS,
    MAIDEN,
    OWLET
}

interface IRadioTheme: ISelect {
    val theme: Theme
    override var select: Boolean
}

data class RadioTheme(override val theme: Theme,
                      override var select: Boolean = false) : IRadioTheme, Serializable