package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService

class ProductDetailRepository(val api:ApiService):BaseRepository() {

    suspend fun getProductDetailResponse(id:String) = safeApiCall {
        api.getProductDetailResponse(id)
    }
}