package com.a.luxurycar.code_files.ui.add_car.fragment

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.adapter.AddMultipleImageAdapter
import com.a.luxurycar.code_files.ui.add_car.model.AddMultipleImageModel
import com.a.luxurycar.common.helper.AlertDialogHelper
import com.a.luxurycar.databinding.FragmentAddCarStepOneBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddCarStepOne : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
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
    var _binding: FragmentAddCarStepOneBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentAddCarStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listImage = arrayListOf()
        manageClickListeners()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    private fun manageClickListeners() {
        binding.linLayoutAddPictures.setOnClickListener {
            openBottomSheet()
        }
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


    // var image_uri: String? = ""

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

                        //val subStrig= path.subSequence(10,path.length)
                        //Log.e("path",subStrig.toString())
                        val image_uri = contentURI.toString()
                        file = File(getPath(contentURI))
                        val imageName = file.name
                        listImage.add(AddMultipleImageModel(image_uri, imageName))
                        setImageRecyclerView()

                    }

                } else if (data?.getData() != null) {

                    val contentURI = data.data
                    val image_uri = contentURI.toString()

                    //val pathName = contentURI?.path
                    //pathName?.substring(pathName.lastIndexOf("/") + 1)



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


    private fun selectMultipleImages() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_MULTIPLE)
    }




}