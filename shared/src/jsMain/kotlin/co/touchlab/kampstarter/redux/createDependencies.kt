package co.touchlab.kampstarter.redux

import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.ApodDb
import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.JsSettings
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.db.SqlPreparedStatement

actual fun createDependencies(): Dependencies {

    //stubbing sql driver since it's not yet present in js
    val sqlDriver = object : SqlDriver {
        override fun close() {
        }

        override fun currentTransaction(): Transacter.Transaction? = null

        override fun execute(
            identifier: Int?,
            sql: String,
            parameters: Int,
            binders: (SqlPreparedStatement.() -> Unit)?
        ) {}

        override fun executeQuery(
            identifier: Int?,
            sql: String,
            parameters: Int,
            binders: (SqlPreparedStatement.() -> Unit)?
        ): SqlCursor = object : SqlCursor {
            override fun close() {}

            override fun getBytes(index: Int): ByteArray? = null

            override fun getDouble(index: Int): Double? = null

            override fun getLong(index: Int): Long? = null

            override fun getString(index: Int): String? = null

            override fun next(): Boolean = false
        }

        override fun newTransaction(): Transacter.Transaction = object : Transacter.Transaction() {
            override val enclosingTransaction: Transacter.Transaction?
                get() = null

            override fun endTransaction(successful: Boolean) {
            }
        }

    }
    val log = Kermit(CommonLogger()).withTag("APOD")
    return Dependencies(
        log = log,
        settings = JsSettings(),
        sqlDriver = sqlDriver,
        databaseHelper = DatabaseHelper(sqlDriver, log)
    )
}

actual fun getActionName(action: Action): String {
    return action.toString()
}