package com.shadowings.apodkmp.home

import com.shadowings.apodkmp.mock.getMockDeps
import com.shadowings.apodkmp.redux.Platforms
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class HandleLatestRequestTests {
    @Test
    fun handleLatestRequestTestFar() {
        val actions = runBlocking {
            val dep = getMockDeps().copy(utils = getMockDeps().utils.copy(currentTimeMillis = { 365L * 24 * 60 * 60 * 1000 }))
            dep.storage.settings.putLong("LATEST_TIMESTAMP", 0)
            return@runBlocking handleLatestRequest(HomeActions.LatestFetch.Request, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.FetchFromWeb::class.java, actions.first()::class.java)
    }

    @Test
    fun handleLatestRequestTestNear() {
        val actions = runBlocking {
            val dep = getMockDeps().copy(utils = getMockDeps().utils.copy(currentTimeMillis = { 1000 }))
            dep.storage.settings.putLong("LATEST_TIMESTAMP", 999)
            return@runBlocking handleLatestRequest(HomeActions.LatestFetch.Request, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.LoadFromCache::class.java, actions.first()::class.java)
    }

    @Test
    fun handleLatestRequestTestNearButJS() {
        val actions = runBlocking {
            val dep = getMockDeps().copy(utils = getMockDeps().utils.copy(currentTimeMillis = { 1000 }, platform = Platforms.Js))
            dep.storage.settings.putLong("LATEST_TIMESTAMP", 999)
            return@runBlocking handleLatestRequest(HomeActions.LatestFetch.Request, dep)
        }
        assertEquals(1, actions.size)
        assertEquals(HomeActions.LatestFetch.FetchFromWeb::class.java, actions.first()::class.java)
    }
}
