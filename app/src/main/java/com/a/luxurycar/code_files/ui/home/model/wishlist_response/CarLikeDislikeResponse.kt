package com.a.luxurycar.code_files.ui.home.model.wishlist_response


import com.google.gson.annotations.SerializedName

data class CarLikeDislikeResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)