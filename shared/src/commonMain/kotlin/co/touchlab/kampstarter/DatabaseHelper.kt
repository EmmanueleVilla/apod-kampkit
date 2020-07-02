package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.ApodDb
import co.touchlab.kampstarter.db.Apods
import co.touchlab.kampstarter.sqldelight.asFlow
import co.touchlab.kampstarter.sqldelight.mapToList
import co.touchlab.kampstarter.sqldelight.transactionWithContext
import co.touchlab.kermit.Kermit
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DatabaseHelper(sqlDriver: SqlDriver,
                     private val log: Kermit,
                     private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default) {
    private val dbRef: ApodDb = ApodDb(sqlDriver)

    fun selectAllItems(limit: Long, offset: Long): Flow<List<Apods>> =
        dbRef.tableQueries
            .selectWithPaging(limit, offset)
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun insertApods(apod: List<Apods>) {
        log.d { "Inserting ${apod.size} apods into database" }
        dbRef.transactionWithContext(backgroundDispatcher) {
            apod.forEach { dbRef.tableQueries.insertApod(it.date, it.copyright, it.explanation, it.hdurl, it.media_type, it.service_version, it.title, it.url)
            }
        }
    }

    suspend fun selectByDate(date: GMTDate): Flow<List<Apods>> =
        dbRef.tableQueries
            .selectByDate( "${date.year} - ${date.month} - ${date.dayOfMonth}")
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)

    suspend fun deleteAll() {
        log.i { "Database Cleared" }
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.tableQueries.deleteAll()
        }
    }

    suspend fun updateFavorite(date: String, favorite: Boolean) {
        log.i { "Apods $date: Favorited $favorite" }
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.tableQueries.updateFavorite(favorite.toLong(), date)
        }
    }
}

fun Apods.isFavorited(): Boolean = this.favorite != 0L
internal fun Boolean.toLong(): Long = if (this) 1L else 0L