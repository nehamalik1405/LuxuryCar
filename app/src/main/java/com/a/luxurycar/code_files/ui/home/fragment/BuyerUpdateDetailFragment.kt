package com.a.luxurycar.code_files.ui.home.fragment

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.UpdateDetailRepository
import com.a.luxurycar.code_files.ui.home.adapter.BuyerViewpagerAdapter
import com.a.luxurycar.code_files.view_model.UpdateDetailViewModel
import com.a.luxurycar.common.application.LuxuryCarApplication
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.helper.CircleTransform
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentBuyerUpdateDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class BuyerUpdateDetailFragment : BaseFragment<UpdateDetailViewModel, FragmentBuyerUpdateDetailBinding, UpdateDetailRepository>() {
    var image_uri: String? = ""
    var isShowPassword = false
    var firstName = ""
    var lastName = ""
    var country = ""
    var state = ""
    var city = ""
    var email = ""
    var phone = ""
    var password = ""
    var confirmPassword = ""
    var type ="Buyer"
    var country_id = ""
    var stateId = ""
    var cityId = ""
    lateinit var arrListCountryHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrListStateHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrListCityHashMap: ArrayList<HashMap<String, String>>
    lateinit var bundle:Bundle

    companion object {
        private val REQUEST_TAKE_PHOTO = 321
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 123
    }


    override fun getViewModel() = UpdateDetailViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentBuyerUpdateDetailBinding.inflate(inflater,container,false)

    override fun getRepository() = UpdateDetailRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callCountryList()
        manageListeners()
        setBuyerDetail()
        liveDataObserver()
    }
    private fun setBuyerDetail() {
        val fullName = SessionManager.getFullName()
        val firstName = SessionManager.getFirstName()
        val lastName = SessionManager.getLastName()
        val email = SessionManager.getEmail()
        val phone = SessionManager.getPhone()
        setPhoto()
        binding.edtTextFirstName.setText(firstName)
        binding.edtTextLastName.setText(lastName)
        binding.edtTextEmail.setText(email)
        binding.edtTextPhoneNo.setText(phone)



        /* if (buyerData != null) {
             binding.txtViewUsername.text = buyerData.data.user.fullname
             binding.txtViewEmail.text =  buyerData.data.user.email
         }*/
    }
    private fun createImageInMultipartAndSendToServer() {

        var buyerImage: MultipartBody.Part? = null

        try {
            var file = FileUtil.from(LuxuryCarApplication.instance, Uri.parse(image_uri))
            val surveyBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            buyerImage = MultipartBody.Part.createFormData("image", file!!.name, surveyBody)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        viewModel.getUploadBuyerProfileImage(buyerImage!!)
        observeUploadAthleteImageCallback()
    }
    private fun observeUploadAthleteImageCallback() {

        viewModel.uploadBuyerProfileImage.observe(viewLifecycleOwner, Observer {
            binding.progressBarUpdateDetailPage.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {

                    if(it.values != null && it.values.status == 1) {
                        SessionManager.setImageUrl(it.values.buyerProfileData?.image.toString())
                        Toast.makeText(
                            requireContext(),
                            it.values.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
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
    private fun callCountryList() {
        viewModel.getCountryList()
        viewModel.countryResponse.observe(viewLifecycleOwner, Observer {
            arrListCountryHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Country Name")
            arrListCountryHashMap.add(hashMapDefaultitem)


            binding.progressBarUpdateDetailPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressBarUpdateDetailPage.visible(isHidden)
                        if (it != null) {
                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrListCountryHashMap.add(hashMap)
                            }
                        }
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrListCountryHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerCountry.setAdapter(adapter)


                }
                is Resource.Failure -> handleApiErrors(it)
            }


        })




    }


    private fun liveDataObserver() {
        viewModel.buyerUpdateDetailResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarUpdateDetailPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {




                        SessionManager.setFirstName(it.values.data.user.firstname)
                        SessionManager.setLastName(it.values.data.user.lastname)
                        SessionManager.setFullName(it.values.data.user.firstname+" "+it.values.data.user.lastname)
                        SessionManager.setEmail(it.values.data.user.email)
                        SessionManager.setPhone(it.values.data.user.phone)
                        SessionManager.setImageUrl(it.values.data.user.image)
                        findNavController().navigate(R.id.nav_home)
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG).show()
                    }
                    else{
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
            if (isUpdateDataValid()) {
                callUpdateDetailApi()
            }
        }

        binding.imgViewUserProfile.setOnClickListener {
            openBottomSheet()
        }
        binding.spinnerCountry.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    country_id = arrListCountryHashMap[position].get(Const.KEY_ID).toString()
                    callStateListApi()
                }

            }
        binding.spinnerState.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    stateId = arrListStateHashMap[position].get(Const.KEY_ID).toString()
                    callCityListApi()
                }

            }
        binding.spinnerCity.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    cityId = arrListCityHashMap[position].get(Const.KEY_ID).toString()

                }

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
                binding.edtTextConfirmPassword.setSelection(binding.edtTextPassword.length())
            } else {
                binding.edtTextConfirmPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEyeConfirmPassword.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextConfirmPassword.setSelection(binding.edtTextConfirmPassword.length())
            }

        }


    }

    private fun callCityListApi() {
        viewModel.getCitiesList(stateId)
        viewModel.cityResponse.observe(viewLifecycleOwner, Observer {
            arrListCityHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "City Name")
            arrListCityHashMap.add(hashMapDefaultitem)


            binding.progressBarUpdateDetailPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status == 1) {
                        binding.progressBarUpdateDetailPage.visible(isHidden)

                        if (it != null) {


                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()

                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrListCityHashMap.add(hashMap)
                            }
                        }
                    }

                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrListCityHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerCity.setAdapter(adapter)


                }
                is Resource.Failure -> handleApiErrors(it)
            }


        })

    }

    private fun callStateListApi() {
        viewModel.getSateList(country_id)
        viewModel.stateResponse.observe(viewLifecycleOwner, Observer {
            arrListStateHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "State Name")
            arrListStateHashMap.add(hashMapDefaultitem)


            binding.progressBarUpdateDetailPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status == 1) {
                        binding.progressBarUpdateDetailPage.visible(isHidden)

                        if (it != null) {


                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()

                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrListStateHashMap.add(hashMap)
                            }
                        }
                    }

                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrListStateHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerState.setAdapter(adapter)


                }
                is Resource.Failure -> handleApiErrors(it)
            }


        })

    }

    private fun callUpdateDetailApi() {

        viewModel.getBuyerUpdateDetails(firstName,lastName,email,phone,password,confirmPassword,country_id,stateId,cityId)
    }

    private fun getDataFromEditField() {
        firstName = binding.edtTextFirstName.getTextInString()
        lastName = binding.edtTextLastName.getTextInString()
        // country = binding.edtTextCountryName.getTextInString()
        //   state = binding.edtTextStateName.getTextInString()
        //  city = binding.edtTextCityName.getTextInString()
        email = binding.edtTextEmail.getTextInString()
        phone = binding.edtTextPhoneNo.getTextInString()
        password = binding.edtTextPassword.getTextInString()
        confirmPassword = binding.edtTextConfirmPassword.getTextInString()
    }

    private fun isUpdateDataValid(): Boolean {

        getDataFromEditField()
        if (Utils.isEmptyOrNull(firstName)) {
            binding.edtTextFirstName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_first_name))
            return false
        } else if (Utils.isEmptyOrNull(lastName)) {
            binding.edtTextLastName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_last_name))
            return false
        }
        else if (country_id == "0") {
            Toast.makeText(requireContext(), getStringFromResource(R.string.error_empty_select_country), Toast.LENGTH_LONG).show()
            return false
        }
        else if (stateId== "0") {
            Toast.makeText(requireContext(), getStringFromResource(R.string.error_empty_select_state), Toast.LENGTH_LONG).show()
            return false
        }
        else if (cityId == "0") {
            Toast.makeText(requireContext(), getStringFromResource(R.string.error_empty_select_city), Toast.LENGTH_LONG).show()
            return false
        } else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!Utils.isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        } else if (Utils.isEmptyOrNull(phone)) {
            binding.edtTextPhoneNo.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_mobile_no))
            return false
        }
        else if (phone.length < 10) {
            binding.edtTextPhoneNo.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_valid_number))
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
        }
        return true
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
                    //setPhoto()
                    createImageInMultipartAndSendToServer()
                }

            }

        } else if (requestCode == 1 && resultCode == 1) {

        }
    }

    private fun setPhoto() {
        val image = SessionManager.getImageUrl()
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
            // setPhoto()
            createImageInMultipartAndSendToServer()
        }
    }


    /*private fun compressTheImageBeforeSendingToServer(imageUri: String): Uri? {
        val compressionRatio = 2 //1 == originalImage, 2 = 50% compression, 4=25% compress

        val file = File(imageUri)
        try {
            val bitMap = Compressor(requireContext()).compressToBitmap(file);
            return getImageUriFromBitmap(bitMap)
        } catch (t: Throwable) {
            Log.e("ERROR", "Error compressing file.$t")
            t.printStackTrace()
            return null
        }

    }

    fun getImageUriFromBitmap(inImage: Bitmap): Uri {

        var path = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            inImage,
            "Title" + Calendar.getInstance().getTime(),
            null
        )

        return Uri.parse(path)
    }

*/
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
        startActivityForResult(photoPickerIntent, REQUEST_SELECT_IMAGE_IN_ALBUM)


    }

}