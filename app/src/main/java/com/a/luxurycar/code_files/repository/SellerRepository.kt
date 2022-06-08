package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SellerRepository(val api:ApiService):BaseRepository() {

    suspend fun getSellerRegisterResponse(body: RequestBody) = safeApiCall {
        api.getSellerRegisterResponse(body)
    }
    suspend fun getUpdateSellerImage(
        part : MultipartBody.Part
    ) = safeApiCall {
        api.updateSellerImage(part)
    }

}