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
    private val _sendOtpResponse: MutableLiveData<Resource<OtpModel>> = MutableLiveData()
    val sendOtpResponse: LiveData<Resource<OtpModel>>
        get() = _sendOtpResponse

    fun getSendOtpResponse(body: RequestBody) = viewModelScope.launch {

        _sendOtpResponse.value = Resource.Loading
        _sendOtpResponse.value = repository.getSendOtpResponse(body)

    }

    // verify otp viewmodel
    private val _verifyOtpResponse: MutableLiveData<Resource<OtpModel>> = MutableLiveData()
    val verifyOtpResponse: LiveData<Resource<OtpModel>>
        get() = _verifyOtpResponse

    fun getVerifyOtpResponse(body: RequestBody) = viewModelScope.launch {

        _verifyOtpResponse.value = Resource.Loading
        _verifyOtpResponse.value = repository.getVerifyResponse(body)

    }

    // update password  viewmodel
    private val _updatePasswordResponse: MutableLiveData<Resource<OtpModel>> = MutableLiveData()
    val updatePasswordResponse: LiveData<Resource<OtpModel>>
        get() = _updatePasswordResponse

    fun getUpdatePasswordResponse(email:String,new_password:String,confirm_password:String) = viewModelScope.launch {

        _updatePasswordResponse.value = Resource.Loading
        _updatePasswordResponse.value = repository.getUpdatePasswordResponse(email,new_password,confirm_password)

    }
}