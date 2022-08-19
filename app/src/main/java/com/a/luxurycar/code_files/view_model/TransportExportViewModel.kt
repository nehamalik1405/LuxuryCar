package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.TransportExportRepository
import com.a.luxurycar.code_files.ui.home.model.contactus.ContactUsResponse
import com.a.luxurycar.code_files.ui.home.model.faqresponse.FAQResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class TransportExportViewModel(val repository: TransportExportRepository):BaseViewModel(repository) {

    private val _faqListResponse: MutableLiveData<Resource<FAQResponse>> = MutableLiveData()
    val faqListResponse: LiveData<Resource<FAQResponse>>
        get() = _faqListResponse

    fun getFaqListResponse() = viewModelScope.launch {
        _faqListResponse.value = Resource.Loading
        _faqListResponse.value = repository.getfaqListResponse()

    }



}