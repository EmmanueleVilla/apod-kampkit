package co.touchlab.kampstarter.redux

import android.content.Context
import co.touchlab.kermit.Kermit
import co.touchlab.kermit.LogcatLogger
import com.russhwolf.settings.AndroidSettings
import org.koin.core.KoinComponent
import org.koin.core.get
import java.text.SimpleDateFormat
import java.util.*

actual fun createDependencies(): Dependencies {
    val container = object : KoinComponent {
        val appContext: Context = get()
    }
    val log = Kermit(LogcatLogger()).withTag("APOD")
    return Dependencies(
        utils = Utils(
            getActionName = {
                if(it::class.qualifiedName != null) {
                    it::class.qualifiedName!!
                }
                it.toString()
            },
            getPlatform = { Platforms.Jvm },
            log = log,
            today = { SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().time) }
        ),
        http = Http(),
        storage = Storage(
            settings = AndroidSettings(container.appContext.getSharedPreferences("APOD_SETTINGS", Context.MODE_PRIVATE))
        )
    )
}
