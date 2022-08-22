package com.a.luxurycar.code_files.ui.home.model.home_response

import com.a.luxurycar.code_files.ui.home.model.CarImages
import com.a.luxurycar.code_files.ui.home.model.CarModel
import com.a.luxurycar.code_files.ui.home.model.Make
import com.google.gson.annotations.SerializedName

data class Listt(

    @SerializedName("id"                      ) var id                    : Int?                 = null,
    @SerializedName("seller_id"               ) var sellerId              : Int?                 = null,
    @SerializedName("seller_type"             ) var sellerType            : String?              = null,
    @SerializedName("title"                   ) var title                 : String?              = null,
    @SerializedName("vin"                     ) var vin                   : String?              = null,
    @SerializedName("make_id"                 ) var makeId                : Int?                 = null,
    @SerializedName("car_model_id"            ) var carModelId            : Int?                 = null,
    @SerializedName("car_year"                ) var carYear               : String?              = null,
    @SerializedName("car_company_id"          ) var carCompanyId          : String?              = null,
    @SerializedName("price"                   ) var price                 : String?              = null,
    @SerializedName("exterior_color_id"       ) var exteriorColorId       : Int?                 = null,
    @SerializedName("interior_color_id"       ) var interiorColorId       : String?              = null,
    @SerializedName("run_kms"                 ) var runKms                : String?              = null,
    @SerializedName("state_id"                ) var stateId               : String?              = null,
    @SerializedName("city_id"                 ) var cityId                : Int?                 = null,
    @SerializedName("360_tour_url"            ) var TourUrl            : String?              = null,
    @SerializedName("location_url"            ) var locationUrl           : String?              = null,
    @SerializedName("door_specification"      ) var doorSpecification     : String?              = null,
    @SerializedName("regional_specification"  ) var regionalSpecification : String?              = null,
    @SerializedName("description"             ) var description           : String?              = null,
    @SerializedName("is_premium"              ) var isPremium             : String?              = null,
    @SerializedName("transmission_type"       ) var transmissionType      : String?              = null,
    @SerializedName("rent"                    ) var rent                  : String?              = null,
    @SerializedName("daily_rent_price"        ) var dailyRentPrice        : String?              = null,
    @SerializedName("weekly_rent_price"       ) var weeklyRentPrice       : String?              = null,
    @SerializedName("monthly_rent_price"      ) var monthlyRentPrice      : String?              = null,
    @SerializedName("deposit"                 ) var deposit               : String?              = null,
    @SerializedName("payment_method"          ) var paymentMethod         : String?              = null,
    @SerializedName("delivery_charges"        ) var deliveryCharges       : String?              = null,
    @SerializedName("horse_power_id"          ) var horsePowerId          : Int?                 = null,
    @SerializedName("full_service_history"    ) var fullServiceHistory    : String?              = null,
    @SerializedName("no_of_cylinders"         ) var noOfCylinders         : String?              = null,
    @SerializedName("fuel_type"               ) var fuelType              : String?              = null,
    @SerializedName("body_type_id"            ) var bodyTypeId            : String?              = null,
    @SerializedName("body_condition_id"       ) var bodyConditionId       : String?              = null,
    @SerializedName("mechanical_condition_id" ) var mechanicalConditionId : String?              = null,
    @SerializedName("steering_type"           ) var steeringType          : String?              = null,
    @SerializedName("warranty"                ) var warranty              : String?              = null,
    @SerializedName("sales_person_id"         ) var salesPersonId         : String?              = null,
    @SerializedName("status"                  ) var status                : String?              = null,
    @SerializedName("created_at"              ) var createdAt             : String?              = null,
    @SerializedName("updated_at"              ) var updatedAt             : String?              = null,
    @SerializedName("car_images"              ) var carImages             : ArrayList<CarImages> = arrayListOf(),
    @SerializedName("make"                    ) var make                  : Make?                = Make(),
    @SerializedName("car_model"               ) var carModel              : CarModel?            = CarModel()
)
