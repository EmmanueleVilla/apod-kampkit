package co.touchlab.kampstarter.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kampstarter.db.Apods
import co.touchlab.kampstarter.models.ApodModel
import co.touchlab.kampstarter.models.ItemDataSummary
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class BreedViewModel : ViewModel() {

    private var apodModel: ApodModel = ApodModel()
    val apodLiveData = MutableLiveData<ItemDataSummary>()
    val errorLiveData = MutableLiveData<String>()

    init {
        observeBreeds()
    }


    @InternalCoroutinesApi
    private fun observeBreeds() {
        viewModelScope.launch {
            apodModel.selectAllApods(1,0).collect(object : FlowCollector<ItemDataSummary> {
                override suspend fun emit(value: ItemDataSummary) {
                    apodLiveData.postValue(value)
                }
            })
        }
    }

    fun getBreedsFromNetwork() {
        viewModelScope.launch {
            apodModel.getApodsFromNetwork()?.let { errorString ->
                errorLiveData.postValue(errorString)
            }
        }
    }
    fun updateBreedFavorite(apod: Apods){
        viewModelScope.launch {
            apodModel.updateApodsFavorite(apod)
        }
    }
}