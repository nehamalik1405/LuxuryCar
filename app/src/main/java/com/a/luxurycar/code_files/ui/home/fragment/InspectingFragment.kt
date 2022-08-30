package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.InspectingRepository
import com.a.luxurycar.code_files.ui.home.adapter.InspectingAdapter
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.a.luxurycar.code_files.view_model.InspectingViewModel
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.toast
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentInspectingBinding
import com.squareup.picasso.Picasso

class InspectingFragment : BaseFragment<InspectingViewModel,FragmentInspectingBinding,InspectingRepository>() {

    var arrCmsbannerContent :ArrayList<CmsbannerContent> = ArrayList()

    override fun getViewModel() = InspectingViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentInspectingBinding.inflate(inflater,container,false)
    override fun getRepository() = InspectingRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListener()
        callCmsApi()

    }
    private fun manageClickListener() {
        binding.btnBookNo.setOnClickListener {
            findNavController().navigate(R.id.nav_book_an_appointment)
        }
        binding.btnEnquire.setOnClickListener {
            findNavController().navigate(R.id.nav_new_enquiry_form)
        }
    }


    private fun callCmsApi() {
        cmsApi()
        viewModel.cmsResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {

                        if(it.values.data?.inspection!=null)
                        {
                            binding.linLayoutEnuireNBookNow.visibility=View.VISIBLE
                            if(it.values.data.inspection.cmsBanner?.image!=null){
                                binding.cardViewForBackGroundImage.visibility=View.VISIBLE
                                Picasso.get().load(it.values.data.inspection.cmsBanner.image).into(binding.imgViewBackground)
                            }
                            if(!it.values.data.inspection.cmsBanner?.cmsbannerContent?.isEmpty()!!){
                                arrCmsbannerContent=
                                    it.values.data.inspection.cmsBanner.cmsbannerContent as ArrayList<CmsbannerContent>
                                setInspectingRecylerView()
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

    private fun setInspectingRecylerView() {
        val inspectingAdapter=InspectingAdapter(this,arrCmsbannerContent)
        binding.recylerViewInspecting.adapter=inspectingAdapter
    }

    private fun cmsApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getCmsResponse()
        } else {
            requireContext().toast("No Internet Connection")
        }
    }
}