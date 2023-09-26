package com.github.ahmad_hossain.quranhifzrevision.android

import android.app.Application
import com.github.ahmad_hossain.quranhifzrevision.AndroidDependencies
import com.github.ahmad_hossain.quranhifzrevision.Dependencies

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        deps = AndroidDependencies(this)
    }

    companion object {
        lateinit var deps: Dependencies
    }
}