package com.a.luxurycar.code_files.ui.add_car.fragment

import android.Manifest
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarStepOneRepository
import com.a.luxurycar.code_files.ui.add_car.adapter.AddMultipleImageAdapter
import com.a.luxurycar.code_files.ui.add_car.model.AddMultipleImageModel
import com.a.luxurycar.code_files.view_model.AddCarStepOneViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.helper.AlertDialogHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentAddCarStepOneBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_add_car_step_one.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddCarStepOneFragment :
    BaseFragment<AddCarStepOneViewModel, FragmentAddCarStepOneBinding, AddCarStepOneRepository>(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    lateinit var arrMakeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrCitiesListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrBodyTypeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrCarModelListHashMap: ArrayList<HashMap<String, String>>
    var makeId = ""
    var cityId = ""
    var bodyTypeId = ""
    var carModelId = ""
    var isShowMoreDetails = false
    var PICK_IMAGE_MULTIPLE = 321
    var CAMERA_REQUEST = 122
    var MY_CAMERA_PERMISSION_CODE = 100
    var imageName = ""
    lateinit var file:File

    companion object {
        private val REQUEST_TAKE_PHOTO = 321
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 123
    }

    lateinit var listImage: ArrayList<AddMultipleImageModel>

    override fun getViewModel()=AddCarStepOneViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAddCarStepOneBinding.inflate(inflater,container,false)
    override fun getRepository()= AddCarStepOneRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listImage = arrayListOf()
        //selectRadioButtonId()
        manageClickListeners()
        callMakeListApi()
        callCitiesListApi()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    private fun manageClickListeners() {
        binding.linLayoutAddPictures.setOnClickListener {
            openBottomSheet()
        }

        binding.spinnerSelectMake.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    makeId = arrMakeListHashMap[position].get(Const.KEY_ID).toString()
                    if (makeId != "0"){
                        callBodyTypeApi()
                    }

                }

            }

        binding.spinnerSelectBodyType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    bodyTypeId = arrBodyTypeListHashMap[position].get(Const.KEY_ID).toString()
                    if (makeId != "0") {
                        callCarModelTypeApi()
                    }
                }

            }
        binding.spinnerSelectModel.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    carModelId = arrBodyTypeListHashMap[position].get(Const.KEY_ID).toString()

                }

            }


        binding.spinnerSelectCity.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    cityId = arrMakeListHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.radioButtonSellCar.setOnClickListener {

            if(binding.radioButtonSellCar.isChecked) {
                binding.txtViewPrice.text = "Price"
                binding.consLayoutWeeklyAndMonthlyPrice.visibility = View.GONE
            }

        }

        binding.radioButtonRentCar.setOnClickListener {

            if(binding.radioButtonRentCar.isChecked) {
                binding.txtViewPrice.text = "Daily Price"
                binding.consLayoutWeeklyAndMonthlyPrice.visibility = View.VISIBLE
            }

        }

        binding.txtViewMoreDetailsAndLessDetails.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.txtViewMoreDetailsAndLessDetails.setOnClickListener {
            isShowMoreDetails = !isShowMoreDetails
            if(isShowMoreDetails){
                binding.cardViewSecondPartCarDetails.visibility = View.VISIBLE
                binding.txtViewMoreDetailsAndLessDetails.text =getString(R.string.str_less_deatils_non_mandatory)
            }else{
                binding.cardViewSecondPartCarDetails.visibility = View.GONE
                binding.txtViewMoreDetailsAndLessDetails.text = getString(R.string.str_more_deatils_non_mandatory)
            }



        }


    }

    private fun callCarModelTypeApi() {
        viewModel.getCarModelResponse(bodyTypeId)
        viewModel.carModelResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrCarModelListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select Model")
            arrCarModelListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrCarModelListHashMap.add(hashMap)
                            }
                        }
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrCarModelListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectModel.setAdapter(adapter)



                }
                is Resource.Failure -> handleApiErrors(it)
            }

        })
    }


    private fun callCitiesListApi() {
        viewModel.getAddCarStepCitiesResponse("1")

        viewModel.citiesListResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrCitiesListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select")
            arrCitiesListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrCitiesListHashMap.add(hashMap)
                            }
                        }
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrCitiesListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectCity.setAdapter(adapter)



                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun callMakeListApi() {
        viewModel.getMakeListResponse()
        viewModel.makeListResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrMakeListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select")
            arrMakeListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrMakeListHashMap.add(hashMap)
                            }
                        }
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrMakeListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectMake.setAdapter(adapter)



                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })

    }
    private fun callBodyTypeApi() {
        viewModel.getBodyTypeResponse(makeId)
        viewModel.bodyTypeResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrBodyTypeListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select")
            arrBodyTypeListHashMap.add(hashMapDefaultitem)

            binding.progressbarAddCarStepOne.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {

                    if (it.values.status == 1) {
                        binding.progressbarAddCarStepOne.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrBodyTypeListHashMap.add(hashMap)
                            }
                        }
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrBodyTypeListHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerSelectBodyType.setAdapter(adapter)



                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_SELECT_IMAGE_IN_ALBUM -> {

                val permissionCheckRead = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )

                val permissionCheckWrite = ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )


                if (permissionCheckRead == 0 && permissionCheckWrite == 0) {
                    selectImageInAlbum()
                } else {
                    AlertDialogHelper.showMessage(
                        requireContext(),
                        getString(R.string.camera_setting)
                    )
                }

                return
            }

            REQUEST_TAKE_PHOTO -> {

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


                if (permissionCheckCamera == 0 && permissionCheckRead == 0 && permissionCheckWrite == 0) {
                    takePhotoFromCamera()
                } else {
                    AlertDialogHelper.showMessage(
                        requireContext(),
                        getString(R.string.camera_setting)
                    )
                }

                return
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == AppCompatActivity.RESULT_OK) {
            galleryAddPic()
        } else if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == AppCompatActivity.RESULT_OK) {

            if (data != null) {
                if (data?.getClipData() != null) {
                    val count = data.clipData!!.itemCount

                    for (i in 0..count - 1) {
                        val contentURI = data.clipData!!.getItemAt(i).uri
                        val image_uri = contentURI.toString()
                        file = File(getPath(contentURI))
                        val imageName = file.name
                        listImage.add(AddMultipleImageModel(image_uri, imageName))
                        setImageRecyclerView()

                    }

                } else if (data?.getData() != null) {

                    val contentURI = data.data
                    val image_uri = contentURI.toString()

                    //get the image name
                     file = File(getPath(contentURI))
                     val imageName = file.name

                    listImage.add(AddMultipleImageModel(image_uri, imageName))
                    setImageRecyclerView()

                }

            }

        } else if (requestCode == 1 && resultCode == 1) {

        }
    }

    private fun getPath(contentURI: Uri?): String {
        var result = ""
        val cursor =
            contentURI?.let { context?.getContentResolver()?.query(it, null, null, null, null) }
        if (cursor == null) { // Source is Dropbox or other similar local file path
            if (contentURI != null) {
                result = contentURI.getPath().toString()
            }
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
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
                        ),REQUEST_TAKE_PHOTO
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
            context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
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
        photoPickerIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(photoPickerIntent, REQUEST_SELECT_IMAGE_IN_ALBUM)


    }

    private fun galleryAddPic() {

        if (currentPhotoPath.isNotEmpty()) {
            val f = File(currentPhotoPath)
            imageName = f.name
            //image_uri = compressTheImageBeforeSendingToServer(currentPhotoPath).toString()
            val image_uri = Uri.fromFile(f).toString()

            listImage.add(AddMultipleImageModel(image_uri,imageName))
            setImageRecyclerView()

        }
    }

    private fun setImageRecyclerView() {
        val addMultipleImageAdapter = AddMultipleImageAdapter(requireContext(),listImage)
        binding.recyclerviewMultipleImageUpload.adapter = addMultipleImageAdapter
        binding.recyclerviewMultipleImageUpload.setNestedScrollingEnabled(false);
        binding.recyclerviewMultipleImageUpload.setLayoutManager(GridLayoutManager(requireContext(), 2))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        val india = LatLng(     23.63936, 79.14712)
        map.addMarker(MarkerOptions().position(india).title("India location"))
        map.moveCamera(CameraUpdateFactory.newLatLng(india))
    }


}