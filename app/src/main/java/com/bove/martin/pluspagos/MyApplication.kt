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
            androidLogger(Level.NONE) //FIXME Here is an open bug, but it is only with the logger https://github.com/InsertKoinIO/koin/issues/847
            androidContext(this@MyApplication)
            modules(listOf(retrofitModule, viewModelModule))
        }

    }
}