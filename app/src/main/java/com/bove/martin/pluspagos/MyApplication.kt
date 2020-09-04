package com.bove.martin.pluspagos

import android.app.Application
import com.bove.martin.pluspagos.data.di.RetrofitModule.retrofitModule
import com.bove.martin.pluspagos.data.di.ViewModelModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(listOf(retrofitModule, viewModelModule))
        }

    }
}