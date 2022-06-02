package com.a.luxurycar.code_files.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.a.luxurycar.R
import com.a.luxurycar.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding


    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController =findNavController(R.id.authNavHostFragment)

        appBarConfiguration= AppBarConfiguration(
            setOf(
                R.id.loginFragment
            )
        )
    }
}