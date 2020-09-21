package com.bove.martin.pluspagos.data.retrofit

import com.bove.martin.pluspagos.presentation.utils.ApiKey
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val urlWithParams = chain.request().url
            .newBuilder()
            .addQueryParameter("public_key", ApiKey.API_KEY)
            .build()

        var newRequest = chain.request()

        newRequest = newRequest.newBuilder()
            .url(urlWithParams)
            .build()

        return chain.proceed(newRequest)
    }

}