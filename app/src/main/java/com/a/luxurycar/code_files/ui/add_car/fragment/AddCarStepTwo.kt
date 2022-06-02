package com.a.luxurycar.code_files.ui.add_car.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarRepository
import com.a.luxurycar.code_files.ui.home.adapter.ProductDetailViewPagerAdapter
import com.a.luxurycar.code_files.ui.home.model.ProductDetailImageModel
import com.a.luxurycar.code_files.view_model.AddCarViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentAddCarStepTwoBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class AddCarStepTwo : BaseFragment<AddCarViewModel, FragmentAddCarStepTwoBinding, AddCarRepository>(),OnMapReadyCallback {
    private lateinit var map: GoogleMap
    lateinit var list:ArrayList<ProductDetailImageModel>
    var page = ""

    override fun getViewModel()=AddCarViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAddCarStepTwoBinding.inflate(inflater,container,false)
    override fun getRepository()= AddCarRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }
    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        val india = LatLng(     23.63936, 79.14712)
        map.addMarker(MarkerOptions().position(india).title("India location"))
        map.moveCamera(CameraUpdateFactory.newLatLng(india))
    }
    private fun setViewPager() {
        list = arrayListOf()
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))
        list.add(ProductDetailImageModel(R.drawable.storage_car))
        list.add(ProductDetailImageModel(R.drawable.ic_sourcing_car2))
        list.add(ProductDetailImageModel(R.drawable.ic_sourcing_car1))

        val viewpager = binding.viewPagerProductDetailPage
        val productDetailViewPagerAdapter = ProductDetailViewPagerAdapter(requireContext(),list)
        viewpager.adapter= productDetailViewPagerAdapter

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                page = (position + 1).toString()
                binding.txtViewPageCounter.text  = "$page/${list.size}"
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })






    }
}