package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.UpdateDetailRepository
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.view_model.UpdateDetailViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentViewProfileBinding


class ViewProfileFragment : BaseFragment<UpdateDetailViewModel,FragmentViewProfileBinding,UpdateDetailRepository>() {

    var firstName = ""
    var lastname = ""
    var email = ""
    var phone = ""

    override fun getViewModel() = UpdateDetailViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentViewProfileBinding.inflate(inflater,container,false)

    override fun getRepository() = UpdateDetailRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageSessionData()
        manageListenerEvent()
        liveDataObserver()

    }

    private fun liveDataObserver() {
        viewModel.UpdateDetailResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarLoginPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        StartActivity(HomeActivity::class.java)
                        updateDataInSession()
                        requireActivity().finishAffinity()
                    }
                    if (it.values != null && !it.values.message.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun manageListenerEvent() {
        binding.btnSaveMyDetails.setOnClickListener {
            onClickUpdateProfileBtn()
        }
    }

    private fun onClickUpdateProfileBtn() {
        firstName = binding.edtTextFirstName.getTextInString()
        lastname = binding.edtTextLastName.getTextInString()
        email = binding.edtTextEmail.getTextInString()
        phone = binding.edtTextMobileNo.getTextInString()

        if(isDataValid()) {
            callUpdateDeatilApi()
        }

    }

    private fun isDataValid(): Boolean {

        if(Utils.isEmptyOrNull(firstName)) {
            binding.edtTextFirstName.error = getString(R.string.str_please_enter_your_first_name)
            binding.edtTextFirstName.requestFocus()
            return false
        } else if(Utils.isEmptyOrNull(lastname)) {
            binding.edtTextLastName.error = getString(R.string.str_please_enter_your_last_name)
            binding.edtTextLastName.requestFocus()
            return false
        } else if(Utils.isEmptyOrNull(phone)) {
            binding.edtTextMobileNo.error = getString(R.string.str_please_enter_your_mobile_number)
            binding.edtTextMobileNo.requestFocus()
            return false
        }
        return true

    }

    private fun callUpdateDeatilApi() {
        viewModel.getUpdateDetails(firstName,lastname,email,phone)
    }

    private fun updateDataInSession() {
        val userData = SessionManager.getUserData()
        userData?.data?.user?.firstname = firstName
        userData?.data?.user?.lastname = lastname
        userData?.data?.user?.phone = phone
        SessionManager.saveUserData(userData!!)
    }

    private fun manageSessionData() {

        val userData = SessionManager.getUserData()?.data?.user

         firstName = userData?.firstname.toString()
         lastname = userData?.lastname.toString()
         email = userData?.email.toString()
         phone = userData?.phone.toString()

        binding.edtTextFirstName.setText(firstName)
        binding.edtTextLastName.setText(lastname)
        binding.edtTextEmail.setText(email)
        binding.edtTextMobileNo.setText(phone)


    }

}