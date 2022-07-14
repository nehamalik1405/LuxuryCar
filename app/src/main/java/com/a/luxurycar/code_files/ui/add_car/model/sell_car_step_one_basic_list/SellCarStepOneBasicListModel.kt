package com.a.luxurycar.code_files.ui.add_car.model.sell_car_step_one_basic_list


import com.google.gson.annotations.SerializedName

data class SellCarStepOneBasicListModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)