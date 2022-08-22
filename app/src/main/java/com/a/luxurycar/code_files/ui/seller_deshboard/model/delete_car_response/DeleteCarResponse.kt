package com.a.luxurycar.code_files.ui.seller_deshboard.model.delete_car_response


import com.google.gson.annotations.SerializedName

data class DeleteCarResponse(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)