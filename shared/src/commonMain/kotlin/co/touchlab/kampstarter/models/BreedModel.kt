package co.touchlab.kampstarter.models

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.currentTimeMillis
import co.touchlab.kampstarter.db.Apods
import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kermit.Kermit
import co.touchlab.stately.ensureNeverFrozen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.inject

class ApodModel() : KoinComponent {
    private val dbHelper: DatabaseHelper by inject()
    private val settings: Settings by inject()
    private val ktorApi: KtorApi by inject()
    private val log: Kermit by inject { parametersOf("ApodModel") }

    companion object {
        internal const val DB_TIMESTAMP_KEY = "DbTimestampKey"
    }

    init {
        ensureNeverFrozen()
    }

    fun selectAllApods(limit: Long, offset: Long) =
        dbHelper.selectAllItems(limit, offset)
            .map { itemList ->
                log.v { "Select all query dirtied" }
                ItemDataSummary(itemList.maxBy { it.date.length }, itemList)
            }

    suspend fun getApodsFromNetwork() : String? {
        fun isApodListStale(currentTimeMS: Long): Boolean {
            val lastDownloadTimeMS = settings.getLong(DB_TIMESTAMP_KEY, 0)
            val oneHourMS = 60 * 60 * 1000
            return (lastDownloadTimeMS + oneHourMS < currentTimeMS)
        }

        val currentTimeMS = currentTimeMillis()
        if (isApodListStale(currentTimeMS)) {
            try {
                val apodResult = ktorApi.getJsonFromApi()
                log.v { "Fetched ${apodResult.date} apod from network" }
                dbHelper.insertApods(listOf(apodResult))
                settings.putLong(DB_TIMESTAMP_KEY, currentTimeMS)

            } catch (e: Exception) {
                return "Unable to download apod list"
            }
        } else {
            log.i { "Apods not fetched from network. Recently updated" }
        }
        return null
    }

    suspend fun updateApodsFavorite(apod: Apods) {
        dbHelper.updateFavorite(apod.date, apod.favorite != 1L)
    }
}

data class ItemDataSummary(val longestItem: Apods?, val allItems: List<Apods>)
