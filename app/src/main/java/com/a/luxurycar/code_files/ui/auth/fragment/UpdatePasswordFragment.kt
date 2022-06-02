package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.ForgotPasswordRepository
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.view_model.ForgotPasswordViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentUpdatePasswordBinding


class UpdatePasswordFragment : BaseFragment<ForgotPasswordViewModel,FragmentUpdatePasswordBinding,ForgotPasswordRepository>() {
    var isShowPassword = false
    var new_password=""
    var confirm_password=""

    override fun getViewModel() = ForgotPasswordViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentUpdatePasswordBinding.inflate(inflater,container,false)

    override fun getRepository() = ForgotPasswordRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListener()

    }

    private fun manageClickListener() {
        binding.btnSubmit.setOnClickListener {
            it.hideKeyboard()
            if(isValid()){
                callUpdatePasswordApi()
            }

        }

        binding.imgViewEyeNewPassword.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.edtTextNewPassword.transformationMethod = null
                binding.imgViewEyeNewPassword.setImageResource(R.mipmap.ic_show_icon)
                binding.edtTextNewPassword.setSelection(binding.edtTextNewPassword.length())
            } else {
                binding.edtTextNewPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEyeNewPassword.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextNewPassword.setSelection(binding.edtTextNewPassword.length())
            }
        }

        binding.imgViewEyeConfirmPassword.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.edtTextConfirnPassword.transformationMethod = null
                binding.imgViewEyeConfirmPassword.setImageResource(R.mipmap.ic_show_icon)
                binding.edtTextConfirnPassword.setSelection(binding.edtTextConfirnPassword.length())
            } else {
                binding.edtTextConfirnPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEyeConfirmPassword.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextConfirnPassword.setSelection(binding.edtTextConfirnPassword.length())
            }

        }
    }

    private fun callUpdatePasswordApi() {
        val email = SessionManager.getEmail()
        if (email != null) {
            viewModel.getUpdatePasswordResponse(email,new_password,confirm_password)
        }

        viewModel.UpdatePasswordResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarUpdatePasswordPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        findNavController().navigate(R.id.loginFragment)
                    }
                    if (it.values != null && !it.values.message.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })

    }


    private fun isValid(): Boolean {
        getDataFromEditField()
        if (Utils.isEmptyOrNull(new_password)) {
            binding.edtTextNewPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_new_password))
            return false
        } else if (Utils.isEmptyOrNull(confirm_password)) {
            binding.edtTextConfirnPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_confirm_password))
            return false
        } else if (!confirm_password.equals(new_password)) {
            binding.edtTextConfirnPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_password_not_match))
            return false
        }
        return true
    }

    private fun getDataFromEditField() {
        new_password = binding.edtTextNewPassword.text.toString().trim()
        confirm_password = binding.edtTextConfirnPassword.text.toString().trim()    }
}