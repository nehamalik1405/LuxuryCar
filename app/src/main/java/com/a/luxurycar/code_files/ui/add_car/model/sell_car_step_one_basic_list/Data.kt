package com.a.luxurycar.code_files.ui.add_car.model.sell_car_step_one_basic_list


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("body_conditions")
    val bodyConditions: List<BodyCondition>,
    @SerializedName("colors")
    val colors: List<Color>,
    @SerializedName("body_types")
    val body_types: List<BodyType>,
    @SerializedName("fuel_type")
    val fuelType: List<String>,
    @SerializedName("full_service_history")
    val fullServiceHistory: List<String>,
    @SerializedName("horse_power")
    val horsePower: List<HorsePower>,
    @SerializedName("mechanical_conditions")
    val mechanicalConditions: List<MechanicalCondition>,
    @SerializedName("number_of_cylinders")
    val numberOfCylinders: List<String>,
    @SerializedName("regional_specifications")
    val regionalSpecifications: List<String>,
    @SerializedName("seller_type")
    val sellerType: List<String>,
    @SerializedName("steering_side")
    val steeringSide: List<String>,
    @SerializedName("transmission_types")
    val transmissionTypes: List<String>,
    @SerializedName("warranty")
    val warranty: List<String>
)