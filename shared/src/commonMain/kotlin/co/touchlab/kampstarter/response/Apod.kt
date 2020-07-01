package co.touchlab.kampstarter.response

import co.touchlab.kampstarter.db.Apods
import kotlinx.serialization.Serializable

@Serializable
data class ApodResult(
    override val date: String,
    override val copyright: String,
    override val explanation: String,
    override val hdurl: String,
    override val media_type: String,
    override val service_version: String,
    override val title: String,
    override val url: String,
    override val favorite: Long = 0
) : Apods