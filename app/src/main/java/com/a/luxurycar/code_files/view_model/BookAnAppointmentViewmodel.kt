package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.BookAnAppointmentRepository
import com.a.luxurycar.code_files.ui.add_car.model.car_model.CarModelListModel
import com.a.luxurycar.code_files.ui.add_car.model.make_list_model.MakeListModel
import com.a.luxurycar.code_files.ui.home.model.appointmentenquiry.BookAppointmentEnquiryResponse
import com.a.luxurycar.code_files.ui.home.model.inspectinginquery.InspectingEnquiryResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class BookAnAppointmentViewmodel(val repository:BookAnAppointmentRepository):BaseViewModel(repository) {


    private val _makeListResponse: MutableLiveData<Resource<MakeListModel>> = MutableLiveData()
    val makeListResponse: LiveData<Resource<MakeListModel>>
        get() = _makeListResponse

    fun getMakeListResponse() = viewModelScope.launch {

        _makeListResponse.value = Resource.Loading
        _makeListResponse.value = repository.getMakeListResponse()

    }


    private val _carModelResponse: MutableLiveData<Resource<CarModelListModel>> = MutableLiveData()
    val carModelResponse: LiveData<Resource<CarModelListModel>>
        get() = _carModelResponse

    fun  getCarModelResponse(makeId: String) = viewModelScope.launch {
        _carModelResponse.value = Resource.Loading
        _carModelResponse.value = repository. getCarModelResponse(makeId)
    }


    private val _appointmentEnquiryResponse: MutableLiveData<Resource<BookAppointmentEnquiryResponse>> = MutableLiveData()
    val appointmentEnquiryResponse: LiveData<Resource<BookAppointmentEnquiryResponse>>
        get() = _appointmentEnquiryResponse

    fun getBookApointmentEnquiryResponse(body: RequestBody) = viewModelScope.launch {
        _appointmentEnquiryResponse.value = Resource.Loading
        _appointmentEnquiryResponse.value = repository.getBookAppointmentEnquiryResponse(body)

    }

}