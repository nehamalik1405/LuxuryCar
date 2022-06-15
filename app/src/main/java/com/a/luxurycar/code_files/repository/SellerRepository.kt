package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SellerRepository(val api:ApiService):BaseRepository() {

    suspend fun getSellerRegisterResponse(body: RequestBody) = safeApiCall {
        api.getSellerRegisterResponse(body)
    }

    suspend fun getUpdateSellerResponse(company_name:String,
                                  email:String,
                                  password:String,
                                  password_confirmation:String,
                                  phone:String,
                                  location:String,
                                  description:String) = safeApiCall{
        api.getUpdateSellerResponse(company_name,email,password,password_confirmation,phone,location,description)
    }

    suspend fun getUpdateSellerImage(
        part : MultipartBody.Part
    ) = safeApiCall {
        api.updateSellerImage(part)
    }

}