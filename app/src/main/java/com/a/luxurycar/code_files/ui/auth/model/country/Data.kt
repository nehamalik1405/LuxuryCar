package com.a.luxurycar.code_files.ui.auth.model.country


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)