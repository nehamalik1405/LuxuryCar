package com.a.luxurycar.code_files.ui.home.model.home_response

import com.google.gson.annotations.SerializedName

data class PlatinumPartnersList(
    @SerializedName("id"                ) var id              : Int?             = null,
    @SerializedName("company_name"      ) var companyName     : String?          = null,
    @SerializedName("image"             ) var image           : String?          = null,
    @SerializedName("user_listing_plan" ) var userListingPlan : UserListingPlan? = UserListingPlan()
)
