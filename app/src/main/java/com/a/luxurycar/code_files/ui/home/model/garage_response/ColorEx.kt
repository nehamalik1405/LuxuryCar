package com.a.luxurycar.code_files.ui.home.model.garage_response


import com.google.gson.annotations.SerializedName

data class ColorEx(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)