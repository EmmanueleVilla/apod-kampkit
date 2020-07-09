package com.shadowings.apodkmp.model

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
}
