package com.a.luxurycar.code_files.ui.home.model.search_ads_response


import com.google.gson.annotations.SerializedName

data class Make(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?
)