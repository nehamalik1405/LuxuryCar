package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class ProductDetailRepository(val api:ApiService):BaseRepository() {

    suspend fun getProductDetailResponse(id:String) = safeApiCall {
        api.getProductDetailResponse(id)
    }
    suspend fun getWishListResponse(requestBody: RequestBody) = safeApiCall {
        api.getWishListResponse(requestBody)
    }

}