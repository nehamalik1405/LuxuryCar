package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.ForgotPasswordRepository
import com.a.luxurycar.code_files.ui.auth.model.forgot_password.OtpModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ForgotPasswordViewModel(val repository: ForgotPasswordRepository): BaseViewModel(repository) {

    // send otp viewmodel
    private val _SendOtpResponse: MutableLiveData<Resource<OtpModel>> = MutableLiveData()
    val SendOtpResponse: LiveData<Resource<OtpModel>>
        get() = _SendOtpResponse

    fun getSendOtpResponse(body: RequestBody) = viewModelScope.launch {

        _SendOtpResponse.value = Resource.Loading
        _SendOtpResponse.value = repository.getSendOtpResponse(body)

    }

    // verify otp viewmodel
    private val _VerifyOtpResponse: MutableLiveData<Resource<OtpModel>> = MutableLiveData()
    val VerifyOtpResponse: LiveData<Resource<OtpModel>>
        get() = _VerifyOtpResponse

    fun getVerifyOtpResponse(body: RequestBody) = viewModelScope.launch {

        _VerifyOtpResponse.value = Resource.Loading
        _VerifyOtpResponse.value = repository.getVerifyResponse(body)

    }

    // update password  viewmodel
    private val _UpdatePasswordResponse: MutableLiveData<Resource<OtpModel>> = MutableLiveData()
    val UpdatePasswordResponse: LiveData<Resource<OtpModel>>
        get() = _UpdatePasswordResponse

    fun getUpdatePasswordResponse(email:String,new_password:String,confirm_password:String) = viewModelScope.launch {

        _UpdatePasswordResponse.value = Resource.Loading
        _UpdatePasswordResponse.value = repository.getUpdatePasswordResponse(email,new_password,confirm_password)

    }
}