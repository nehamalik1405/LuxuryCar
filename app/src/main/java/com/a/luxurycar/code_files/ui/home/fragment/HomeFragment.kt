package com.a.luxurycar.code_files.ui.home.fragment

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.HomeRepository
import com.a.luxurycar.code_files.ui.auth.AuthActivity
import com.a.luxurycar.code_files.ui.home.adapter.*
import com.a.luxurycar.code_files.ui.home.model.home.*
import com.a.luxurycar.code_files.view_model.HomeViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.code_files.ui.home.model.home_response.BannerList
import com.a.luxurycar.code_files.ui.home.model.home_response.Listt
import com.a.luxurycar.code_files.ui.home.model.home_response.PlatinumPartnersList
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.StartActivity
import com.a.luxurycar.common.utils.convertJsonToRequestBody
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepository>() {
    var isShowMoreOption = false

     // lateinit var arrMakeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrModelListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearFromListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearToListHashMap: ArrayList<HashMap<String, String>>
    //lateinit var arrPriceFromListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrPriceToListHashMap: ArrayList<HashMap<String, String>>

    lateinit var arrCarSelectMakeHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrSelectModelHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrDailyPriceToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrDailyPriceFromHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrMinPriceToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrMaxPriceToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrWeeklyPriceToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrWeeklyPriceFromHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrKilometerToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrKilometerFromHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearFromHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrColorHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrDepositHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrPaymentHashMap: ArrayList<HashMap<String, String>>



    lateinit var arrSuggestedList: ArrayList<Listt>
    lateinit var arrPremiumList: ArrayList<Listt>
    lateinit var arrPromotedList: ArrayList<Listt>
    lateinit var arrBannerList: ArrayList<BannerList>
    lateinit var platinumPartnersList: ArrayList<PlatinumPartnersList>


    lateinit var arrCarMake: ArrayList<Make>
    lateinit var arrModel: ArrayList<CarModel>
    lateinit var arrDailyPrizeTo: ArrayList<PriceRange>
    lateinit var arrDailyPrizeFrom: ArrayList<PriceRange>
    lateinit var arrrWeekPriceTo: ArrayList<PriceRange>
    lateinit var arrWeeklyPriceFrom: ArrayList<PriceRange>
    lateinit var arrKilometerTo: ArrayList<KmsIncluded>
    lateinit var arrKilometerFrom: ArrayList<KmsIncluded>
    lateinit var arrCarColor: ArrayList<Color>
    lateinit var arrMinPrice: ArrayList<PriceRange>
    lateinit var arrMaxPrice: ArrayList<PriceRange>
    lateinit var arrDeposit: ArrayList<Deposit>
    lateinit var arrPayment: ArrayList<PaymentMethod>
    lateinit var arrTransmissionType: ArrayList<TransmissionType>

    var makeId = ""
    var modelId = ""
    var kilometerTo = ""
    var kilometerFrom = ""
    var yearTo = ""
    var yearFrom = ""
    var priceTo = ""
    var priceFrom = ""
    var deposit = ""
    var payment = ""
    var dailyPriceTo = ""
    var dailyPriceFrom = ""
    var weeklyPriceTo = ""
    var weeklyPriceFrom = ""
    var colorId = ""

    var isBuy="0"


    override fun getViewModel() = HomeViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getRepository() = HomeRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPreviousYearList()
        setDefaultMakeAndModel()
        setDeafultKiloMeterAndColor()
        setDefaultPrice()
        setDefaultPaymentAndDepositItem()
        setSelectionOnButton()
        callHomePageApi()
        //manageSpinnerItemsLIst()

        manageClickListener()



    }
    private fun getPreviousYearList() {
        val list = arrayListOf<String>()
        arrYearToHashMap = ArrayList()
        arrYearFromHashMap = ArrayList()

        //default item for all list
        val hashMapDefaultitem1 = HashMap<String, String>()
        hashMapDefaultitem1.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem1.put(Const.KEY_NAME, "Select Year [To]")

        //add default item
        arrYearToHashMap.add(hashMapDefaultitem1)

        val hashMapDefaultitem2 = HashMap<String, String>()
        hashMapDefaultitem2.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem2.put(Const.KEY_NAME, "Select Year [From]")

        //add default item
        arrYearFromHashMap.add(hashMapDefaultitem2)


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
            arrYearToHashMap.add(hashMap)
            arrYearFromHashMap.add(hashMap)
        }

        val adapteryearTo = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrYearToHashMap
        )


        //Drop down layout style - list view with radio button
        adapteryearTo.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerYearTo.setAdapter(adapteryearTo)


        val adapteryearFrom = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrYearFromHashMap
        )


        //Drop down layout style - list view with radio button
        adapteryearFrom.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerYearFrom.setAdapter(adapteryearFrom)
    }
    var selectedTransmissionType=""
    private fun manageClickListener() {

        binding.btnSearch.setOnClickListener {
            if(SessionManager.isUserLoggedIn()){

                if(!arrSelectedTransmission.isEmpty()){
                    selectedTransmissionType=arrSelectedTransmission.joinToString()
                    callSearchFilterApi()
                }

            }else{
                StartActivity(AuthActivity::class.java)
            }

        }

        binding.txtViewMoreOptions.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.txtViewMoreOptions.setOnClickListener {
            isShowMoreOption = !isShowMoreOption
            if (isShowMoreOption) {
                binding.consLayoutTransmissionType.visibility = View.VISIBLE
                binding.txtViewMoreOptions.text = getString(R.string.str_less_options)
            } else {
                binding.consLayoutTransmissionType.visibility = View.GONE
                binding.txtViewMoreOptions.text = getString(R.string.str_more_options)
            }


        }
        binding.imgViewSuggestedRightArrow.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", Const.PARAM_SUGGESTED)
            bundle.putString("list_name", "Suggested listing")
            findNavController().navigate(R.id.nav_car_listing, bundle)
        }

        binding.imgViewPremiumRightArrow.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", Const.PARAM_PREMIUM)
            bundle.putString("list_name", "Premium listing")
            findNavController().navigate(R.id.nav_car_listing, bundle)
        }

        binding.imgViewFeaturedRightArrow.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", Const.PARAM_FEATURED)
            bundle.putString("list_name", "Featured listing")
            findNavController().navigate(R.id.nav_car_listing, bundle)
        }

        binding.spinnerSelectedMake.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    makeId = arrCarSelectMakeHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerSelectedModel.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    modelId = arrSelectModelHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerDaiyPriceTo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    dailyPriceTo = arrDailyPriceToHashMap[position].get(Const.KEY_ID).toString()
                }

            }
        binding.spinnerDailyPriceFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    dailyPriceFrom = arrDailyPriceFromHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerWeeklyPriceTo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    weeklyPriceTo = arrWeeklyPriceToHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerWeeklyPriceFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    weeklyPriceFrom = arrWeeklyPriceFromHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerKilometerTo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    kilometerTo = arrKilometerToHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerKilometerFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    kilometerFrom = arrKilometerFromHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerSelectColor.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    colorId = arrColorHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerYearTo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    yearTo = arrYearToHashMap[position].get(Const.KEY_NAME).toString()
                }

            }

        binding.spinnerYearFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                     yearFrom= arrYearFromHashMap[position].get(Const.KEY_NAME).toString()
                }

            }

        binding.spinnerPriceTo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    priceTo = arrMinPriceToHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerPriceFrom.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    priceFrom = arrMaxPriceToHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerDeposit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    deposit = arrDepositHashMap[position].get(Const.KEY_ID).toString()
                }

            }

        binding.spinnerPayment.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    payment = arrPaymentHashMap[position].get(Const.KEY_ID).toString()
                }

            }
    }

    private fun callSearchFilterApi() {
        val userId = SessionManager.getUserData()?.id.toString()

        val jsonObject = JSONObject()
        try {
            if (isBuy.equals("0")){
                jsonObject.put(Const.PARAM_MAKE_ID_FOR_SEARCH_ADS, makeId)
                jsonObject.put(Const.PARAM_CAR_MODEL_ID, modelId)
                jsonObject.put(Const.PARAM_KILOMETER_FROM_FOR_SEARCH_ADS, kilometerFrom)
                jsonObject.put(Const.PARAM_KILOMETER_TO_FOR_SEARCH_ADS, kilometerTo)
                jsonObject.put(Const.PARAM_TRAMISION_TYPE_FOR_SEARCH_ADS,selectedTransmissionType)
                jsonObject.put(Const.PARAM_EXTERIOR_COLOR_ID,colorId)
                jsonObject.put(Const.PARAM_USER_ID, SessionManager.getUserData()?.id.toString())
                jsonObject.put(Const.PARAM_RENT,isBuy)
                jsonObject.put(Const.PARAM_TO_YEAR,yearTo)
                jsonObject.put(Const.PARAM_FROM_YEAR,yearFrom)
                jsonObject.put(Const.PARAM_MIN_PRICE,priceTo)
                jsonObject.put(Const.PARAM_MAX_PRICE,priceFrom)
            }
            if (isBuy.equals("1")){
                jsonObject.put(Const.PARAM_MAKE_ID_FOR_SEARCH_ADS, makeId.toInt())
                jsonObject.put(Const.PARAM_CAR_MODEL_ID, modelId.toInt())
                jsonObject.put(Const.PARAM_KILOMETER_FROM_FOR_SEARCH_ADS, kilometerFrom)
                jsonObject.put(Const.PARAM_KILOMETER_TO_FOR_SEARCH_ADS, kilometerTo)
                jsonObject.put(Const.PARAM_TRAMISION_TYPE_FOR_SEARCH_ADS,"Manual")
                jsonObject.put(Const.PARAM_EXTERIOR_COLOR_ID,colorId.toInt())
                jsonObject.put(Const.PARAM_USER_ID,userId.toInt())
                jsonObject.put(Const.PARAM_RENT,isBuy)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()

        viewModel.getsearchAdsResponseResponse(body)

        viewModel.searchAdsResponseResponse.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                binding.progressBarHomePage.visible(it is Resource.Loading)
                when (it) {

                    is Resource.Success -> {
                        if (it != null && it.values.status == 1) {
                            Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                                .show()

                        } else {
                            Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    is Resource.Failure -> handleApiErrors(it)
                }
            })
    }

    private fun liveDataObserver() {
        viewModel.advertiserSuggestedListResponse.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                binding.progressBarHomePage.visible(it is Resource.Loading)
                when (it) {

                    is Resource.Success -> {
                        if (it != null && it.values.status == 1) {

                            //initiaize suggested list
                            arrBannerList = arrayListOf()
                            arrSuggestedList = arrayListOf()
                            arrPremiumList = arrayListOf()
                            arrPromotedList = arrayListOf()
                            platinumPartnersList = arrayListOf()
                            arrDeposit = arrayListOf()
                            arrPayment = arrayListOf()
                            arrTransmissionType = arrayListOf()


                            arrCarMake = arrayListOf()
                            arrModel = arrayListOf()
                            arrDailyPrizeFrom = arrayListOf()
                            arrDailyPrizeTo = arrayListOf()
                            arrMinPrice = arrayListOf()
                            arrMaxPrice = arrayListOf()
                            arrrWeekPriceTo = arrayListOf()
                            arrWeeklyPriceFrom = arrayListOf()
                            arrKilometerTo = arrayListOf()
                            arrKilometerFrom = arrayListOf()
                            arrCarColor = arrayListOf()



                            arrBannerList = it.values.data!!.banners!!.list

                            arrSuggestedList = it.values.data!!.suggestedCars!!.list

                            arrPremiumList = it.values.data!!.premiumCars!!.list

                            arrPromotedList = it.values.data!!.featuredCars!!.list

                            platinumPartnersList = it.values.data!!.platinumPartners!!.list

                            if (it.values.data!!.search_filters_list.list.makes != null && !it.values.data!!.search_filters_list.list.makes.isEmpty()) {
                                arrCarMake =
                                    it.values.data!!.search_filters_list.list.makes as ArrayList<Make>
                            }
                            if (it.values.data!!.search_filters_list.list.deposit != null && !it.values.data!!.search_filters_list.list.deposit.isEmpty()) {
                                arrDeposit =
                                    it.values.data!!.search_filters_list.list.deposit as ArrayList<Deposit>
                            }
                            if (it.values.data!!.search_filters_list.list.paymentMethod != null && !it.values.data!!.search_filters_list.list.paymentMethod.isEmpty()) {
                                arrPayment =
                                    it.values.data!!.search_filters_list.list.paymentMethod as ArrayList<PaymentMethod>
                            }
                            if (it.values.data!!.search_filters_list.list.carModels != null && !it.values.data!!.search_filters_list.list.carModels.isEmpty()) {
                                arrModel =
                                    it.values.data!!.search_filters_list.list.carModels
                            }
                            if (it.values.data!!.search_filters_list.list.priceRange != null && !it.values.data!!.search_filters_list.list.priceRange.isEmpty()) {
                                arrrWeekPriceTo =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>
                                arrWeeklyPriceFrom =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>
                                arrDailyPrizeFrom =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>
                                arrDailyPrizeTo =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>

                                arrMinPrice =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>

                                arrMaxPrice =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>


                            }
                            if (it.values.data!!.search_filters_list.list.colors != null && !it.values.data!!.search_filters_list.list.colors.isEmpty()) {
                                arrCarColor =
                                    it.values.data!!.search_filters_list.list.colors as ArrayList<Color>
                            }
                            if (it.values.data!!.search_filters_list.list.kmsIncluded != null && !it.values.data!!.search_filters_list.list.kmsIncluded.isEmpty()) {
                                arrKilometerFrom =
                                    it.values.data!!.search_filters_list.list.kmsIncluded as ArrayList<KmsIncluded>
                                arrKilometerTo =
                                    it.values.data!!.search_filters_list.list.kmsIncluded as ArrayList<KmsIncluded>

                            }
                            if (it.values.data!!.search_filters_list.list.transmissionTypes != null && !it.values.data!!.search_filters_list.list.transmissionTypes.isEmpty()) {
                                arrTransmissionType =
                                    it.values.data!!.search_filters_list.list.transmissionTypes
                            }

                            setSuggestedList()
                            setPremiumList()
                            setPromotedList()
                            setPlatinumPartnersList()
                            setViewPager()
                            setTranmissionRecyclerView()
                            checkListNullability()

                            setPaymentMethodAndDeposit()
                            setPriceRangeSpinner()
                            setMakeAndCarModel()
                            setKiloMeterAndCarColor()


                        } else {
                            Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    is Resource.Failure -> handleApiErrors(it)
                }
            })

    }

    var arrSelectedTransmission =  ArrayList<String> ()

    fun setTranmissionTypeListener(name: String, value: Boolean) {
        if(!arrSelectedTransmission.contains(name) && value){
            arrSelectedTransmission.add(name)
        }else if(arrSelectedTransmission.contains(name) && value==false){
            arrSelectedTransmission.remove(name)
        }

    }

    private fun setTranmissionRecyclerView() {
        if (!arrTransmissionType.isEmpty()){
            val adapter = TransmissionTypeAdapter(this,arrTransmissionType)
            binding.recyclerViewTransmissionType.adapter = adapter
        }

    }

    private fun setDefaultPaymentAndDepositItem(){
        //set  deposit
        arrDepositHashMap = ArrayList()
        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Select Deposit")
        arrDepositHashMap.add(hashMapDefaultitem)

        //set  payment method
        arrPaymentHashMap = ArrayList()
        val hashMapDefaultitem2 = HashMap<String, String>()
        hashMapDefaultitem2.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem2.put(Const.KEY_NAME, "Select Payment")
        arrPaymentHashMap.add(hashMapDefaultitem2)
    }

    private fun setDeafultKiloMeterAndColor(){
        //set kilometer to
        arrKilometerToHashMap = ArrayList()
        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Kilometer [To]")
        arrKilometerToHashMap.add(hashMapDefaultitem)

        //set kilometer from
        arrKilometerFromHashMap = ArrayList()

        val hashMapDefaultitem2 = HashMap<String, String>()
        hashMapDefaultitem2.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem2.put(Const.KEY_NAME, "Kilometer [From]")
        arrKilometerFromHashMap.add(hashMapDefaultitem2)

        //set car color
        arrColorHashMap = ArrayList()

        val hashMapDefaultitem3 = HashMap<String, String>()
        hashMapDefaultitem3.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem3.put(Const.KEY_NAME, "Select Color")
        arrColorHashMap.add(hashMapDefaultitem3)

    }

    private fun setDefaultMakeAndModel(){
        //set select car make to
        arrCarSelectMakeHashMap = ArrayList()

        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Select Car Make")
        arrCarSelectMakeHashMap.add(hashMapDefaultitem)

        //set select car model to
        arrSelectModelHashMap = ArrayList()

        val hashMapDefaultitem2 = HashMap<String, String>()
        hashMapDefaultitem2.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem2.put(Const.KEY_NAME, "Select Model")
        arrSelectModelHashMap.add(hashMapDefaultitem2)

    }

    private fun setDefaultPrice(){
        //set daily price to
        arrDailyPriceToHashMap = ArrayList()

        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Daily Price [To]")
        arrDailyPriceToHashMap.add(hashMapDefaultitem)

        //set daily price from

        arrDailyPriceFromHashMap = ArrayList()

        val hashMapDefaultitem2= HashMap<String, String>()
        hashMapDefaultitem2.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem2.put(Const.KEY_NAME, "Daily Price [From]")
        arrDailyPriceFromHashMap.add(hashMapDefaultitem2)
    }

    private fun setPaymentMethodAndDeposit() {





        for (item in arrDeposit) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrDepositHashMap.add(hashMap)
        }

        val adapter = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrDepositHashMap
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerDeposit.setAdapter(adapter)

        for (item in arrPayment) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrPaymentHashMap.add(hashMap)
        }

        val adapter2 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrPaymentHashMap
        )

        adapter2.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerPayment.setAdapter(adapter2)

    }

    private fun setKiloMeterAndCarColor() {

        for (item in arrKilometerTo) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrKilometerToHashMap.add(hashMap)
        }

        val adapter = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrKilometerToHashMap
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerKilometerTo.setAdapter(adapter)


        for (item in arrKilometerFrom) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrKilometerFromHashMap.add(hashMap)
        }

        val adapter2 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrKilometerFromHashMap
        )

        adapter2.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerKilometerFrom.setAdapter(adapter2)


        for (item in arrCarColor) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrColorHashMap.add(hashMap)
        }

        val adapter3 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrColorHashMap
        )

        adapter3.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerSelectColor.setAdapter(adapter3)
    }

    private fun setMakeAndCarModel() {


        for (item in arrCarMake) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrCarSelectMakeHashMap.add(hashMap)
        }

        val adapter = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrCarSelectMakeHashMap
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerSelectedMake.setAdapter(adapter)



        for (item in arrModel) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrSelectModelHashMap.add(hashMap)
        }

        val adapter2 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrSelectModelHashMap
        )

        adapter2.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerSelectedModel.setAdapter(adapter2)


    }

    private fun setPriceRangeSpinner() {


        for (item in arrDailyPrizeTo) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrDailyPriceToHashMap.add(hashMap)
        }

        val adapter = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrDailyPriceToHashMap
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

       binding.spinnerDaiyPriceTo.setAdapter(adapter)



        for (item in arrDailyPrizeFrom) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrDailyPriceFromHashMap.add(hashMap)
        }

        val adapter2 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrDailyPriceFromHashMap
        )

        adapter2.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerDailyPriceFrom.setAdapter(adapter2)



        arrWeeklyPriceToHashMap = ArrayList()

        val hashMapDefaultitem3 = HashMap<String, String>()
        hashMapDefaultitem3.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem3.put(Const.KEY_NAME, "Weekly Price [To]")
        arrWeeklyPriceToHashMap.add(hashMapDefaultitem3)

        for (item in arrrWeekPriceTo) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrWeeklyPriceToHashMap.add(hashMap)
        }

        val adapter3 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrWeeklyPriceToHashMap
        )

        adapter3.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerWeeklyPriceTo.setAdapter(adapter3)



        arrWeeklyPriceFromHashMap = ArrayList()

        val hashMapDefaultitem4 = HashMap<String, String>()
        hashMapDefaultitem4.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem4.put(Const.KEY_NAME, "Weekly Price [From]")
        arrWeeklyPriceFromHashMap.add(hashMapDefaultitem3)

        for (item in arrWeeklyPriceFrom) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrWeeklyPriceFromHashMap.add(hashMap)
        }

        val adapter4 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrWeeklyPriceFromHashMap
        )

        adapter4.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerWeeklyPriceFrom.setAdapter(adapter4)



        arrMinPriceToHashMap = ArrayList()

        val hashMapDefaultitem5 = HashMap<String, String>()
        hashMapDefaultitem5.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem5.put(Const.KEY_NAME, "Select Price [To]")
        arrMinPriceToHashMap.add(hashMapDefaultitem5)

        for (item in arrMinPrice) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrMinPriceToHashMap.add(hashMap)
        }

        val adapter5 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrMinPriceToHashMap
        )

        adapter5.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerPriceTo.setAdapter(adapter5)


        arrMaxPriceToHashMap = ArrayList()

        val hashMapDefaultitem6 = HashMap<String, String>()
        hashMapDefaultitem6.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem6.put(Const.KEY_NAME, "Select Price [From]")
        arrMaxPriceToHashMap.add(hashMapDefaultitem6)

        for (item in arrMaxPrice) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrMaxPriceToHashMap.add(hashMap)
        }

        val adapter6 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrMaxPriceToHashMap
        )

        adapter6.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerPriceFrom.setAdapter(adapter6)



    }

    private fun setPlatinumPartnersList() {
        val ourPlatinumPartnersAdapter =
            OurPlatinumPartnersAdapter(requireContext(), platinumPartnersList, this)
        binding.recyclerviewOurPlatinumPartnersList.adapter = ourPlatinumPartnersAdapter
        binding.recyclerviewOurPlatinumPartnersList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    fun navigateToFindGaragesFragment() {
        findNavController().navigate(R.id.nav_find_Garages)
    }

   /* private fun manageSpinnerItemsLIst() {

        // select make spinner
        arrMakeListHashMap = ArrayList()
        val hashMapDefaultitem1 = HashMap<String, String>()
        hashMapDefaultitem1.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem1.put(Const.KEY_NAME, "Select Make")
        arrMakeListHashMap.add(hashMapDefaultitem1)

        val adapterMakeList = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrMakeListHashMap
        )
        adapterMakeList.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerSelectedMake.adapter = adapterMakeList


        // select model spinner
        arrModelListHashMap = ArrayList()
        val hashMapDefaultitem2 = HashMap<String, String>()
        hashMapDefaultitem2.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem2.put(Const.KEY_NAME, "Select Model")
        arrModelListHashMap.add(hashMapDefaultitem2)

        val adapterModelList = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrModelListHashMap
        )
        adapterModelList.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerSelectedModel.adapter = adapterModelList

        // select year from spinner
        arrYearFromListHashMap = ArrayList()
        val hashMapDefaultitem3 = HashMap<String, String>()
        hashMapDefaultitem3.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem3.put(Const.KEY_NAME, "Year From")
        arrYearFromListHashMap.add(hashMapDefaultitem3)

        val adapterYearFromList = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrYearFromListHashMap
        )
        adapterYearFromList.setDropDownViewResource(android.R.layout.simple_spinner_item)
      //  binding.spi.adapter = adapterYearFromList

        // select year to spinner
        arrYearToListHashMap = ArrayList()
        val hashMapDefaultitem4 = HashMap<String, String>()
        hashMapDefaultitem4.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem4.put(Const.KEY_NAME, "Year To")
        arrYearToListHashMap.add(hashMapDefaultitem4)

        val adapterYearToList = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrYearToListHashMap
        )
        adapterYearToList.setDropDownViewResource(android.R.layout.simple_spinner_item)
     //   binding.spinnerYearTo.adapter = adapterYearToList


        // select price from spinner
        arrPriceFromListHashMap = ArrayList()
        val hashMapDefaultitem5 = HashMap<String, String>()
        hashMapDefaultitem5.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem5.put(Const.KEY_NAME, "Price From")
        arrPriceFromListHashMap.add(hashMapDefaultitem5)

        val adapterPrieFromList = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrPriceFromListHashMap
        )
        adapterPrieFromList.setDropDownViewResource(android.R.layout.simple_spinner_item)
      //  binding.spinnerPriceFrom.adapter = adapterPrieFromList


        // select price to spinner
        arrPriceToListHashMap = ArrayList()
        val hashMapDefaultitem6 = HashMap<String, String>()
        hashMapDefaultitem6.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem6.put(Const.KEY_NAME, "Price To")
        arrPriceToListHashMap.add(hashMapDefaultitem6)

        val adapterPriceToList = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrPriceToListHashMap
        )
        adapterPriceToList.setDropDownViewResource(android.R.layout.simple_spinner_item)
      //  binding.spinnerPriceTo.adapter = adapterPriceToList


    }*/

    private fun callHomePageApi() {
        viewModel.getAdvertiserSuggestedListResponse()
        liveDataObserver()
    }

    private fun checkListNullability() {
        if (!arrSuggestedList.isEmpty()) {
            binding.txtViewSuggestedListing.visibility = View.VISIBLE
            binding.imgViewSuggestedRightArrow.visibility = View.VISIBLE
        }
        if (!arrPremiumList.isEmpty()) {
            binding.txtViewPremiumListing.visibility = View.VISIBLE
            binding.imgViewPremiumRightArrow.visibility = View.VISIBLE
        }
        if (!arrPromotedList.isEmpty()) {
            binding.txtViewFeaturedListing.visibility = View.VISIBLE
            binding.imgViewFeaturedRightArrow.visibility = View.VISIBLE
        }
        if (!arrBannerList.isEmpty()) {
            binding.cardViewImageViewPager.visibility = View.VISIBLE
            //binding.txtViewBannerTitle.visibility = View.VISIBLE
        }
        if (!platinumPartnersList.isEmpty()) {
            binding.txtViewOurPlatinumPartnersList.visibility = View.VISIBLE
            //binding.imgViewOurPlatinumPartnersRightArrow.visibility =View.VISIBLE
        }
    }

    private fun setSelectionOnButton() {
        binding.btnToBuy.setOnClickListener {
            binding.btnToBuy.setBackgroundResource(R.drawable.drawable_button_background)
            binding.btnToRent.setBackgroundResource(R.drawable.disable_button_background)
            isBuy="0"

           //binding.conLayoutSelectCity.visibility = View.GONE
           binding.linLayoutDailyPrice.visibility = View.GONE
           binding.linLayoutWeeklyPrice.visibility = View.GONE
           binding.linLayoutDepositAndPaymentMethod.visibility = View.GONE
           binding.linLayoutYear.visibility = View.VISIBLE
           binding.linLayoutPrice.visibility = View.VISIBLE

        }
        binding.btnToRent.setOnClickListener {
            binding.btnToRent.setBackgroundResource(R.drawable.drawable_button_background)
            binding.btnToBuy.setBackgroundResource(R.drawable.disable_button_background)
            isBuy="1"
           // binding.conLayoutSelectCity.visibility = View.VISIBLE
            binding.linLayoutDailyPrice.visibility = View.VISIBLE
            binding.linLayoutWeeklyPrice.visibility = View.VISIBLE
            binding.linLayoutDepositAndPaymentMethod.visibility = View.VISIBLE
            binding.linLayoutYear.visibility = View.GONE
            binding.linLayoutPrice.visibility = View.GONE

        }

    }

    private fun setViewPager() {
        val imageAdapter = ImageAdapter(requireContext(), arrBannerList)
        val photos_viewpager = binding.photosViewpager
        photos_viewpager.adapter = imageAdapter
        TabLayoutMediator(binding.tabLayout, photos_viewpager) { tab, position ->

        }.attach()

        val handler = Handler()
        val update = Runnable {
            val listSize = arrBannerList.size
            if (photos_viewpager.getCurrentItem() < arrBannerList.size - 1) {
                photos_viewpager.setCurrentItem(photos_viewpager.getCurrentItem() + 1, true);
            } else {
                photos_viewpager.setCurrentItem(0, true);
            }
            /*if (currentPage == listSize) {
                currentPage = 0
                photos_viewpager.setCurrentItem(currentPage++, true)
            }else{
                //The second parameter ensures smooth scrolling
                photos_viewpager.setCurrentItem(currentPage++, true)
            }*/
            // Toast.makeText(requireContext(),"${photos_viewpager.currentItem}",Toast.LENGTH_LONG).show()
        }
        Timer().schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(update)
            }
        }, 2500, 2500)
    }

    private fun setSuggestedList() {
        val advertieserSuggestedList =
            SuggestedListAdapter(requireContext(), arrSuggestedList, this)
        binding.recyclerviewSuggestedList.adapter = advertieserSuggestedList
        binding.recyclerviewSuggestedList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setPromotedList() {
        val promotedListAdapter = PromotedListAdapter(requireContext(), arrPromotedList, this)
        binding.recyclerviewPromotedList.adapter = promotedListAdapter
        binding.recyclerviewPromotedList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setPremiumList() {
        val premiumListAdapter = PremiumListAdapter(requireContext(), arrPremiumList, this)
        binding.recyclerviewPremiumList.adapter = premiumListAdapter
        binding.recyclerviewPremiumList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    fun navigateToProductDetailPage(id: String) {
        val bundle = Bundle()
        bundle.putString("product_detail_id", id)
        findNavController().navigate(R.id.productDetailFragment, bundle)
    }
}

