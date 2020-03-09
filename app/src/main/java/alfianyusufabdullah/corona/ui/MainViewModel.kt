package alfianyusufabdullah.corona.ui

import alfianyusufabdullah.corona.data.entity.DataResponse
import alfianyusufabdullah.corona.data.entity.Infected
import alfianyusufabdullah.corona.data.entity.Location
import alfianyusufabdullah.corona.data.repository.DataRepository
import alfianyusufabdullah.corona.util.Mapper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ResponseCache

@ExperimentalCoroutinesApi
class MainViewModel(private val dataRepository: DataRepository, private val mapper: Mapper) :
    ViewModel() {

    private val _location = MutableLiveData<Location>()
    private val _data = MutableLiveData<DataResponse>()
    private val _loading = MutableLiveData<Boolean>()

    val location: LiveData<Location>
        get() = _location

    val data: LiveData<DataResponse>
        get() = _data

    val loading: LiveData<Boolean>
        get() = _loading

    fun retrieveCoronaInfectedLocationData() {
        viewModelScope.launch {

            dataRepository.loadMainData()
                .zip(dataRepository.loadDataWithLocation()) { mainData, locations ->
                    Infected(mainData, locations ?: emptyArray())
                }
                .map {
                    it.copy().apply {
                        mainData = mapper.lastUpdateMapper(it.mainData as DataResponse)
                    }
                }
                .map {
                    it.copy().apply {
                        locations =
                            mapper.locationNameMapper(it.locations)?.toTypedArray() ?: emptyArray()
                    }
                }
                .map {
                    it.copy().apply {
                        locations = mapper.locationLastUpdateMapper(it.locations)?.toTypedArray()
                            ?: emptyArray()
                    }
                }
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .collect {
                    _data.value = it.mainData

                    it.locations.forEach { location ->
                        delay(30)
                        _location.value = location
                    }
                }
        }
    }

    fun reloadInformationOnDashboard() {
        val latest = data.value
        _data.value = latest
    }
}