package com.bove.martin.pluspagos.data.retrofit

import com.bove.martin.pluspagos.Constantes
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by Martín Bove on 22-Jul-20.
 * E-mail: mbove77@gmail.com
 */
class AuthIntercector : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val urlWithParams = chain.request().url
            .newBuilder()
            .addQueryParameter("public_key", Constantes.API_KEY)
            .build()

        var newRequest = chain.request()

        newRequest = newRequest.newBuilder()
            .url(urlWithParams)
            .build()

        return chain.proceed(newRequest)
    }

}