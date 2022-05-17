package com.a.luxurycar.code_files.ui.home


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.auth.AuthActivity
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*


class HomeActivity : AppCompatActivity() {
    lateinit var dialog:Dialog
    lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var imageViewMenu: ImageView
    lateinit var imageViewProfile: ImageView
    lateinit var navigationMenuView: NavigationMenuView
    lateinit var linLayoutPopUpMenu:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        imageViewMenu = findViewById(R.id.imgViewMenu)
        imageViewProfile = findViewById(R.id.imgViewProfile)


        menageClickEvents()
        setBottomNavigation()
        setLeftNavView()
        setRightNavView()


        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_follow_us -> {
                    val popup = PopupMenu(this@HomeActivity, findViewById(R.id.nav_follow_us))
                    val inflater: MenuInflater = popup.getMenuInflater()
                    inflater.inflate(R.menu.follow_menu, popup.getMenu())
                    popup.show()
                }

            }
            false
        })



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
    }
    private fun setBottomNavigation() {

        bottomNavigation.setupWithNavController(navController)

       /* bottomNavigation.setOnNavigationItemSelectedListener { it
             val itemId = it.itemId

                if (itemId == R.id.nav_contact) {
                    navController.navigate(R.id.nav_contact)
                }
                else if(itemId == R.id.nav_home){
                    navController.navigate(R.id.nav_home)
                }
                else if(itemId == R.id.nav_follow_us){
                    navController.navigate(R.id.nav_follow_us)
                }


            true
        }*/

    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun menageClickEvents() {
        imageViewMenu.setOnClickListener {
            openOrCloseDrawer()
        }

        imageViewProfile.setOnClickListener{
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
    private fun openBottomSheet() {

        // on below line we are inflating a layout file which we have created.

        /*   val view = layoutInflater.inflate(R.layout.bottom_view, null)*/
        val uTube = dialog.findViewById<ImageView>(R.id.imgViewYouTube)
        val linkedIn = dialog.findViewById<ImageView>(R.id.imgViewLinkedIn)
        val Instagram = dialog.findViewById<ImageView>(R.id.imgViewInstagram)
        val facebook = dialog.findViewById<ImageView>(R.id.imgViewFacebook)


        uTube.setOnClickListener {
            Toast.makeText(applicationContext,"You Tube click",Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        linkedIn.setOnClickListener {
            Toast.makeText(applicationContext,"LinkedIn click",Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        Instagram.setOnClickListener {
            Toast.makeText(applicationContext,"Instagram click",Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        facebook.setOnClickListener {
            Toast.makeText(applicationContext,"Facebook click",Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

    }
    }
