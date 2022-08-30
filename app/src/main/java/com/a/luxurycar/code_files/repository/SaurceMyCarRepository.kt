package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class SaurceMyCarRepository(val api:ApiService):BaseRepository() {

    suspend fun getMakeListResponse()= safeApiCall {
        api.getMakeListResponse()
    }
    suspend fun getCarModelResponse(makeId: String) = safeApiCall {
        api.getCarModelResponse(makeId)
    }

    suspend fun getSaurceMyCarResponse(body: RequestBody) = safeApiCall {
        api.getSaurceMyCarResponse(body)
    }
}