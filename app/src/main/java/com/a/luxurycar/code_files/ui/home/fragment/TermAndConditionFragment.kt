package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.TermAndConditionRepository
import com.a.luxurycar.code_files.view_model.TermAndConditionViewModel
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.toast
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentTermAndConditionBinding

class TermAndConditionFragment :
    BaseFragment<TermAndConditionViewModel, FragmentTermAndConditionBinding, TermAndConditionRepository>() {


    override fun getViewModel() = TermAndConditionViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentTermAndConditionBinding.inflate(inflater, container, false)

    override fun getRepository() =
        TermAndConditionRepository(ApiAdapter.buildApi(ApiService::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callCmsApi()

    }

    private fun callCmsApi() {
        cmsApi()
        viewModel.cmsResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        if (!it.values.data?.termsConditions?.content?.isEmpty()!!) {
                            val plainText = Html.fromHtml(it.values.data?.termsConditions?.content).toString()
                            binding.txtViewTermConditions.text = plainText
                        }
                    } else {
                        requireContext().toast(it.values.message!!)
                    }
                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }
        })
    }

    private fun cmsApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getCmsResponse()
        } else {
            requireContext().toast("No Internet Connection")
        }
    }


}