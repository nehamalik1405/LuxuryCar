package com.a.luxurycar.code_files.ui.add_car.model.cities_list


import com.google.gson.annotations.SerializedName

data class CitiesListModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)

