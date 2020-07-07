package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.mock.getMockDeps
import com.shadowings.apodkmp.model.Apod
import com.shadowings.apodkmp.redux.Http
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.ContentType
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test

class HandleLatestFetchFromWebTests {
    @Test
    fun handleLatestFetchFromWebOk() {
        val response = listOf(Apod(date = "MOCK"), Apod(date = "MOCK"), Apod(date = "MOCK"))
        val actions = runBlocking {
            val dep = getMockDeps().copy(http = Http(
                httpClient = HttpClient(MockEngine) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                    engine {
                        addHandler { request ->
                            when (request.url.fullPath) {
                                "/latest" -> {
                                    val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                                    val responseJSON = Json(JsonConfiguration.Stable).stringify(Apod.serializer().list, response)
                                    respond(responseJSON, headers = responseHeaders)
                                }
                                else -> error("Unhandled ${request.url.fullPath}")
                            }
                        }
                    }
                }
            ))
            return@runBlocking handleLatestFetchFromWeb(HomeActions.LatestFetch.Request, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.DownloadCompleted::class.java, actions.first()::class.java)
        assertEquals(3, (actions[0] as HomeActions.LatestFetch.DownloadCompleted).payload.size)
        (actions[0] as HomeActions.LatestFetch.DownloadCompleted).payload.forEach { assertEquals("MOCK", it.date) }
    }

    @Test
    fun handleLatestFetchFromError() {
        val actions = runBlocking {
            val dep = getMockDeps().copy(http = Http(
                httpClient = HttpClient(MockEngine) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                    engine {
                        addHandler { request ->
                            error("Unhandled ${request.url.fullPath}")
                        }
                    }
                }
            ))
            return@runBlocking handleLatestFetchFromWeb(HomeActions.LatestFetch.Request, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.LoadFromCache::class.java, actions.first()::class.java)
    }

    @Test
    fun handleLatestFetchException() {
        val actions = runBlocking {
            val dep = getMockDeps().copy(http = Http(
                httpClient = HttpClient(MockEngine) {
                    install(JsonFeature) {
                        serializer = KotlinxSerializer()
                    }
                    engine {
                        addHandler { request ->
                            throw Exception("mock")
                        }
                    }
                }
            ))
            return@runBlocking handleLatestFetchFromWeb(HomeActions.LatestFetch.Request, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.LoadFromCache::class.java, actions.first()::class.java)
    }
}
