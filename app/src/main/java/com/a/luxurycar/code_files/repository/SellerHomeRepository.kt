package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService

class SellerHomeRepository(val api:ApiService):BaseRepository() {

    suspend fun getSellerDetailDasboardResponse() = safeApiCall {
        api.getSellerDetailDasboardResponse()
    }
    suspend fun getFindGaragesResponse() = safeApiCall {
        api.getFindGaragesResponse()
    }

    suspend fun getSellerForSaleListResponse(id:String) = safeApiCall {
        api.getSellerForSaleListResponse(id)
    }
    suspend fun getSellerForRenListResponse(id:String) = safeApiCall {
        api.getSellerForRenListResponse(id)
    }


}