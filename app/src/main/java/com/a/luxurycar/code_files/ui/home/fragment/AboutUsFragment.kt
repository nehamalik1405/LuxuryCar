package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AboutUsRepository
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.a.luxurycar.code_files.view_model.AboutUsViewModel
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.toast
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentAboutUsBinding
import com.squareup.picasso.Picasso

class AboutUsFragment : BaseFragment<AboutUsViewModel,FragmentAboutUsBinding,AboutUsRepository>() {

    var arrListContentBanner:ArrayList<CmsbannerContent> = ArrayList()

    override fun getViewModel()= AboutUsViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAboutUsBinding.inflate(inflater,container,false)

    override fun getRepository() = AboutUsRepository(ApiAdapter.buildApi(ApiService::class.java))

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
                        if(!it.values.data?.aboutUs?.content.isNullOrEmpty()){
                            binding.txtViewDescriptionOne.visibility = View.VISIBLE
                            val content = Html.fromHtml(it.values.data?.aboutUs?.content).toString()
                          binding.txtViewDescriptionOne.text = content
                        }
                        if(!it.values.data?.aboutUs?.cmsBanner?.image.isNullOrEmpty()){
                            binding.cardViewCarImageBanner.visibility = View.VISIBLE
                           Picasso.get().load(it.values.data?.aboutUs?.cmsBanner?.image.toString()).into(binding.imgViewBannerCar)
                        }
                        if(!it.values.data?.aboutUs?.cmsBanner?.cmsbannerContent.isNullOrEmpty()){
                            arrListContentBanner = it.values.data?.aboutUs?.cmsBanner?.cmsbannerContent as ArrayList<CmsbannerContent>/* = java.util.ArrayList<com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent> */



                            if (!arrListContentBanner.get(0).logo.isNullOrEmpty()){
                                binding.cardViewCarImageBanner2.visibility = View.VISIBLE
                                Picasso.get().load(arrListContentBanner.get(0).logo).into(binding.imgViewBanner2)
                            }
                            if (!arrListContentBanner.get(1).content.isNullOrEmpty()){
                                binding.linLayoutHeading.visibility = View.VISIBLE
                                val content = Html.fromHtml(arrListContentBanner.get(1).content).toString()
                                binding.txtViewHeading.text = content
                            }

                            if (!arrListContentBanner.get(2).logo.isNullOrEmpty()){
                                binding.cardViewCarImageBanner3.visibility = View.VISIBLE
                                Picasso.get().load(arrListContentBanner.get(2).logo).into(binding.imgViewBanner3)
                            }




                        }
                    } else {
                        requireContext().toast(it?.values?.message!!)
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