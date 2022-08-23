package com.a.luxurycar.code_files.ui.home.fragment

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.HomeRepository
import com.a.luxurycar.code_files.ui.home.adapter.*
import com.a.luxurycar.code_files.ui.home.model.home.*
import com.a.luxurycar.code_files.view_model.HomeViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.code_files.ui.home.model.home_response.BannerList
import com.a.luxurycar.code_files.ui.home.model.home_response.Listt
import com.a.luxurycar.code_files.ui.home.model.home_response.PlatinumPartnersList
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepository>() {
    var isShowMoreOption = false

    lateinit var arrMakeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrModelListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearFromListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearToListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrPriceFromListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrPriceToListHashMap: ArrayList<HashMap<String, String>>

    lateinit var arrCarSelectMakeHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrSelectModelHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrDailyPriceToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrDailyPriceFromHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrWeeklyPriceToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrWeeklyPriceFromHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrKilimeterToHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrKilimeterFromHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrColorHashMap: ArrayList<HashMap<String, String>>


    lateinit var arrSuggestedList: ArrayList<Listt>
    lateinit var arrPremiumList: ArrayList<Listt>
    lateinit var arrPromotedList: ArrayList<Listt>
    lateinit var arrBannerList: ArrayList<BannerList>
    lateinit var platinumPartnersList: ArrayList<PlatinumPartnersList>


    lateinit var arrCarMake: ArrayList<Make>
    lateinit var arrModel: ArrayList<CarModel>
    lateinit var arrDailyPrizeTo: ArrayList<PriceRange>
    lateinit var arrCarPrizeForm: ArrayList<PriceRange>
    lateinit var arrCarWeekTo: ArrayList<PriceRange>
    lateinit var arrCarMontlyTo: ArrayList<PriceRange>
    lateinit var arrKilometerTo: ArrayList<KmsIncluded>
    lateinit var arrKilometerFrom: ArrayList<KmsIncluded>
    lateinit var arrCarColor: ArrayList<Color>


    override fun getViewModel() = HomeViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getRepository() = HomeRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSelectionOnButton()
        callHomePageApi()
        manageSpinnerItemsLIst()

        manageClickListener()

    }

    private fun manageClickListener() {
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
        /*  binding.txtViewLessOptions.setOnClickListener {
              binding.consLayoutTransmissionType.visibility = View.GONE
              binding.txtViewMoreOptions.visibility = View.VISIBLE
              binding.txtViewLessOptions.visibility = View.GONE
          }*/
        /* binding.btnSearch.setOnClickListener {
             findNavController().navigate(R.id.productDetailFragment)
         }*/
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


                            arrCarMake = arrayListOf()
                            arrModel = arrayListOf()
                            arrCarPrizeForm = arrayListOf()
                            arrDailyPrizeTo = arrayListOf()
                            arrCarWeekTo = arrayListOf()
                            arrCarMontlyTo = arrayListOf()
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
                            if (it.values.data!!.search_filters_list.list.priceRange != null && !it.values.data!!.search_filters_list.list.priceRange.isEmpty()) {
                                arrCarPrizeForm =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>
                                arrCarWeekTo =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>
                                arrCarMontlyTo =
                                    it.values.data!!.search_filters_list.list.priceRange as ArrayList<PriceRange>
                                arrDailyPrizeTo =
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

                            setSuggestedList()
                            setPremiumList()
                            setPromotedList()
                            setPlatinumPartnersList()
                            setViewPager()
                            checkListNullability()

                            setPriceRangeSpinner()


                        } else {
                            Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    is Resource.Failure -> handleApiErrors(it)
                }
            })

    }

    private fun setPriceRangeSpinner() {
        arrDailyPriceToHashMap = ArrayList()

        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Daily Price [From]")
        arrPriceFromListHashMap.add(hashMapDefaultitem)

        for (item in arrCarPrizeForm) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrPriceFromListHashMap.add(hashMap)
        }

        val adapter = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrPriceFromListHashMap
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

       // binding.spinnerPriceTo.setAdapter(adapter)



        arrDailyPriceFromHashMap = ArrayList()

        val hashMapDefaultitem2= HashMap<String, String>()
        hashMapDefaultitem2.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem2.put(Const.KEY_NAME, "Daily Price [To]")
        arrPriceFromListHashMap.add(hashMapDefaultitem2)

        for (item in arrCarPrizeForm) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrDailyPriceFromHashMap.add(hashMap)
        }

        val adapter2 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrPriceFromListHashMap
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerPriceFrom.setAdapter(adapter2)



        arrWeeklyPriceToHashMap = ArrayList()

        val hashMapDefaultitem3 = HashMap<String, String>()
        hashMapDefaultitem3.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem3.put(Const.KEY_NAME, "Weekly Price [To]")
        arrWeeklyPriceToHashMap.add(hashMapDefaultitem3)

        for (item in arrCarPrizeForm) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "" + item.id)
            hashMap.put(Const.KEY_NAME, item.name.toString())
            arrWeeklyPriceToHashMap.add(hashMap)
        }

        val adapter3 = AdapterSpinner(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrPriceFromListHashMap
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerDailrPriceTo.setAdapter(adapter3)



        arrWeeklyPriceFromHashMap = ArrayList()

        val hashMapDefaultitem4 = HashMap<String, String>()
        hashMapDefaultitem4.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem4.put(Const.KEY_NAME, "Weekly Price [From]")
        arrWeeklyPriceFromHashMap.add(hashMapDefaultitem3)

        for (item in arrCarPrizeForm) {
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

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

        binding.spinnerDailrPriceFrom.setAdapter(adapter4)




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

    private fun manageSpinnerItemsLIst() {

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
        binding.spi.adapter = adapterYearFromList

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
        binding.spinnerYearTo.adapter = adapterYearToList


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
        binding.spinnerPriceFrom.adapter = adapterPrieFromList


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
        binding.spinnerPriceTo.adapter = adapterPriceToList


    }

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
            binding.txtViewBannerTitle.visibility = View.VISIBLE
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
        }
        binding.btnToRent.setOnClickListener {
            binding.btnToRent.setBackgroundResource(R.drawable.drawable_button_background)
            binding.btnToBuy.setBackgroundResource(R.drawable.disable_button_background)
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

