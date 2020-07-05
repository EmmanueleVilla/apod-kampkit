package co.touchlab.kampstarter

import co.touchlab.kampstarter.model.Apod
import co.touchlab.kampstarter.redux.getDep
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.client.request.get
import kotlinx.coroutines.*
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
                } catch (e : Exception) {
                    call.respondText { e.toString() }
                }
            }
        }
    }.start(wait = true)
}

fun getApod() : String {
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
        return@runBlocking "after result: " + afterResult.size
        //return@runBlocking Json(JsonConfiguration.Stable).stringify(Apod.serializer(), afterResult[0])
    }
}