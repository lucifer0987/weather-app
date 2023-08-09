package com.example.kmm_demo.android.data.dto

data class WeatherItem(
    var curr_temp: Long = 0,
    var min_temp: Long = 0,
    var description: String = "",
    var max_temp: Long = 0
)