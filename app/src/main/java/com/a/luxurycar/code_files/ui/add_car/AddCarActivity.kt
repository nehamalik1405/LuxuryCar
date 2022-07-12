package com.a.luxurycar.code_files.ui.add_car

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
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
    lateinit var viewLineStep1 : View
    lateinit var viewLineStep2 : View
    lateinit var viewLineStep3 : View
    lateinit var viewLineStep4 : View
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

        viewLineStep1 = findViewById(R.id.viewLineStep1)
        viewLineStep2 = findViewById(R.id.viewLineStep2)
        viewLineStep3 = findViewById(R.id.viewLineStep3)
        viewLineStep4 = findViewById(R.id.viewLineStep4)

        manageClickListener()
    }



    private fun manageClickListener() {

        step1.setOnClickListener {
            stepTextViewAddCar.setText("Add car step-1")
            viewLineStep1.setBackgroundResource(R.color.yellow_color)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.green)
            viewLineStep4.setBackgroundResource(R.color.green)

            navController.navigate(R.id.addCarStepOne)
        }
        step2.setOnClickListener {
           // if(navController.currentDestination?.id == R.id.addCarStepTwo){
                stepTextViewAddCar.setText("Add car step-2")
                viewLineStep1.setBackgroundResource(R.color.green)
                viewLineStep2.setBackgroundResource(R.color.yellow_color)
                viewLineStep3.setBackgroundResource(R.color.green)
                viewLineStep4.setBackgroundResource(R.color.green)
            //}


            navController.navigate(R.id.addCarStepTwo)

        }
        step3.setOnClickListener {
            stepTextViewAddCar.setText("Add car step-3")
            viewLineStep1.setBackgroundResource(R.color.green)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.yellow_color)
            viewLineStep4.setBackgroundResource(R.color.green)
            navController.navigate(R.id.addCarStepThree)
        }
        step4.setOnClickListener {
            stepTextViewAddCar.setText("Add car step-4")
            viewLineStep1.setBackgroundResource(R.color.green)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.green)
            viewLineStep4.setBackgroundResource(R.color.yellow_color)
            navController.navigate(R.id.addCarStepFour)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        finish()
    }

}