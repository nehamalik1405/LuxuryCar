package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.BookAnAppointmentRepository
import com.a.luxurycar.code_files.view_model.BookAnAppointmentViewmodel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentBookAnAppointmentBinding

class BookAnAppointmentFragment : BaseFragment<BookAnAppointmentViewmodel,FragmentBookAnAppointmentBinding,BookAnAppointmentRepository>() {

    override fun getViewModel()=BookAnAppointmentViewmodel::class.java
        override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentBookAnAppointmentBinding.inflate(inflater,container,false)

    override fun getRepository() = BookAnAppointmentRepository(ApiAdapter.buildApi(ApiService::class.java))


}