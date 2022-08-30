package com.a.luxurycar.code_files.ui.seller_deshboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SellerHomeRepository
import com.a.luxurycar.code_files.ui.add_car.AddCarActivity
import com.a.luxurycar.code_files.ui.home.model.garage_response.GarageCar
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForRentAdapter
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForSaleAdapter
import com.a.luxurycar.code_files.view_model.SellerHomeViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.databinding.FragmentSellerHomeBinding
import com.squareup.picasso.Picasso
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list.Data
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.utils.*

class SellerHomeFragment : BaseFragment<SellerHomeViewModel,FragmentSellerHomeBinding,SellerHomeRepository>() {

    lateinit var forSaleList:ArrayList<Data>
    lateinit var forRentList:ArrayList<Data>
    lateinit var forSaleAdapter:ForSaleAdapter
    lateinit var forRentAdapter:ForRentAdapter

    override fun getViewModel() =SellerHomeViewModel::class.java

        override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        )= FragmentSellerHomeBinding.inflate(inflater,container,false)

    override fun getRepository() = SellerHomeRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkCondition()
        manageClickListener()
        callSellerDetailApi()
        callSellerForSaleListApi()

    }

    private fun checkCondition() {
        val data = SessionManager.getUserData()
        if (data?.role.equals(Const.KEY_BUYER)){
            binding.consLayoutTabForBuyerEnqukles.visibility = View.GONE

        }
    }

    private fun observeGetSellerForRentListResponse() {
        viewModel.getSellerForRenListResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarSellerHome.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        forRentList = arrayListOf()
                        forRentList = it.values.data

                        setForRentList()

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

    private fun callSellerForRentListApi() {
        rentListApi()
        observeGetSellerForRentListResponse()
    }

    private fun rentListApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getSellerRenListResponse("1")
        } else {
            requireContext().toast("No Internet Connection")
        }
    }

    private fun observeGetSellerForSaleListResponse() {
        viewModel.getSellerForSaleListResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarSellerHome.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        forSaleList = arrayListOf()
                        forSaleList = it.values.data
                            setForSaleList()

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

    private fun callSellerForSaleListApi() {
        saleListApi()
        observeGetSellerForSaleListResponse()
    }

    private fun saleListApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getSellerForSaleListResponse("0")

        } else {
            requireContext().toast("No Internet Connection")
        }

    }

    private fun observeCallSellerDetailResponse() {
        viewModel.sellerDatailDasboardResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarSellerHome.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {

                        val data = it.values.data

                        binding.rootSellerDashBoardPage.visibility = View.VISIBLE

                        if(!data.image.isNullOrEmpty()){
                            Picasso.get().load(data.image).into(binding.imgViewSellerCard)
                        }
                        if(!data.companyName.isNullOrEmpty()){
                            binding.txtViewSellerName.text = data.companyName
                        }
                        if(data.countryCode !=null){
                            binding.txtViewSellerCityName.text = data.countryCode.toString()
                        }
                        if(data.dialCode != null){
                            binding.txtViewDialCode.text = "+"+data.dialCode.toString()
                        }
                        if(!data.phone.isNullOrEmpty()){
                            binding.txtViewPhoneNumber.text = data.phone
                        }
                        if(!data.email.isNullOrEmpty()){
                            binding.txtViewEmail.text = data.email
                        }
                        if(!data.description.isNullOrEmpty()){
                            binding.txtViewLoremIpsumisSimplyDummyTextofPrintingInSellerHome.text = data.description
                        }
                        if(data.rentCars != null){
                            binding.txtViewForRentValue.text = data.rentCars.toString()
                        }
                        if(data.saleCars != null){
                            binding.txtViewForSaleValue.text = data.saleCars.toString()
                        }

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

    private fun callSellerDetailApi() {
        sellerDetailApi()

        observeCallSellerDetailResponse()
    }

    private fun sellerDetailApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getSellerDetailDasboardResponse()
        } else {
            requireContext().toast("No Internet Connection")
        }
    }

    private fun setForRentList() {
       /* binding.recyclerViewForRentList.visibility = View.VISIBLE
        binding.recyclerViewForSaleList.visibility = View.GONE*/
       forRentAdapter = ForRentAdapter(requireContext(),forRentList,this)
        binding.recyclerViewForRentList.adapter = forRentAdapter
    }

    private fun setForSaleList() {
      /*  binding.recyclerViewForRentList.visibility = View.GONE
        binding.recyclerViewForSaleList.visibility = View.VISIBLE*/
         forSaleAdapter = ForSaleAdapter(requireContext(),forSaleList,this)
        binding.recyclerViewForSaleList.adapter = forSaleAdapter
    }

    private fun manageClickListener() {
        binding.conLayoutFabButton.setOnClickListener {
            StartActivity(AddCarActivity::class.java)
            //requireActivity().finishAffinity()
        }

        binding.imgViewEditProfile.setOnClickListener {
            findNavController().navigate(R.id.nav_updateSellerProfileFragment)
        }

        binding.consLayoutTabForSale.setOnClickListener {
            binding.viewLineForSale.setBackgroundResource(R.color.yellow_color)
            binding.viewLineForRent.setBackgroundResource(R.color.green)
            binding.viewLineForFilter.setBackgroundResource(R.color.green)
            binding.viewLineForBuyerEnquiries.setBackgroundResource(R.color.green)
           /* binding.consLayoutTabForRent.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(0)
            binding.consLayoutTabForSale.setBackgroundResource(R.drawable.drawable_tab_background)*/
            binding.recyclerViewForRentList.visibility = View.GONE
            binding.recyclerViewForSaleList.visibility = View.VISIBLE
            callSellerForSaleListApi()
        }
        binding.consLayoutTabForRent.setOnClickListener {
            binding.viewLineForSale.setBackgroundResource(R.color.green)
            binding.viewLineForRent.setBackgroundResource(R.color.yellow_color)
            binding.viewLineForFilter.setBackgroundResource(R.color.green)
            binding.viewLineForBuyerEnquiries.setBackgroundResource(R.color.green)
            /*binding.consLayoutTabForSale.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(0)
            binding.consLayoutTabForRent.setBackgroundResource(R.drawable.drawable_tab_background)*/
            binding.recyclerViewForRentList.visibility = View.VISIBLE
            binding.recyclerViewForSaleList.visibility = View.GONE
            callSellerForRentListApi()
        }
        binding.consLayoutTabForBuyerEnqukles.setOnClickListener {
            binding.viewLineForSale.setBackgroundResource(R.color.green)
            binding.viewLineForRent.setBackgroundResource(R.color.green)
            binding.viewLineForFilter.setBackgroundResource(R.color.green)
            binding.viewLineForBuyerEnquiries.setBackgroundResource(R.color.yellow_color)
           /* binding.consLayoutTabForRent.setBackgroundResource(0)
            binding.consLayoutTabForSale.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(R.drawable.drawable_tab_background)*/
        }
        binding.imgViewFilterList.setOnClickListener {
            binding.viewLineForSale.setBackgroundResource(R.color.green)
            binding.viewLineForRent.setBackgroundResource(R.color.green)
            binding.viewLineForFilter.setBackgroundResource(R.color.yellow_color)
            binding.viewLineForBuyerEnquiries.setBackgroundResource(R.color.green)
            /* binding.consLayoutTabForRent.setBackgroundResource(0)
             binding.consLayoutTabForSale.setBackgroundResource(0)
             binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(R.drawable.drawable_tab_background)*/
        }
    }
    fun callDeleteItemApi(id:String){
        viewModel.getDeleteCarResponse(id)
        viewModel.getDeleteCarResponse.observe(viewLifecycleOwner, Observer {

            binding.progressBarSellerHome.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        callSellerForSaleListApi()
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



}