package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_three_plan_response


import com.google.gson.annotations.SerializedName

data class ListingDetails(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("listing_plan_id")
    val listingPlanId: String,
    @SerializedName("plan_expiry")
    val planExpiry: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)