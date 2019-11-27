package com.dang.msautorization

import android.app.Application
import com.dang.msautorization.di.AppComponent
import com.dang.msautorization.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
                .builder()
                .context(this)
                .build()
    }

}