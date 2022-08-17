package com.a.luxurycar.code_files.ui.seller_deshboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SellerHomeRepository
import com.a.luxurycar.code_files.ui.add_car.AddCarActivity
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForRentAdapter
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForSaleAdapter
import com.a.luxurycar.code_files.view_model.SellerHomeViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.StartActivity
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentSellerHomeBinding
import com.squareup.picasso.Picasso
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list.Data

class SellerHomeFragment : BaseFragment<SellerHomeViewModel,FragmentSellerHomeBinding,SellerHomeRepository>() {


    lateinit var forSaleList:ArrayList<Data>
    lateinit var forRentList:ArrayList<Data>

    override fun getViewModel() =SellerHomeViewModel::class.java

        override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentSellerHomeBinding.inflate(inflater,container,false)

    override fun getRepository() = SellerHomeRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forRentList = arrayListOf()
        forSaleList = arrayListOf()

        manageClickListener()
        callSellerDetailApi()
        callSellerForSaleListApi()
        observeCallSellerDetailResponse()
        observeGetSellerForSaleListResponse()
        observeGetSellerForRentListResponse()
        //setForSaleList()
        //setForRentList()
    }

    private fun observeGetSellerForRentListResponse() {
        viewModel.getSellerForRenListResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarSellerHome.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        forRentList = it.values.data
                        setForRentList()

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

    private fun callSellerForRentListApi() {
        viewModel.getSellerRenListResponse("1")
    }

    private fun observeGetSellerForSaleListResponse() {
        viewModel.getSellerForSaleListResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarSellerHome.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        forSaleList = it.values.data
                            setForSaleList()

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

    private fun callSellerForSaleListApi() {
     viewModel.getSellerForSaleListResponse("0")
    }

    private fun observeCallSellerDetailResponse() {
        viewModel.sellerDatailDasboardResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarSellerHome.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        val data = it.values.data

                        Picasso.get().load(data.image).into(binding.imgViewSellerCard)
                        binding.txtViewSellerCompanyName.text = data.companyName
                        binding.txtViewPhoneNumber.text = data.phone
                        binding.txtViewLoremIpsumisSimplyDummyTextofPrintingInSellerHome.text = data.description

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

    private fun callSellerDetailApi() {
        viewModel.getSellerDetailDasboardResponse()
    }

    private fun setForRentList() {
      val forRentAdapter = ForRentAdapter(requireContext(),forRentList)
        binding.recyclerViewAllList.adapter = forRentAdapter

    }

    private fun setForSaleList() {
        val forSaleAdapter = ForSaleAdapter(requireContext(),forSaleList)
        binding.recyclerViewAllList.adapter = forSaleAdapter
    }
    private fun manageClickListener() {
        binding.conLayoutFabButton.setOnClickListener {
            StartActivity(AddCarActivity::class.java)
            //requireActivity().finishAffinity()
        }

        binding.consLayoutTabForSale.setOnClickListener {
            binding.consLayoutTabForRent.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(0)
            binding.consLayoutTabForSale.setBackgroundResource(R.drawable.drawable_tab_background)
            callSellerForSaleListApi()

        }
        binding.consLayoutTabForRent.setOnClickListener {
            binding.consLayoutTabForSale.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(0)
            binding.consLayoutTabForRent.setBackgroundResource(R.drawable.drawable_tab_background)
            callSellerForRentListApi()

        }
        binding.consLayoutTabForBuyerEnqukles.setOnClickListener {
            binding.consLayoutTabForRent.setBackgroundResource(0)
            binding.consLayoutTabForSale.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(R.drawable.drawable_tab_background)
        }
    }



}