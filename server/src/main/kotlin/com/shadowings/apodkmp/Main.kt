package com.shadowings.apodkmp

import com.mongodb.ConnectionString
import com.shadowings.apodkmp.model.Apod
import com.shadowings.apodkmp.redux.getDep
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.request.get
import io.ktor.features.CORS
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.gzip
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.takeFrom
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.litote.kmongo.async.KMongo
import org.litote.kmongo.async.getCollection
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.eq

val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
    ConnectionString("$it?retryWrites=false")
}

val client = if (connectionString != null) KMongo.createClient(connectionString) else KMongo.createClient()
val database = client.getDatabase(connectionString?.database ?: "apods")
val collection = database.getCollection<Apod>()

@Serializable
data class Error(val message: String)

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(Netty, port) {
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
                    call.respondText(getApod(), ContentType.Application.Json)
                } catch (e: Exception) {
                    call.respondText(
                        Json(JsonConfiguration.Stable)
                            .stringify(Error.serializer(), Error(e.toString())), ContentType.Application.Json)
                }
            }

            get("/latest") {
                try {
                    call.respondText(getLatest(), ContentType.Application.Json)
                } catch (e: Exception) {
                    call.respondText(
                        Json(JsonConfiguration.Stable)
                            .stringify(Error.serializer(), Error(e.toString())), ContentType.Application.Json)
                }
            }
        }
    }.start(wait = true)
}

fun getApodAtDateOffset(offset: Int): Apod {
    val dep = getDep()
    return runBlocking {
        val day = dep.utils.date(offset)
        val result = collection.find(Apod::date eq day).toList()
        if (result.size == 0) {
            try {
                val apodResult = dep.http.httpClient.get<Apod> {
                    url {
                        takeFrom("https://api.nasa.gov/")
                        encodedPath = "planetary/apod?api_key=OFxlCY0NrHskLzRpbnSjUh2xpgkVPLg3Pfq98jfQ&date=$day"
                    }
                }
                collection.insertOne(apodResult)
                return@runBlocking apodResult
            } catch (e: Exception) {
                Apod()
            }
        } else {
            result[0]
        }
    }
}

fun getLatest(): String {
    val dep = getDep()
    return runBlocking {
        val latest: List<Apod> = List(10) {
            getApodAtDateOffset(it + 1)
        }
        return@runBlocking Json(JsonConfiguration.Stable).stringify(Apod.serializer().list, latest)
    }
}

fun getApod(): String {
    return runBlocking {
        val apod = getApodAtDateOffset(0)
        return@runBlocking Json(JsonConfiguration.Stable).stringify(Apod.serializer(), apod)
    }
}
