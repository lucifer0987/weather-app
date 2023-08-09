package com.example.kmm_demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform