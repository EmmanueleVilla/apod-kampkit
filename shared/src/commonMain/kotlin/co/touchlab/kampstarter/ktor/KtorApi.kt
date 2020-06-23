package co.touchlab.kampstarter.ktor

import co.touchlab.kampstarter.response.ApodResult

interface KtorApi {
    suspend fun getJsonFromApi(): ApodResult
}