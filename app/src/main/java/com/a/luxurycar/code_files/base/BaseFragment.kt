package com.a.luxurycar.code_files.base;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.a.luxurycar.MainActivity
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiAdapter.Companion.buildApi
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.utils.StartActivity
import kotlinx.coroutines.launch


abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val apiAdapter = ApiAdapter()

    protected lateinit var userPreferences: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userPreferences = SessionManager(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        return binding.root
    }


    fun logout() = lifecycleScope.launch {
        //val authToken = userPreferences.authToken.first()
        val api = buildApi(ApiService::class.java, "authToken")
        viewModel.logout(api)
        //userPreferences.clear()
        requireActivity().StartActivity(MainActivity::class.java)
    }


    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getRepository(): R



}