package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.HomeRepository

import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.AdvertiserSuggestedListModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch

class HomeViewModel(val repository: HomeRepository): BaseViewModel(repository){

    private val _AdvertiserSuggestedListResponse: MutableLiveData<Resource<AdvertiserSuggestedListModel>> = MutableLiveData()
    val AdvertiserSuggestedListResponse: LiveData<Resource<AdvertiserSuggestedListModel>>
        get() = _AdvertiserSuggestedListResponse

    fun getAdvertiserSuggestedListResponse() = viewModelScope.launch {

        _AdvertiserSuggestedListResponse.value = Resource.Loading
        _AdvertiserSuggestedListResponse.value =repository.getAdvertiserSuggestedListResponse()

    }

}