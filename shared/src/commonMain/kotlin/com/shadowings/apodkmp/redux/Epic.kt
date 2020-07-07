package com.shadowings.apodkmp.redux

import com.shadowings.apodkmp.home.homeEpics

internal val appStateEpic = listOf(::homeEpics)
