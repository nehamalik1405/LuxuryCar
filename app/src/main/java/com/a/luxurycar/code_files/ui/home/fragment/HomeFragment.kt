package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.HomeRepository
import com.a.luxurycar.code_files.ui.home.adapter.SuggestedListAdapter
import com.a.luxurycar.code_files.ui.home.adapter.ImageAdapter
import com.a.luxurycar.code_files.ui.home.adapter.PremiumListAdapter
import com.a.luxurycar.code_files.ui.home.adapter.PromotedListAdapter
import com.a.luxurycar.code_files.ui.home.model.ImageModel
import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.AdvertiserSuggestedListModel
import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.Listt
import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.SuggestedCars
import com.a.luxurycar.code_files.view_model.HomeViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding,HomeRepository>() {


    lateinit var arrAdvertiserSuggerstedList : ArrayList<AdvertiserSuggestedListModel>
    lateinit var arrSuggestedList : ArrayList<Listt>
    lateinit var arrPremiumList : ArrayList<Listt>
    lateinit var arrPromotedList : ArrayList<Listt>
    lateinit var arrBannerList : ArrayList<Listt>
    var currentPage = 0


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

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.productDetailFragment)
        }
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

                    }
                }

                is Resource.Failure -> handleApiErrors(it)
            }


        })

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

    private fun setSuggestedList() {


        val advertieserSuggestedList = SuggestedListAdapter(requireContext(),arrSuggestedList)
        binding.recyclerviewSuggestedList.adapter = advertieserSuggestedList
        binding.recyclerviewSuggestedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)

    }

    private fun setViewPager() {
        val imageAdapter = ImageAdapter(requireContext(),arrBannerList)
        val photos_viewpager = binding.photosViewpager
        photos_viewpager.adapter= imageAdapter


        val handler = Handler()
        val update = Runnable {
            if (currentPage == arrBannerList.size) {
                currentPage = 0
            }
            //The second parameter ensures smooth scrolling
            photos_viewpager.setCurrentItem(currentPage++, true)
           // Toast.makeText(requireContext(),"${photos_viewpager.currentItem}",Toast.LENGTH_LONG).show()
        }
        Timer().schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(update)
            }
        }, 2000, 2000)


        TabLayoutMediator(binding.tabLayout, photos_viewpager) { tab, position ->

        }.attach()
    }


}


/*  CoroutineScope(Dispatchers.Main).launch {
         for ( i  in list){
                delay(2000)

                photos_viewpager.currentItem++

            }


        }*/