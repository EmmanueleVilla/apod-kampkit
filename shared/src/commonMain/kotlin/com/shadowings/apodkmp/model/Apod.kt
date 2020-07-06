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
)
