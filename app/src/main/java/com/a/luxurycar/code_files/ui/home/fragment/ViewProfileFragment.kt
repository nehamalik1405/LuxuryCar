package com.a.luxurycar.code_files.ui.home.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
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
import com.a.luxurycar.code_files.repository.UpdateDetailRepository
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.ui.home.adapter.BuyerViewpagerAdapter
import com.a.luxurycar.code_files.ui.seller_deshboard.SellerDeshboardActivity
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.SellerProfileViewpager
import com.a.luxurycar.code_files.view_model.UpdateDetailViewModel
import com.a.luxurycar.common.application.LuxuryCarApplication
import com.a.luxurycar.common.helper.CircleTransform
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentViewProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ViewProfileFragment :
    BaseFragment<UpdateDetailViewModel, FragmentViewProfileBinding, UpdateDetailRepository>() {
    var image_uri: String? = ""
    var firstName = ""
    var lastname = ""
    var email = ""
    var phone = ""

    companion object {
        private val REQUEST_TAKE_PHOTO = 321
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 123
    }

    lateinit var buyerViewpagerAdapter: BuyerViewpagerAdapter
    override fun getViewModel() = UpdateDetailViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentViewProfileBinding.inflate(inflater, container, false)

    override fun getRepository() =
        UpdateDetailRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setBuyerDetail()
        manageClickListener()
    }


    private fun manageClickListener() {
        binding.imgViewEditProfile.setOnClickListener {
            findNavController().navigate(R.id.nav_buyerUpdateDetailFragment)
        }

        binding.imgViewUserProfile.setOnClickListener {
            openBottomSheet()
        }

    }

    private fun setBuyerDetail() {

        /*  val userData = SessionManager.getUserData()

          val fullName = userData?.fullName
          val email = userData?.email
          setPhoto()
          (activity as HomeActivity?)?.setRightHeader()
          (activity as HomeActivity?)?.setLeftHeader()

          if(!Utils.isEmptyOrNull(fullName)) {
              binding.txtViewUsername.text = fullName
          } else if(!Utils.isEmptyOrNull(userData?.firstname)) {
              binding.txtViewUsername.text = userData?.firstname + " "+userData?.lastname
          }


          if(!Utils.isEmptyOrNull(email)) {
              binding.txtViewEmail.text =  email
          }*/
        if (SessionManager.isUserLoggedIn()) {

            val data = SessionManager.getUserData()

            if (data != null) {

                setPhoto()

                val email = data.email

                if (!Utils.isEmptyOrNull(email)) {
                    binding.txtViewEmail.text = email
                }

                if (data.role.equals("Buyer")) {
                    val fullName = data?.fullName

                    (activity as HomeActivity?)?.setRightHeader()
                    (activity as HomeActivity?)?.setLeftHeader()

                    if (!Utils.isEmptyOrNull(fullName)) {
                        binding.txtViewUsername.text = fullName
                    } else if (!Utils.isEmptyOrNull(data?.firstname)) {
                        binding.txtViewUsername.text = data?.firstname + " " + data?.lastname
                    }

                }
                else if (data.role.equals("Seller")) {
                    val companyName = data?.companyName

                    (activity as HomeActivity?)?.setRightHeader()
                    (activity as HomeActivity?)?.setLeftHeader()

                    if (!Utils.isEmptyOrNull(companyName)) {
                        binding.txtViewUsername.text = companyName
                    }
                }


            }
        }

        /*if (buyerData != null) {
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
            binding.progressBarUploadProfileImage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.values != null && it.values.status == 1) {
                        val updatedImage = it.values.buyerProfileData?.image
                        val loginResponse = SessionManager.getUserData()?.apply {
                            image = updatedImage!!
                        }
                        SessionManager.saveUserData(loginResponse!!)
                        setPhoto()
                    }


                    if (!Utils.isEmptyOrNull(it.values.message)) {
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


    private fun setViewPager() {
        buyerViewpagerAdapter = BuyerViewpagerAdapter(this)
        binding.viewPagerProfile.adapter = buyerViewpagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPagerProfile) { tab, position ->
            var tabName = ""
            if (position == 0) {
                tabName = "My Profile"
            } else if (position == 1) {
                tabName = "Saved Cars"
            } else {
                tabName = "Payment History"
            }
            tab.text = tabName

        }.attach()

        buyerViewpagerAdapter.setOnTabChangeListener(object :
            BuyerViewpagerAdapter.OnTabChangeListener {
            override fun onTabChange(BuyerProfileDetailFragment: Boolean) {
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        val userData = SessionManager.getUserData()
        val image = userData?.image
        (!Utils.isEmptyOrNull(image))
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
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
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