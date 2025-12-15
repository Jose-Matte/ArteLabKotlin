package com.localgo.artelabspa.data.remote.weather

import com.localgo.artelabspa.data.remote.weather.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("lang") lang: String = "es"
    ): WeatherResponse
}