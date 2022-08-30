package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.SaurceMyCarRepository
import com.a.luxurycar.code_files.ui.add_car.model.car_model.CarModelListModel
import com.a.luxurycar.code_files.ui.add_car.model.make_list_model.MakeListModel
import com.a.luxurycar.code_files.ui.home.model.saurce_my_car_response.SaurceMyCarResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class SaurceMyCarViewModel(val repository:SaurceMyCarRepository):BaseViewModel(repository) {

    private val _makeListResponse: MutableLiveData<Resource<MakeListModel>> = MutableLiveData()
    val makeListResponse: LiveData<Resource<MakeListModel>>
        get() = _makeListResponse

    fun getMakeListResponse() = viewModelScope.launch {

        _makeListResponse.value = Resource.Loading
        _makeListResponse.value = repository.getMakeListResponse()

    }


    private val _carModelResponse: MutableLiveData<Resource<CarModelListModel>> = MutableLiveData()
    val carModelResponse: LiveData<Resource<CarModelListModel>>
        get() = _carModelResponse

    fun  getCarModelResponse(makeId: String) = viewModelScope.launch {
        _carModelResponse.value = Resource.Loading
        _carModelResponse.value = repository. getCarModelResponse(makeId)
    }


    private val _SaurceMyCarResponse: MutableLiveData<Resource<SaurceMyCarResponse>> = MutableLiveData()
    val saurceMyCarResponse: LiveData<Resource<SaurceMyCarResponse>>
        get() = _SaurceMyCarResponse

    fun getSaurceMyCarResponse(body: RequestBody) = viewModelScope.launch {
        _SaurceMyCarResponse.value = Resource.Loading
        _SaurceMyCarResponse.value = repository.getSaurceMyCarResponse(body)
    }

}