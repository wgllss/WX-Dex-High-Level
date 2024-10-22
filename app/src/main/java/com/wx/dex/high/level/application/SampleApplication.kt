package com.wx.dex.high.level.application

import android.app.Application
import io.fastkv.FastKVConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

class SampleApplication : Application() {

    companion object {
        lateinit var application: Application
    }


    override fun onCreate() {
        super.onCreate()
        application = this
        FastKVConfig.setExecutor(Dispatchers.Default.asExecutor())
    }
}