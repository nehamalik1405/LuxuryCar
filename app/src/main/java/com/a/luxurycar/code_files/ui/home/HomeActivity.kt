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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.auth.AuthActivity
import com.a.luxurycar.common.helper.AlertDialogHelper
import com.a.luxurycar.common.helper.CircleTransform
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.utils.HelperClass
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.databinding.ActivityHomeBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var imageViewMenu: ImageView
    lateinit var imgViewOpenProfile: ImageView
    lateinit var consLayout:ConstraintLayout
    lateinit var imageViewProfilePhoto:ImageView
    lateinit var navViewRight:NavigationView
    lateinit var navViewLeft:NavigationView
    lateinit var leftHeaderView: View
    lateinit var rightHeaderView: View
    val REQUEST_CODE = 100

    var openRightNavigation=false

    var id = ""
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

        openRightNavigation=intent.getBooleanExtra("OpenDrawer",false)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        consLayout = findViewById<ConstraintLayout>(R.id.popupLayout)
        imageViewMenu = findViewById(R.id.imgViewMenu)
        imgViewOpenProfile = findViewById(R.id.imgViewOpenProfile)
        navViewRight = findViewById(R.id.navViewRight)
        navViewLeft = findViewById(R.id.navViewLeft)
        leftHeaderView  = navViewLeft.getHeaderView(0)
        rightHeaderView  = navViewRight.getHeaderView(0)
        navViewRight.setItemIconTintList(null)

        menageClickEvents()
        setBottomNavigation()
        checkMenuCondition()
        setLeftNavView()
        setLeftHeader()
        setRightNavView()
        setRightHeader()

        checkConditionForDrawer()
        lockedTheDrawerBothSide()



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
    private fun lockedTheDrawerBothSide() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
    fun checkConditionForDrawer() {
         if(SessionManager.isUserLoggedIn()){
                 openOrCloseDrawerProfile()

         }
    }

    private fun checkMenuCondition() {
        val itemAboutUs: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_about_us)
        val itemCarListing: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_car_listing)
        val itemTransport: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_transport)
        val itemSourcing: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_sourcing)
        val itemStorage: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_storage)
        val itemInspecting: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_inspecting)
        val itemSellUrCar: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_sell_ur_car)
        val itemSaurceMyCar: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_saurce_my_car)
        val itemFindGarages: MenuItem = binding.navViewLeft.getMenu().findItem(R.id.nav_find_Garages)
        val userRole = SessionManager.getUserRole()
        if(SessionManager.isUserLoggedIn() && userRole != null && userRole.equals(Const.KEY_BUYER, true)){
            itemAboutUs.setVisible(true)
            itemCarListing.setVisible(true)
            itemTransport.setVisible(true)
            itemSourcing.setVisible(true)
            itemStorage.setVisible(true)
            itemInspecting.setVisible(true)
            itemSellUrCar.setVisible(false)
            itemSaurceMyCar.setVisible(true)
            itemFindGarages.setVisible(true)

        }else if(SessionManager.isUserLoggedIn() && userRole != null && userRole.equals(Const.KEY_SELLER, true)){
            itemAboutUs.setVisible(true)
            itemCarListing.setVisible(true)
            itemTransport.setVisible(true)
            itemSourcing.setVisible(true)
            itemStorage.setVisible(true)
            itemInspecting.setVisible(true)
            itemSellUrCar.setVisible(true)
            itemSaurceMyCar.setVisible(false)
            itemFindGarages.setVisible(false)



        }


       /* if (!SessionManager.isUserLoggedIn()){
            itemAboutUs.setVisible(true)
            itemCarListing.setVisible(true)
            itemTransport.setVisible(true)
            itemSourcing.setVisible(true)
            itemStorage.setVisible(true)
            itemInspecting.setVisible(true)
            itemSellUrCar.setVisible(false)
            itemSaurceMyCar.setVisible(false)
            itemFindGarages.setVisible(false)
        }else{
            itemAboutUs.setVisible(true)
            itemCarListing.setVisible(true)
            itemTransport.setVisible(true)
            itemSourcing.setVisible(true)
            itemStorage.setVisible(true)
            itemInspecting.setVisible(true)
            itemSellUrCar.setVisible(false)
            itemSaurceMyCar.setVisible(true)
            itemFindGarages.setVisible(true)
        }

*/
    }

    fun setLeftHeader() {
        val userData = SessionManager.getUserData()
        val txtViewEmail = leftHeaderView.findViewById<TextView>(R.id.textViewHeaderEmail)
        val textViewUserName = leftHeaderView.findViewById<TextView>(R.id.txtViewHeaderUserName)
        val userImage = leftHeaderView.findViewById<ImageView>(R.id.imgViewUserProfile)
        //setPhoto()
         if (userData!= null){
             val fullName = userData!!.fullName
             val email = userData.email
             val countryName = userData.companyName
             val image = userData.image


             if (userData.role.equals(Const.KEY_BUYER)){
                 if(!Utils.isEmptyOrNull(fullName)) {
                     textViewUserName.text = fullName
                 } else if (!Utils.isEmptyOrNull(userData?.firstname)) {
                     textViewUserName.text = userData?.firstname +" " +userData?.lastname
                 }
             }
             if (userData.role.equals(Const.KEY_SELLER)){
                 if(!Utils.isEmptyOrNull(countryName)) {
                     textViewUserName.text = countryName
                 }
             }

             if(!Utils.isEmptyOrNull(email)) {
                 txtViewEmail.text =  email
             }

             if(!Utils.isEmptyOrNull(image)) {
                 Picasso.get().load(image).transform(CircleTransform()).into(userImage)
             }
         }

    }


     fun setRightHeader() {
        val userData = SessionManager.getUserData()
        val txtViewEmail = rightHeaderView.findViewById<TextView>(R.id.textViewHeaderEmail)
        val textViewUserName = rightHeaderView.findViewById<TextView>(R.id.txtViewHeaderUserName)
        val userImage = rightHeaderView.findViewById<ImageView>(R.id.imgViewUserProfile)
        //setPhoto()
     /*  if (userData != null) {
           textViewUserName.text=userData.data.user.fullname
            txtViewEmail.text=userData.data.user.email
        }*/

         if(userData!= null){
             val fullName = userData!!.fullName
             val email = userData.email
             val image = userData.image
             val countryName = userData.companyName



             if (userData.role.equals(Const.KEY_BUYER)){
                 if(!Utils.isEmptyOrNull(fullName)) {
                     textViewUserName.text = fullName
                 } else if (!Utils.isEmptyOrNull(userData?.firstname)) {
                     textViewUserName.text = userData?.firstname +" " +userData?.lastname
                 }
             }
             if (userData.role.equals(Const.KEY_SELLER)){
                 if(!Utils.isEmptyOrNull(countryName)) {
                     textViewUserName.text = countryName
                 }
             }

             if(!Utils.isEmptyOrNull(email)) {
                 txtViewEmail.text =  email
             }

             if(!Utils.isEmptyOrNull(image)) {
                 Picasso.get().load(image).transform(CircleTransform()).into(userImage)
             }

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
               /* else if(itemId == R.id.nav_language){
                    navController.navigate(R.id.nav_language)
                }*/
                /*else if(itemId == R.id.nav_condition){
                    navController.navigate(R.id.nav_condition)
                }*/
                else if(itemId == R.id.nav_change_password){
                    navController.navigate(R.id.nav_change_password)
                }
                else if(itemId == R.id.nav_logout) {

                        val builder1 = AlertDialog.Builder(this@HomeActivity)
                        builder1.setMessage("Are You Sure You Want to logout your account?")
                        builder1.setCancelable(true)
                        builder1.setPositiveButton("Yes") { dialog, id ->
                            val sessionManager = SessionManager(this@HomeActivity)
                            sessionManager.logout()
                            finishAffinity()
                            startActivity(Intent(applicationContext, HomeActivity::class.java))

                        }
                        builder1.setNegativeButton("No")
                        { dialog, id -> dialog.cancel() }
                        val alert11 = builder1.create()
                        alert11.show()

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
                else if(itemId == R.id.nav_storage){
                    navController.navigate(R.id.nav_storage)
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
       // binding.navViewLeft.setupWithNavController(navController)
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
                    navController.navigate((R.id.nav_follow_us))
                  //  onButtonShowPopupWindowClick(findViewById(R.id.nav_follow_us))
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

            if(!SessionManager.isUserLoggedIn()){
                startActivity(Intent(this@HomeActivity, AuthActivity::class.java))
            }else{
                openOrCloseDrawerProfile()
            }

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



   /* fun getCurrentId(id:String){
        this.id = id
    }
    fun currentDestination(){
        if(navController.currentDestination?.id == R.id.addCarStepOne){
            stepTextViewAddCar.setText("Add car step-1")
            stepTextViewAddCar.setText("Add car step-1")
            viewLineStep1.setBackgroundResource(R.color.yellow_color)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.green)
            viewLineStep4.setBackgroundResource(R.color.green)
        }
        if(navController.currentDestination?.id == R.id.addCarStepTwo){
            stepTextViewAddCar.setText("Add car step-2")
            viewLineStep1.setBackgroundResource(R.color.green)
            viewLineStep2.setBackgroundResource(R.color.yellow_color)
            viewLineStep3.setBackgroundResource(R.color.green)
            viewLineStep4.setBackgroundResource(R.color.green)
        }
        if(navController.currentDestination?.id == R.id.addCarStepThree){
            stepTextViewAddCar.setText("Add car step-3")
            viewLineStep1.setBackgroundResource(R.color.green)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.yellow_color)
            viewLineStep4.setBackgroundResource(R.color.green)
        }
        if(navController.currentDestination?.id == R.id.addCarStepFour){
            stepTextViewAddCar.setText("Add car step-4")
            viewLineStep1.setBackgroundResource(R.color.green)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.green)
            viewLineStep4.setBackgroundResource(R.color.yellow_color)
        }
    }*/


}
