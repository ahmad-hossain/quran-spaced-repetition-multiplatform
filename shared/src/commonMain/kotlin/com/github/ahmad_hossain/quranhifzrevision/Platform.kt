package com.github.ahmad_hossain.quranhifzrevision

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform