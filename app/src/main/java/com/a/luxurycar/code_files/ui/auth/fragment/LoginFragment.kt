package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import android.provider.Settings
import android.text.method.PasswordTransformationMethod
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
import com.a.luxurycar.code_files.ui.seller_deshboard.SellerDeshboardActivity
import com.a.luxurycar.code_files.view_model.LoginViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentLoginBinding

import org.json.JSONObject


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {
    var isShowPassword = false
    var email = ""
    var password = ""
    var type = ""

    override fun getViewModel() = LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getRepository() = LoginRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = arguments?.getString(Const.KEY_TYPE).toString()
        manageListeners()
        setLoginType()
        liveDataObserver()


    }

    private fun setLoginType() {
         if (type != null){
             binding.txtViewLoginTitle.text = "$type Log In"
         }else{
             binding.txtViewLoginTitle.text = "Log In"
         }



    }

    private fun liveDataObserver() {
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarLoginPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1 && it.values.data.user.role == "Buyer") {
                        SessionManager.saveUserData(it.values)
                        SessionManager.setFullName(it.values.data.user.fullname)
                        if (it.values.data.user.company_name != null){
                            SessionManager.setCompanyName(it.values.data.user.company_name)
                        }
                        SessionManager.setFirstName(it.values.data.user.firstname)
                        SessionManager.setLastName(it.values.data.user.lastname)
                        SessionManager.setEmail(it.values.data.user.email)
                        SessionManager.setPhone(it.values.data.user.phone)
                        SessionManager.setImageUrl(it.values.data.user.image)
                        StartActivity(HomeActivity::class.java)
                        requireActivity().finishAffinity()
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else if (it.values.status != null && it.values.status == 1 && it.values.data.user.role == "Seller") {
                        SessionManager.saveUserData(it.values)
                        if (it.values.data.user.company_name != null){
                            SessionManager.setCompanyName(it.values.data.user.company_name)
                        }
                        SessionManager.setEmail(it.values.data.user.email)
                        SessionManager.setPhone(it.values.data.user.phone)
                        SessionManager.setImageUrl(it.values.data.user.image)
                        StartActivity(SellerDeshboardActivity::class.java)
                        requireActivity().finishAffinity()
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                            .show()
                    }


                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }


    private fun manageListeners() {

        binding.txtViewRegister.setOnClickListener {
            if (type == "Customer"){
               val bundle = Bundle()
                bundle.putString(Const.KEY_TYPE, type)
                findNavController().navigate(R.id.registerFragment,bundle)
            }
            if (type == "Seller"){
                val bundle = Bundle()
                bundle.putString(Const.KEY_TYPE, type)
                findNavController().navigate(R.id.sellerRegisterFragment,bundle)
            }
        }

        binding.btnLogin.setOnClickListener {
            it.hideKeyboard()
            if (isValidation()) {
                callLoginApi()
            }
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

        email = binding.edtTextEmail.getTextInString()
        password = binding.edtTextPassword.getTextInString()


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

    private fun callLoginApi()  {

        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_EMAIL, email)
            jsonObject.put(Const.PARAM_PASSWARD, password)
            jsonObject.put(Const.PARAM_DEVICE_ID, getDeviceId())
            jsonObject.put(Const.PARAM_DEVICE_TOKEN, "test")
            jsonObject.put(Const.PARAM_DEVICE_TYPE, "A")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getLoginResponse(body)

    }

    fun getDeviceId(): String? {
        return Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }


}