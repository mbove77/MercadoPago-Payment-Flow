package com.bove.martin.pluspagos.data.di


import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Martín Bove on 30-Aug-20.
 * E-mail: mbove77@gmail.com
 */
object ViewModelModule {
    val viewModelModule = module {
        viewModel {
           MainActivityViewModel(get())
        }
    }
}