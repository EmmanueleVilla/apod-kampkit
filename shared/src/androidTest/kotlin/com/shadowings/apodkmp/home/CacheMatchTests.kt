package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.mock.getMockDeps
import com.shadowings.apodkmp.model.Apod
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test

class CacheMatchTests {

    // checking that writeCache and loadCache uses the same key
    @Test
    fun handleCacheMatch() {
        val response = listOf(Apod(date = "MOCK"), Apod(date = "MOCK"), Apod(date = "MOCK"))
        val dep = getMockDeps()
        val json = Json(JsonConfiguration.Stable).stringify(Apod.serializer().list, response)
        val actions = runBlocking {
            return@runBlocking handleDownloadCompleted(HomeActions.LatestFetch.DownloadCompleted(response), dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.LoadFromCache::class.java, actions.first()::class.java)
        assertEquals(json, dep.storage.settings.getString(dep.constants.latestKey))
        assertEquals(dep.utils.currentTimeMillis(), dep.storage.settings.getLong(dep.constants.latestTimestampKey))

        val actions2 = runBlocking {
            return@runBlocking handleLoadLatestFromCache(HomeActions.LatestFetch.LoadFromCache, dep)
        }
        assertEquals(1, actions2.size)
        assertEquals(HomeActions.LatestFetch.Completed::class.java, actions2.first()::class.java)
        assertEquals(response, (actions2.first() as HomeActions.LatestFetch.Completed).payload)
    }
}
