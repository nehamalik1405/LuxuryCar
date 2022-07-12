package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_three_plan_response


import com.google.gson.annotations.SerializedName

data class AddCarStepThreeSelectedPlanResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)