package com.coroutinetrialapp.ui

import android.app.Application
import androidx.lifecycle.*
import com.coroutinetrialapp.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel (private val mainRepository: MainRepository) :ViewModel(){

    private val _windspeed: MutableLiveData<String> = MutableLiveData()
    val windspeed: LiveData<String> = _windspeed

    fun fetchWindSpeed() {
        viewModelScope.launch {
            //レスポンス結果を取得
            val response = mainRepository.fetchWindSpeed()
            val daily = response?.daily
            val windSpeedList: List<Double>? = daily?.windspeed_10m_max
            val timeList: List<String>? = daily?.time

            if(windSpeedList != null && timeList != null) {
                _windspeed.postValue(processResponse(timeList,windSpeedList))
            }
        }
    }

    //TextViewに反映するためにレスポンス結果を加工
    private fun processResponse(_timeList: List<String>,_windSpeedList: List<Double>):String {
        var stWindSpeed = ""
        for(i in _timeList.indices) {
            stWindSpeed += "${_timeList[i]}：${_windSpeedList[i]}km/h\n"
        }
        return stWindSpeed
    }
}

class MainViewModelFactory(private val repository: MainRepository, application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

