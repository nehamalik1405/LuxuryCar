package com.a.luxurycar.code_files.ui.home.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.ProductDetailRepository
import com.a.luxurycar.code_files.ui.home.adapter.ProductDetailOfCarAdapter
import com.a.luxurycar.code_files.ui.home.adapter.ProductDetailViewPagerAdapter
import com.a.luxurycar.code_files.ui.home.model.ProductDetailImageModel
import com.a.luxurycar.code_files.ui.home.model.product_detail_response.CarImage
import com.a.luxurycar.code_files.ui.home.model.product_detail_response.Detail
import com.a.luxurycar.code_files.view_model.ProductDetailViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentProductDetailBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions







class ProductDetailFragment : BaseFragment<ProductDetailViewModel,FragmentProductDetailBinding,ProductDetailRepository>(),OnMapReadyCallback{
    private lateinit var map: GoogleMap
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var latitude = 0.0
    var longitude = 0.0
    var distance = 0.0
    var currentPage = 0
    private lateinit var productDetailViewPagerAdapter:ProductDetailViewPagerAdapter
//    lateinit var list:ArrayList<ProductDetailImageModel>
    lateinit var arrBannerCarImageList:ArrayList<CarImage>
    lateinit var arrCarDetailList:ArrayList<Detail>

    override fun getViewModel() = ProductDetailViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentProductDetailBinding.inflate(inflater,container,false)

    override fun getRepository()= ProductDetailRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrBannerCarImageList = arrayListOf()
        arrCarDetailList = arrayListOf()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // initialize fused location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getCurrentLocation()
        callProductDetailsApi()
        observeProductDetailsApiResponse()

    }

    private fun observeProductDetailsApiResponse() {
        viewModel.getProductDetailResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarProductDetailPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if(it != null && it.values.status == 1) {
                        arrBannerCarImageList = it.values.data.carImages
                        arrCarDetailList = it.values.data.details
                        if(it.values.data.carAds !=null){
                            binding.txtViewChevroletComoro.text = it.values.data.carAds.get(0).title
                            binding.txtViewAED.text ="AED ${it.values.data.carAds.get(0).price}"
                            binding.txtViewLoremIpsumisSimplyDummyTextofPrinting.setText(Html.fromHtml(it.values.data.carAds.get(0).description).toString())
                            binding.txtViewLocationAddress.text = it.values.data.carAds.get(0).locationUrl
                        }
                         setViewPager()
                        setDeatilOfCar()
                    }
                    else{
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun setDeatilOfCar() {

        val productDetailOfCarAdapter = ProductDetailOfCarAdapter(arrCarDetailList)
        binding.recyclerViewDetails.adapter = productDetailOfCarAdapter

    }

    private fun callProductDetailsApi() {
     val bundle = arguments
        var id = ""
        if (bundle != null) {
            id = bundle.getString("product_detail_id").toString()
        }
        viewModel.getProductDetailResponse(id)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray,
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(requireContext(), "You need to grant permission to access location",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE);
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                latitude = location.latitude
                 longitude = location.longitude

                //tvProvider.text = "Provider: ${location.provider}"

            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed on getting current location",
                    Toast.LENGTH_SHORT).show()
            }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        map.isMyLocationEnabled = true

        val india = LatLng(latitude, longitude)
        map.addMarker(MarkerOptions().position(india).title("india"))
        map.moveCamera(CameraUpdateFactory.newLatLng(india))

       /* val locationA = Location("")
        locationA.latitude = latitude
        locationA.longitude = longitude
        val locationB = Location("")
        locationB.latitude = 28.6158591
        locationB.longitude = 77.3706722
        distance = (locationA.distanceTo(locationB) / 1000).toDouble()
        var dist = "$distance M"

        if (distance > 1000.0f) {
            distance = distance / 1000.0f
            dist = "$distance KM"
        }

        Toast.makeText(requireContext(),dist.toString(),Toast.LENGTH_LONG).show()*/
        map.setOnMapClickListener {
           val geocoder = Geocoder(context)
            val current = geocoder.getFromLocation(latitude,longitude,1)
            val destination = geocoder.getFromLocation(22.7529391, 75.8915147, 1)
           /* val uri = Uri.parse("geo:${latitude},${longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)*/

            val url = "http://maps.google.com/maps?saddr=" + current.get(0).subLocality +
                    "&daddr=" +destination.get(0).subLocality
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

    }
    private fun setViewPager() {
        /*list = arrayListOf()
        list.add(ProductDetailImageModel(R.drawable.ic_car_image))
        list.add(ProductDetailImageModel(R.drawable.storage_car))
        list.add(ProductDetailImageModel(R.drawable.ic_sourcing_car2))
        list.add(ProductDetailImageModel(R.drawable.ic_sourcing_car1))*/

        val viewpager = binding.viewPagerProductDetailPage
         productDetailViewPagerAdapter = ProductDetailViewPagerAdapter(requireContext(),arrBannerCarImageList)
        viewpager.adapter= productDetailViewPagerAdapter
        // To get swipe event of viewpager2
        // To get swipe event of viewpager2
        viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            // triggered when you select a new page
            override fun onPageSelected(position: Int) {
                currentPage = position + 1
                setViewPagerCurrentPage()
            }
        })
       /* productDetailViewPagerAdapter.onItemClick = {
            val dialog = Dialog(requireContext(), android.R.style.Theme_Light)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.fragment_view_pager_details_image);
            val imageCarViewpager = dialog.findViewById<ViewPager2>(R.id.imgViewCarDialog)
            val tab = dialog.findViewById<TabLayout>(R.id.tab_layout)
            val productDetailImageAdapter = ProductDetailImageAdapter(requireContext(),list)
         // productDetailImageModelList.clear()
            imageCarViewpager.adapter = productDetailImageAdapter

            // set the current position on sub viewpager in alert dialog
            imageCarViewpager.currentItem = viewpager.currentItem
            TabLayoutMediator(tab, imageCarViewpager) { tab, position -> }.attach()

            val imageBack = dialog.findViewById<ImageView>(R.id.imgViewBack)

            //item.image?.let { Picasso.get().load(it).into(imageCarDialog) };
            dialog.show();
            imageBack.setOnClickListener {
                dialog.dismiss()
            }
        }*/
    }

    private fun setViewPagerCurrentPage() {
        binding.txtViewPageCounter.text = "${ currentPage}/${arrBannerCarImageList.size}"

    }
/*    fun distance(
        lat1: Double,
        lng1: Double,
        lat2: Double,
        lng2: Double,
    ): Float {
        val earthRadius = 6371000.0 //meters
        val dLat = Math.toRadians((lat2 - lat1).toDouble())
        val dLng = Math.toRadians((lng2 - lng1).toDouble())
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1.toDouble())) * Math.cos(
            Math.toRadians(lat2.toDouble())) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c =
            2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return (earthRadius * c).toFloat()
    }*/


}