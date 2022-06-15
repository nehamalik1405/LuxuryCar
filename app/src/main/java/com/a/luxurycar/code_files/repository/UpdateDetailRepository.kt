package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UpdateDetailRepository(val api:ApiService): BaseRepository() {

    suspend fun getUpdateDetails(firstName:String,
                                 lastName:String,
                                 email:String,
                                 phone:String,
                                 country_id:String,state_id:String,city_id:String) = safeApiCall {
        api.getUpdateDetails(firstName,lastName,email,phone,country_id, state_id, city_id)
    }

    suspend fun getUploadBuyerProfileImage(
        part : MultipartBody.Part
    ) = safeApiCall {
        api.getUploadBuyerProfileImage(part)
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