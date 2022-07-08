package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.AddCarStepOneRepository
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_one.AddCarStepOneModel
import com.a.luxurycar.code_files.ui.add_car.model.body_type.BodyTypeListModel
import com.a.luxurycar.code_files.ui.add_car.model.car_model.CarModelListModel
import com.a.luxurycar.code_files.ui.add_car.model.cities_list.CitiesListModel
import com.a.luxurycar.code_files.ui.add_car.model.make_list_model.MakeListModel
import com.a.luxurycar.code_files.ui.add_car.model.sale_person.SalePersonModelList
import com.a.luxurycar.code_files.ui.add_car.model.sell_car_step_one_basic_list.SellCarStepOneBasicListModel
import com.a.luxurycar.code_files.ui.add_car.model.upload_images.AddCarStepOneUploadImagesModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddCarStepOneViewModel(val repository: AddCarStepOneRepository):BaseViewModel(repository){

    //
    private val _makeListResponse: MutableLiveData<Resource<MakeListModel>> = MutableLiveData()
    val makeListResponse: LiveData<Resource<MakeListModel>>
        get() = _makeListResponse

    fun getMakeListResponse() = viewModelScope.launch {

        _makeListResponse.value = Resource.Loading
        _makeListResponse.value = repository.getMakeListResponse()

    }


    private val _bodyTypeResponse: MutableLiveData<Resource<BodyTypeListModel>> = MutableLiveData()
    val bodyTypeResponse: LiveData<Resource<BodyTypeListModel>>
        get() = _bodyTypeResponse

    fun getBodyTypeResponse(makeId: String) = viewModelScope.launch {
        _bodyTypeResponse.value = Resource.Loading
        _bodyTypeResponse.value = repository.getBodyTypeResponse(makeId)
    }

    private val _carModelResponse: MutableLiveData<Resource<CarModelListModel>> = MutableLiveData()
    val carModelResponse: LiveData<Resource<CarModelListModel>>
        get() = _carModelResponse

    fun  getCarModelResponse(bodyTypeId: String) = viewModelScope.launch {
        _carModelResponse.value = Resource.Loading
        _carModelResponse.value = repository. getCarModelResponse(bodyTypeId)
    }

    private val _sellCarStepOneResponse: MutableLiveData<Resource<SellCarStepOneBasicListModel>> = MutableLiveData()
    val sellCarStepOneResponse: LiveData<Resource<SellCarStepOneBasicListModel>>
        get() = _sellCarStepOneResponse

    fun   getSellCarStepOneResponse() = viewModelScope.launch {
        _sellCarStepOneResponse.value = Resource.Loading
        _sellCarStepOneResponse.value = repository. getSellCarStepOneResponse()
    }

    private val _citiesListResponse: MutableLiveData<Resource<CitiesListModel>> = MutableLiveData()
    val citiesListResponse: LiveData<Resource<CitiesListModel>>
        get() = _citiesListResponse

    fun getAddCarStepCitiesResponse(cityId: String) = viewModelScope.launch {
        _citiesListResponse.value = Resource.Loading
        _citiesListResponse.value = repository.getAddCarStepCitiesResponse(cityId)
    }


    private val _salePersonResponse: MutableLiveData<Resource<SalePersonModelList>> = MutableLiveData()
    val salePersonResponse: LiveData<Resource<SalePersonModelList>>
        get() = _salePersonResponse

    fun getSalePersonResponse(salePersonId: String) = viewModelScope.launch {
        _salePersonResponse.value = Resource.Loading
        _salePersonResponse.value = repository.getSalePersonResponse(salePersonId)
    }


    private val _addCarStepOneResponse: MutableLiveData<Resource<AddCarStepOneModel>> = MutableLiveData()
    val addCarStepOneResponse: LiveData<Resource<AddCarStepOneModel>>
        get() = _addCarStepOneResponse

    fun getAddCarStepOneResponse(body: RequestBody) = viewModelScope.launch {
        _addCarStepOneResponse.value = Resource.Loading
        _addCarStepOneResponse.value = repository.getAddCarStepOneResponse(body)
    }


    private val _getMultipleImageResponse: MutableLiveData<Resource<AddCarStepOneUploadImagesModel>> = MutableLiveData()
    val getMultipleImageResponse: LiveData<Resource<AddCarStepOneUploadImagesModel>>
        get() = _getMultipleImageResponse

    fun getMultipleUploadImagesResponse(id:String, image: ArrayList<MultipartBody.Part>) = viewModelScope.launch {
        _getMultipleImageResponse.value = Resource.Loading
        _getMultipleImageResponse.value = repository.getMultipleUploadImagesResponse(id, image)
    }



}