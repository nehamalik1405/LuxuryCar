package com.a.luxurycar.code_files.ui.add_car.model.step_three_listing_plan


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("duration")
    val duration: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    var isSelected:Boolean = false
)