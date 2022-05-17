package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class UpdateDetailRepository(val api:ApiService): BaseRepository() {

    suspend fun getUpdateDetails(firstName:String,lastName:String,email:String,phone:String) = safeApiCall {
        api.getUpdateDetails(firstName,lastName,email,phone)
    }


}