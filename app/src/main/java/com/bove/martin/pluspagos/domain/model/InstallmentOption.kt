package com.bove.martin.pluspagos.domain.model


import com.google.gson.annotations.SerializedName

data class InstallmentOption(
    @SerializedName("agreements")
    val agreements: Any,
    @SerializedName("issuer")
    val issuer: Issuer,
    @SerializedName("merchant_account_id")
    val merchantAccountId: Any,
    @SerializedName("payer_costs")
    val payerCosts: List<PayerCost>,
    @SerializedName("payment_method_id")
    val paymentMethodId: String,
    @SerializedName("payment_type_id")
    val paymentTypeId: String,
    @SerializedName("processing_mode")
    val processingMode: String
)