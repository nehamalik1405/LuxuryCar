package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.FindGaragesRepository
import com.a.luxurycar.code_files.ui.home.adapter.FindGarageAdapter
import com.a.luxurycar.code_files.view_model.FindGaragesViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentFindGaragesBinding
import com.a.luxurycar.code_files.ui.seller_deshboard.model.find_garages.Data


class FindGaragesFragment : BaseFragment<FindGaragesViewModel,FragmentFindGaragesBinding,FindGaragesRepository>() {

    lateinit var findGaragesList:ArrayList<Data>
    lateinit var searchFindGaragesList:ArrayList<Data>
    lateinit var findGarageAdapter:FindGarageAdapter

    override fun getViewModel() = FindGaragesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )=FragmentFindGaragesBinding.inflate(inflater,container,false)
    override fun getRepository()= FindGaragesRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findGaragesList = arrayListOf()
        filterFindGaragesList()
        callFindGaragesApi()
        observeCallFindGaragesApiResponse()
    }

    private fun filterFindGaragesList() {
        binding.edtTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(editable: Editable?) {
                filterList(editable.toString())
            }
        })
    }

    private fun filterList(filterItem: String) {

        searchFindGaragesList = (findGaragesList.filter {
            it.companyName.contains(filterItem, true)
        } as ArrayList<Data>?)!!
        findGarageAdapter.getSearchGarageListList(searchFindGaragesList!!)
    }

    private fun observeCallFindGaragesApiResponse() {
        viewModel.getFindGaragesResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarFindGarages.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        findGaragesList = it.values.data
                        setFindGaragesList()
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

    private fun callFindGaragesApi() {
      viewModel.getFindGaragesResponse()

    }

    private fun setFindGaragesList() {
        findGarageAdapter =FindGarageAdapter(findGaragesList)
        binding.recyclerViewFindGarages.adapter = findGarageAdapter
    }

}