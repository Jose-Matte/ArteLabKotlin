package com.localgo.artelabspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.artelabspa.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weather = MutableStateFlow<String>("")
    val weather: StateFlow<String> = _weather

    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                val res = repository.getWeather(city)
                _weather.value =
                    "${res.location.name}: ${res.current.temp_c}°C - ${res.current.condition.text}"
            } catch (e: Exception) {
                _weather.value = "No se pudo cargar el clima"
            }
        }
    }
}