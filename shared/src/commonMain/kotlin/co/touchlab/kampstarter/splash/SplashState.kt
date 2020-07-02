package co.touchlab.kampstarter.splash

import co.touchlab.kampstarter.db.Apods

data class SplashState(val apod: Apods = object : Apods {
    override val date: String
        get() = ""
    override val copyright: String
        get() = ""
    override val explanation: String
        get() = ""
    override val hdurl: String
        get() = ""
    override val media_type: String
        get() = ""
    override val service_version: String
        get() = ""
    override val title: String
        get() = ""
    override val url: String
        get() = ""
    override val favorite: Long
        get() = 0L

})