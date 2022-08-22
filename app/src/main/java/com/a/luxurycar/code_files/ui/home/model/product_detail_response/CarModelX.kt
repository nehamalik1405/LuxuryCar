package com.a.luxurycar.code_files.ui.home.model.product_detail_response


import com.google.gson.annotations.SerializedName

data class CarModelX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)