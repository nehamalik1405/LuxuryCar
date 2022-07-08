package com.a.luxurycar.code_files.ui.add_car.model.body_type


import com.google.gson.annotations.SerializedName

data class BodyTypeListModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)