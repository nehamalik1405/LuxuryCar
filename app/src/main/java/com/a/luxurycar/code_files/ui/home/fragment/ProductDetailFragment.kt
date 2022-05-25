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
import kotlinx.android.synthetic.main.nav_header.*


class ProductDetailFragment : Fragment(),OnMapReadyCallback {
    private lateinit var map: GoogleMap
    var _binding: FragmentProductDetailBinding? = null
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
        map=googleMap
        val india = LatLng(     23.63936, 79.14712)
        map.addMarker(MarkerOptions().position(india).title("india location"))
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