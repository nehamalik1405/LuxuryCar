package com.a.luxurycar.code_files.ui.add_car.model.sale_person


import com.google.gson.annotations.SerializedName

data class SalePersonModelList(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)