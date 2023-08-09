package com.example.kmm_demo.android.data

sealed class Resource<T>(
    val data: T? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data = data)
    class Loading<T> : Resource<T>()
    class Error<T> : Resource<T>()
    class NoInternet<T> : Resource<T>()
}
