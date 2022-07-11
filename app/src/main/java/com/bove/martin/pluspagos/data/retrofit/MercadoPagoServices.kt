package com.bove.martin.pluspagos.data.retrofit

import com.bove.martin.pluspagos.domain.model.CardIssuer
import com.bove.martin.pluspagos.domain.model.InstallmentOption
import com.bove.martin.pluspagos.domain.model.Payment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MercadoPagoServices {
    @GET("payment_methods")
    suspend fun paymentMethods(): Response<List<Payment>>

    @GET("payment_methods/card_issuers")
    suspend fun getCardIssuers(@Query("payment_method_id") payment_id: String): Response<List<CardIssuer>>

    @GET("payment_methods/installments")
    suspend fun getInstallmentsOptions(
        @Query("payment_method_id") payment_id: String,
        @Query("amount") amount: Float,
        @Query("issuer.id") issuer_id: String
    ): Response<List<InstallmentOption>>

    @GET("payment_methods/installments")
    suspend fun getInstallmentsOptions(
        @Query("payment_method_id") payment_id: String,
        @Query("amount") amount: Float,
    ): Response<List<InstallmentOption>>
}
