package com.example.androiddevchallenge

import android.app.Application
import okhttp3.OkHttpClient
import rxhttp.wrapper.param.RxHttp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RxHttp.setDebug(true)
        RxHttp.init(OkHttpClient())
    }
}