package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("body_condition_id")
    val bodyConditionId: Int,
    @SerializedName("body_type_id")
    val bodyTypeId: Int,
    @SerializedName("car_company_id")
    val carCompanyId: Any,
    @SerializedName("car_model_id")
    val carModelId: Int,
    @SerializedName("car_year")
    val carYear: String,
    @SerializedName("city_id")
    val cityId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("daily_rent_price")
    val dailyRentPrice: String,
    @SerializedName("description")
    val description: Any,
    @SerializedName("door_specification")
    val doorSpecification: Any,
    @SerializedName("exterior_color_id")
    val exteriorColorId: Int,
    @SerializedName("fuel_type")
    val fuelType: String,
    @SerializedName("full_service_history")
    val fullServiceHistory: String,
    @SerializedName("horse_power_id")
    val horsePowerId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("interior_color_id")
    val interiorColorId: Int,
    @SerializedName("is_premium")
    val isPremium: String,
    @SerializedName("location_url")
    val locationUrl: Any,
    @SerializedName("make_id")
    val makeId: Int,
    @SerializedName("mechanical_condition_id")
    val mechanicalConditionId: Int,
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
    val salesPersonId: Int,
    @SerializedName("seller_id")
    val sellerId: Int,
    @SerializedName("seller_type")
    val sellerType: String,
    @SerializedName("state_id")
    val stateId: Any,
    @SerializedName("status")
    val status: String,
    @SerializedName("steering_type")
    val steeringType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("360_tour_url")
    val tourUrl: Any,
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