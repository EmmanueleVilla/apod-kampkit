package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.model.Apod
import kotlinx.serialization.Serializable

@Serializable
data class HomeState(
    val latest: List<Apod> = listOf(),
    val favourites: List<Apod> = listOf()
)
