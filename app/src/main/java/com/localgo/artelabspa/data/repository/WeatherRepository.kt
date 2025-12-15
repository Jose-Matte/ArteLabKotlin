package com.localgo.artelabspa.data.repository

import com.localgo.artelabspa.BuildConfig
import com.localgo.artelabspa.data.remote.weather.WeatherRetrofit
import com.localgo.artelabspa.data.remote.weather.dto.WeatherResponse

class WeatherRepository {

    suspend fun getWeather(city: String): WeatherResponse {
        return WeatherRetrofit.api.getCurrentWeather(
            apiKey = BuildConfig.WEATHER_API_KEY,
            city = city
        )
    }
}
