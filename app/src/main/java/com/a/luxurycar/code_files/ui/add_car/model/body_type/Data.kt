package com.a.luxurycar.code_files.ui.add_car.model.body_type


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)