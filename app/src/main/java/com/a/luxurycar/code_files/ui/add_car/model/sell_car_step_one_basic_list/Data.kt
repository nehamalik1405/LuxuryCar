package com.a.luxurycar.code_files.ui.add_car.model.sell_car_step_one_basic_list


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("body_conditions")
    val bodyConditions: List<BodyCondition>,
    @SerializedName("body_types")
    val bodyTypes: List<BodyType>,
    @SerializedName("colors")
    val colors: List<Color>,
    @SerializedName("delivery_charges")
    val deliveryCharges: List<DeliveryCharge>,
    @SerializedName("fuel_type")
    val fuelType: List<FuelType>,
    @SerializedName("full_service_history")
    val fullServiceHistory: List<FullServiceHistory>,
    @SerializedName("horse_power")
    val horsePower: List<HorsePower>,
    @SerializedName("mechanical_conditions")
    val mechanicalConditions: List<MechanicalCondition>,
    @SerializedName("number_of_cylinders")
    val numberOfCylinders: List<NumberOfCylinder>,
    @SerializedName("payment_method")
    val paymentMethod: List<PaymentMethod>,
    @SerializedName("regional_specifications")
    val regionalSpecifications: List<RegionalSpecification>,
    @SerializedName("seller_type")
    val sellerType: List<SellerType>,
    @SerializedName("steering_side")
    val steeringSide: List<SteeringSide>,
    @SerializedName("transmission_types")
    val transmissionTypes: List<TransmissionType>,
    @SerializedName("warranty")
    val warranty: List<Warranty>
)