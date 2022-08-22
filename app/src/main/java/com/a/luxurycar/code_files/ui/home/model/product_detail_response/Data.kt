package com.a.luxurycar.code_files.ui.home.model.product_detail_response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("car_ads")
    val carAds: ArrayList<CarAd>,
    @SerializedName("car_images")
    val carImages: ArrayList<CarImage>,
    @SerializedName("details")
    val details: ArrayList<Detail>,
    @SerializedName("related_listings")
    val relatedListings: ArrayList<RelatedListings>
)