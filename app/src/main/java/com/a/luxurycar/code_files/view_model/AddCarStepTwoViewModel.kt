package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.AddCarStepTwoRepository
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.AddCarStepTwoResponse
import com.a.luxurycar.code_files.ui.add_car.model.step_three_listing_plan.AddCarStepThreeListingPlanModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch

class AddCarStepTwoViewModel(val repository: AddCarStepTwoRepository):BaseViewModel(repository) {


    private val _getAddCarStepTwoResponse: MutableLiveData<Resource<AddCarStepTwoResponse>> = MutableLiveData()
    val getAddCarStepTwoResponse: LiveData<Resource<AddCarStepTwoResponse>>
        get() = _getAddCarStepTwoResponse
    fun getAddSellerListingPlan(id:String) = viewModelScope.launch {

        _getAddCarStepTwoResponse.value = Resource.Loading
        _getAddCarStepTwoResponse.value = repository.getAddCarStepTwoResponse(id)

    }


}