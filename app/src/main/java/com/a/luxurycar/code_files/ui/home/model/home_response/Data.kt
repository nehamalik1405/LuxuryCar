package com.a.luxurycar.code_files.ui.home.model.home_response


import com.a.luxurycar.code_files.ui.home.model.home.SearchFilterList
import com.google.gson.annotations.SerializedName

data class Data(


    @SerializedName("banners") var banners: Banners? = Banners(),
    @SerializedName("suggestedCars") var suggestedCars: SuggestedCars? = SuggestedCars(),
    @SerializedName("premiumCars") var premiumCars: PremiumCars? = PremiumCars(),
    @SerializedName("featuredCars") var featuredCars: FeaturedCars? = FeaturedCars(),
    @SerializedName("platinumPartners") var platinumPartners: PlatinumPartners? = PlatinumPartners(),
    @SerializedName("search_filters_list") var search_filters_list: SearchFilterList=SearchFilterList()



)
