package com.bove.martin.pluspagos.data.di

import com.bove.martin.pluspagos.data.MemoryRepository
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {

    val viewModelModule = module {
        single {
            MemoryRepository(null,null,null,null,null)
        }
        viewModel {
           MainActivityViewModel(get(), get())
        }
    }
}