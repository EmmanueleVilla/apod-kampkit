package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.mock.getMockDeps
import com.shadowings.apodkmp.model.Apod
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test

class HandleLoadLatestFromCacheTests {

    @Test
    fun handleLoadLatestFromCacheTestsHitCache() {
        val response = listOf(Apod(date = "MOCK"), Apod(date = "MOCK"), Apod(date = "MOCK"))
        val dep = getMockDeps()
        val json = Json(JsonConfiguration.Stable).stringify(Apod.serializer().list, response)
        dep.storage.settings.putString("LATEST", json)
        val actions = runBlocking {
            return@runBlocking handleLoadLatestFromCache(HomeActions.LatestFetch.LoadFromCache, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.Completed::class.java, actions.first()::class.java)
        assertEquals(response, (actions.first() as HomeActions.LatestFetch.Completed).payload)
    }

    @Test
    fun handleLoadLatestFromCacheTestsHitCacheButInvalid() {
        val dep = getMockDeps()
        dep.storage.settings.putString("LATEST", "invalid json")
        val actions = runBlocking {
            return@runBlocking handleLoadLatestFromCache(HomeActions.LatestFetch.LoadFromCache, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.Error::class.java, actions.first()::class.java)
    }

    @Test
    fun handleLoadLatestFromCacheNoHit() {
        val dep = getMockDeps()
        val actions = runBlocking {
            return@runBlocking handleLoadLatestFromCache(HomeActions.LatestFetch.LoadFromCache, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.Error::class.java, actions.first()::class.java)
    }
}
