package com.a.luxurycar.code_files.ui.add_car.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarStepTwoRepository
import com.a.luxurycar.code_files.ui.add_car.AddCarActivity
import com.a.luxurycar.code_files.ui.add_car.adapter.AddCarPreviewAdapter
import com.a.luxurycar.code_files.ui.add_car.adapter.AddCarStepTwoDetailAdapter
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.CarAd
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.CarImage
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.Detail
import com.a.luxurycar.code_files.ui.home.fragment.SellYourCarFragment
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
    lateinit var carList:ArrayList<CarImage>
    lateinit var carAdList:ArrayList<CarAd>
    lateinit var detailList:ArrayList<Detail>

    var page = ""
    var id=""

    override fun getViewModel()=AddCarStepTwoViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAddCarStepTwoBinding.inflate(inflater,container,false)
    override fun getRepository()= AddCarStepTwoRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            id = bundle.getString("car_ads_id").toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carList = arrayListOf()
        carAdList = arrayListOf()
        detailList = arrayListOf()
        detailList = arrayListOf()

        callSellCarStepTwoApi()

        val navHostFragment=requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val fragment=  navHostFragment.childFragmentManager.fragments.get(0)
        (fragment as SellYourCarFragment).currentDestination()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun observeSellCarStepTwoApiResponse() {
        viewModel.getAddCarStepTwoResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbarAddCarStepTwo.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        val data = it.values.data.carImages
                        carAdList = it.values.data.carAds
                        detailList = it.values.data.details
                        setViewPager(data)
                        addCarDataSet()
                        setDetailRecyclerview()
                        binding.rootPreviewAddCarStepTwoPage.visibility = View.VISIBLE

                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()

                    }

                    if (!Utils.isEmptyOrNull(it.values.message)) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }
        })
    }

    private fun setDetailRecyclerview() {
        val addCarStepTwoDetailAdapter = AddCarStepTwoDetailAdapter(detailList)
        binding.recyclerViewDetails.adapter = addCarStepTwoDetailAdapter
    }

    private fun addCarDataSet() {

        for (item in carAdList){
            binding.txtViewChevroletComoro.text = item.title
            binding.txtViewAED.text = item.price+"AED"
          /*  binding.txtViewMakeResult.text = item.make.name
            binding.txtViewModalResult.text = item.carModel.name
            binding.txtView2021.text = item.carYear
            binding.txtViewKilometersResults.text = item.runKms
            binding.txtViewPriceInAED.text = item.price
            binding.txtViewFiveStarGlobalNCAP.text = item.regionalSpecification
            binding.txtViewExteriorColorType.text = item.colorEx.name
            binding.txtViewTransmissionTypeManual.text = item.transmissionType
            binding.txtViewHoursePowerType.text = item.horsePower.name*/

            // set the description
            binding.txtViewLoremIpsumisSimplyDummyTextofPrinting.text = item.description.toString()
            binding.txtViewLocationAddress.text = item.locationUrl
        }
        // set the detail



    }

    private fun callSellCarStepTwoApi() {

       viewModel.getAddSellerListingPlan(id)
        observeSellCarStepTwoApiResponse()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        val india = LatLng(     23.63936, 79.14712)
        map.addMarker(MarkerOptions().position(india).title("India location"))
        map.moveCamera(CameraUpdateFactory.newLatLng(india))
    }
    private fun setViewPager(data: ArrayList<CarImage>) {


        carList.addAll(data)



        val viewpager = binding.viewPagerProductDetailPage
        val productDetailViewPagerAdapter = AddCarPreviewAdapter(requireContext(),carList)
        viewpager.adapter= productDetailViewPagerAdapter

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                page = (position + 1).toString()
               // binding.txtViewPageCounter.text  = "$page/${carList.size}"
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