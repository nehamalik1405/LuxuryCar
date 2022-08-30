package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.GarageDetailsRepository
import com.a.luxurycar.code_files.ui.home.model.garage_response.GarageDetailResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch

class GarageDetailsViewModel(val repository: GarageDetailsRepository):BaseViewModel(repository) {

    private val _getGaragesDetailsResponse: MutableLiveData<Resource<GarageDetailResponse>> = MutableLiveData()
    val getGaragesDetailsResponse: LiveData<Resource<GarageDetailResponse>>
        get() = _getGaragesDetailsResponse

    fun getGaragesDetailsResponse(id:String) = viewModelScope.launch {
        _getGaragesDetailsResponse.value = Resource.Loading
        _getGaragesDetailsResponse.value = repository.getGaragesDetailsResponse(id)

    }


}