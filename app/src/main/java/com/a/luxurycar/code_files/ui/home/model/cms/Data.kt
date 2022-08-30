package com.a.luxurycar.code_files.ui.home.model.cms


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("about-us")
    val aboutUs: AboutUs?,
    @SerializedName("advertise-with-us")
    val advertiseWithUs: AdvertiseWithUs?,
    @SerializedName("contact-us")
    val contactUs: ContactUs?,
    @SerializedName("inspection")
    val inspection: Inspection?,
    @SerializedName("privacy-policy")
    val privacyPolicy: PrivacyPolicy?,
    @SerializedName("sourcing")
    val sourcing: Sourcing?,
    @SerializedName("storage-page")
    val storagePage: StoragePage?,
    @SerializedName("terms-conditions")
    val termsConditions: TermsConditions?,
    @SerializedName("transport-export")
    val transportExport: TransportExport?
)