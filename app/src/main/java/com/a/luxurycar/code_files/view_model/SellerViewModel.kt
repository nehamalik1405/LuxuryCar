package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.SellerRepository
import com.a.luxurycar.code_files.ui.auth.model.UploadSellerImage
import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SellerViewModel(val repository: SellerRepository):BaseViewModel(repository) {

    private val _sellerRegisterResponse: MutableLiveData<Resource<RegistrationResponse>> = MutableLiveData()
    val sellerRegisterResponse: LiveData<Resource<RegistrationResponse>>
        get() = _sellerRegisterResponse

    fun getSellerRegisterResponse(body: RequestBody) = viewModelScope.launch {

        _sellerRegisterResponse.value = Resource.Loading
        _sellerRegisterResponse.value = repository.getSellerRegisterResponse(body)

    }


    private val _updateSellerImage : MutableLiveData<Resource<UploadSellerImage>> = MutableLiveData()
    val updateSellerImage: LiveData<Resource<UploadSellerImage>>
        get() = _updateSellerImage
    fun getUpdateSellerImage(part : MultipartBody.Part) = viewModelScope.launch {
        _updateSellerImage.value = Resource.Loading
        _updateSellerImage.value = repository.getUpdateSellerImage(part)

    }

}