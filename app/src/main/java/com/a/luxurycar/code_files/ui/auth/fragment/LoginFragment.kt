package com.a.luxurycar.code_files.ui.auth.fragment

import android.app.Notification
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.MainActivity
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.LoginRepository
import com.a.luxurycar.code_files.ui.auth.model.LoginCommonResponse
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.ui.seller_deshboard.SellerDeshboardActivity
import com.a.luxurycar.code_files.view_model.LoginViewModel
import com.a.luxurycar.common.application.LuxuryCarApplication.Companion.application
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentLoginBinding
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import org.json.JSONObject
import java.util.*


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>(){
    var isShowPassword = false
    var email = ""
    var password = ""
    var type = ""
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var callbackManager: CallbackManager
    var textView: TextView? = null
    private val RC_SIGN_IN = 1001
    var nameFacebook = ""
    var emailFacebook = ""
    var userID = ""
    var firstName= ""
    var lastName = ""


    override fun getViewModel() = LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getRepository() = LoginRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        //LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY)
        //AppEventsLogger.activateApp(application)

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        manageListeners()
        liveDataObserver()
    }

    private fun liveDataObserver() {
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarLoginPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {

                        val user = it.values.data.user
                        val loginResponse = LoginCommonResponse(
                            firstname = user.firstname,
                            lastname = user.lastname,
                            fullName = user.fullname,
                            email = user.email,
                            companyName = user.company_name,
                            phone = user.phone,
                            role = user.role,
                            image = user.image,
                            id = user.id,
                            description = user.description,
                            location = user.location,
                            countryId=user.countryId,
                            stateId=user.stateId,
                            cityId = user.cityId
                        )

                        SessionManager.saveUserData(loginResponse)
                        SessionManager.setAuthorizationToken(it.values.data.accessToken)
                        SessionManager.setUserRole(user.role)
                        val intent=Intent(requireActivity(),HomeActivity::class.java)
                        startActivity(intent)

                       /* if(user.role.equals(Const.KEY_BUYER, true)) {
                           // StartActivity(HomeActivity::class.java)

                            val intent=Intent(requireActivity(),HomeActivity::class.java).apply {
                                putExtra("OpenDrawer",HelperClass.openRightNavigation)
                            }
                            startActivity(intent)

                        }else if(user.role.equals(Const.KEY_SELLER, true)){

                            //StartActivity(HomeActivity::class.java)
                        }*/
                        requireActivity().finishAffinity()

                    }else{
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                            .show()
                    }



                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }
        })
    }

    private fun facebookLoginClicked() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance()
            .logInWithReadPermissions(this,
                Arrays.asList("email","public_profile", "user_friends"))
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.DIALOG_ONLY)
        LoginManager.getInstance().registerCallback(callbackManager,object :
            FacebookCallback<LoginResult> {
            override fun onCancel() {
                Log.e("Fb cancel", "")

                if (AccessToken.getCurrentAccessToken() != null) {
                   LoginManager.getInstance().logOut()
                }
            }

            override fun onError(error: FacebookException) {
                Log.e("Fb error", "")
                if (AccessToken.getCurrentAccessToken() != null) {
                   LoginManager.getInstance().logOut()
                }
            }

            override fun onSuccess(result: LoginResult) {
                Log.e("facebook", "Facebook successfully loging")
                val request = GraphRequest.newMeRequest(
                        result.accessToken,object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {
                          if(response != null){
                          /*   emailFacebook = response.getJSONObject()!!.getString("email");
                              fbFirst_name = response.getJSONObject()!!.getString("first_name");
                              fbLast_name = response.getJSONObject()!!.getString("last_name");*/

                              emailFacebook =obj!!.getString("email");
                              firstName = obj!!.getString("first_name");
                              lastName = obj!!.getString("last_name");
                              Toast.makeText(requireContext(),"welcome $firstName $lastName",Toast.LENGTH_LONG).show()
                          }

                        }

                    })


                val parameters = Bundle()
                parameters.putString("fields", "id,name,email,gender,first_name,last_name")
                //parameters.putString("auth_type", "reauthenticate")
                request.parameters = parameters
                request.executeAsync()

                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut()
                }

            }

        })

    }
    private fun manageListeners() {

        binding.txtViewRegister.setOnClickListener {
            /*if (type == "Customer"){
               val bundle = Bundle()
                bundle.putString(Const.KEY_TYPE, type)
                findNavController().navigate(R.id.registerFragment,bundle)
            }
            if (type == "Seller"){
                val bundle = Bundle()
                bundle.putString(Const.KEY_TYPE, type)
                findNavController().navigate(R.id.sellerRegisterFragment,bundle)
            }*/
            findNavController().navigate(R.id.nav_registerationType)
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

        binding.imgViewGoogleLogin.setOnClickListener {

            val intent = mGoogleSignInClient!!.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)

        }

        binding.imgViewFbLogin.setOnClickListener {
            facebookLoginClicked()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account != null) {
                    Toast.makeText(requireContext(),account.displayName+" "+account.email,Toast.LENGTH_LONG).show()
                }
                if (account != null) {
                    Log.e("account detail",account.displayName+" "+account.email+" "+account.givenName+" "+account.account)
                    account.email
                    account.idToken
                    account.displayName
                    account.givenName
                    account.account

                }

                Toast.makeText(requireContext(),"welcome ${account?.displayName}",Toast.LENGTH_LONG).show()

            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.e("TAG","signInResult:failed code=" + e.statusCode)
            }
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);

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