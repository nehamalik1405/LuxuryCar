package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.ForgotPasswordRepository
import com.a.luxurycar.code_files.view_model.ForgotPasswordViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentOtpVarificationBinding
import org.json.JSONObject


class OtpVarificationFragment : BaseFragment<ForgotPasswordViewModel,FragmentOtpVarificationBinding,ForgotPasswordRepository>() {



    var edtTextCodeOne = ""
    var edtTextCodeTwo = ""
    var edtTextCodeThree = ""
    var edtTextCodeFour = ""
    var otpForVerify = ""
    var isResend = false

    lateinit var countDownTimer : CountDownTimer
    var countDownTime = 30000L
    var counter = 30


    override fun getViewModel() = ForgotPasswordViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentOtpVarificationBinding.inflate(inflater,container,false)

    override fun getRepository() = ForgotPasswordRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListener()
        setResentOtpTimer()


    }

    private fun setResentOtpTimer() {

        var counter = 30

        countDownTimer = object: CountDownTimer(countDownTime,1000){
            override fun onTick(p0: Long) {
                isResend = false
                counter--
                binding.txtViewResendTimer.setText("["+counter+"]")

            }

            override fun onFinish() {
                binding.txtViewResendCode.SetTextColor(R.color.color_blue)
                binding.txtViewResendCode.setText(R.string.str_resend_code)
                binding.txtViewResendTimer.setText("")
                isResend = true
            }

        }
        countDownTimer.start()
    }

    private fun manageClickListener() {
        binding.btnVerify.setOnClickListener {
            it.hideKeyboard()
            if(isValidId()){
                callVerifyOtpApi()
            }
        }
        manageAutoFillEditText()

        binding.txtViewResendCode.setOnClickListener {
            resendOtpClickEvent()
        }

    }

    private fun resendOtpClickEvent() {
        if(isResend) {
            isResend = false

            var counter = 30
            binding.txtViewResendCode.setTextColor(ContextCompat.getColor(requireContext(),R.color.sub_title_color))
            binding.txtViewResendCode.setText(R.string.str_resend_code_no_underline)
            countDownTimer = object: CountDownTimer(countDownTime,1000){
                override fun onTick(p0: Long) {

                    isResend = false
                    counter--
                    binding.txtViewResendTimer.setText(""+counter)

                }

                override fun onFinish() {
                    binding.txtViewResendCode.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                    binding.txtViewResendCode.setText(R.string.str_resend_code)
                    binding.txtViewResendTimer.setText("")
                    isResend = true
                }

            }
            countDownTimer.start()
            callResendOtpApi()
        }
    }


    private fun manageAutoFillEditText() {
        binding.edtTextCodeOne.addTextChangedListener(GenericTextWatcher( binding.edtTextCodeOne, binding.edtTextCodeTwo))
        binding.edtTextCodeTwo.addTextChangedListener(GenericTextWatcher(binding.edtTextCodeTwo, binding.edtTextCodeThree))
        binding.edtTextCodeThree.addTextChangedListener(GenericTextWatcher(binding.edtTextCodeThree,  binding.edtTextCodeFour))
        binding.edtTextCodeFour.addTextChangedListener(GenericTextWatcher( binding.edtTextCodeFour, null))


        binding.edtTextCodeOne.setOnKeyListener(GenericKeyEvent(binding.edtTextCodeOne, null))
        binding.edtTextCodeTwo.setOnKeyListener(GenericKeyEvent(binding.edtTextCodeTwo, binding.edtTextCodeOne))
        binding.edtTextCodeThree.setOnKeyListener(GenericKeyEvent( binding.edtTextCodeThree, binding.edtTextCodeTwo))
        binding.edtTextCodeFour.setOnKeyListener(GenericKeyEvent( binding.edtTextCodeFour,binding.edtTextCodeThree))

    }

    private fun callVerifyOtpApi() {
        callRequestForVerifyOpt()
        viewModel.VerifyOtpResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarVerifyOtpPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        findNavController().navigate(R.id.updatePasswordFragment)
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT).show()
                    }
                    if (it.values != null && !it.values.message.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun callRequestForVerifyOpt() {
        val jsonObject = JSONObject()
        val email = SessionManager.getEmail()
        try {
            jsonObject.put(Const.PARAM_EMAIL, email)
            jsonObject.put(Const.PARAM_OTP, otpForVerify)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getVerifyOtpResponse(body)

    }



    private fun callResendOtpApi() {
        val email = SessionManager.getEmail()
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_EMAIL, email)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getSendOtpResponse(body)

        viewModel.SendOtpResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarVerifyOtpPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        findNavController().navigate(R.id.otpVarificationFragment)
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT).show()
                    }
                    if (it.values != null && !it.values.message.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })


    }


    private fun isValidId(): Boolean {
        edtTextCodeOne = binding.edtTextCodeOne.text.toString().trim()
        edtTextCodeTwo = binding.edtTextCodeTwo.text.toString().trim()
        edtTextCodeThree = binding.edtTextCodeThree.text.toString().trim()
        edtTextCodeFour = binding.edtTextCodeFour.text.toString().trim()
        otpForVerify = edtTextCodeOne + edtTextCodeTwo + edtTextCodeThree + edtTextCodeFour

        if(Utils.isEmptyOrNull(otpForVerify)) {
            Toast.makeText(requireContext(), "Please enter OTP.", Toast.LENGTH_LONG).show()
            //findNavController().navigate(R.id.updatePasswordFragment)
            return false
        }else if(otpForVerify.length < 4) {
            Toast.makeText(requireContext(), "Please enter proper otp.", Toast.LENGTH_LONG).show()
            //findNavController().navigate(R.id.updatePasswordFragment)
            return false
        }

        return true
    }



}