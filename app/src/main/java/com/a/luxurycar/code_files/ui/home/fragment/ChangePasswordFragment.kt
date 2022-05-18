package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.ChangePasswordRepository
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.view_model.ChangePasswordViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentChangePasswordBinding
import org.json.JSONObject


class ChangePasswordFragment : BaseFragment<ChangePasswordViewModel,FragmentChangePasswordBinding,ChangePasswordRepository>() {

    var oldPassword = ""
    var newPassword = ""
    var confirmPassword = ""
    override fun getViewModel()=ChangePasswordViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentChangePasswordBinding.inflate(inflater,container,false)

    override fun getRepository()= ChangePasswordRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageListenerEvent()
    }

    private fun manageListenerEvent() {
      binding.btnSubmit.setOnClickListener {
          onClickChangePasswordBtn()
      }
    }

    private fun onClickChangePasswordBtn() {
        oldPassword = binding.edtTextOldPassword.text.toString().trim()
        newPassword = binding.edtTextNewPassword.text.toString().trim()
        confirmPassword = binding.edtTextConfirnPassword.text.toString().trim()

        if(isDataValid()) {
            callChangePasswordApi()
            viewModel.ChangePasswordResponse.observe(viewLifecycleOwner, Observer {
                binding.progressBarChangePasswordPage.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {
                        if (it.values.status != null && it.values.status == 1) {
                            StartActivity(HomeActivity::class.java)
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

    }

    private fun callChangePasswordApi() {
        createRequestAndCallChangePasswordApi()

    }
    private fun createRequestAndCallChangePasswordApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_OLD_PASSWARD, oldPassword)
            jsonObject.put(Const.PARAM_NEW_PASSWARD, newPassword)
            jsonObject.put(Const.PARAM_NEW_CONFIRM_PASSWARD, confirmPassword)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getRegisterResponse(body)

    }
    private fun isDataValid(): Boolean {

        if(Utils.isEmptyOrNull(oldPassword)) {
            binding.edtTextOldPassword.error = "Please enter old Password."
            binding.edtTextOldPassword.requestFocus()
            return false
        } else if(Utils.isEmptyOrNull(newPassword)) {
            binding.edtTextNewPassword.error = "Please enter new password."
            binding.edtTextNewPassword.requestFocus()
            return false
        } else if(Utils.isEmptyOrNull(confirmPassword)) {
            binding.edtTextConfirnPassword.error = "Please enter confirm password."
            binding.edtTextConfirnPassword.requestFocus()
            return false
        }
        else if (!confirmPassword.equals(newPassword)) {
            binding.edtTextConfirnPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_password_not_match))
            return false
        }
        return true


    }
}