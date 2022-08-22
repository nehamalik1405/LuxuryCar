package com.a.luxurycar.code_files.ui.seller_deshboard.model.find_garages


import com.google.gson.annotations.SerializedName

data class FindGaragesResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)