package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class AddCarStepThreeRepository(val api:ApiService):BaseRepository() {

    suspend fun getAddSellerListingPlan()= safeApiCall {
        api.getAddSellerListingPlan()
    }
    suspend fun getAddSellerPlan(body: RequestBody) = safeApiCall {
        api.getAddSellerPlan(body)
    }

}