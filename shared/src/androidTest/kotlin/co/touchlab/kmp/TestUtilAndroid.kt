package co.touchlab.kampstarter

import android.app.Application
import co.touchlab.kampstarter.db.ApodDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import androidx.test.core.app.ApplicationProvider

internal actual fun testDbConnection(): SqlDriver {
    val app = ApplicationProvider.getApplicationContext<Application>()
    return AndroidSqliteDriver(ApodDb.Schema, app, "droidcondb")
}