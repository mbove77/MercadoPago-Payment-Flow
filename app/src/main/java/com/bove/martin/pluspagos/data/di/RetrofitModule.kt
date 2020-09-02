package com.bove.martin.pluspagos.data.di

import com.bove.martin.pluspagos.presentation.utils.Constants
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.data.retrofit.AuthInterceptor
import com.bove.martin.pluspagos.data.retrofit.MercadoPagoServices
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {

    val retrofitModule = module {
        single {
            okHttp()
        }
        single {
            retrofit(Constants.API_BASE_URL, get())
        }
        single {
            get<Retrofit>().create(MercadoPagoServices::class.java)
        }
        single {
            MercadoPagoRepository(get())
        }
    }

    private fun okHttp(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(AuthInterceptor())
        return okHttpClientBuilder.build()
    }

    private fun retrofit(api_url: String, okHttpClient: OkHttpClient): Retrofit {
       return Retrofit.Builder()
            .baseUrl(api_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

}