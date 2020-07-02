package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.splash.splashEpics
import org.reduxkotlin.Store

typealias Epic <S> = (Store<S>, S, Action, Dependencies) -> Unit

internal val appStateEpic: List<Epic<AppState>> = listOf(splashEpics)