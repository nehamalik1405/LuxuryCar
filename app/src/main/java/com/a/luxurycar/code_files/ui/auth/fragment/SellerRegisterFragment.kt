package com.a.luxurycar.code_files.ui.auth.fragment

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SellerRepository
import com.a.luxurycar.code_files.ui.auth.model.LoginCommonResponse
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.ui.seller_deshboard.SellerDeshboardActivity
import com.a.luxurycar.code_files.view_model.SellerViewModel
import com.a.luxurycar.common.application.LuxuryCarApplication
import com.a.luxurycar.common.helper.AlertDialogHelper
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentSellerRegisterBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext


class SellerRegisterFragment : BaseFragment<SellerViewModel, FragmentSellerRegisterBinding, SellerRepository>() {
    var isShowPassword = false
    var isShowConfirmPassword = false
    var companyName = ""
    var firstName=""
    var lastName=""
    var email= ""
    var address=""
    var password=""
    var confirmPassword=""
    var phone = ""
    var description = ""
    var image_uri = ""

    override fun getViewModel() = SellerViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentSellerRegisterBinding.inflate(inflater,container,false)
    override fun getRepository()= SellerRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageListeners()
        liveDataObserver()
    }
    private fun liveDataObserver() {
        viewModel.sellerRegisterResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarSellerSignUpPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        val user = it.values.data.user
                        val loginResponse = LoginCommonResponse(
                            email = user.email,
                            companyName = user.company_name,
                            phone = user.phone,
                            role = user.role,
                            image = user.image,
                            id = user.id,
                            description = user.description,
                            location = user.location,
                        )

                        SessionManager.saveUserData(loginResponse)
                        SessionManager.setAuthorizationToken(it.values.data.accessToken)
                        SessionManager.setUserRole(user.role)

                        if(user.role.equals(Const.KEY_BUYER, true)) {
                            StartActivity(HomeActivity::class.java)
                        } else {
                            StartActivity(SellerDeshboardActivity::class.java)
                        }
                        requireActivity().finishAffinity()

                    }else{
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG).show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }
        })
    }
    private fun manageListeners() {
        binding.btnRegister.setOnClickListener {
            it.hideKeyboard()
          //createImageInMultipartAndSendToServer()
            if (isRegisterDataValid()){
                callSellerRegisterApi()
            }
        }


        binding.txtViewLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.imgViewEyePassword.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.edtTextPassword.transformationMethod = null
                binding.imgViewEyePassword.setImageResource(R.mipmap.ic_show_icon)
                binding.edtTextPassword.setSelection(binding.edtTextPassword.length())
            } else {
                binding.edtTextPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEyePassword.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextPassword.setSelection(binding.edtTextPassword.length())
            }
        }
        binding.imgViewEyeConfirmPassword.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.edtTextConfirmPassword.transformationMethod = null
                binding.imgViewEyeConfirmPassword.setImageResource(R.mipmap.ic_show_icon)
                binding.edtTextConfirmPassword.setSelection(binding.edtTextConfirmPassword.length())
            } else {
                binding.edtTextConfirmPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEyeConfirmPassword.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextConfirmPassword.setSelection(binding.edtTextConfirmPassword.length())
            }
        }
    }
    private fun callSellerRegisterApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.SELLER_COMPANY_NAME, companyName)
            jsonObject.put(Const.SELLER_PARAM_EMAIL, email)
            jsonObject.put(Const.SELLER_PARAM_PASSWARD, password)
            jsonObject.put(Const.SELLER_PARAM_CONFIRM_PASSWARD, confirmPassword)
            jsonObject.put(Const.SELLER_PARAM_Phone, phone)
            jsonObject.put(Const.SELLER_PARAM_LOCATION, address)
            jsonObject.put(Const.SELLER_PARAM_DESCRIPTION, description)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getSellerRegisterResponse(body)
    }
    private fun createImageInMultipartAndSendToServer() {

        var sellerImage: MultipartBody.Part? = null

        try {
            var file = FileUtil.from(LuxuryCarApplication.instance, Uri.parse(image_uri))
            val surveyBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            sellerImage = MultipartBody.Part.createFormData("image", file!!.name, surveyBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        viewModel.getUpdateSellerImage(sellerImage!!)
    }
    private fun getDataFromEditField() {
        companyName = binding.edtTextCompanyName.getTextInString()
        email = binding.edtTextEmail.getTextInString()
        address = binding.edtTextLocation.getTextInString()
        password = binding.edtTextPassword.getTextInString()
        phone = binding.edtTextPhoneNo.getTextInString()
        confirmPassword = binding.edtTextConfirmPassword.getTextInString()
        description = binding.edtTextDescription.getTextInString()
    }
    private fun isRegisterDataValid(): Boolean {

        getDataFromEditField()
        if (Utils.isEmptyOrNull(companyName)) {
            binding.edtTextCompanyName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_company_name))
            return false
        }
        else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!Utils.isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        }
        else if (Utils.isEmptyOrNull(phone)) {
            binding.edtTextPhoneNo.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_phone_number))
            return false
        }
        else if (phone.length < 10) {
            binding.edtTextPhoneNo.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_valid_number))
            return false
        }
        else if (Utils.isEmptyOrNull(address)) {
            binding.edtTextLocation.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_address))
            return false
        }
        else if (Utils.isEmptyOrNull(password)) {
            binding.edtTextPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_password))
            return false
        } else if (Utils.isEmptyOrNull(confirmPassword)) {
            binding.edtTextConfirmPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_confirm_password))
            return false
        } else if (!confirmPassword.equals(password)) {
            binding.edtTextConfirmPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_password_not_match))
            return false
        }else if (Utils.isEmptyOrNull(description)) {
            binding.edtTextDescription.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_description))
            return false
        }
        return true
    }











}