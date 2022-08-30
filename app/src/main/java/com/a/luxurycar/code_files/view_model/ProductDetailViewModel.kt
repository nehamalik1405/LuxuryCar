package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.ProductDetailRepository
import com.a.luxurycar.code_files.ui.home.model.product_detail_response.ProductDetailsResponse
import com.a.luxurycar.code_files.ui.home.model.wishlist_response.CarLikeDislikeResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ProductDetailViewModel(val repository:ProductDetailRepository):BaseViewModel(repository) {

    private val _getProductDetailResponse: MutableLiveData<Resource<ProductDetailsResponse>> = MutableLiveData()
    val getProductDetailResponse: LiveData<Resource<ProductDetailsResponse>>
        get() = _getProductDetailResponse

    fun getProductDetailResponse(id:String) = viewModelScope.launch {
        _getProductDetailResponse.value = Resource.Loading
        _getProductDetailResponse.value = repository.getProductDetailResponse(id)

    }

 private val _getWishListResponse: MutableLiveData<Resource<CarLikeDislikeResponse>> = MutableLiveData()
    val getWishListResponse: LiveData<Resource<CarLikeDislikeResponse>>
        get() = _getWishListResponse

    fun getWishListResponse(requestBody: RequestBody) = viewModelScope.launch {
        _getWishListResponse.value = Resource.Loading
        _getWishListResponse.value = repository.getWishListResponse(requestBody)

    }

}