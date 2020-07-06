package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.model.Apod
import com.shadowings.apodkmp.redux.Action

// toString override because we cannot print the action name via reflection in js
sealed class HomeActions : Action() {
    sealed class LatestFetch : Action() {
        object Request : LatestFetch() {
            override fun toString(): String {
                return "HomeActions.LatestFetch.Request"
            }
        }
        object LoadFromCache : LatestFetch() {
            override fun toString(): String {
                return "HomeActions.LatestFetch.LoadFromCache"
            }
        }
        object FetchFromWeb : LatestFetch() {
            override fun toString(): String {
                return "HomeActions.LatestFetch.FetchFromWeb"
            }
        }
        data class DownloadCompleted(val payload: Apod) : LatestFetch() {
            override fun toString(): String {
                return "HomeActions.LatestFetch.DownloadCompleted"
            }
        }
        data class Completed(val payload: Apod) : LatestFetch() {
            override fun toString(): String {
                return "HomeActions.LatestFetch.Completed"
            }
        }
        data class Error(val message: String) : LatestFetch() {
            override fun toString(): String {
                return "HomeActions.LatestFetch.Error"
            }
        }
    }
    sealed class ApodFetch : Action() {
        object Request : ApodFetch() {
            override fun toString(): String {
                return "HomeActions.ApodFetch.Request"
            }
        }
        object LoadFromCache : ApodFetch() {
            override fun toString(): String {
                return "HomeActions.ApodFetch.LoadFromCache"
            }
        }
        object FetchFromWeb : ApodFetch() {
            override fun toString(): String {
                return "HomeActions.ApodFetch.FetchFromWeb"
            }
        }
        data class DownloadCompleted(val payload: Apod) : ApodFetch() {
            override fun toString(): String {
                return "HomeActions.ApodFetch.DownloadCompleted"
            }
        }
        data class Completed(val payload: Apod) : ApodFetch() {
            override fun toString(): String {
                return "HomeActions.ApodFetch.Completed"
            }
        }
        data class Error(val message: String) : ApodFetch() {
            override fun toString(): String {
                return "HomeActions.ApodFetch.Error"
            }
        }
    }
}
