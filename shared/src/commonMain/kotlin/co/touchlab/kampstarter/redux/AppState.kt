package co.touchlab.kampstarter.redux
import co.touchlab.kampstarter.splash.SplashState
import co.touchlab.kermit.Kermit
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver

data class AppState(val dependencies: Dependencies = Dependencies(), val splashState: SplashState = SplashState())
