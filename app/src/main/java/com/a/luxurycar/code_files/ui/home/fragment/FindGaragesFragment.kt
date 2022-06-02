package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.FindGaragesRepository
import com.a.luxurycar.code_files.ui.home.adapter.FindGarageAdapter
import com.a.luxurycar.code_files.view_model.FindGaragesViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentFindGaragesBinding


class FindGaragesFragment : BaseFragment<FindGaragesViewModel,FragmentFindGaragesBinding,FindGaragesRepository>() {


    override fun getViewModel() = FindGaragesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )=FragmentFindGaragesBinding.inflate(inflater,container,false)
    override fun getRepository()= FindGaragesRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListener()
    }

    private fun manageClickListener() {
        val findGarageAdapter =FindGarageAdapter()
        binding.recyclerViewFindGarages.adapter = findGarageAdapter
    }

}