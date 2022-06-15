package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.BuyerRegistrationRepository
import com.a.luxurycar.code_files.ui.auth.model.country.CountryListModel
import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class BuyerRegistrationViewModel(val repository: BuyerRegistrationRepository): BaseViewModel(repository) {

    private val _registerResponse: MutableLiveData<Resource<RegistrationResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<RegistrationResponse>>
        get() = _registerResponse

    fun getRegisterResponse(body: RequestBody) = viewModelScope.launch {

        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.getRegisterResponse(body)

    }

    //get country response
    private val _countryResponse: MutableLiveData<Resource<CountryListModel>> = MutableLiveData()
    val countryResponse: LiveData<Resource<CountryListModel>>
        get() = _countryResponse

    fun getCountryList() = viewModelScope.launch {

        _countryResponse.value = Resource.Loading
        _countryResponse.value = repository.getCountryList()

    }

    //get state response
    private val _stateResponse: MutableLiveData<Resource<CountryListModel>> = MutableLiveData()
    val stateResponse: LiveData<Resource<CountryListModel>>
        get() = _stateResponse

    fun getSateList(state_Id:String) = viewModelScope.launch {
        _stateResponse.value = Resource.Loading
        _stateResponse.value = repository.getStateList(state_Id)

    }


    //get state response
    private val _cityResponse: MutableLiveData<Resource<CountryListModel>> = MutableLiveData()
    val cityResponse: LiveData<Resource<CountryListModel>>
        get() = _cityResponse

    fun getCitiesList(city_Id:String) = viewModelScope.launch {
        _cityResponse.value = Resource.Loading
        _cityResponse.value = repository.getCitiesList(city_Id)

    }


}