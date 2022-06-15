package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class BuyerRegistrationRepository(val api: ApiService): BaseRepository() {

    suspend fun getRegisterResponse(body: RequestBody) = safeApiCall {
        api.getRegisterResponse(body)
    }
    suspend fun getCountryList() = safeApiCall {
        api.getCountryList()
    }
    suspend fun getStateList(state_Id:String) = safeApiCall {
        api.getStateList(state_Id)
    }
    suspend fun getCitiesList(city_Id:String) = safeApiCall {
        api.getCitiesList(city_Id)
    }
}