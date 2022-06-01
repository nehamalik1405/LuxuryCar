package com.a.luxurycar.code_files.ui.home


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.auth.AuthActivity
import com.a.luxurycar.common.helper.AlertDialogHelper
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.databinding.ActivityHomeBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var imageViewMenu: ImageView
    lateinit var imgViewOpenProfile: ImageView
    lateinit var consLayout:ConstraintLayout
    lateinit var imageViewProfilePhoto:ImageView
    lateinit var navView:NavigationView
    lateinit var headerView: View
    val REQUEST_CODE = 100

    var isShow = false
    companion object {
        private val REQUEST_TAKE_PHOTO = 321
        val REQUEST_SELECT_IMAGE_IN_ALBUM = 123
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        consLayout = findViewById<ConstraintLayout>(R.id.popupLayout)
        imageViewMenu = findViewById(R.id.imgViewMenu)
        imgViewOpenProfile = findViewById(R.id.imgViewOpenProfile)
        navView = findViewById(R.id.navViewRight)
        headerView  = navView.getHeaderView(0)
        navViewRight.setItemIconTintList(null)

        menageClickEvents()
        setBottomNavigation()
        setLeftNavView()
        setRightNavView()
        setRightHeader()
        setPhoto()



        navController.popBackStack(R.id.nav_home, false)

/*

        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_follow_us -> {
                    val popup = PopupMenu(this@HomeActivity, findViewById(R.id.nav_follow_us))
                    val inflater: MenuInflater = popup.getMenuInflater()
                    inflater.inflate(R.menu.pop_up_menu, popup.getMenu())
                    popup.show()

                }

            }
            false
        })
*/


    }



    private fun setRightHeader() {
        val userData = SessionManager.getUserData()
        imageViewProfilePhoto = headerView.findViewById(R.id.imageViewProfilePhoto)
        val txtViewEmail = headerView.findViewById<TextView>(R.id.textViewEmail)
        val textViewUserName = headerView.findViewById<TextView>(R.id.txtViewUserName)
        //setPhoto()
       if (userData != null) {
           textViewUserName.text=userData.data.user.fullname
            txtViewEmail.text=userData.data.user.email
        }

        imageViewProfilePhoto.setOnClickListener {
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
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )

                val permissionCheckWrite = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )


                if (permissionCheckRead == 0 && permissionCheckWrite == 0) {
                    selectImageInAlbum()
                } else {
                    AlertDialogHelper.showMessage(
                        this,
                        getString(R.string.camera_setting)
                    )
                }

                return
            }

            REQUEST_TAKE_PHOTO -> {

                val permissionCheckCamera = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                )

                val permissionCheckRead = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )

                val permissionCheckWrite = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )


                if (permissionCheckCamera == 0 && permissionCheckRead == 0 && permissionCheckWrite == 0) {
                    takePhotoFromCamera()
                } else {
                    AlertDialogHelper.showMessage(
                        this,
                        getString(R.string.camera_setting)
                    )
                }

                return
            }

        }
    }


   // var image_uri: String? = ""

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
                       val image_uri = contentURI.toString()
                        SessionManager.setImageUrl(image_uri)
                        setPhoto()
                    }

                } else if (data?.getData() != null) {
                    // if single image is selected

                    val contentURI = data.data
                   val image_uri = contentURI.toString()
                    SessionManager.setImageUrl(image_uri)
                    setPhoto()
                }

            }

        } else if (requestCode == 1 && resultCode == 1) {

        }
    }

    private fun setPhoto() {
        val image_Url = SessionManager.getImageUrl()
        //Picasso.get().load(image_Url.toString()).transform(CircleTransform()).into(imgViewProfile)
       // Picasso.get().load(image_Url.toString()).into(imageViewProfilePhoto)
         if(image_Url != null){
             Glide.with(this)
                 .load(image_Url.toString())
                 .centerCrop()
                 .into(imageViewProfilePhoto)
         }

    }

    private fun openBottomSheet() {
        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.alert_dialog_profile_picture, null)
        val dialog = BottomSheetDialog(this)
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
            this,
            Manifest.permission.CAMERA
        )

        val permissionCheckRead = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionCheckWrite = ContextCompat.checkSelfPermission(
           this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionCheckCamera == -1 || permissionCheckRead == -1 || permissionCheckWrite == -1) {

                if (!Settings.System.canWrite(this)) {
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
                        this, "com.luxurycar.fileprovider",
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
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
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
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val permissionCheckWrite = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionCheckRead == -1 || permissionCheckWrite == -1) {

                if (!Settings.System.canWrite(this)) {
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

    private fun galleryAddPic() {

        if (currentPhotoPath.isNotEmpty()) {
            val f = File(currentPhotoPath)
            //image_uri = compressTheImageBeforeSendingToServer(currentPhotoPath).toString()
           val image_uri = Uri.fromFile(f).toString()
            SessionManager.setImageUrl(image_uri)
            setPhoto()
        }
    }

    private fun setRightNavView() {
        NavigationUI.setupWithNavController(
            binding.navViewRight,
            navController
        )

        binding.navViewRight.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val itemId = item.itemId

                if (itemId == R.id.nav_profiles) {
                    navController.navigate(R.id.nav_profiles)
                }
                else if(itemId == R.id.nav_language){
                    navController.navigate(R.id.nav_language)
                }
                else if(itemId == R.id.nav_condition){
                    navController.navigate(R.id.nav_condition)
                }
                else if(itemId == R.id.nav_change_password){
                    navController.navigate(R.id.nav_change_password)
                }
                else if(itemId == R.id.nav_logout) {
                    val sessionManager = SessionManager(this@HomeActivity)
                    sessionManager.logout()
                    startActivity(Intent(applicationContext, AuthActivity::class.java))
                    finish()
                }
                binding.drawerLayout.closeDrawer(GravityCompat.END)
                return true
            }
        })
    }

    private fun setLeftNavView(){
        NavigationUI.setupWithNavController(
            binding.navViewLeft,
            navController
        )
        binding.navViewLeft.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val itemId = item.itemId

                if (itemId == R.id.nav_about_us) {
                    navController.navigate(R.id.nav_about_us)
                }
                else if(itemId == R.id.nav_car_listing){
                    navController.navigate(R.id.nav_car_listing)
                }
                else if(itemId == R.id.nav_transport){
                    navController.navigate(R.id.nav_transport)
                }
                else if(itemId == R.id.nav_sourcing){
                    navController.navigate(R.id.nav_sourcing)
                }
                else if(itemId == R.id.nav_inspecting){
                    navController.navigate(R.id.nav_inspecting)
                }
                else if(itemId == R.id.nav_sell_ur_car){
                    navController.navigate(R.id.nav_sell_ur_car)
                }
                else if(itemId == R.id.nav_saurce_my_car){
                    navController.navigate(R.id.nav_saurce_my_car)
                }
                else if(itemId == R.id.nav_find_Garages){
                    navController.navigate(R.id.nav_find_Garages)
                }


                binding.drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
        })

    }

    private fun setBottomNavigation() {

        bottomNavigation.setupWithNavController(navController)

        bottomNavigation.setOnNavigationItemSelectedListener { it
             val itemId = it.itemId

                if (itemId == R.id.nav_contact) {
                    navController.navigate(R.id.nav_contact)
                   // consLayout.visibility = View.GONE
                }
                else if(itemId == R.id.nav_home){
                    navController.navigate(R.id.nav_home)
                    //consLayout.visibility = View.GONE
                }
                else if(itemId == R.id.nav_follow_us) {
                    onButtonShowPopupWindowClick(findViewById(R.id.nav_follow_us))


                    /* val imgYouTube = findViewById<ImageView>(R.id.imgViewYoutube)
                    val imgViewLinkedIn = findViewById<ImageView>(R.id.imgViewLinkedIn)
                    val imgViewInstagram = findViewById<ImageView>(R.id.imgViewInstagram)
                    val imgViewFacebook = findViewById<ImageView>(R.id.imgViewFacebook)

                    imgYouTube.setOnClickListener {
                        consLayout.visibility = View.GONE
                       Toast.makeText(applicationContext,"you tube",Toast.LENGTH_LONG).show()
                     }
                    imgViewLinkedIn.setOnClickListener {
                        consLayout.visibility = View.GONE
                        Toast.makeText(applicationContext,"Linkedin",Toast.LENGTH_LONG).show()
                    }
                    imgViewInstagram.setOnClickListener {
                        consLayout.visibility = View.GONE
                        Toast.makeText(applicationContext,"Instagram",Toast.LENGTH_LONG).show()
                    }
                    imgViewFacebook.setOnClickListener {
                        consLayout.visibility = View.GONE
                        Toast.makeText(applicationContext,"Facebook",Toast.LENGTH_LONG).show()
                    }

                }*/
                }
            true
        }
      /*  bottomNavigation?.menu?.findItem(R.id.nav_follow_us)?.setOnMenuItemClickListener {


            onButtonShowPopupWindowClick(findViewById(R.id.nav_follow_us))



            return@setOnMenuItemClickListener false
        }*/

    }

    private fun onButtonShowPopupWindowClick(view: View) {

        //navController.backQueue
        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window, null)
        val imgViewYoutube = popupView.findViewById<ImageView>(R.id.imgViewYoutube)
        val imgViewLinkedIn = popupView.findViewById<ImageView>(R.id.imgViewLinkedIn)
        val imgViewInstagram = popupView.findViewById<ImageView>(R.id.imgViewInstagram)
        val imgViewFacebook = popupView.findViewById<ImageView>(R.id.imgViewFacebook)
      // create the popup window
       // val width = LinearLayout.LayoutParams.WRAP_CONTENT
       // val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        val parm1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val popupWindow = PopupWindow(popupView, parm1.width, parm1.height, focusable)

        popupView.layoutParams = parm1
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken

        // show the popup window
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.BOTTOM or Gravity.RIGHT, 80,195)



        // dismiss the popup window when touched

       /* // dismiss the popup window when touched
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }*/
        imgViewYoutube.setOnClickListener {
            Toast.makeText(applicationContext,"Click pop up youtube", Toast.LENGTH_LONG).show()
            popupWindow.dismiss()
        }
        imgViewLinkedIn.setOnClickListener {
            Toast.makeText(applicationContext,"Click pop up lindedin", Toast.LENGTH_LONG).show()
            popupWindow.dismiss()
        }
        imgViewInstagram.setOnClickListener {
            Toast.makeText(applicationContext,"Click pop up instagram", Toast.LENGTH_LONG).show()
            popupWindow.dismiss()
        }
        imgViewFacebook.setOnClickListener {
            Toast.makeText(applicationContext,"Click pop up facebook", Toast.LENGTH_LONG).show()
            popupWindow.dismiss()
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun menageClickEvents() {
        imageViewMenu.setOnClickListener {
            openOrCloseDrawer()
        }

        imgViewOpenProfile.setOnClickListener{
            openOrCloseDrawerProfile()
        }

    }

    private fun openOrCloseDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)

        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun openOrCloseDrawerProfile() {

        if(binding.drawerLayout.isDrawerOpen(GravityCompat.END)){
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }else{
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }
    }


    override fun onBackPressed() {
        when(navController.currentDestination?.id) {
            R.id.nav_home-> {
                val builder1 = AlertDialog.Builder(this)
                builder1.setMessage("Are You Sure You Want to Exit the App?")
                builder1.setCancelable(true)
                builder1.setPositiveButton("Yes") { dialog, id ->

                    finish()

                }
                builder1.setNegativeButton("No")
                { dialog, id -> dialog.cancel() }
                val alert11 = builder1.create()
                alert11.show()
            }
            else ->
            {
                super.onBackPressed()
            }
        }
    }

 /*   override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
            isShow = false
            consLayout.visibility = View.GONE


        return super.dispatchTouchEvent(ev)
    }*/
/*    override fun onTouchEvent(event: MotionEvent?): Boolean {
        isShow = false
            consLayout.visibility = View.GONE

        return super.onTouchEvent(event)
    }*/

    fun capturePhoto() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imgViewProfile.setImageURI(data?.data) // handle chosen image
        }
    }*/
    }
