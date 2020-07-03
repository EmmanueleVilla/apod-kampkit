package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.splash.splashEpics
import org.reduxkotlin.Store

internal val appStateEpic: List<(Store<AppState>, Action, Dependencies) -> Unit> = listOf(::splashEpics)