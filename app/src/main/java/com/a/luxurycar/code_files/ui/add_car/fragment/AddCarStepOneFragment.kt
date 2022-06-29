package com.a.luxurycar.code_files.ui.add_car.fragment

import android.Manifest
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarStepOneRepository
import com.a.luxurycar.code_files.ui.add_car.adapter.AddMultipleImageAdapter
import com.a.luxurycar.code_files.ui.add_car.model.AddMultipleImageModel
import com.a.luxurycar.code_files.view_model.AddCarStepOneViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.helper.AlertDialogHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentAddCarStepOneBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_add_car_step_one.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddCarStepOneFragment :
    BaseFragment<AddCarStepOneViewModel, FragmentAddCarStepOneBinding, AddCarStepOneRepository>(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    lateinit var arrMakeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrCitiesListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrBodyTypeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrCarModelListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrBodyConditionListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrColorListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrHorsePowerListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrMechanicalConditionListHashMap: ArrayList<HashMap<String, String>>
   // lateinit var arrRegionalSpecificationList: ArrayList<String>
    lateinit var arrRegionalSpecificationListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrTransMissionTypeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrSellerTypeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrFullServiceHistoryListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrNumberOfCylinderListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrFuelTypeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrSteeringListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrWarrantyListHashMap: ArrayList<HashMap<String, String>>

    var makeId = ""
    var cityId = ""
    var bodyTypeId = ""
    var carModelId = ""
    var bodyConditionId = ""
    var interiorColor = ""
    var exteriourColor = ""
    var horsePowerId = ""
    var  mechanicalConditionId = ""
    var  regionalSpecificationNAme = ""
    var transmissionTypeName = ""
    var sellerTypeName = ""
    var fullServiceHistoryName = ""
    var numberOfCylinderName = ""
    var fuelTypeName = ""
    var steeringSideName = ""
    var warrantyName = ""
    var isShowMoreDetails = false
    var PICK_IMAGE_MULTIPLE = 321
    var CAMERA_REQUEST = 122
    var MY_CAMERA_PERMISSION_CODE = 100
    var imageName = ""
    lateinit var file:File

    companion object {
        private val REQUEST_TAKE_PHOTO = 321
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 123
    }

    lateinit var listImage: ArrayList<AddMultipleImageModel>

    override fun getViewModel()=AddCarStepOneViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAddCarStepOneBinding.inflate(inflater,container,false)
    override fun getRepository()= AddCarStepOneRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listImage = arrayListOf()
        //selectRadioButtonId()
        manageClickListeners()
        callMakeListApi()
        callCitiesListApi()
        callSellCarStepOneBasicListApi()
        observeSellCarStepOneBasicListResponse()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    private fun observeSellCarStepOneBasicListResponse() {

        viewModel.sellCarStepOneResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            initializeAllSellCarStepOneListAndDefaultItem()

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            //add regional specification
                            for (item in it.values.data.regionalSpecifications) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" )
                                hashMap.put(Const.KEY_NAME, item)
                                arrRegionalSpecificationListHashMap.add(hashMap)
                            }
                            //add transmission type specification
                            for (item in it.values.data.transmissionTypes) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" )
                                hashMap.put(Const.KEY_NAME, item)
                                arrTransMissionTypeListHashMap.add(hashMap)
                            }
                            //add seller type
                            for (item in it.values.data.sellerType) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" )
                                hashMap.put(Const.KEY_NAME, item)
                                arrSellerTypeListHashMap.add(hashMap)
                            }
                            //add full service history
                            for (item in it.values.data.fullServiceHistory) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" )
                                hashMap.put(Const.KEY_NAME, item)
                                arrFullServiceHistoryListHashMap.add(hashMap)
                            }
                            //add number of cylinders
                            for (item in it.values.data.numberOfCylinders) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" )
                                hashMap.put(Const.KEY_NAME, item.toString())
                                arrNumberOfCylinderListHashMap.add(hashMap)
                            }
                            //add fuel type
                            for (item in it.values.data.fuelType) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" )
                                hashMap.put(Const.KEY_NAME, item)
                                arrFuelTypeListHashMap.add(hashMap)
                            }
                            //add steering side
                            for (item in it.values.data.steeringSide) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" )
                                hashMap.put(Const.KEY_NAME, item)
                                arrSteeringListHashMap.add(hashMap)
                            }

                            //add warranty side
                            for (item in it.values.data.warranty) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" )
                                hashMap.put(Const.KEY_NAME, item)
                                arrWarrantyListHashMap.add(hashMap)
                            }
                            //add body condition
                            for (item in it.values.data.bodyConditions) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrBodyConditionListHashMap.add(hashMap)
                            }
                            // add color
                            for (item in it.values.data.colors) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrColorListHashMap.add(hashMap)
                            }

                            // add house power
                            for (item in it.values.data.horsePower) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrHorsePowerListHashMap.add(hashMap)
                            }

                            // add mechanical condition
                            for (item in it.values.data.mechanicalConditions) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrMechanicalConditionListHashMap.add(hashMap)
                            }
                        }
                    }
                    setSpinnerAdapterAndDropdown()
                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })

    }

    private fun setSpinnerAdapterAndDropdown() {
        val adapterRegionalSpecification =AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrRegionalSpecificationListHashMap)


        val adapterTransMissionTypeList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrTransMissionTypeListHashMap)

        val adapterSellerTypeList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrSellerTypeListHashMap)

        val adapterFullServiceHistoryList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrFullServiceHistoryListHashMap)

        val adapterNumberOfCylinderList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrNumberOfCylinderListHashMap)

        val adapterFuelTypeList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrFuelTypeListHashMap)

        val adapterSteeringList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrSteeringListHashMap)


        val adapterWarrantyList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrWarrantyListHashMap)

        val adapterBodyCondition = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrBodyConditionListHashMap
        )
        val adapterColor = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrColorListHashMap
        )
        val adapterHorsePower = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrHorsePowerListHashMap
        )
        val adapterMechanicalCondition = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrMechanicalConditionListHashMap
        )



        //Drop down layout style - list view for body condition
        adapterBodyCondition.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectBodyCondition.setAdapter(adapterBodyCondition)

        //Drop down layout style - list view
        adapterRegionalSpecification.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectRegionalSpecification.setAdapter(adapterRegionalSpecification)

        //Drop down layout style - list view
        adapterTransMissionTypeList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectTransmissionType.setAdapter(adapterTransMissionTypeList)

        //Drop down layout style - list view
        adapterSellerTypeList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectSellerType.setAdapter(adapterSellerTypeList)

        //Drop down layout style - list view
        adapterFullServiceHistoryList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectFullServiceHistory.setAdapter(adapterFullServiceHistoryList)

        //Drop down layout style - list view
        adapterNumberOfCylinderList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectNumberOfCylinders.setAdapter(adapterNumberOfCylinderList)

        //Drop down layout style - list view
        adapterFuelTypeList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectFuelType.setAdapter(adapterFuelTypeList)

        //Drop down layout style - list view
        adapterSteeringList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectSteeringSide.setAdapter(adapterSteeringList)

        //Drop down layout style - list view
        adapterWarrantyList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectWarranty.setAdapter(adapterWarrantyList)


        //Drop down layout style - list view
        adapterColor.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectExteriourColour.setAdapter(adapterColor)
        binding.spinnerSelectInteriorColour.setAdapter(adapterColor)

        //Drop down layout style - list view
        adapterHorsePower.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectHorsepower.setAdapter(adapterHorsePower)

        //Drop down layout style - list view
        adapterMechanicalCondition.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectMechanicalCondition.setAdapter(adapterMechanicalCondition)

    }

    private fun initializeAllSellCarStepOneListAndDefaultItem() {
        //initialize the list

        arrBodyConditionListHashMap = ArrayList()
        arrColorListHashMap = ArrayList()
        arrHorsePowerListHashMap = ArrayList()
        arrMechanicalConditionListHashMap = ArrayList()

        arrRegionalSpecificationListHashMap = ArrayList()
        arrTransMissionTypeListHashMap = ArrayList()
        arrSellerTypeListHashMap = ArrayList()
        arrFullServiceHistoryListHashMap = ArrayList()
        arrNumberOfCylinderListHashMap = ArrayList()
        arrFuelTypeListHashMap = ArrayList()
        arrSteeringListHashMap = ArrayList()
        arrWarrantyListHashMap = ArrayList()

        //default item for all list
        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Select")

        //add default item
        arrBodyConditionListHashMap.add(hashMapDefaultitem)
        arrColorListHashMap.add(hashMapDefaultitem)
        arrHorsePowerListHashMap.add(hashMapDefaultitem)
        arrMechanicalConditionListHashMap.add(hashMapDefaultitem)

        arrTransMissionTypeListHashMap.add(hashMapDefaultitem)
        arrRegionalSpecificationListHashMap.add(hashMapDefaultitem)
        arrSellerTypeListHashMap.add(hashMapDefaultitem)
        arrFullServiceHistoryListHashMap.add(hashMapDefaultitem)
        arrNumberOfCylinderListHashMap.add(hashMapDefaultitem)
        arrFuelTypeListHashMap.add(hashMapDefaultitem)
        arrSteeringListHashMap.add(hashMapDefaultitem)
        arrWarrantyListHashMap.add(hashMapDefaultitem)
    }

    private fun callSellCarStepOneBasicListApi() {
     viewModel.getSellCarStepOneResponse()
    }

    private fun manageClickListeners() {
        binding.linLayoutAddPictures.setOnClickListener {
            openBottomSheet()
        }

        binding.spinnerSelectMake.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    makeId = arrMakeListHashMap[position].get(Const.KEY_ID).toString()
                    if (makeId != "0"){
                        callBodyTypeApi()
                    }

                }

            }

        binding.spinnerSelectBodyType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    bodyTypeId = arrBodyTypeListHashMap[position].get(Const.KEY_ID).toString()
                    if (bodyTypeId != "0") {
                        callCarModelTypeApi()
                    }
                }

            }
        binding.spinnerSelectModel.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    carModelId = arrCarModelListHashMap[position].get(Const.KEY_ID).toString()

                }

            }


        binding.spinnerSelectCity.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    cityId = arrMakeListHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.spinnerSelectBodyCondition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    bodyConditionId = arrBodyConditionListHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.spinnerSelectRegionalSpecification.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    regionalSpecificationNAme = arrRegionalSpecificationListHashMap.get(position).toString()

                }

            }

        binding.spinnerSelectTransmissionType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    transmissionTypeName = arrTransMissionTypeListHashMap.get(position).toString()

                }

            }

        binding.spinnerSelectSellerType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    sellerTypeName = arrSellerTypeListHashMap.get(position).toString()

                }

            }

        binding.spinnerSelectFullServiceHistory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    fullServiceHistoryName = arrFullServiceHistoryListHashMap.get(position).toString()

                }

            }

        binding.spinnerSelectNumberOfCylinders.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    numberOfCylinderName = arrNumberOfCylinderListHashMap.get(position).toString()

                }

            }

        binding.spinnerSelectFuelType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    fuelTypeName = arrFuelTypeListHashMap.get(position).toString()

                }

            }

        binding.spinnerSelectSteeringSide.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    steeringSideName = arrSteeringListHashMap.get(position).toString()

                }

            }

        binding.spinnerSelectWarranty.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    warrantyName = arrWarrantyListHashMap.get(position).toString()

                }

            }

        binding.spinnerSelectExteriourColour.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    exteriourColor = arrColorListHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.spinnerSelectInteriorColour.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    interiorColor = arrColorListHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.spinnerSelectHorsepower.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    horsePowerId = arrHorsePowerListHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.spinnerSelectMechanicalCondition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    mechanicalConditionId = arrMechanicalConditionListHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.radioButtonSellCar.setOnClickListener {

            if(binding.radioButtonSellCar.isChecked) {
                binding.txtViewPrice.text = "Price"
                binding.consLayoutWeeklyAndMonthlyPrice.visibility = View.GONE
            }

        }

        binding.radioButtonRentCar.setOnClickListener {

            if(binding.radioButtonRentCar.isChecked) {
                binding.txtViewPrice.text = "Daily Price"
                binding.consLayoutWeeklyAndMonthlyPrice.visibility = View.VISIBLE
            }

        }

        binding.txtViewMoreDetailsAndLessDetails.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.txtViewMoreDetailsAndLessDetails.setOnClickListener {
            isShowMoreDetails = !isShowMoreDetails
            if(isShowMoreDetails){
                binding.cardViewSecondPartCarDetails.visibility = View.VISIBLE
                binding.txtViewMoreDetailsAndLessDetails.text =getString(R.string.str_less_deatils_non_mandatory)
            }else{
                binding.cardViewSecondPartCarDetails.visibility = View.GONE
                binding.txtViewMoreDetailsAndLessDetails.text = getString(R.string.str_more_deatils_non_mandatory)
            }



        }


    }

    private fun callCarModelTypeApi() {
        viewModel.getCarModelResponse(bodyTypeId)
        viewModel.carModelResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrCarModelListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select Model")
            arrCarModelListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrCarModelListHashMap.add(hashMap)
                            }
                        }
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrCarModelListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectModel.setAdapter(adapter)



                }
                is Resource.Failure -> handleApiErrors(it)
            }

        })
    }


    private fun callCitiesListApi() {
        viewModel.getAddCarStepCitiesResponse("1")

        viewModel.citiesListResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrCitiesListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select")
            arrCitiesListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrCitiesListHashMap.add(hashMap)
                            }
                        }
                    }
                    else{
                        Toast.makeText(requireContext(),it.values.message,Toast.LENGTH_LONG).show()
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrCitiesListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectCity.setAdapter(adapter)



                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun callMakeListApi() {
        viewModel.getMakeListResponse()
        viewModel.makeListResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrMakeListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select")
            arrMakeListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrMakeListHashMap.add(hashMap)
                            }
                        }
                    }
                   else{
                       Toast.makeText(requireContext(),it.values.message,Toast.LENGTH_LONG).show()
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrMakeListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectMake.setAdapter(adapter)



                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })

    }
    private fun callBodyTypeApi() {
        viewModel.getBodyTypeResponse(makeId)
        viewModel.bodyTypeResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrBodyTypeListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select")
            arrBodyTypeListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrBodyTypeListHashMap.add(hashMap)
                            }
                        }
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrBodyTypeListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectBodyType.setAdapter(adapter)



                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_SELECT_IMAGE_IN_ALBUM -> {

                val permissionCheckRead = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )

                val permissionCheckWrite = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )


                if (permissionCheckRead == 0 && permissionCheckWrite == 0) {
                    selectImageInAlbum()
                } else {
                    AlertDialogHelper.showMessage(
                        requireContext(),
                        getString(R.string.camera_setting)
                    )
                }

                return
            }

            REQUEST_TAKE_PHOTO -> {

                val permissionCheckCamera = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                )

                val permissionCheckRead = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )

                val permissionCheckWrite = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )


                if (permissionCheckCamera == 0 && permissionCheckRead == 0 && permissionCheckWrite == 0) {
                    takePhotoFromCamera()
                } else {
                    AlertDialogHelper.showMessage(
                        requireContext(),
                        getString(R.string.camera_setting)
                    )
                }

                return
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == AppCompatActivity.RESULT_OK) {
            galleryAddPic()
        } else if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == AppCompatActivity.RESULT_OK) {

            if (data != null) {
                if (data?.getClipData() != null) {
                    val count = data.clipData!!.itemCount

                    for (i in 0..count - 1) {
                        val contentURI = data.clipData!!.getItemAt(i).uri
                        val image_uri = contentURI.toString()
                        file = File(getPath(contentURI))
                        val imageName = file.name
                        listImage.add(AddMultipleImageModel(image_uri, imageName))
                        setImageRecyclerView()

                    }

                } else if (data?.getData() != null) {

                    val contentURI = data.data
                    val image_uri = contentURI.toString()

                    //get the image name
                     file = File(getPath(contentURI))
                     val imageName = file.name

                    listImage.add(AddMultipleImageModel(image_uri, imageName))
                    setImageRecyclerView()

                }

            }

        } else if (requestCode == 1 && resultCode == 1) {

        }
    }

    private fun getPath(contentURI: Uri?): String {
        var result = ""
        val cursor =
            contentURI?.let { context?.getContentResolver()?.query(it, null, null, null, null) }
        if (cursor == null) { // Source is Dropbox or other similar local file path
            if (contentURI != null) {
                result = contentURI.getPath().toString()
            }
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }


    private fun openBottomSheet() {
        // on below line we are inflating  layout file which we have created.
        val view = layoutInflater.inflate(R.layout.alert_dialog_profile_picture, null)
        val dialog = BottomSheetDialog(requireContext())
        val linLayoutCamera = view.findViewById<LinearLayout>(R.id.linLayoutCamera)
        val linLayoutGallery = view.findViewById<LinearLayout>(R.id.linLayoutGallery)

        linLayoutCamera.setOnClickListener {
            selectFromCameraPermission()
            dialog.dismiss()
        }

        linLayoutGallery.setOnClickListener {
            selectFromGalleryPermission()
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }
    private fun selectFromCameraPermission() {

        val permissionCheckCamera = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )

        val permissionCheckRead = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionCheckWrite = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionCheckCamera == -1 || permissionCheckRead == -1 || permissionCheckWrite == -1) {

                if (!Settings.System.canWrite(requireContext())) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                        ),REQUEST_TAKE_PHOTO
                    )
                } else {

                    takePhotoFromCamera()
                }
            } else {
                takePhotoFromCamera()
            }
        } else {
            takePhotoFromCamera()
        }

    }
    lateinit var currentPhotoPath: String
    var photoFile: File? = null
    var fileUri: Uri? = null
    private fun takePhotoFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .also { takePictureIntent ->
                photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    fileUri = FileProvider.getUriForFile(
                        requireContext(), "com.luxurycar.fileprovider",
                        it
                    )
                    //AppLogger.d("asad_fileUri", "$fileUri")
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                    startActivityForResult(takePictureIntent,
                        REQUEST_TAKE_PHOTO)
                }
            }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    private fun selectFromGalleryPermission() {

        val permissionCheckRead = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionCheckWrite = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionCheckRead == -1 || permissionCheckWrite == -1) {

                if (!Settings.System.canWrite(requireContext())) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                        ), REQUEST_SELECT_IMAGE_IN_ALBUM
                    )
                } else {


                    selectImageInAlbum()

                }
            } else {

                selectImageInAlbum()

            }
        } else {

            selectImageInAlbum()

        }
    }

    fun selectImageInAlbum() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        photoPickerIntent.type = "image/*"
        photoPickerIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(photoPickerIntent, REQUEST_SELECT_IMAGE_IN_ALBUM)


    }

    private fun galleryAddPic() {

        if (currentPhotoPath.isNotEmpty()) {
            val f = File(currentPhotoPath)
            imageName = f.name
            //image_uri = compressTheImageBeforeSendingToServer(currentPhotoPath).toString()
            val image_uri = Uri.fromFile(f).toString()

            listImage.add(AddMultipleImageModel(image_uri,imageName))
            setImageRecyclerView()

        }
    }

    private fun setImageRecyclerView() {
        val addMultipleImageAdapter = AddMultipleImageAdapter(requireContext(),listImage)
        binding.recyclerviewMultipleImageUpload.adapter = addMultipleImageAdapter
        binding.recyclerviewMultipleImageUpload.setNestedScrollingEnabled(false);
        binding.recyclerviewMultipleImageUpload.setLayoutManager(GridLayoutManager(requireContext(), 2))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        val india = LatLng(     23.63936, 79.14712)
        map.addMarker(MarkerOptions().position(india).title("India location"))
        map.moveCamera(CameraUpdateFactory.newLatLng(india))
    }


}