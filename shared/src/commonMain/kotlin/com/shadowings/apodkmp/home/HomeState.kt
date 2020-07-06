package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.model.Apod
import kotlinx.serialization.Serializable

@Serializable
data class HomeState(val apod: Apod = Apod())
