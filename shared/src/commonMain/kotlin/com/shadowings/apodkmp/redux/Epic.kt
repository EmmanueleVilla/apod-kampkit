package com.shadowings.apodkmp.redux

import com.shadowings.apodkmp.home.homeEpics
import org.reduxkotlin.Store

internal val appStateEpic: List<(Store<AppState>, Action, Dependencies) -> Unit> = listOf(::homeEpics)
