package com.a.luxurycar.code_files.ui.home.model.home


import com.google.gson.annotations.SerializedName

data class Make(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)