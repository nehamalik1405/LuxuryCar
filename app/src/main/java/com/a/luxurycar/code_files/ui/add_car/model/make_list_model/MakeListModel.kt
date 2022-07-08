package com.a.luxurycar.code_files.ui.add_car.model.make_list_model


import com.google.gson.annotations.SerializedName

data class MakeListModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)