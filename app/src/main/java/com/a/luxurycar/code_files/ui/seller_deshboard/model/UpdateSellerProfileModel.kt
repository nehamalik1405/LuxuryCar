package com.a.luxurycar.code_files.ui.seller_deshboard.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class UpdateSellerProfileModel {

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("User")
        @Expose
        var user: User? = null

        inner class User {
            @SerializedName("id")
            @Expose
            var id: Int? = null

            @SerializedName("role")
            @Expose
            var role: String? = null

            @SerializedName("company_name")
            @Expose
            var companyName: String? = null

            @SerializedName("email")
            @Expose
            var email: String? = null

            @SerializedName("phone")
            @Expose
            var phone: String? = null

            @SerializedName("location")
            @Expose
            var location: String? = null

            @SerializedName("description")
            @Expose
            var description: String? = null

            @SerializedName("image")
            @Expose
            var image: String? = null

            @SerializedName("device_id")
            @Expose
            var deviceId: String? = null

            @SerializedName("device_token")
            @Expose
            var deviceToken: String? = null

            @SerializedName("device_type")
            @Expose
            var deviceType: String? = null
        }
    }
}