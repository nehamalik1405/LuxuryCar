package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.UpdateDetailRepository
import com.a.luxurycar.code_files.ui.auth.model.country.CountryListModel
import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
import com.a.luxurycar.code_files.ui.home.model.update_details.UpdateBuyerProfileImageModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UpdateDetailViewModel(val repository: UpdateDetailRepository):BaseViewModel(repository) {

    //buyer update detail response
    private val _buyerUpdateDetailResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val buyerUpdateDetailResponse: LiveData<Resource<LoginResponse>>
        get() = _buyerUpdateDetailResponse

    fun getBuyerUpdateDetails(firstName:String,
                              lastName:String,
                              email:String,
                              phone:String,
                              password:String,
                              password_confirmation:String,
                              country_id:String,
                              state_id:String,
                              city_id:String) = viewModelScope.launch {
        _buyerUpdateDetailResponse.value = Resource.Loading
        _buyerUpdateDetailResponse.value = repository.getUpdateDetails(firstName,lastName,email,phone,password, password_confirmation, country_id, state_id, city_id)

    }

    //Buyer uppdate image response
    private val _uploadBuyerProfileImage: MutableLiveData<Resource<UpdateBuyerProfileImageModel>> = MutableLiveData()
    val uploadBuyerProfileImage: LiveData<Resource<UpdateBuyerProfileImageModel>>
        get() = _uploadBuyerProfileImage

    fun getUploadBuyerProfileImage(part : MultipartBody.Part) = viewModelScope.launch {
        _uploadBuyerProfileImage.value = Resource.Loading
        _uploadBuyerProfileImage.value = repository.getUploadBuyerProfileImage(part)
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