package com.bove.martin.pluspagos.domain.model

import com.google.gson.annotations.SerializedName

data class PayerCost(
    @SerializedName("discount_rate")
    val discountRate: Float,
    @SerializedName("installment_amount")
    val installmentAmount: Float,
    @SerializedName("installment_rate")
    val installmentRate: Float,
    @SerializedName("installment_rate_collector")
    val installmentRateCollector: List<String>,
    @SerializedName("installments")
    val installments: Int,
    @SerializedName("labels")
    val labels: List<String>,
    @SerializedName("max_allowed_amount")
    val maxAllowedAmount: Float,
    @SerializedName("min_allowed_amount")
    val minAllowedAmount: Float,
    @SerializedName("payment_method_option_id")
    val paymentMethodOptionId: String,
    @SerializedName("recommended_message")
    val recommendedMessage: String,
    @SerializedName("reimbursement_rate")
    val reimbursementRate: Any,
    @SerializedName("total_amount")
    val totalAmount: Float
)
