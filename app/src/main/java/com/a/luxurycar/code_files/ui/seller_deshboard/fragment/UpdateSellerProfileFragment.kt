package com.a.luxurycar.code_files.ui.seller_deshboard.fragment

import android.Manifest
import android.content.Intent
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
import com.a.luxurycar.code_files.ui.seller_deshboard.SellerDeshboardActivity
import com.a.luxurycar.code_files.view_model.SellerViewModel
import com.a.luxurycar.common.application.LuxuryCarApplication
import com.a.luxurycar.common.helper.CircleTransform
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentUpdateSellerProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UpdateSellerProfileFragment : BaseFragment<SellerViewModel, FragmentUpdateSellerProfileBinding,SellerRepository>() {
   // var isShowPassword = false
 //   var isShowConfirmPassword = false
    var companyName = ""
    var firstName=""
    var lastName=""
    var email= ""
    var location=""
    var password=""
    var confirmPassword=""
    var phone = ""
    var description = ""
    var type = ""
    var image_uri: String? = ""
    companion object {
        private val REQUEST_TAKE_PHOTO = 321
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 123
    }

    override fun getViewModel()=SellerViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentUpdateSellerProfileBinding.inflate(inflater,container,false)
    override fun getRepository()= SellerRepository(ApiAdapter.buildApi(ApiService::class.java))
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSellerDetail()
        manageListeners()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_TAKE_PHOTO) {
            selectFromCameraPermission()
        } else if(requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
            selectFromGalleryPermission()
        }

    }

    private fun liveDataObserver() {
        viewModel.sellerUpdateResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarSavedSellerUpdateProfile.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {

                        val user = it.values.data?.user
                        val loginResponse = LoginCommonResponse(
                            email = user?.email,
                            companyName = user?.companyName,
                            phone = user?.phone,
                            role = user?.role!!,
                            image = user.image,
                            id = user?.id!!,
                            description = user.description,
                            location = user.location,
                        )
                        SessionManager.saveUserData(loginResponse)
                        (requireActivity() as SellerDeshboardActivity).setRightHeader()
                        findNavController().popBackStack()

                    }

                    if(!Utils.isEmptyOrNull(it.values.message)) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG).show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }
    private fun manageListeners() {
        binding.btnUpdate.setOnClickListener {
            it.hideKeyboard()
            //createImageInMultipartAndSendToServer()
            if (isUpdateDataValid()){
                callSellerUpdateApi()
            }
        }
        binding.imgViewUserProfile.setOnClickListener {
            openBottomSheet()
        }



       /* binding.imgViewEyePassword.setOnClickListener {
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
        }*/
    }
    private fun callSellerUpdateApi() {
        liveDataObserver()
    viewModel.getUpdateSellerDetailResponse(companyName,email,phone,location,description)

    }

    private fun getDataFromEditField() {
        companyName = binding.edtTextCompanyName.getTextInString()
        phone = binding.edtTextPhoneNo.getTextInString()
        email = binding.edtTextEmail.getTextInString()
        location = binding.edtTextLocation.getTextInString()
       /* password = binding.edtTextPassword.getTextInString()
        confirmPassword = binding.edtTextConfirmPassword.getTextInString()*/
        description = binding.edtTextDescription.getTextInString()
    }
    private fun isUpdateDataValid(): Boolean {

        getDataFromEditField()
        if (Utils.isEmptyOrNull(companyName)) {
            binding.edtTextCompanyName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_company_name))
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
        else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!Utils.isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        }

        else if (Utils.isEmptyOrNull(location)) {
            binding.edtTextLocation.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_address))
            return false
        }
      /*  else if (Utils.isEmptyOrNull(password)) {
            binding.edtTextPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_password))
            return false
        } else if (Utils.isEmptyOrNull(confirmPassword)) {
            binding.edtTextConfirmPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_confirm_password))
            return false
        } else if (!confirmPassword.equals(password)) {
            binding.edtTextConfirmPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_password_not_match))
            return false
        }*/else if (Utils.isEmptyOrNull(description)) {
            binding.edtTextDescription.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_description))
            return false
        }
        return true
    }

    private fun setSellerDetail() {
        val userData = SessionManager.getUserData()
        val company = userData?.companyName
        val email = userData?.email
        val phone = userData?.phone
        val location = userData?.location
        val description = userData?.description

        binding.edtTextCompanyName.setText(company)
        binding.edtTextEmail.setText(email)
        binding.edtTextPhoneNo.setText(phone)
        binding.edtTextLocation.setText(location)
        binding.edtTextDescription.setText(description)

        setPhoto()
        (activity as SellerDeshboardActivity?)?.setRightHeader()
          //  binding.txtViewUsername.text = sellerData.data.user.company_name
         //   binding.txtViewEmail.text =  sellerData.data.user.email

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == AppCompatActivity.RESULT_OK) {
            galleryAddPic()
        } else if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == AppCompatActivity.RESULT_OK) {

            Log.e("pick", "photo gallry")

            if (data != null) {
                if (data?.getClipData() != null) {
                    val count = data.clipData!!.itemCount

                    for (i in 0..count - 1) {
                        val contentURI = data.clipData!!.getItemAt(i).uri
                        image_uri = contentURI.toString()
                        //setPhoto()
                        createImageInMultipartAndSendToServer()
                    }

                } else if (data?.getData() != null) {
                    // if single image is selected

                    val contentURI = data.data
                    image_uri = contentURI.toString()
                   // setPhoto()
                    createImageInMultipartAndSendToServer()
                }

            }

        } else if (requestCode == 1 && resultCode == 1) {

        }
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
        observeUploadAthleteImageCallback()
    }

    private fun observeUploadAthleteImageCallback() {

        viewModel.updateSellerImage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.progressBarSavedSellerUpdateProfile.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {

                    if(it.values != null && it.values.status == 1) {


                        val userData = SessionManager.getUserData()
                        userData!!.image = it.values.data?.image

                        SessionManager.saveUserData(userData)
                        setPhoto()
                        findNavController().popBackStack()

                    }

                    if(!Utils.isEmptyOrNull(it.values.message)) {
                        Toast.makeText(
                            requireContext(),
                            it.values.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Resource.Failure -> handleApiErrors(it)

            }

        })

    }
    private fun setPhoto() {
        val userData = SessionManager.getUserData()
        val image = userData?.image
        if (!Utils.isEmptyOrNull(image))
        Picasso.get().load(image).transform(CircleTransform()).into(binding.imgViewUserProfile)
    }




    private fun openBottomSheet() {
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.alert_dialog_profile_picture, null)
        val dialog = BottomSheetDialog(requireContext())
        val linLayoutCamera = view.findViewById<LinearLayout>(R.id.linLayoutCamera)
        val linLayoutGallery = view.findViewById<LinearLayout>(R.id.linLayoutGallery)

        linLayoutCamera.setOnClickListener {
            selectFromCameraPermission()
            dialog.dismiss()
        }

        linLayoutGallery.setOnClickListener {
            selectFromGalleryPermission()
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }


    private fun selectFromCameraPermission() {

        val permissionCheckCamera = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )

        val permissionCheckRead = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionCheckWrite = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionCheckCamera == -1 || permissionCheckRead == -1 || permissionCheckWrite == -1) {

                if (!Settings.System.canWrite(requireContext())) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                        ), REQUEST_TAKE_PHOTO
                    )
                } else {

                    takePhotoFromCamera()
                }
            } else {
                takePhotoFromCamera()
            }
        } else {
            takePhotoFromCamera()
        }

    }


    lateinit var currentPhotoPath: String
    var photoFile: File? = null
    var fileUri: Uri? = null
    private fun takePhotoFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .also { takePictureIntent ->
                photoFile = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    fileUri = FileProvider.getUriForFile(
                        requireContext(), "com.luxurycar.fileprovider",
                        it
                    )
                    //AppLogger.d("asad_fileUri", "$fileUri")
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                    startActivityForResult(takePictureIntent,
                        REQUEST_TAKE_PHOTO)
                }
            }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    private fun galleryAddPic() {

        if (currentPhotoPath.isNotEmpty()) {
            val f = File(currentPhotoPath)
            //image_uri = compressTheImageBeforeSendingToServer(currentPhotoPath).toString()
            image_uri = Uri.fromFile(f).toString()
            //setPhoto()
            createImageInMultipartAndSendToServer()
        }
    }


    private fun selectFromGalleryPermission() {

        val permissionCheckRead = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionCheckWrite = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionCheckRead == -1 || permissionCheckWrite == -1) {

                if (!Settings.System.canWrite(requireContext())) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                        ), REQUEST_SELECT_IMAGE_IN_ALBUM
                    )
                } else {


                    selectImageInAlbum()

                }
            } else {

                selectImageInAlbum()

            }
        } else {

            selectImageInAlbum()

        }
    }


    fun selectImageInAlbum() {


        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent,
            REQUEST_SELECT_IMAGE_IN_ALBUM)


    }


}