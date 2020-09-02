package com.bove.martin.pluspagos.data

import com.bove.martin.pluspagos.data.retrofit.MercadoPagoServices
import com.bove.martin.pluspagos.domain.model.CardIssuer
import com.bove.martin.pluspagos.domain.model.InstallmentOption
import com.bove.martin.pluspagos.domain.model.PayerCost
import com.bove.martin.pluspagos.domain.model.Payment
import retrofit2.Response

class MercadoPagoRepository(private val mercadoPagoServices: MercadoPagoServices) {
    var userAmountSelection: Double? = null
    var userPaymentSelection: Payment? = null
    var userCardIssuerSelection: CardIssuer? = null
    var userInstallmentSelection: PayerCost? = null

    suspend fun getPaymentsMethods() : Response<List<Payment>>  {
        return mercadoPagoServices.paymentMethods()
    }

    suspend fun getCardIssuers(id:String): Response<List<CardIssuer>> {
        return mercadoPagoServices.getCardIssuers(id)
    }

    suspend fun getInstallmentsOptions(id: String, amount:Float, issuerId:String): Response<List<InstallmentOption>> {
      return  mercadoPagoServices.getInstallmentsOptions(id, amount, issuerId)
    }

    suspend fun getInstallmentsOptions(id: String, amount:Float): Response<List<InstallmentOption>> {
        return  mercadoPagoServices.getInstallmentsOptions(id, amount)
    }
}