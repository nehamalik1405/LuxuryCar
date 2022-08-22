package com.a.luxurycar.code_files.ui.home.model.product_detail_response


import com.google.gson.annotations.SerializedName

data class MakeX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)