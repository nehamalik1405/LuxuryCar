package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.SellerRepository
import com.a.luxurycar.code_files.ui.auth.model.seller.SellerRegistrationModel
import com.a.luxurycar.code_files.ui.seller_deshboard.UpdateSellerProfileImageModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SellerViewModel(val repository: SellerRepository):BaseViewModel(repository) {

    //seller registration response
    private val _sellerRegisterResponse: MutableLiveData<Resource<SellerRegistrationModel>> = MutableLiveData()
    val sellerRegisterResponse: LiveData<Resource<SellerRegistrationModel>>
        get() = _sellerRegisterResponse

    fun getSellerRegisterResponse(body: RequestBody) = viewModelScope.launch {

        _sellerRegisterResponse.value = Resource.Loading
        _sellerRegisterResponse.value = repository.getSellerRegisterResponse(body)

    }

    //seller update details response

 /*   private val _sellerUpdateDetailResponse: MutableLiveData<Resource<SellerRegistrationModel>> = MutableLiveData()
    val sellerUpdateDetailResponse: LiveData<Resource<SellerRegistrationModel>>
        get() = _sellerUpdateDetailResponse*/
    fun getUpdateSellerDetailResponse(company_name:String,
                          email:String,
                          password:String,
                          password_confirmation:String,
                          phone:String,
                          location:String,
                          description:String)
        = viewModelScope.launch {

     _sellerRegisterResponse.value = Resource.Loading
     _sellerRegisterResponse.value =
        repository.getUpdateSellerResponse(company_name, email, password, password_confirmation, phone, location, description)

        }


   // update image seller
    private val _updateSellerImage : MutableLiveData<Resource<UpdateSellerProfileImageModel>> = MutableLiveData()
    val updateSellerImage: LiveData<Resource<UpdateSellerProfileImageModel>>
        get() = _updateSellerImage
    fun getUpdateSellerImage(part : MultipartBody.Part) = viewModelScope.launch {
        _updateSellerImage.value = Resource.Loading
        _updateSellerImage.value = repository.getUpdateSellerImage(part)

    }

}