package co.touchlab.kampstarter

import co.touchlab.kampstarter.db.Apods
import co.touchlab.kampstarter.models.ApodModel
import co.touchlab.kampstarter.models.ItemDataSummary
import co.touchlab.stately.ensureNeverFrozen
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.inject
import co.touchlab.kermit.Kermit

class NativeViewModel(
    private val viewUpdate: (ItemDataSummary) -> Unit,
    private val errorUpdate: (String) -> Unit
) : KoinComponent {

    private val log: Kermit by inject { parametersOf("BreedModel") }
    private val scope = MainScope(Dispatchers.Main, log)
    private val apodModel: ApodModel

    init {
        ensureNeverFrozen()
        apodModel = ApodModel()
        observeBreeds()
    }

    private fun observeBreeds() {
        scope.launch {
            log.v { "Observe Breeds" }
            apodModel.selectAllApods(1,0)
                .collect { summary ->
                    log.v { "Collecting Things" }
                    viewUpdate(summary)
                }
        }
    }

    fun getBreedsFromNetwork() {
        scope.launch {
            apodModel.getApodsFromNetwork()?.let{ errorString ->
                errorUpdate(errorString)
            }
        }
    }
    fun updateBreedFavorite(apod: Apods){
        scope.launch {
            apodModel.updateApodsFavorite(apod)
        }
    }

    fun onDestroy() {
        scope.onDestroy()
    }
}