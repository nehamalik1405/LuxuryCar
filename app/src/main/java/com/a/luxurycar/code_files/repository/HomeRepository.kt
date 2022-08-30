package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class HomeRepository(val api: ApiService):BaseRepository() {

    suspend fun getAdvertiserSuggestedListResponse() = safeApiCall {
        api.getAdvertiserSuggestedListResponse()
    }
    suspend fun getsearchAdsResponseResponse(requestBody: RequestBody) = safeApiCall {
        api.getsearchAdsResponseResponse(requestBody)
    }

    suspend fun getCarModelResponse(makeId: String) = safeApiCall {
        api.getCarModelResponse(makeId)
    }

}