package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.NewEnquiryFormRepository
import com.a.luxurycar.code_files.ui.home.model.contactus.ContactUsResponse
import com.a.luxurycar.code_files.ui.home.model.inspectinginquery.InspectingEnquiryResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class NewEnquiryFormViewModel(val repository : NewEnquiryFormRepository):BaseViewModel(repository) {


    private val _inspectingEnquiryResponse: MutableLiveData<Resource<InspectingEnquiryResponse>> = MutableLiveData()
    val inspectingEnquiryResponse: LiveData<Resource<InspectingEnquiryResponse>>
        get() = _inspectingEnquiryResponse

    fun getInspectingResponse(body: RequestBody) = viewModelScope.launch {
        _inspectingEnquiryResponse.value = Resource.Loading
        _inspectingEnquiryResponse.value = repository.getinspectingEnquiryResponse(body)

    }




}