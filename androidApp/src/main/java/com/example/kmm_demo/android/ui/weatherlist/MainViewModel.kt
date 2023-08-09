package com.example.kmm_demo.android.ui.weatherlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm_demo.android.data.Resource
import com.example.kmm_demo.android.data.dto.WeatherItem
import com.example.kmm_demo.android.data.repository.DataRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    lateinit var dataRepository: DataRepository

    init {
        dataRepository = DataRepository()
    }

    private val weatherDataPrivate = MutableLiveData<Resource<MutableList<WeatherItem>>>()
    val weatherData: LiveData<Resource<MutableList<WeatherItem>>> get() = weatherDataPrivate

    fun getWeatherListByCity(city: String) {
        weatherDataPrivate.value = Resource.Loading()

        viewModelScope.launch {
            val weatherList = dataRepository.getWeatherListByCity(city)
            weatherDataPrivate.value = weatherList
        }
    }

}