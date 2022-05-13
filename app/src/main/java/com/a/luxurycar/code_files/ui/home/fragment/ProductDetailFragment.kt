package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.adapter.ProductDetailViewPagerAdapter
import com.a.luxurycar.code_files.ui.home.model.ProductDetailImageModel
import com.a.luxurycar.databinding.FragmentProductDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class ProductDetailFragment : Fragment(),OnMapReadyCallback {

    var _binding:FragmentProductDetailBinding? = null
    val binding get() = _binding!!;
    lateinit var list:ArrayList<ProductDetailImageModel>
    var page = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(-33.852, 151.211)
        googleMap.addMarker(MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private fun setViewPager() {
        list = arrayListOf()
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))

        val productDetailViewPagerAdapter = ProductDetailViewPagerAdapter(requireContext(),list)
        val viewpager = binding.viewPagerProductDetailPage
        viewpager.adapter= productDetailViewPagerAdapter

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                page = (position + 1).toString()
                binding.txtViewPageCounter.text  = "$page/7"
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