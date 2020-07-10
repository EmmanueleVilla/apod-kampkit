package com.shadowings.apodkmp.model

import io.ktor.http.URLBuilder
import kotlinx.serialization.Serializable

@Serializable
data class Apod(
    val date: String = "",
    val copyright: String = "",
    val explanation: String = "",
    val hdurl: String = "",
    val media_type: String = "",
    val service_version: String = "",
    val title: String = "",
    val url: String = ""
) {
    var imageAspectRatio = 0.0F
    val imageUrl: String
        get() {
            return when (media_type) {
                "image" -> url
                "video" -> "https://img.youtube.com/vi/$youtubeId/hqdefault.jpg"
                else -> ""
            }
        }

    val imageUrlHD: String
        get() {
            return when (media_type) {
                "image" -> hdurl
                "video" -> "https://img.youtube.com/vi/$youtubeId/hqdefault.jpg"
                else -> ""
            }
        }

    val youtubeId: String
        get() {
            if (url == "") {
                return ""
            }
            val regex = Regex("(ed/|v=|be/|/v/|/e/)(.*?)([?&#])")
            val matchResult = regex.find("$url?") ?: return ""
            return matchResult.groupValues[2]
        }

    val startTimeMs: Int
        get() {
            if (url == "") {
                return 0
            }
            val parameters = URLBuilder(url).parameters
            if (parameters["start"] == null) {
                return 0
            }
            return (parameters["start"]!!.toIntOrNull() ?: 0) * 1000
        }
}
