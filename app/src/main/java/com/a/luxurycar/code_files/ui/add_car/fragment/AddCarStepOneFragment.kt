package com.a.luxurycar.code_files.ui.add_car.fragment

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
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
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarStepOneRepository
import com.a.luxurycar.code_files.ui.add_car.AddCarActivity
import com.a.luxurycar.code_files.ui.add_car.adapter.AddMultipleImageAdapter
import com.a.luxurycar.code_files.ui.add_car.model.AddMultipleImageModel
import com.a.luxurycar.code_files.ui.add_car.model.ImageIndexSelectionModel
import com.a.luxurycar.code_files.view_model.AddCarStepOneViewModel
import com.a.luxurycar.common.application.LuxuryCarApplication
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.helper.AlertDialogHelper
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentAddCarStepOneBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AddCarStepOneFragment :
    BaseFragment<AddCarStepOneViewModel, FragmentAddCarStepOneBinding, AddCarStepOneRepository>(),
    OnMapReadyCallback {
    private var image_uri: String = ""
    private lateinit var map: GoogleMap
    lateinit var listOfMultipPartImages: ArrayList<MultipartBody.Part>
    lateinit var arrMakeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearListHashMap: ArrayList<HashMap<String, String>>
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
    lateinit var arrDeliveryChargesListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrPaymentMethodListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrsalePersonListHashMap: ArrayList<HashMap<String, String>>

    //var chasisNumber = ""
    var makeId = ""
    var makeName = ""
    var cityId = ""
    var cityName = ""
    var bodyTypeId = ""
    var bodyTypeName = ""
    var carModelId = ""
    var carModelName = ""
    var kiloMetere = ""
    var price = ""
    var deposit = ""
    var dailyPrice =""
    var weeklyPrice = ""
    var monthlyPrice = ""
    var bodyConditionId = ""
    var bodyConditionName = ""
    var interiorColorId = ""
    var interiorColorName = ""
    var exteriourColorId = ""
    var exteriourColorName = ""
    var horsePowerId = ""
    var horsePowerName = ""
    var mechanicalConditionId = ""
    var mechanicalConditionName = ""
    var regionalSpecificationName = ""
    var transmissionTypeName = ""
    var transmissionTypeId = ""
    var sellerTypeId = ""
    var sellerTypeName = ""
    var deliveryChargesId = ""
    var deliveryChargesName = ""
    var paymentMethodId = ""
    var paymentMethodName = ""
    var yearName = ""
    var fullServiceHistoryName = ""
    var fullServiceHistoryId = ""
    var numberOfCylinderName = ""
    var fuelTypeName = ""
    var steeringSideId = ""
    var steeringSideName = ""
    var warrantyName = ""
    var warrantyId = ""
    var salePersonId = ""
    var salePersonName = ""
    var title = ""
    var description = ""
    var locationUrl =
        "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d462560.30119248916!2d54.94729410040116!3d25.076381469564634!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3e5f43496ad9c645%3A0xbde66e5084295162!2sDubai%20-%20United%20Arab%20Emirates!5e0!3m2!1sen!2sin!4v1656315325167!5m2!1sen!2sin"
    var tourUrl = ""
    var rent = "0"
    var sellerId = ""
    var idForImageUpload = ""

    var isShowMoreDetails = false
    var PICK_IMAGE_MULTIPLE = 321
    var CAMERA_REQUEST = 122
    var MY_CAMERA_PERMISSION_CODE = 100
    var imageName = ""
    lateinit var file: File

    private var actualImage: File? = null
    private var compressedImage: File? = null

    companion object {
        private val REQUEST_TAKE_PHOTO = 321
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 123
    }

    lateinit var listImage: ArrayList<AddMultipleImageModel>

    var arrForImagePosition = ArrayList<String>()

    override fun getViewModel() = AddCarStepOneViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAddCarStepOneBinding.inflate(inflater, container, false)

    override fun getRepository() =
        AddCarStepOneRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listImage = arrayListOf()
        //selectRadioButtonId()
        manageClickListeners()
        callMakeListApi()
        initializeAllSellCarStepOneListAndDefaultItem()
        callCitiesListApi()
        callSellCarStepOneBasicListApi()
        callSalePersonApi()
        getPreviousYearList()
        setSpinnerAdapterAndDropdown()
        observeSellCarStepOneBasicListResponse()
        observeAddCarStepOneImageApiResponse()
        observeAddCarStepOneApiResponse()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    private fun addCarModelAdapter() {
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

    private fun manageClickListeners() {
        binding.consLayoutUploadImage.setOnClickListener {
            openBottomSheet()
        }

        binding.btnNext.setOnClickListener {


          /*  (activity as AddCarActivity).getCurrentId("32")
           findNavController().navigate(R.id.addCarStepTwo)*/

           if (validation()) {
                addCarStepOnePostApi()
            }
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
                    makeName = arrMakeListHashMap[position].get(Const.KEY_NAME).toString()
                    if (makeId != "0") {
                        callCarModelTypeApi()
                    }

                }

            }

        binding.spinnerSelectYear.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    yearName = arrYearListHashMap[position].get(Const.KEY_NAME).toString()

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
                    /* if (bodyTypeId != "0") {
                         callCarModelTypeApi()
                     }*/
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
                    carModelName = arrCarModelListHashMap[position].get(Const.KEY_NAME).toString()

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
                    cityId = arrCitiesListHashMap[position].get(Const.KEY_ID).toString()
                    cityName = arrCitiesListHashMap[position].get(Const.KEY_NAME).toString()

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
                    bodyConditionId =
                        arrBodyConditionListHashMap[position].get(Const.KEY_ID).toString()

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
                    regionalSpecificationName =
                        arrRegionalSpecificationListHashMap[position].get(Const.KEY_NAME).toString()

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
                    transmissionTypeName =
                        arrTransMissionTypeListHashMap[position].get(Const.KEY_NAME).toString()
                    transmissionTypeId =
                        arrTransMissionTypeListHashMap[position].get(Const.KEY_ID).toString()

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
                    sellerTypeName =
                        arrSellerTypeListHashMap[position].get(Const.KEY_NAME).toString()
                    sellerTypeId =
                        arrSellerTypeListHashMap[position].get(Const.KEY_ID).toString()

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
                    fullServiceHistoryName =
                        arrFullServiceHistoryListHashMap[position].get(Const.KEY_NAME).toString()
                    fullServiceHistoryId =
                        arrFullServiceHistoryListHashMap[position].get(Const.KEY_ID).toString()

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

                    numberOfCylinderName =
                        arrNumberOfCylinderListHashMap[position].get(Const.KEY_NAME).toString()

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
                    fuelTypeName = arrFuelTypeListHashMap[position].get(Const.KEY_NAME).toString()

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
                    steeringSideName =
                        arrSteeringListHashMap[position].get(Const.KEY_NAME).toString()
                    steeringSideId =
                        arrSteeringListHashMap[position].get(Const.KEY_ID).toString()

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
                    warrantyId = arrWarrantyListHashMap[position].get(Const.KEY_ID).toString()
                    warrantyName = arrWarrantyListHashMap[position].get(Const.KEY_NAME).toString()

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
                    exteriourColorId = arrColorListHashMap[position].get(Const.KEY_ID).toString()
                    exteriourColorName =
                        arrColorListHashMap[position].get(Const.KEY_NAME).toString()

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
                    interiorColorId = arrColorListHashMap[position].get(Const.KEY_ID).toString()
                    interiorColorName = arrColorListHashMap[position].get(Const.KEY_NAME).toString()

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
                    horsePowerName =
                        arrHorsePowerListHashMap[position].get(Const.KEY_NAME).toString()

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
                    mechanicalConditionId =
                        arrMechanicalConditionListHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.spinnerSelectAssignSalePerson.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    salePersonId = arrsalePersonListHashMap[position].get(Const.KEY_ID).toString()
                    salePersonName = arrMakeListHashMap[position].get(Const.KEY_NAME).toString()
                    /* if (makeId != "0") {
                         callBodyTypeApi()
                     }*/

                }

            }

        binding.spinnerSelectDeliveryCharge.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    deliveryChargesId = arrDeliveryChargesListHashMap[position].get(Const.KEY_ID).toString()
                    deliveryChargesName = arrDeliveryChargesListHashMap[position].get(Const.KEY_NAME).toString()

                }

            }

        binding.spinnerSelectPaymentMethod.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    paymentMethodId = arrPaymentMethodListHashMap[position].get(Const.KEY_ID).toString()
                    paymentMethodName = arrPaymentMethodListHashMap[position].get(Const.KEY_NAME).toString()
                }

            }

        binding.radioButtonSellCar.setOnClickListener {

            if (binding.radioButtonSellCar.isChecked) {
                rent = "0"
                binding.txtViewPrice.text = "Price"
                binding.edtTextPrice.visibility = View.VISIBLE
                binding.edtTextDailyPrice.visibility = View.GONE
                binding.consLayoutWeeklyAndMonthlyPrice.visibility = View.GONE
                binding.consLayoutDeliveryChargesAndPaymentMethod.visibility = View.GONE
                binding.consLayoutSellerTypeAndFullServiceHistory.visibility = View.VISIBLE

               /* //price visibility visibible
                binding.conLayoutSelectPrice.visibility = View.VISIBLE
                binding.txtViewPrice.visibility = View.VISIBLE
                binding.txtViewPriceStar.visibility = View.VISIBLE*/

                //deposit price visibility hide
                binding.txtViewDeposit.visibility = View.GONE
                binding.txtViewDepositStar.visibility = View.GONE
                binding.conLayoutDepositPrice.visibility = View.GONE
            }

        }

        binding.radioButtonRentCar.setOnClickListener {

            if (binding.radioButtonRentCar.isChecked) {
                rent = "1"
                binding.txtViewPrice.text = "Daily Price"
                binding.edtTextPrice.visibility = View.GONE
                binding.edtTextDailyPrice.visibility = View.VISIBLE
                binding.consLayoutWeeklyAndMonthlyPrice.visibility = View.VISIBLE
                binding.consLayoutDeliveryChargesAndPaymentMethod.visibility = View.VISIBLE
                binding.consLayoutSellerTypeAndFullServiceHistory.visibility = View.GONE

                /*//price visibility hide
                binding.conLayoutSelectPrice.visibility = View.GONE
                binding.txtViewPrice.visibility = View.GONE
                binding.txtViewPriceStar.visibility = View.GONE*/

                //deposit price visibility visibible
               binding.txtViewDeposit.visibility = View.VISIBLE
                binding.txtViewDepositStar.visibility = View.VISIBLE
                binding.conLayoutDepositPrice.visibility = View.VISIBLE
            }

        }

        binding.txtViewMoreDetailsAndLessDetails.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.txtViewMoreDetailsAndLessDetails.setOnClickListener {
            isShowMoreDetails = !isShowMoreDetails
            if (isShowMoreDetails) {
                binding.consLayoutSecondPartCarDetails.visibility = View.VISIBLE
                binding.txtViewMoreDetailsAndLessDetails.text =
                    getString(R.string.str_less_deatils_non_mandatory)
            } else {
                binding.consLayoutSecondPartCarDetails.visibility = View.GONE
                binding.txtViewMoreDetailsAndLessDetails.text =
                    getString(R.string.str_more_deatils_non_mandatory)
            }
        }


    }
    private fun observeAddCarStepOneImageApiResponse() {

        viewModel.getMultipleImageResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.addCarStepOneResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                binding.progressbarAddCarStepOne.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {
                        if (it.values.status != null && it.values.status == 1) {
                            Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                                .show()

                             val bundle = Bundle()
                            bundle.putString("car_ads_id",idForImageUpload)
                            if(!idForImageUpload.isNullOrEmpty()){
                                (activity as AddCarActivity?)?.getCurrentId(idForImageUpload)
                                findNavController().navigate(R.id.addCarStepTwo,bundle)
                            }


                        }

                        if (!Utils.isEmptyOrNull(it.values.message)) {
                            Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                                .show()
                        }

                    }
                    is Resource.Failure -> handleApiErrors(it)
                }
            })
        })

    }

    private fun observeAddCarStepOneApiResponse() {
        viewModel.addCarStepOneResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()

                        idForImageUpload = it.values.data.carAds.id.toString()

                        callUploadMultipleImagesApi()

                    }

                    if (!Utils.isEmptyOrNull(it.values.message)) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun callSalePersonApi() {
        val id = SessionManager.getUserData()?.id.toString()
        viewModel.getSalePersonResponse("73")

        viewModel.salePersonResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrsalePersonListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select Person Name")
            arrsalePersonListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.firstname + " " + item.lastname)
                                arrsalePersonListHashMap.add(hashMap)
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrsalePersonListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectAssignSalePerson.setAdapter(adapter)


                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })

    }

    private fun getPreviousYearList() {
        val list = arrayListOf<String>()
        arrYearListHashMap = ArrayList()

        //default item for all list
        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Select")

        //add default item
        arrYearListHashMap.add(hashMapDefaultitem)


        val prevYear = Calendar.getInstance()
        list.add(prevYear[Calendar.YEAR].toString())
        var i = 1
        for (i in i until 50) {
            prevYear.add(Calendar.YEAR, -1)
            list.add(prevYear[Calendar.YEAR].toString())
            i.dec()
        }
        for (item in list) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "")
            hashMap.put(Const.KEY_NAME, item)
            arrYearListHashMap.add(hashMap)
        }
    }

    private fun observeSellCarStepOneBasicListResponse() {

        viewModel.sellCarStepOneResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {


            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            //add regional specification
                            for (item in it.values.data.regionalSpecifications) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrRegionalSpecificationListHashMap.add(hashMap)
                            }
                            //add transmission type specification
                            for (item in it.values.data.transmissionTypes) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrTransMissionTypeListHashMap.add(hashMap)
                            }
                            //add body type
                            for (item in it.values.data.bodyTypes) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrBodyTypeListHashMap.add(hashMap)
                            }

                            //add seller type
                            for (item in it.values.data.sellerType) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrSellerTypeListHashMap.add(hashMap)
                            }
                            //add full service history
                            for (item in it.values.data.fullServiceHistory) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrFullServiceHistoryListHashMap.add(hashMap)
                            }
                            //add number of cylinders
                            for (item in it.values.data.numberOfCylinders) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrNumberOfCylinderListHashMap.add(hashMap)
                            }
                            //add fuel type
                            for (item in it.values.data.fuelType) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrFuelTypeListHashMap.add(hashMap)
                            }
                            //add steering side
                            for (item in it.values.data.steeringSide) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrSteeringListHashMap.add(hashMap)
                            }

                            //add warranty side
                            for (item in it.values.data.warranty) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrWarrantyListHashMap.add(hashMap)
                            }
                            //add body condition
                            for (item in it.values.data.bodyConditions) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrBodyConditionListHashMap.add(hashMap)
                            }
                            //add color
                            for (item in it.values.data.colors) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrColorListHashMap.add(hashMap)
                            }

                            //add house power
                            for (item in it.values.data.horsePower) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrHorsePowerListHashMap.add(hashMap)
                            }

                            //add mechanical condition
                            for (item in it.values.data.mechanicalConditions) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrMechanicalConditionListHashMap.add(hashMap)
                            }
                            //add mechanical condition
                            for (item in it.values.data.deliveryCharges) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrDeliveryChargesListHashMap.add(hashMap)
                            }
                            //add mechanical condition
                            for (item in it.values.data.paymentMethod) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrPaymentMethodListHashMap.add(hashMap)
                            }
                        }
                    }
                    //setSpinnerAdapterAndDropdown()
                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })

    }

    private fun setSpinnerAdapterAndDropdown() {
        val adapterRegionalSpecification = AdapterSpinner(requireContext(),
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
        val adapterYear = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrYearListHashMap
        )
        val adapterBodyType = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrBodyTypeListHashMap
        )
        val adapterDeliveryChargesList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrDeliveryChargesListHashMap)

        val adapterPaymentMethodList = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item, arrPaymentMethodListHashMap)


        //Drop down layout style - list view for body type
        adapterBodyType.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data body type adapter to spinner
        binding.spinnerSelectBodyType.setAdapter(adapterBodyType)

       //Drop down layout style - list view for year
        adapterYear.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectYear.setAdapter(adapterYear)

        //Drop down layout style - list view for body condition
        adapterBodyCondition.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectBodyCondition.setAdapter(adapterBodyCondition)

        //Drop down layout style - list view for regional specification
        adapterRegionalSpecification.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectRegionalSpecification.setAdapter(adapterRegionalSpecification)

        //Drop down layout style - list view for transmission type
        adapterTransMissionTypeList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectTransmissionType.setAdapter(adapterTransMissionTypeList)

        //Drop down layout style - list view for seller type
        adapterSellerTypeList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectSellerType.setAdapter(adapterSellerTypeList)

        //Drop down layout style - list view for full service history
        adapterFullServiceHistoryList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectFullServiceHistory.setAdapter(adapterFullServiceHistoryList)

        //Drop down layout style - list view for cylinder
        adapterNumberOfCylinderList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectNumberOfCylinders.setAdapter(adapterNumberOfCylinderList)

        //Drop down layout style - list view for fuel type
        adapterFuelTypeList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectFuelType.setAdapter(adapterFuelTypeList)

        //Drop down layout style - list view for steering
        adapterSteeringList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectSteeringSide.setAdapter(adapterSteeringList)

        //Drop down layout style - list view for warranty
        adapterWarrantyList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectWarranty.setAdapter(adapterWarrantyList)


        //Drop down layout style - list view for exteriour color
        adapterColor.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectExteriourColour.setAdapter(adapterColor)
        binding.spinnerSelectInteriorColour.setAdapter(adapterColor)

        //Drop down layout style - list view for horse power
        adapterHorsePower.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectHorsepower.setAdapter(adapterHorsePower)

        //Drop down layout style - list view for mechanical condition
        adapterMechanicalCondition.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectMechanicalCondition.setAdapter(adapterMechanicalCondition)

        //Drop down layout style - list view for delivery charges
        adapterDeliveryChargesList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data body type adapter to spinner
        binding.spinnerSelectDeliveryCharge.setAdapter(adapterDeliveryChargesList)

        //Drop down layout style - list view for delivery charges
        adapterPaymentMethodList.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data body type adapter to spinner
        binding.spinnerSelectPaymentMethod.setAdapter(adapterPaymentMethodList)

    }

    private fun initializeAllSellCarStepOneListAndDefaultItem() {
        //initialize the list
        arrCitiesListHashMap = ArrayList()
        arrCarModelListHashMap = ArrayList()
        arrBodyConditionListHashMap = ArrayList()
        arrColorListHashMap = ArrayList()
        arrHorsePowerListHashMap = ArrayList()
        arrMechanicalConditionListHashMap = ArrayList()
        arrBodyTypeListHashMap = ArrayList()

        arrRegionalSpecificationListHashMap = ArrayList()
        arrTransMissionTypeListHashMap = ArrayList()
        arrSellerTypeListHashMap = ArrayList()
        arrFullServiceHistoryListHashMap = ArrayList()
        arrNumberOfCylinderListHashMap = ArrayList()
        arrFuelTypeListHashMap = ArrayList()
        arrSteeringListHashMap = ArrayList()
        arrWarrantyListHashMap = ArrayList()
        arrDeliveryChargesListHashMap = ArrayList()
        arrPaymentMethodListHashMap = ArrayList()


        //default item for all list
        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Select")

        //add default item
        arrCitiesListHashMap.add(hashMapDefaultitem)
        arrCarModelListHashMap.add(hashMapDefaultitem)
        arrBodyTypeListHashMap.add(hashMapDefaultitem)
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
        arrDeliveryChargesListHashMap.add(hashMapDefaultitem)
        arrPaymentMethodListHashMap.add(hashMapDefaultitem)
        addCitiesAdapter()
        addCarModelAdapter()
    }

    private fun addCitiesAdapter() {
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

    private fun callSellCarStepOneBasicListApi() {
        viewModel.getSellCarStepOneResponse()
    }

    private fun callUploadMultipleImagesApi() {

        var UserId: RequestBody? = null
        UserId = RequestBody.create("text/plain".toMediaTypeOrNull(), idForImageUpload.toString())
        viewModel.getMultipleUploadImagesResponse(UserId, arrOfImageList)
    }

    private fun addCarStepOnePostApi() {
        sellerId = SessionManager.getUserData()?.id.toString()
        val jsonObject = JSONObject()
        try {
            //jsonObject.put(Const.PARAM_VIN_CHEESIS_NUMBER, chasisNumber)
            jsonObject.put(Const.PARAM_CITY_ID, cityId)
            jsonObject.put(Const.PARAM_MAKE_ID, makeId)
            jsonObject.put(Const.PARAM_BODY_TYPE_ID, bodyTypeId)
            jsonObject.put(Const.PARAM_CAR_MODEL_ID, carModelId)
            jsonObject.put(Const.PARAM_CAR_YEAR, yearName)
            jsonObject.put(Const.PARAM_RUN_KILOMETERS, kiloMetere)
            jsonObject.put(Const.PARAM_PRICE, price)
            jsonObject.put(Const.PARAM_DEPOSIT, deposit)
            jsonObject.put(Const.PARAM_DAILY_RENT_PRICE, dailyPrice)
            jsonObject.put(Const.PARAM_REGIONAL_SPECIFICATION, regionalSpecificationName)
            jsonObject.put(Const.PARAM_EXTERIOR_COLOR_ID, exteriourColorId)
            jsonObject.put(Const.PARAM_TRANSMISSION_TYPE, transmissionTypeId)
            jsonObject.put(Const.PARAM_HORSE_POWER_Id, horsePowerId)
            jsonObject.put(Const.PARAM_SELLER_TYPE, sellerTypeId)
            jsonObject.put(Const.PARAM_FULL_SERVICE_HISTORY, fullServiceHistoryId)
            jsonObject.put(Const.PARAM_DELIVERY_CHARGES, deliveryChargesId)
            jsonObject.put(Const.PARAM_PAYMENT_METHOD, paymentMethodId)
            jsonObject.put(Const.PARAM_NO_OF_CYLINDERS, numberOfCylinderName)
            jsonObject.put(Const.PARAM_INTERIOR_COLOR_ID, interiorColorId)
            jsonObject.put(Const.PARAM_FUEL_TYPE, fuelTypeName)
            jsonObject.put(Const.PARAM_BODY_CONDITION_ID, bodyConditionId)
            jsonObject.put(Const.PARAM_MECHANICAL_CONDITION_ID, mechanicalConditionId)
            jsonObject.put(Const.PARAM_STEERING_TYPE, steeringSideId)
            jsonObject.put(Const.PARAM_WARRENTY, warrantyId)
            jsonObject.put(Const.PARAM_SALES_PERSON_ID, salePersonId)
            jsonObject.put(Const.PARAM_TITLE, title)
            jsonObject.put(Const.PARAM_DESCRIPTION, description)
            jsonObject.put(Const.PARAM_LOCATION_URL, locationUrl)
            jsonObject.put(Const.PARAM_TOUR_URL, tourUrl)
            jsonObject.put(Const.PARAM_RENT, rent)
            jsonObject.put(Const.PARAM_DAILY_RENT_PRICE, dailyPrice)
            jsonObject.put(Const.PARAM_WEEKLY_RENT_PRICE, weeklyPrice)
            jsonObject.put(Const.PARAM_MONTHLY_RENT_PRICE, monthlyPrice)
            jsonObject.put(Const.PARAM_SELLER_ID, sellerId)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getAddCarStepOneResponse(body)
    }

    private fun validation(): Boolean {

        getDataFromEditText()

        if (cityName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_city),
                Toast.LENGTH_LONG).show()
            return false
        } else if (makeName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_make),
                Toast.LENGTH_LONG).show()
            return false
        } else if (carModelName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_car_model),
                Toast.LENGTH_LONG).show()
            return false
        } else if (yearName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_year),
                Toast.LENGTH_LONG).show()
            return false
        } else if (kiloMetere.isNullOrEmpty()) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_kilometers),
                Toast.LENGTH_LONG).show()
            return false
        } else if (price.isNullOrEmpty()) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_price),
                Toast.LENGTH_LONG).show()
            return false
        }
        else if (deposit.isNullOrEmpty() && binding.conLayoutDepositPrice.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_deposit),
                Toast.LENGTH_LONG).show()
            return false
        }
        else if (dailyPrice.isNullOrEmpty() && binding.edtTextDailyPrice.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_daily_price),
                Toast.LENGTH_LONG).show()
            return false
        }
        else if (weeklyPrice.isNullOrEmpty() && binding.consLayoutWeeklyAndMonthlyPrice.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_weekly_price),
                Toast.LENGTH_LONG).show()
            return false
        } else if (monthlyPrice.isNullOrEmpty() && binding.consLayoutWeeklyAndMonthlyPrice.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_monthly_price),
                Toast.LENGTH_LONG).show()
            return false
        } else if (regionalSpecificationName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_regional_specification),
                Toast.LENGTH_LONG).show()
            return false
        } else if (exteriourColorName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_exteriour_colour),
                Toast.LENGTH_LONG).show()
            return false
        } else if (transmissionTypeName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_transmission_type),
                Toast.LENGTH_LONG).show()
            return false
        } else if (horsePowerName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_horse_power),
                Toast.LENGTH_LONG).show()
            return false
        } else if (sellerTypeName.equals("Select", true) && binding.consLayoutSellerTypeAndFullServiceHistory.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_seller_type),
                Toast.LENGTH_LONG).show()
            return false
        } else if (fullServiceHistoryName.equals("Select", true) && binding.consLayoutSellerTypeAndFullServiceHistory.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_full_service_history),
                Toast.LENGTH_LONG).show()
            return false
        }
        else if (deliveryChargesName.equals("Select", true)  && binding.consLayoutDeliveryChargesAndPaymentMethod.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_full_service_history),
                Toast.LENGTH_LONG).show()
            return false
        }
        else if (paymentMethodName.equals("Select", true)  && binding.consLayoutDeliveryChargesAndPaymentMethod.visibility == View.VISIBLE) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_full_service_history),
                Toast.LENGTH_LONG).show()
            return false
        }
        else if (title.isNullOrEmpty()) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_title),
                Toast.LENGTH_LONG).show()
            return false
        }
        else if (description.isNullOrEmpty()) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_description_message),
                Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun getDataFromEditText() {
        //chasisNumber = binding.edtEnterChasisNumber.getTextInString()
        kiloMetere = binding.edtTextKilometers.getTextInString()
        price = binding.edtTextPrice.getTextInString()
        deposit = binding.edtTextDepositPrice.getTextInString()
        dailyPrice = binding.edtTextDailyPrice.getTextInString()
        weeklyPrice = binding.edtTextWeeklyPrice.getTextInString()
        monthlyPrice = binding.edtTextMonthlyPrice.getTextInString()
        title = binding.edtTextTitle.getTextInString()
        description = binding.edtTextDescription.getTextInString()
    }

    private fun callCarModelTypeApi() {
        viewModel.getCarModelResponse(makeId)

        viewModel.carModelResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

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


                }
                is Resource.Failure -> handleApiErrors(it)
            }

        })
    }

    private fun callCitiesListApi() {
        viewModel.getAddCarStepCitiesResponse("1")

        viewModel.citiesListResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

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
                    } else {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
                    }


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
                    } else {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
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

            try {
                actualImage = photoFile
            } catch (e: IOException) {
                e.printStackTrace()
            }
            compressImage()


        } else if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == AppCompatActivity.RESULT_OK) {

            if (data != null) {


                if (data.getData() != null) {
                    // if single image is selected

                    try {
                        actualImage = FileUtil.from(requireContext(), data.data)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    compressImage()


                }

            }
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
                        ), REQUEST_TAKE_PHOTO
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

            listImage.add(AddMultipleImageModel(image_uri, imageName))
            setImageRecyclerView()

        }
    }

    private fun setImageRecyclerView() {

        arrForImagePosition.add("${arrayOfImages.size}")

        val addMultipleImageAdapter =
            AddMultipleImageAdapter(requireContext(), arrayOfImages, this@AddCarStepOneFragment,arrForImagePosition)
        //addMultipleImageAdapter.positionList(arrImages)
        binding.recyclerviewMultipleImageUpload.adapter = addMultipleImageAdapter
        binding.recyclerviewMultipleImageUpload.setNestedScrollingEnabled(false);
        binding.recyclerviewMultipleImageUpload.setLayoutManager(GridLayoutManager(requireContext(),
            2))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val india = LatLng(23.63936, 79.14712)
        map.addMarker(MarkerOptions().position(india).title("India location"))
        map.moveCamera(CameraUpdateFactory.newLatLng(india))
    }

   // lateinit var arrImages: ArrayList<HashMap<String, String>>
    val arrOfImageList: ArrayList<MultipartBody.Part> = ArrayList()
    var arrayOfImages: ArrayList<ImageIndexSelectionModel> = ArrayList()
    var index = 0

    private fun compressImage() {
        actualImage?.let { imageFile ->

            lifecycleScope.launch(Dispatchers.Main) {

                compressedImage = Compressor.compress(requireContext(), imageFile) {
                    resolution(600, 540)
                    quality(30)
                    format(Bitmap.CompressFormat.JPEG)
                    size(1024000)

                }
                delay(500)

                arrOfImageList.add(createMutiPartForm())

                index++
                arrayOfImages.add(ImageIndexSelectionModel(compressedImage!!.absolutePath, index))
            /*    arrImages = ArrayList()

                if (arrOfImageList.size > 0) {

                    arrOfImageList.forEachIndexed { index, item ->
                        val hashMap = HashMap<String, String>()
                        hashMap.put(Const.KEY_ID, "" + "${index+1}")
                        hashMap.put(Const.KEY_NAME, "" + "${index+1}")
                        arrImages.addAll(listOf(hashMap))
                    }

                    arrayOfImages.add(compressedImage!!.absolutePath)

                }
*/
                setImageRecyclerView()

            }


        }

    }
    fun startIndexFromCurrentIndex(i: Int) {
        if(arrayOfImages.isNullOrEmpty()){
            index = 0
            arrayOfImages.clear()
            arrForImagePosition.clear()
        }else{
            index=i
        }

    }
   var count=0
    fun createMutiPartForm(): MultipartBody.Part {
        var image: MultipartBody.Part? = null
        count++
        try {
            var file = FileUtil.from(LuxuryCarApplication.instance, Uri.fromFile(compressedImage))
            val surveyBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            image = MultipartBody.Part.createFormData("images[]", count.toString(), surveyBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image!!
    }

    /*var arrOfImagesPostion = ArrayList<Int>()
    fun onSpinnerItemOnClick(pos: Int) {
        if (arrOfImagesPostion.contains(pos)) {

            arrOfImagesPostion.remove(pos)
            arrOfImagesPostion.add(pos)

            Toast.makeText(requireContext(),"item removed than added", Toast.LENGTH_LONG).show()

        } else {

            arrOfImagesPostion.add(pos)
            Toast.makeText(requireContext(),"item  added", Toast.LENGTH_LONG).show()

        }

    }*/
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


                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

}







