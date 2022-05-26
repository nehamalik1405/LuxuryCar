package com.a.luxurycar.code_files.ui.add_car

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.a.luxurycar.R
import com.a.luxurycar.databinding.ActivityAddCarBinding

class AddCarActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddCarBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    lateinit var step1: ConstraintLayout
    lateinit var step2: ConstraintLayout
    lateinit var step3: ConstraintLayout
    lateinit var step4: ConstraintLayout
    lateinit var stepTextViewAddCar:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_content_main_for_add_car)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        stepTextViewAddCar = findViewById(R.id.txtViewAddCar)
        step1 = findViewById(R.id.consLayoutTabStep1)
        step2 = findViewById(R.id.consLayoutTabStep2)
        step3 = findViewById(R.id.consLayoutTabStep3)
        step4 = findViewById(R.id.consLayoutTabStep4)
        manageClickListener()
    }

    private fun manageClickListener() {

        step1.setOnClickListener {
            stepTextViewAddCar.setText("Add car step-1")
            step1.setBackgroundResource(R.drawable.drawable_tab_background)
            step2.setBackgroundResource(0)
            step3.setBackgroundResource(0)
            step4.setBackgroundResource(0)

            navController.navigate(R.id.addCarStepOne)
        }
        step2.setOnClickListener {
            stepTextViewAddCar.setText("Add car step-2")
            step1.setBackgroundResource(0)
            step2.setBackgroundResource(R.drawable.drawable_tab_background)
            step3.setBackgroundResource(0)
            step4.setBackgroundResource(0)

            navController.navigate(R.id.addCarStepTwo)

        }
        step3.setOnClickListener {
            stepTextViewAddCar.setText("Add car step-3")
            step1.setBackgroundResource(0)
            step2.setBackgroundResource(0)
            step3.setBackgroundResource(R.drawable.drawable_tab_background)
            step4.setBackgroundResource(0)
            navController.navigate(R.id.addCarStepThree)
        }
        step4.setOnClickListener {
            stepTextViewAddCar.setText("Add car step-4")
            step1.setBackgroundResource(0)
            step2.setBackgroundResource(0)
            step3.setBackgroundResource(0)
            step4.setBackgroundResource(R.drawable.drawable_tab_background)
            navController.navigate(R.id.addCarStepFour)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}