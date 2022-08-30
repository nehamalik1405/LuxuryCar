package com.a.luxurycar.code_files.ui.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.GarageDetailsRepository
import com.a.luxurycar.code_files.ui.home.adapter.FindGarageListForRentAdapter
import com.a.luxurycar.code_files.ui.home.adapter.FindGarageListForSaleAdapter
import com.a.luxurycar.code_files.ui.home.model.garage_response.Data
import com.a.luxurycar.code_files.ui.home.model.garage_response.GarageCar
import com.a.luxurycar.code_files.ui.home.model.garage_response.GarageDetailResponse
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForRentAdapter
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForSaleAdapter
import com.a.luxurycar.code_files.view_model.GarageDetailsViewModel
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.toast
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentGarageDetailsBinding
import com.squareup.picasso.Picasso


class GarageDetailsFragment :
    BaseFragment<GarageDetailsViewModel, FragmentGarageDetailsBinding, GarageDetailsRepository>() {

    lateinit var forSaleList:ArrayList<GarageCar>
    lateinit var forRentList:ArrayList<GarageCar>
    lateinit var findGarageListForRentAdapter:FindGarageListForRentAdapter
    lateinit var findGarageListForSaleAdapter:FindGarageListForSaleAdapter

    override fun getViewModel() = GarageDetailsViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentGarageDetailsBinding.inflate(inflater, container, false)

    override fun getRepository() =
        GarageDetailsRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forRentList = arrayListOf()
        forSaleList = arrayListOf()

        manageClickListener()
        callGarageDetailApi()
    }

    private fun callGarageDetailApi() {
        garageDetailApi()

        viewModel.getGaragesDetailsResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        binding.rootGarageDetailsPage.visibility = View.VISIBLE
                        Picasso.get().load(it.values.data?.garageDetail?.image.toString()).into(binding.imgViewSellerCard)
                        binding.txtViewGarageName.text = it.values.data?.garageDetail?.companyName
                        binding.txtViewCityName.text = it.values.data?.garageDetail?.countryCode
                        binding.txtViewDialCode.text = it.values.data?.garageDetail?.dialCode
                        binding.txtViewPhoneNumber.text = it.values.data?.garageDetail?.phone
                        binding.txtViewLoremIpsumisSimplyDummyTextofPrintingInSellerHome.text = it.values.data?.garageDetail?.description
                        binding.txtViewForRentValue.text = it.values.data?.garageDetail?.rentCars.toString()
                        binding.txtViewForSaleValue.text = it.values.data?.garageDetail?.saleCars.toString()

                        if (!it.values.data?.garageCarList.isNullOrEmpty()){
                            for (item in it.values.data?.garageCarList!!){
                                if(item?.rent.equals("0")){
                                    if (item != null) {
                                        forSaleList.add(item)
                                        setForSaleList()
                                    }
                                }
                                if(item?.rent.equals("1")){
                                    if (item != null) {
                                        forRentList.add(item)
                                        //setForRentList()
                                    }
                                }
                            }


                        }


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
    private fun setForRentList() {
        findGarageListForRentAdapter = FindGarageListForRentAdapter(requireContext(),forRentList,this)
        binding.recyclerViewAllList.adapter = findGarageListForRentAdapter
    }

    private fun setForSaleList() {
        findGarageListForSaleAdapter = FindGarageListForSaleAdapter(requireContext(),forSaleList,this)
        binding.recyclerViewAllList.adapter = findGarageListForSaleAdapter
    }
    private fun garageDetailApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            val bundle = arguments
            if (bundle != null) {
               val id = bundle.getString(Const.PARAM_ID_FOR_GARAGE).toString()
                viewModel.getGaragesDetailsResponse(id)
            }

        } else {
            requireContext().toast("No Internet Connection")
        }
    }

    fun manageClickListener() {
        binding.btnEmail.setOnClickListener {

            binding.btnEmail.setBackgroundResource(R.drawable.drawable_email_button_background)
            binding.imgViewEmail.setColorFilter(getResources().getColor(R.color.white))
            binding.txtViewEmail.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                ))

            binding.btnCall.setBackgroundResource(R.drawable.drawable_call_button_background)
            binding.imgViewCall.setColorFilter(getResources().getColor(R.color.green))
            binding.txtViewCall.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                ))

            val intent = Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:"))
            startActivity(intent)


        }

        binding.btnCall.setOnClickListener {

            binding.btnCall.setBackgroundResource(R.drawable.drawable_email_button_background)
            binding.imgViewCall.setColorFilter(getResources().getColor(R.color.white))
            binding.txtViewCall.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                ))

            binding.btnEmail.setBackgroundResource(R.drawable.drawable_call_button_background)
            binding.imgViewEmail.setColorFilter(getResources().getColor(R.color.green))
            binding.txtViewEmail.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                ))
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:")
            startActivity(intent)
        }

        binding.consLayoutTabForSale.setOnClickListener {
            setForSaleList()
            binding.viewLineForSale.setBackgroundResource(R.color.yellow_color)
            binding.viewLineForRent.setBackgroundResource(R.color.green)
           // binding.consLayoutTabForRent.setBackgroundResource(0)
           // binding.consLayoutTabForSale.setBackgroundResource(R.drawable.drawable_tab_background)


        }
        binding.consLayoutTabForRent.setOnClickListener {
            setForRentList()
            binding.viewLineForSale.setBackgroundResource(R.color.green)
            binding.viewLineForRent.setBackgroundResource(R.color.yellow_color)
          //  binding.consLayoutTabForSale.setBackgroundResource(0)
          //  binding.consLayoutTabForRent.setBackgroundResource(R.drawable.drawable_tab_background)


        }
    }


}