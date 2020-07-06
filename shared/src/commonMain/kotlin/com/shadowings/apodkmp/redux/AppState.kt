package com.shadowings.apodkmp.redux
import com.shadowings.apodkmp.home.HomeState

data class AppState(val homeState: HomeState = HomeState())
