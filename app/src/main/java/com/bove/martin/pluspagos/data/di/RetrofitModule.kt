package com.bove.martin.pluspagos.data.di

import com.bove.martin.pluspagos.Constantes
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.data.retrofit.AuthIntercector
import com.bove.martin.pluspagos.data.retrofit.MercadoPagoServices
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Martín Bove on 29-Aug-20.
 * E-mail: mbove77@gmail.com
 */
object RetrofitModule {

    val retrofitModule = module {
        single {
            okHttp()
        }
        single {
            retrofit(Constantes.API_BASE_URL, get())
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
        okHttpClientBuilder.addInterceptor(AuthIntercector())
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