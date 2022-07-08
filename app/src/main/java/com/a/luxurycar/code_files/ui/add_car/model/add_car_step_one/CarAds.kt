package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_one


import com.google.gson.annotations.SerializedName

data class CarAds(
    @SerializedName("body_condition_id")
    val bodyConditionId: String,
    @SerializedName("body_type_id")
    val bodyTypeId: String,
    @SerializedName("car_model_id")
    val carModelId: String,
    @SerializedName("car_year")
    val carYear: String,
    @SerializedName("city_id")
    val cityId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("daily_rent_price")
    val dailyRentPrice: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("exterior_color_id")
    val exteriorColorId: String,
    @SerializedName("fuel_type")
    val fuelType: String,
    @SerializedName("full_service_history")
    val fullServiceHistory: String,
    @SerializedName("horse_power_id")
    val horsePowerId: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("interior_color_id")
    val interiorColorId: String,
    @SerializedName("make_id")
    val makeId: String,
    @SerializedName("mechanical_condition_id")
    val mechanicalConditionId: String,
    @SerializedName("monthly_rent_price")
    val monthlyRentPrice: Any,
    @SerializedName("no_of_cylinders")
    val noOfCylinders: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("regional_specification")
    val regionalSpecification: String,
    @SerializedName("rent")
    val rent: String,
    @SerializedName("run_kms")
    val runKms: String,
    @SerializedName("sales_person_id")
    val salesPersonId: Any,
    @SerializedName("seller_id")
    val sellerId: Int,
    @SerializedName("seller_type")
    val sellerType: String,
    @SerializedName("steering_type")
    val steeringType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("transmission_type")
    val transmissionType: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("vin")
    val vin: String,
    @SerializedName("warranty")
    val warranty: String,
    @SerializedName("weekly_rent_price")
    val weeklyRentPrice: Any
)