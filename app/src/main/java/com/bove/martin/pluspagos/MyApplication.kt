package com.bove.martin.pluspagos

import android.app.Application
import com.bove.martin.pluspagos.data.di.RetrofitModule.retrofitModule
import com.bove.martin.pluspagos.data.di.ViewModelModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by Martín Bove on 29-Aug-20.
 * E-mail: mbove77@gmail.com
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(listOf(retrofitModule, viewModelModule))
        }

    }
}