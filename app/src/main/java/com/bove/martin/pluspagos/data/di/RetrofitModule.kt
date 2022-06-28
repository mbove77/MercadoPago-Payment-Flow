package com.bove.martin.pluspagos.data.di

import com.bove.martin.pluspagos.AppConstants
import com.bove.martin.pluspagos.data.retrofit.AuthInterceptor
import com.bove.martin.pluspagos.data.retrofit.MercadoPagoServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(AuthInterceptor())

        return Retrofit.Builder()
            .baseUrl(AppConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideMercadoPagoApi(retrofit: Retrofit): MercadoPagoServices {
        return retrofit.create(MercadoPagoServices::class.java)
    }

}