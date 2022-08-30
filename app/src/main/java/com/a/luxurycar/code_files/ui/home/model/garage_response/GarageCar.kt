package com.a.luxurycar.code_files.ui.home.model.garage_response


import com.google.gson.annotations.SerializedName

data class GarageCar(
    @SerializedName("body_condition")
    val bodyCondition: BodyCondition?,
    @SerializedName("body_condition_id")
    val bodyConditionId: Int?,
    @SerializedName("body_type")
    val bodyType: Any?,
    @SerializedName("body_type_id")
    val bodyTypeId: Any?,
    @SerializedName("car_company_id")
    val carCompanyId: Any?,
    @SerializedName("car_images")
    val carImages: List<CarImage?>?,
    @SerializedName("car_model")
    val carModel: CarModel?,
    @SerializedName("car_model_id")
    val carModelId: Int?,
    @SerializedName("car_year")
    val carYear: String?,
    @SerializedName("city")
    val city: City?,
    @SerializedName("city_id")
    val cityId: Int?,
    @SerializedName("color_ex")
    val colorEx: ColorEx?,
    @SerializedName("color_in")
    val colorIn: ColorIn?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("daily_rent_price")
    val dailyRentPrice: String?,
    @SerializedName("delivery_charges")
    val deliveryCharges: String?,
    @SerializedName("deposit")
    val deposit: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("door_specification")
    val doorSpecification: Any?,
    @SerializedName("exterior_color_id")
    val exteriorColorId: Int?,
    @SerializedName("fuel_type")
    val fuelType: String?,
    @SerializedName("full_service_history")
    val fullServiceHistory: String?,
    @SerializedName("horse_power")
    val horsePower: HorsePower?,
    @SerializedName("horse_power_id")
    val horsePowerId: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("interior_color_id")
    val interiorColorId: Int?,
    @SerializedName("is_display")
    val isDisplay: String?,
    @SerializedName("is_premium")
    val isPremium: String?,
    @SerializedName("location_url")
    val locationUrl: String?,
    @SerializedName("make")
    val make: Make?,
    @SerializedName("make_id")
    val makeId: Int?,
    @SerializedName("mechanical_condition")
    val mechanicalCondition: MechanicalCondition?,
    @SerializedName("mechanical_condition_id")
    val mechanicalConditionId: Int?,
    @SerializedName("monthly_rent_price")
    val monthlyRentPrice: String?,
    @SerializedName("no_of_cylinders")
    val noOfCylinders: String?,
    @SerializedName("payment_method")
    val paymentMethod: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("regional_specification")
    val regionalSpecification: String?,
    @SerializedName("rent")
    val rent: String?,
    @SerializedName("run_kms")
    val runKms: String?,
    @SerializedName("sales_person_id")
    val salesPersonId: Int?,
    @SerializedName("seller_id")
    val sellerId: Int?,
    @SerializedName("seller_type")
    val sellerType: String?,
    @SerializedName("state_id")
    val stateId: Any?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("steering_type")
    val steeringType: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("360_tour_url")
    val tourUrl: Any?,
    @SerializedName("transmission_type")
    val transmissionType: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("vin")
    val vin: Any?,
    @SerializedName("warranty")
    val warranty: String?,
    @SerializedName("weekly_rent_price")
    val weeklyRentPrice: String?
)