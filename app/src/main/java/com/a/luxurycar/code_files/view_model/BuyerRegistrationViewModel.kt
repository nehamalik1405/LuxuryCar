package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.BuyerRegistrationRepository

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
}