package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.CarListRepository
import com.a.luxurycar.code_files.ui.home.model.car_list_response.CarListResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch

class CarListViewModel(val repository: CarListRepository):BaseViewModel(repository){

    private val _getCarListResponse:MutableLiveData<Resource<CarListResponse>> = MutableLiveData()
        val getCarListResponse:LiveData<Resource<CarListResponse>>
       get() = _getCarListResponse

    fun getCarListResponse(id: String) = viewModelScope.launch {
        _getCarListResponse.value = Resource.Loading
        _getCarListResponse.value = repository.getCarListResponse(id)
    }
}