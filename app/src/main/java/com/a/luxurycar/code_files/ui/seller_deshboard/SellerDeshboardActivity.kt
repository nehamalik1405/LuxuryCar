package com.a.luxurycar.code_files.ui.seller_deshboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.auth.AuthActivity
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.databinding.ActivitySellerDeshboardBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.toolbar.*


class SellerDeshboardActivity : AppCompatActivity() {

    lateinit var binding:ActivitySellerDeshboardBinding
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySellerDeshboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_content_main_for_seller)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        manageClickListener()
        setRightNavView()
        setRightHeader()
    }

    private fun manageClickListener() {
        imgViewOpenProfile.setOnClickListener{
            openOrCloseDrawerSellerProfile()
        }
    }

    private fun openOrCloseDrawerSellerProfile() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.END)){
            binding.drawerLayout.closeDrawer(GravityCompat.END)
        }else{
            binding.drawerLayout.openDrawer(GravityCompat.END)
        }
    }

    private fun setRightHeader() {

        val header = binding.navViewRightSeller.getHeaderView(0)
        val sellerData = SessionManager.getUserData()
        val txtViewEmail = header.findViewById<TextView>(R.id.textViewHeaderEmail)
        val textViewUserName = header.findViewById<TextView>(R.id.txtViewHeaderUserName)
        //setPhoto()
        if (sellerData != null) {
            textViewUserName.text=sellerData.data.user.company_name
            txtViewEmail.text=sellerData.data.user.email
        }

    }

    private fun setRightNavView() {
        NavigationUI.setupWithNavController(
            binding.navViewRightSeller,
            navController
        )

        binding.navViewRightSeller.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val itemId = item.itemId

                if (itemId == R.id.nav_sellerProfileFragment) {
                    navController.navigate(R.id.nav_sellerProfileFragment)}
                else if(itemId == R.id.nav_change_password){
                    navController.navigate(R.id.nav_change_password)
                }
                else if(itemId == R.id.nav_logout) {
                    val sessionManager = SessionManager(this@SellerDeshboardActivity)
                    sessionManager.logout()
                    startActivity(Intent(applicationContext, AuthActivity::class.java))
                    finish()
                }
                binding.drawerLayout.closeDrawer(GravityCompat.END)
                return true
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}