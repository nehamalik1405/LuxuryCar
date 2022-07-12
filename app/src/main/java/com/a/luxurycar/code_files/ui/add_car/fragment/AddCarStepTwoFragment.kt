package com.a.luxurycar.code_files.ui.add_car.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarStepTwoRepository
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.Data
import com.a.luxurycar.code_files.ui.home.adapter.ProductDetailViewPagerAdapter
import com.a.luxurycar.code_files.ui.home.model.ProductDetailImageModel
import com.a.luxurycar.code_files.view_model.AddCarStepTwoViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentAddCarStepTwoBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class AddCarStepTwoFragment : BaseFragment<AddCarStepTwoViewModel, FragmentAddCarStepTwoBinding, AddCarStepTwoRepository>(),OnMapReadyCallback {
    private lateinit var map: GoogleMap
    lateinit var list:ArrayList<ProductDetailImageModel>
    var page = ""

    override fun getViewModel()=AddCarStepTwoViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAddCarStepTwoBinding.inflate(inflater,container,false)
    override fun getRepository()= AddCarStepTwoRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callSellCarStepTwoApi()
        observeSellCarStepTwoApiResponse()
        setViewPager()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    private fun observeSellCarStepTwoApiResponse() {
        viewModel.getAddCarStepTwoResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbarAddCarStepTwo.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        val data = it.values.data
                        addCarDataSet(data)

                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()

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

    private fun addCarDataSet(data: Data) {

        // set the detail
        binding.txtViewChevroletComoro.text = data.title
        binding.txtViewAED.text = data.price+"AED"
        binding.txtViewMakeResult.text = data.makeId.toString()
        binding.txtViewModalResult.text = data.carModelId.toString()
        binding.txtView2021.text = data.carYear
        binding.txtViewKilometersResults.text = data.runKms
        binding.txtViewPriceInAED.text = data.price
        binding.txtViewFiveStarGlobalNCAP.text = data.regionalSpecification
        binding.txtViewExteriorColorType.text = data.exteriorColorId.toString()
        binding.txtViewTransmissionTypeManual.text = data.transmissionType
        binding.txtViewHoursePowerType.text = data.horsePowerId.toString()

        // set the description
         binding.txtViewLoremIpsumisSimplyDummyTextofPrinting.text = data.description.toString()


    }

    private fun callSellCarStepTwoApi() {
        val bundle = arguments
        var id = ""
        if (bundle != null) {
            id = bundle.getString("car_ads_id").toString()
        }
     viewModel.getAddSellerListingPlan("95")

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
               // binding.txtViewPageCounter.text  = "$page/${list.size}"
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