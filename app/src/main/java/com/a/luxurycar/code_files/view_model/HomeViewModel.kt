package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.HomeRepository
import com.a.luxurycar.code_files.ui.home.model.HomeResponse
import com.a.luxurycar.code_files.ui.home.model.search_ads_response.SearchAdsResponse

import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class HomeViewModel(val repository: HomeRepository): BaseViewModel(repository){

    private val _advertiserSuggestedListResponse: MutableLiveData<Resource<HomeResponse>> = MutableLiveData()
    val advertiserSuggestedListResponse: LiveData<Resource<HomeResponse>>
        get() = _advertiserSuggestedListResponse

    fun getAdvertiserSuggestedListResponse() = viewModelScope.launch {

        _advertiserSuggestedListResponse.value = Resource.Loading
        _advertiserSuggestedListResponse.value =repository.getAdvertiserSuggestedListResponse()

    }

    private val _searchAdsResponseResponse: MutableLiveData<Resource<SearchAdsResponse>> = MutableLiveData()
    val searchAdsResponseResponse: LiveData<Resource<SearchAdsResponse>>
        get() = _searchAdsResponseResponse

    fun getsearchAdsResponseResponse(requestBody: RequestBody) = viewModelScope.launch {

        _searchAdsResponseResponse.value = Resource.Loading
        _searchAdsResponseResponse.value =repository.getsearchAdsResponseResponse(requestBody)

    }

}