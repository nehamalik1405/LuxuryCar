package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.HomeRepository
import com.a.luxurycar.code_files.ui.home.adapter.ImageAdapter
import com.a.luxurycar.code_files.ui.home.adapter.PremiumListAdapter
import com.a.luxurycar.code_files.ui.home.adapter.PromotedListAdapter
import com.a.luxurycar.code_files.ui.home.adapter.SuggestedListAdapter
import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.Listt
import com.a.luxurycar.code_files.view_model.HomeViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding,HomeRepository>() {
     lateinit var arrMakeListHashMap:ArrayList<HashMap<String, String>>
     lateinit var arrModelListHashMap:ArrayList<HashMap<String, String>>
     lateinit var arrYearFromListHashMap:ArrayList<HashMap<String, String>>
     lateinit var arrYearToListHashMap:ArrayList<HashMap<String, String>>
     lateinit var arrPriceFromListHashMap:ArrayList<HashMap<String, String>>
     lateinit var arrPriceToListHashMap:ArrayList<HashMap<String, String>>
    lateinit var arrSuggestedList : ArrayList<Listt>
    lateinit var arrPremiumList : ArrayList<Listt>
    lateinit var arrPromotedList : ArrayList<Listt>
    lateinit var arrBannerList : ArrayList<Listt>

    override fun getViewModel()=HomeViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentHomeBinding.inflate(inflater,container,false)
    override fun getRepository()= HomeRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSelectionOnButton()
        callHomePageApi()
        manageSpinnerItemsLIst()
        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.productDetailFragment)
        }
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
        binding.spinnerYearFrom.adapter = adapterYearFromList

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

        viewModel.AdvertiserSuggestedListResponse.observe(viewLifecycleOwner , androidx.lifecycle.Observer {
            binding.progressBarHomePage.visible(it is Resource.Loading)
            when (it) {

                is Resource.Success -> {
                    if(it != null) {

                        //initiaize suggested list
                        arrBannerList = arrayListOf()
                        arrSuggestedList = arrayListOf()
                        arrPremiumList = arrayListOf()
                        arrPromotedList = arrayListOf()

                        arrBannerList=it.values.data!!.banners!!.list

                        arrSuggestedList=it.values.data!!.suggestedCars!!.list

                        arrPremiumList=it.values.data!!.premiumCars!!.list

                        arrPromotedList=it.values.data!!.promotedCars!!.list

                        setSuggestedList()
                        setPremiumList()
                        setPromotedList()
                        setViewPager()
                        checkListNullability()

                    }
                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })

    }
    private fun checkListNullability() {
        if (!arrSuggestedList.isEmpty()){
            binding.txtViewSuggestedListing.visibility = View.VISIBLE
            binding.imgViewSuggestedRightArrow.visibility = View.VISIBLE
        }
        if (!arrPremiumList.isEmpty()){
            binding.txtViewPremiumListing.visibility = View.VISIBLE
            binding.imgViewPremiumRightArrow.visibility = View.VISIBLE
        }
        if (!arrPromotedList.isEmpty()){
            binding.txtViewPromotedListing.visibility =View.VISIBLE
            binding.imgViewPromotedRightArrow.visibility =View.VISIBLE
        }
    }
    private fun setSelectionOnButton() {
        binding.btnToBuy.setOnClickListener{
            binding.btnToBuy.setBackgroundResource(R.drawable.drawable_button_background)
            binding.btnToRent.setBackgroundResource(R.drawable.disable_button_background)
       }
        binding.btnToRent.setOnClickListener{
           binding.btnToRent.setBackgroundResource(R.drawable.drawable_button_background)
            binding.btnToBuy.setBackgroundResource(R.drawable.disable_button_background)
        }

    }

    private fun setViewPager() {
        val imageAdapter = ImageAdapter(requireContext(),arrBannerList)
        val photos_viewpager = binding.photosViewpager
        photos_viewpager.adapter= imageAdapter
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
        val advertieserSuggestedList = SuggestedListAdapter(requireContext(),arrSuggestedList)
        binding.recyclerviewSuggestedList.adapter = advertieserSuggestedList
        binding.recyclerviewSuggestedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)

    }
    private fun setPromotedList() {
        val promotedListAdapter = PromotedListAdapter(requireContext(),arrPromotedList)
        binding.recyclerviewPromotedList.adapter = promotedListAdapter
        binding.recyclerviewPromotedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)

    }
    private fun setPremiumList() {
        val premiumListAdapter = PremiumListAdapter(requireContext(),arrPremiumList)
        binding.recyclerviewPremiumList.adapter = premiumListAdapter
        binding.recyclerviewPremiumList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)
    }
}

