package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.SellerHomeRepository
import com.a.luxurycar.code_files.ui.seller_deshboard.model.find_garages.FindGaragesResponse
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list.SellerCarListResponse
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_dashboard.SellerDetailDashboardResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch

class SellerHomeViewModel(val repository:SellerHomeRepository):BaseViewModel(repository) {

    private val _sellerDatailDasboardResponse: MutableLiveData<Resource<SellerDetailDashboardResponse>> = MutableLiveData()
    val sellerDatailDasboardResponse: LiveData<Resource<SellerDetailDashboardResponse>>
        get() = _sellerDatailDasboardResponse

    fun getSellerDetailDasboardResponse() = viewModelScope.launch {
        _sellerDatailDasboardResponse.value = Resource.Loading
        _sellerDatailDasboardResponse.value = repository.getSellerDetailDasboardResponse()
    }


    private val _getFindGaragesResponse: MutableLiveData<Resource<FindGaragesResponse>> = MutableLiveData()
    val getFindGaragesResponse: LiveData<Resource<FindGaragesResponse>>
        get() = _getFindGaragesResponse

    fun getFindGaragesResponse() = viewModelScope.launch {
        _getFindGaragesResponse.value = Resource.Loading
        _getFindGaragesResponse.value = repository.getFindGaragesResponse()

    }

    private val _getSellerForSaleListResponse: MutableLiveData<Resource<SellerCarListResponse>> = MutableLiveData()
    val getSellerForSaleListResponse: LiveData<Resource<SellerCarListResponse>>
        get() = _getSellerForSaleListResponse

    fun getSellerForSaleListResponse(id:String) = viewModelScope.launch {
        _getSellerForSaleListResponse.value = Resource.Loading
        _getSellerForSaleListResponse.value = repository.getSellerForSaleListResponse(id)

    }

    private val _getSellerForRenListResponse: MutableLiveData<Resource<SellerCarListResponse>> = MutableLiveData()
    val getSellerForRenListResponse: LiveData<Resource<SellerCarListResponse>>
        get() = _getSellerForRenListResponse

    fun getSellerRenListResponse(id:String) = viewModelScope.launch {
        _getSellerForRenListResponse.value = Resource.Loading
        _getSellerForRenListResponse.value = repository.getSellerForRenListResponse(id)

    }



}