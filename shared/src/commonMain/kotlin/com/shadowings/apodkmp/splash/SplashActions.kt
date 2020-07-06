package com.shadowings.apodkmp.splash

import com.shadowings.apodkmp.model.Apod
import com.shadowings.apodkmp.redux.Action

// toString override because we cannot print the action name via reflection in js
sealed class SplashActions : Action() {
    sealed class ApodFetch : Action() {
        object Request : ApodFetch() {
            override fun toString(): String {
                return "SplashActions.ApodFetch.Request"
            }
        }
        object LoadFromCache : ApodFetch() {
            override fun toString(): String {
                return "SplashActions.ApodFetch.LoadFromCache"
            }
        }
        object FetchFromWeb : ApodFetch() {
            override fun toString(): String {
                return "SplashActions.ApodFetch.FetchFromWeb"
            }
        }
        data class DownloadCompleted(val payload: Apod) : ApodFetch() {
            override fun toString(): String {
                return "SplashActions.ApodFetch.DownloadCompleted"
            }
        }
        data class Completed(val payload: Apod) : ApodFetch() {
            override fun toString(): String {
                return "SplashActions.ApodFetch.Completed"
            }
        }
        data class Error(val message: String) : ApodFetch() {
            override fun toString(): String {
                return "SplashActions.ApodFetch.Error"
            }
        }
    }
}
