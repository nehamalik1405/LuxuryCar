package com.a.luxurycar.code_files.ui.home.model.home


import com.google.gson.annotations.SerializedName

data class SearchFilterList(
    @SerializedName("list")
    val list: Car=Car(),
    @SerializedName("message")
    val message: String="",
    @SerializedName("status")
    val status: Int=0
)