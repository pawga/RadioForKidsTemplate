package com.pawga.radio.data.model

import com.squareup.moshi.JsonClass

/**
 * Created by sivannikov
 */

@JsonClass(generateAdapter = true)
data class JsonRadioStationItem (
        val description: String? = null,
        val id: String? = null,
        val streams: List<Bitrate>? = null,
        val locale: String? = null,
        val bitrate: Int = 0,
        val stream: String? = null,
        val name: String? = null,
        val type: String? = null,
        val image: String? = null) {

    @JsonClass(generateAdapter = true)
    data class Bitrate (
            val bitrate: Int = 0,
            val id: String? = null,
            val stream: String? = null
    )
}