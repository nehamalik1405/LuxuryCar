package com.a.luxurycar.code_files.ui.add_car.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.a.luxurycar.code_files.ui.add_car.model.step_three_listing_plan.Data
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarStepThreeRepository
import com.a.luxurycar.code_files.ui.add_car.adapter.SellerPlanListAdapter
import com.a.luxurycar.code_files.view_model.AddCarStepThreeViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.convertJsonToRequestBody
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentAddCarStepThreeBinding
import org.json.JSONObject


class AddCarStepThreeFragment :
    BaseFragment<AddCarStepThreeViewModel, FragmentAddCarStepThreeBinding, AddCarStepThreeRepository>() {

    lateinit var planList:List<Data>

    override fun getViewModel() = AddCarStepThreeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAddCarStepThreeBinding.inflate(inflater, container, false)

    override fun getRepository() = AddCarStepThreeRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planList = arrayListOf()
        callSellCarStepThreeApi()
        observeSellerStepThreeResponse()
        observeAddCarStepThreeSelectedPlanResponse()

    }

    private fun observeAddCarStepThreeSelectedPlanResponse() {
        viewModel.getAddCarStepThreeSelectedPlan.observe(viewLifecycleOwner, Observer {

            binding.progressBarForAddCarStepThree.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()

                    }
                    else{
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }

        })
    }

    private fun setSellerPlanListOnRecyclerView() {
        val sellerPlanListAdapter = SellerPlanListAdapter(requireContext(),planList as ArrayList<Data>,this@AddCarStepThreeFragment /* = java.util.ArrayList<com.a.luxurycar.code_files.ui.add_car.model.step_three_listing_plan.Data> */)
        binding.recyclerViewSelectedPlan.adapter = sellerPlanListAdapter
    }

    private fun observeSellerStepThreeResponse() {
        viewModel.getAddSellerListingPlanResponse.observe(viewLifecycleOwner , Observer {
            binding.progressBarForAddCarStepThree.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {

                        planList=it.values.data
                        setSellerPlanListOnRecyclerView()
                       /* Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()*/

                    }
                   else{
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun callSellCarStepThreeApi() {
     viewModel.getAddSellerListingPlan()
    }

    fun onItemClickListner(listPlanId: String, status: String) {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_LISTING_PLAN_ID, listPlanId)
            jsonObject.put(Const.PARAM_STATUS, status)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getAddCarStepThreeSelectedPlan(body)

    }
}