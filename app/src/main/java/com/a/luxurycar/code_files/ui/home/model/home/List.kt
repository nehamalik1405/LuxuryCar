package com.a.luxurycar.code_files.ui.home.model.home


import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("car_models")
    val carModels: ArrayList<CarModel> = arrayListOf(),
    @SerializedName("colors")
    val colors: ArrayList<Color> = arrayListOf(),
    @SerializedName("deposit")
    val deposit: ArrayList<Deposit> = arrayListOf(),
    @SerializedName("kms_included")
    val kmsIncluded: ArrayList<KmsIncluded> = arrayListOf(),
    @SerializedName("makes")
    val makes: ArrayList<Make> = arrayListOf(),
    @SerializedName("payment_method")
    val paymentMethod: ArrayList<PaymentMethod> = arrayListOf(),
    @SerializedName("price_range")
    val priceRange: ArrayList<PriceRange> = arrayListOf(),
    @SerializedName("transmission_types")
    val transmissionTypes: ArrayList<TransmissionType> = arrayListOf()
)