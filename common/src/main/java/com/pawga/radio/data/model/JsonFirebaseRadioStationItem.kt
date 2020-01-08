package com.pawga.radio.data.model

import com.squareup.moshi.Json

/**
 * Created by sivannikov
 */

data class JsonFirebaseRadioStationItem(
        @Json(name = "bitrate") val bitrate: Int = 0,
        @Json(name = "description") val description: String? = null,
        @Json(name = "id") val id: String? = null,
        @Json(name = "image") val image: String? = null,
        @Json(name = "locale") val locale: String? = null,
        @Json(name = "name") val name: String? = null,
        @Json(name = "stream") val stream: String? = null,
        @Json(name = "streams") val streams: List<Stream>? = null,
        @Json(name = "type") val type: String? = null
) {
    data class Stream(
            @Json(name = "bitrate") val bitrate: Int = 0,
            @Json(name = "id") val id: String? = null,
            @Json(name = "stream") val stream: String? = null
    )
}