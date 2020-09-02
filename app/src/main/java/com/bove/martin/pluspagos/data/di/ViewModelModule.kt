package com.bove.martin.pluspagos.data.di

import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val viewModelModule = module {
        viewModel {
           MainActivityViewModel(get())
        }
    }
}