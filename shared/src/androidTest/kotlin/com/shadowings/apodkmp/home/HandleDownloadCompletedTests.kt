package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.mock.getMockDeps
import com.shadowings.apodkmp.model.Apod
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test

class HandleDownloadCompletedTests {
    @Test
    fun handleDownloadCompletedTestsOK() {
        val response = listOf(Apod(date = "MOCK"), Apod(date = "MOCK"), Apod(date = "MOCK"))
        val dep = getMockDeps()
        val json = Json(JsonConfiguration.Stable).stringify(Apod.serializer().list, response)
        val actions = runBlocking {
            return@runBlocking handleDownloadCompleted(HomeActions.LatestFetch.DownloadCompleted(response), dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.LoadFromCache::class.java, actions.first()::class.java)
        assertEquals(json, dep.storage.settings.getString("LATEST"))
        assertEquals(dep.utils.currentTimeMillis(), dep.storage.settings.getLong("DOWNLOAD_TIMESTAMP_KEY"))
    }

    @Test
    fun handleDownloadCompletedTestsKO() {
        val response = listOf(Apod(date = "MOCK"), Apod(date = "MOCK"), Apod(date = "MOCK"))
        val dep = getMockDeps().copy(utils = getMockDeps().utils.copy(currentTimeMillis = { throw Exception() }))
        val actions = runBlocking {
            return@runBlocking handleDownloadCompleted(HomeActions.LatestFetch.DownloadCompleted(response), dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.Error::class.java, actions.first()::class.java)
    }
}
