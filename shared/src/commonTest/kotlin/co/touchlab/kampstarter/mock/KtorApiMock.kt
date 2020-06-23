package co.touchlab.kampstarter.mock

import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.response.ApodResult
import co.touchlab.karmok.MockManager

class KtorApiMock:KtorApi{
    //Call recording provided by experimental library Karmok
    internal val mock = InnerMock()
    override suspend fun getJsonFromApi(): ApodResult {
        return mock.getJsonFromApi.invokeSuspend({ getJsonFromApi() }, listOf())
    }

    class InnerMock(delegate: Any? = null) : MockManager(delegate) {
        internal val getJsonFromApi = MockFunctionRecorder<KtorApiMock, ApodResult>()
    }

    fun successResult(): ApodResult {
        val map = HashMap<String, List<String>>().apply {
            put("appenzeller", emptyList())
            put("australian", listOf("shepherd"))
        }
        return ApodResult(map, "success")
    }
}