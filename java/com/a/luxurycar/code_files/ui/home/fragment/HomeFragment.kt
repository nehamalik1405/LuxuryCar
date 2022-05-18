package com.a.luxurycar.code_files.ui.home.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.HomeRepository
import com.a.luxurycar.code_files.ui.home.adapter.AdvertieserSuggestedList
import com.a.luxurycar.code_files.ui.home.adapter.ImageAdapter
import com.a.luxurycar.code_files.ui.home.model.ImageModel
import com.a.luxurycar.code_files.view_model.HomeViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding,HomeRepository>() {

    lateinit var list:ArrayList<ImageModel>
    var currentPage = 0

    override fun getViewModel()=HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentHomeBinding.inflate(inflater,container,false)
    override fun getRepository()= HomeRepository(ApiAdapter.buildApi(ApiService::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addImage()
        setViewPager()
        setAdvertiserList()

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_storageFragment)
        }
    }

    private fun setAdvertiserList() {
        val advertieserSuggestedList = AdvertieserSuggestedList(requireContext())
        binding.recyclerviewSuggestedList.adapter = advertieserSuggestedList
        binding.recyclerviewSuggestedList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)

    }

    private fun setViewPager() {
        val imageAdapter = ImageAdapter(requireContext(),list)
        val photos_viewpager = binding.photosViewpager
        photos_viewpager.adapter= imageAdapter

        val handler = Handler()
        val update = Runnable {
            if (currentPage == list.size) {
                currentPage = 0
            }
            //The second parameter ensures smooth scrolling
            photos_viewpager.setCurrentItem(currentPage++, true)
        }
        Timer().schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(update)
            }
        }, 2500, 2500)

        TabLayoutMediator(binding.tabLayout, photos_viewpager) { tab, position -> }.attach()
    }

    /*private fun addImage() {
        list = arrayListOf()
        list.add(ImageModel("https://demonuts.com/Demonuts/SampleImages/W-03.JPG"))
        list.add(ImageModel("https://demonuts.com/Demonuts/SampleImages/W-08.JPG"))
        list.add(ImageModel("https://demonuts.com/Demonuts/SampleImages/W-10.JPG"))
        list.add(ImageModel("https://demonuts.com/Demonuts/SampleImages/W-13.JPG"))
        list.add(ImageModel("https://demonuts.com/Demonuts/SampleImages/W-17.JPG"))
        list.add(ImageModel("https://demonuts.com/Demonuts/SampleImages/W-21.JPG"))
    }*/
}


/*  CoroutineScope(Dispatchers.Main).launch {
         for ( i  in list){
                delay(2000)

                photos_viewpager.currentItem++

            }


        }*/