package com.a.luxurycar.code_files.ui.add_car.model.step_three_listing_plan


import com.google.gson.annotations.SerializedName

data class AddCarStepThreeListingPlanModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)