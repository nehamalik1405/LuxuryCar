package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.StorageRepository
import com.a.luxurycar.code_files.ui.home.adapter.StorageAdapter
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.a.luxurycar.code_files.view_model.StorageViewModel
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentStorageBinding
import com.squareup.picasso.Picasso


class StorageFragment : BaseFragment<StorageViewModel,FragmentStorageBinding,StorageRepository>() {

    lateinit var enquiesList: ArrayList<CmsbannerContent>

    override fun getViewModel() = StorageViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentStorageBinding.inflate(inflater, container, false)

    override fun getRepository() =
        StorageRepository(ApiAdapter.buildApi(ApiService::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* addEnuireList()
        setRecyclerViewAdapter()*/
        callCmsApi()

    }

    private fun callCmsApi() {
        cmsApi()
        viewModel.cmsResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        if(!it.values.data?.storagePage?.cmsBanner?.image.isNullOrEmpty()){
                            Picasso.get().load(it.values.data?.storagePage?.cmsBanner?.image.toString()).into(binding.imgViewCar)
                        }
                        if(!it.values.data?.storagePage?.cmsBanner?.cmsbannerContent?.isEmpty()!!){
                            enquiesList=it.values.data?.storagePage?.cmsBanner?.cmsbannerContent as  ArrayList<CmsbannerContent>
                            setRecyclerViewAdapter()
                            binding.rootStoragePage.visibility = View.VISIBLE
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

  /*  private fun addEnuireList() {
        list = arrayListOf()
        list.add(StorageModel(R.mipmap.ic_engine,getString(R.string.str_engine_starts)))
        list.add(StorageModel(R.mipmap.ic_anti_flat,getString(R.string.str_anti_flat_spot_management)))
        list.add(StorageModel(R.mipmap.ic_insurance,getString(R.string.str_insurance)))
        list.add(StorageModel(R.mipmap.ic_battery,getString(R.string.str_battery_trickle_vharging)))
        list.add(StorageModel(R.mipmap.ic_sanitization,getString(R.string.str_interior_preparation_and_sanitization)))
        list.add(StorageModel(R.mipmap.ic_tire_rotation,getString(R.string.str_tire_rotation_and_checks)))
    }*/

    private fun setRecyclerViewAdapter() {
        val storageAdapter = StorageAdapter(enquiesList)

        binding.recyclerViewForEnquireList.adapter = storageAdapter
        binding.recyclerViewForEnquireList.setLayoutManager(GridLayoutManager(requireContext(), 2))
        binding.recyclerViewForEnquireList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
              Toast.makeText(requireContext(),position.toString(),Toast.LENGTH_LONG).show()
            }
        })

    }


}