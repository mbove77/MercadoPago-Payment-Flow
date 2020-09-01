package com.bove.martin.pluspagos.domain.model


import com.google.gson.annotations.SerializedName

data class CardIssuer(
    @SerializedName("id")
    val id: String,
    @SerializedName("merchant_account_id")
    val merchantAccountId: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("processing_mode")
    val processingMode: String,
    @SerializedName("secure_thumbnail")
    val secureThumbnail: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)