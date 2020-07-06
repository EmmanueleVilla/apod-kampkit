package com.shadowings.apodkmp

import com.shadowings.apodkmp.model.Apod
import com.shadowings.apodkmp.redux.getDep
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.request.get
import io.ktor.features.CORS
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.gzip
import io.ktor.http.HttpMethod
import io.ktor.http.takeFrom
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.litote.kmongo.async.KMongo
import org.litote.kmongo.async.getCollection
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.eq

val client = KMongo.createClient()
val database = client.getDatabase("apods")
val collection = database.getCollection<Apod>()

fun main() {
    embeddedServer(Netty, 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }

        routing {
            get("/apod") {
                try {
                    call.respondText(getApod())
                } catch (e: Exception) {
                    call.respondText { e.toString() }
                }
            }
        }
    }.start(wait = true)
}

fun getApod(): String {
    return runBlocking {
        val dep = getDep()
        val today = dep.utils.today()
        val result = collection.find(Apod::date eq today).toList()
        if (result.size == 0) {
            try {
                val apodResult = dep.http.httpClient.get<Apod> {
                    url {
                        takeFrom("https://api.nasa.gov/")
                        encodedPath = "planetary/apod?api_key=OFxlCY0NrHskLzRpbnSjUh2xpgkVPLg3Pfq98jfQ"
                    }
                }
                collection.insertOne(apodResult)
            } catch (e: Exception) {
                return@runBlocking e.toString()
            }
        }
        val afterResult = collection.find(Apod::date eq today).toList()
        return@runBlocking Json(JsonConfiguration.Stable).stringify(Apod.serializer(), afterResult[0])
    }
}
