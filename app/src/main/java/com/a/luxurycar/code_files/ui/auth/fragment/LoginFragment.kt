package com.a.luxurycar.code_files.ui.auth.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.LoginRepository
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.view_model.LoginViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentLoginBinding
import org.json.JSONObject


class LoginFragment :  BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {
    var isShowPassword = false
    var email = ""
    var password = ""

    override fun getViewModel() = LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )=FragmentLoginBinding.inflate(inflater,container,false)

    override fun getRepository()= LoginRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageListeners()


    }

    private fun manageListeners() {
        binding.txtViewRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            it.hideKeyboard()
           if (isValidation()) {
               callLoginApi()
           }
            binding.txtViewForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPassword)
            }

            /*binding.edtTextPassword.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (isValidation()) {
                        callLoginApi()
                    }
                    return@OnEditorActionListener true
                }
                false
            })
*/

        }
        binding.txtViewForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.forgotPassword)
        }
        binding.imgViewEye.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.edtTextPassword.transformationMethod = null
                binding.imgViewEye.setImageResource(R.mipmap.ic_show_icon)
                binding.edtTextPassword.setSelection(binding.edtTextPassword.length())
            } else {
                binding.edtTextPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEye.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextPassword.setSelection(binding.edtTextPassword.length())
            }
        }
    }

    private fun isValidation(): Boolean {

        //  val emailPattern: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        email = binding.edtTextEmail.text.toString().trim()
        password = binding.edtTextPassword.text.toString().trim()


        if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!Utils.isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        } else if (Utils.isEmptyOrNull(password)) {
            binding.edtTextPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_password))
            return false
        }

        return true

    }

    private fun callLoginApi() {
        login()

        viewModel.LoginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarLoginPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        SessionManager.saveUserData(it.values)
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

    private fun login() {

        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_EMAIL, email)
            jsonObject.put(Const.PARAM_PASSWARD, password)
            jsonObject.put("device_id", "test")
            jsonObject.put("device_token", "test")
            jsonObject.put("device_type", "A")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getLoginResponse(body)

    }
    fun getDeviceId(context:Context):String?{
        return Settings.Secure.getString(context.contentResolver,Settings.Secure.ANDROID_ID)
    }



}