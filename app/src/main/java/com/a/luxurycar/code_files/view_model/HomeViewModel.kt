package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.HomeRepository
import com.a.luxurycar.code_files.ui.home.model.HomeResponse

import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch

class HomeViewModel(val repository: HomeRepository): BaseViewModel(repository){

    private val _advertiserSuggestedListResponse: MutableLiveData<Resource<HomeResponse>> = MutableLiveData()
    val advertiserSuggestedListResponse: LiveData<Resource<HomeResponse>>
        get() = _advertiserSuggestedListResponse

    fun getAdvertiserSuggestedListResponse() = viewModelScope.launch {

        _advertiserSuggestedListResponse.value = Resource.Loading
        _advertiserSuggestedListResponse.value =repository.getAdvertiserSuggestedListResponse()

    }

}