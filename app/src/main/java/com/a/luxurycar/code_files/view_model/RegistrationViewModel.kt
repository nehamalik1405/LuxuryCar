package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.RegistrationRepository

import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class RegistrationViewModel(val repository: RegistrationRepository): BaseViewModel(repository) {

    private val _RegisterResponse: MutableLiveData<Resource<RegistrationResponse>> = MutableLiveData()
    val RegisterResponse: LiveData<Resource<RegistrationResponse>>
        get() = _RegisterResponse

    fun getRegisterResponse(body: RequestBody) = viewModelScope.launch {

        _RegisterResponse.value = Resource.Loading
        _RegisterResponse.value = repository.getRegisterResponse(body)

    }
}