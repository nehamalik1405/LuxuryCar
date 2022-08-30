package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.AdvertiseWithUsRepository
import com.a.luxurycar.code_files.ui.home.model.advertiser_response.AdvertiserWithUsResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class AdvertiseWithUsViewModel(val repository:AdvertiseWithUsRepository):BaseViewModel(repository) {


    private val _advertiserEnquiryResponse: MutableLiveData<Resource<AdvertiserWithUsResponse>> = MutableLiveData()
    val advertiserEnquiryResponse: LiveData<Resource<AdvertiserWithUsResponse>>
        get() = _advertiserEnquiryResponse


    fun getAdvertiserEnquiryResponse(body: RequestBody) = viewModelScope.launch {
        _advertiserEnquiryResponse.value = Resource.Loading
        _advertiserEnquiryResponse.value = repository.getAdvertiserEnquiryResponse(body)

    }

}