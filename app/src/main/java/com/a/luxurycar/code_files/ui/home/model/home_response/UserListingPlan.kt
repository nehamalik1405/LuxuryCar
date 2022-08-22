package com.a.luxurycar.code_files.ui.home.model.home_response

import com.google.gson.annotations.SerializedName

data class UserListingPlan(
    @SerializedName("id"              ) var id            : Int?         = null,
    @SerializedName("user_id"         ) var userId        : Int?         = null,
    @SerializedName("listing_plan_id" ) var listingPlanId : Int?         = null,
    @SerializedName("plan_expiry"     ) var planExpiry    : String?      = null,
    @SerializedName("status"          ) var status        : String?      = null,
    @SerializedName("created_at"      ) var createdAt     : String?      = null,
    @SerializedName("updated_at"      ) var updatedAt     : String?      = null,
    @SerializedName("listing_plan"    ) var listingPlan   : ListingPlan? = ListingPlan()
)