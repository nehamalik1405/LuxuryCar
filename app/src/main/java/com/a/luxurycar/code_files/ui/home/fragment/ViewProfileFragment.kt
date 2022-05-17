package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
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
import com.a.luxurycar.common.utils.StartActivity
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.toEditable
import com.a.luxurycar.common.utils.visible
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

    }

    private fun manageListenerEvent() {
        binding.btnSaveMyDetails.setOnClickListener {
            callUpdateDeatilApi()
        }
    }

    private fun callUpdateDeatilApi() {
        viewModel.getUpdateDetails(firstName,lastname,lastname,lastname)
   /*     viewModel.UpdateDetailResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarLoginPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values. != null) {
                        SessionManager.saveUserData(it.values)
                        StartActivity(HomeActivity::class.java)
                        requireActivity().finishAffinity()
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })*/

    }

    private fun manageSessionData() {
         firstName = SessionManager.getUserData()?.data?.user?.firstname.toString()
         lastname = SessionManager.getUserData()?.data?.user?.lastname.toString()
         email = SessionManager.getUserData()?.data?.user?.email.toString()
         phone = SessionManager.getUserData()?.data?.user?.phone.toString()

        binding.edtTextFirstName.setText(firstName)
        binding.edtTextLastName.setText(lastname)
        binding.edtTextEmail.setText(email)
        binding.edtTextMobileNo.setText(phone)


    }

}