package com.shadowings.apodkmp.redux

import com.shadowings.apodkmp.splash.splashEpics
import org.reduxkotlin.Store

internal val appStateEpic: List<(Store<AppState>, Action, Dependencies) -> Unit> = listOf(::splashEpics)
