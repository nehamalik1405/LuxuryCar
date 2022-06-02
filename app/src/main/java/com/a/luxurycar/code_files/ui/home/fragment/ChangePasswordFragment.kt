package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
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


class ChangePasswordFragment :
    BaseFragment<ChangePasswordViewModel, FragmentChangePasswordBinding, ChangePasswordRepository>() {
    var isShowPassword = false
    var oldPassword = ""
    var newPassword = ""
    var confirmPassword = ""

    override fun getViewModel() = ChangePasswordViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentChangePasswordBinding.inflate(inflater, container, false)

    override fun getRepository() =
        ChangePasswordRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageListenerEvent()
        liveDataObserver()
    }

    private fun liveDataObserver() {
        viewModel.ChangePasswordResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarChangePasswordPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        StartActivity(HomeActivity::class.java)
                        requireActivity().finishAffinity()
                    }
                    if (it.values != null && !it.values.message.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun manageListenerEvent() {
        binding.btnSubmit.setOnClickListener {
            onClickChangePasswordBtn()
        }

        binding.imgViewEyeOldPassword.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.edtTextOldPassword.transformationMethod = null
                binding.imgViewEyeOldPassword.setImageResource(R.mipmap.ic_show_icon)
                binding.edtTextOldPassword.setSelection(binding.edtTextOldPassword.length())
            } else {
                binding.edtTextOldPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEyeOldPassword.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextOldPassword.setSelection(binding.edtTextOldPassword.length())
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

    private fun onClickChangePasswordBtn() {
        oldPassword = binding.edtTextOldPassword.text.toString().trim()
        newPassword = binding.edtTextNewPassword.text.toString().trim()
        confirmPassword = binding.edtTextConfirnPassword.text.toString().trim()

        if (isDataValid()) {
            callChangePasswordApi()
        }

    }

    private fun callChangePasswordApi() {
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

        if (Utils.isEmptyOrNull(oldPassword)) {
            binding.edtTextOldPassword.error = getString(R.string.error_empty_old_password)
            binding.edtTextOldPassword.requestFocus()
            return false
        } else if (Utils.isEmptyOrNull(newPassword)) {
            binding.edtTextNewPassword.error = getString(R.string.error_empty_password)
            binding.edtTextNewPassword.requestFocus()
            return false
        } else if (Utils.isEmptyOrNull(confirmPassword)) {
            binding.edtTextConfirnPassword.error = getString(R.string.error_empty_confirm_password)
            binding.edtTextConfirnPassword.requestFocus()
            return false
        } else if (!confirmPassword.equals(newPassword)) {
            binding.edtTextConfirnPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_password_not_match))
            return false
        }
        return true


    }
}