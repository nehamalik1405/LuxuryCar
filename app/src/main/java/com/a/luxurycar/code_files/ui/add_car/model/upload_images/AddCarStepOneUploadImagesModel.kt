package com.a.luxurycar.code_files.ui.add_car.model.upload_images


import com.google.gson.annotations.SerializedName

data class AddCarStepOneUploadImagesModel(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)