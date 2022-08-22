package com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list


import com.google.gson.annotations.SerializedName

data class CarModel(
    @SerializedName("body_type_id")
    val bodyTypeId: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("make_id")
    val makeId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)