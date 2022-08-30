package com.a.luxurycar.code_files.ui.home.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SellYourCarRepository
import com.a.luxurycar.code_files.view_model.SellYourCarViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.ActivityAddCarBinding
import com.a.luxurycar.databinding.FragmentSellYourCarBinding


class SellYourCarFragment :
    BaseFragment<SellYourCarViewModel, FragmentSellYourCarBinding, SellYourCarRepository>() {

    var carAdId = ""
    override fun getViewModel() = SellYourCarViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSellYourCarBinding.inflate(inflater, container, false)

    override fun getRepository() =
        SellYourCarRepository(ApiAdapter.buildApi(ApiService::class.java))

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    lateinit var step1: ConstraintLayout
    lateinit var step2: ConstraintLayout
    lateinit var step3: ConstraintLayout
    lateinit var step4: ConstraintLayout
    lateinit var viewLineStep1: View
    lateinit var viewLineStep2: View
    lateinit var viewLineStep3: View
    lateinit var viewLineStep4: View
    lateinit var stepTextViewAddCar: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main_for_add_car) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        stepTextViewAddCar = view.findViewById(R.id.txtViewAddCar)


        step1 = view.findViewById(R.id.consLayoutTabStep1)
        step2 = view.findViewById(R.id.consLayoutTabStep2)
        step3 = view.findViewById(R.id.consLayoutTabStep3)
        step4 = view.findViewById(R.id.consLayoutTabStep4)

        viewLineStep1 = view.findViewById(R.id.viewLineStep1)
        viewLineStep2 = view.findViewById(R.id.viewLineStep2)
        viewLineStep3 = view.findViewById(R.id.viewLineStep3)
        viewLineStep4 = view.findViewById(R.id.viewLineStep4)

        manageClickListener()
    }

    fun getCurrentId(cardAddId: String) {
        carAdId = cardAddId
    }

    fun currentDestination() {
        if (navController.currentDestination?.id == R.id.addCarStepOne) {
            stepTextViewAddCar.setText("Add car step-1")
            stepTextViewAddCar.setText("Add car step-1")
            viewLineStep1.setBackgroundResource(R.color.yellow_color)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.green)
            viewLineStep4.setBackgroundResource(R.color.green)
        }
        if (navController.currentDestination?.id == R.id.addCarStepTwo) {
            stepTextViewAddCar.setText("Add car step-2")
            viewLineStep1.setBackgroundResource(R.color.green)
            viewLineStep2.setBackgroundResource(R.color.yellow_color)
            viewLineStep3.setBackgroundResource(R.color.green)
            viewLineStep4.setBackgroundResource(R.color.green)
        }
        if (navController.currentDestination?.id == R.id.addCarStepThree) {
            stepTextViewAddCar.setText("Add car step-3")
            viewLineStep1.setBackgroundResource(R.color.green)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.yellow_color)
            viewLineStep4.setBackgroundResource(R.color.green)
        }
        if (navController.currentDestination?.id == R.id.addCarStepFour) {
            stepTextViewAddCar.setText("Add car step-4")
            viewLineStep1.setBackgroundResource(R.color.green)
            viewLineStep2.setBackgroundResource(R.color.green)
            viewLineStep3.setBackgroundResource(R.color.green)
            viewLineStep4.setBackgroundResource(R.color.yellow_color)
        }
    }


    private fun manageClickListener() {

        //val id  = navController.currentDestination?.id
        /* step1.setOnClickListener {
             stepTextViewAddCar.setText("Add car step-1")
             viewLineStep1.setBackgroundResource(R.color.yellow_color)
             viewLineStep2.setBackgroundResource(R.color.green)
             viewLineStep3.setBackgroundResource(R.color.green)
             viewLineStep4.setBackgroundResource(R.color.green)
             navController.navigate(R.id.addCarStepOne)
         }*/

        step2.setOnClickListener {
            if (!carAdId.isNullOrEmpty()) {
                stepTextViewAddCar.setText("Add car step-2")
                viewLineStep1.setBackgroundResource(R.color.green)
                viewLineStep2.setBackgroundResource(R.color.yellow_color)
                viewLineStep3.setBackgroundResource(R.color.green)
                viewLineStep4.setBackgroundResource(R.color.green)

                if (!carAdId.equals("") && !carAdId.isNullOrEmpty()) {
                    val bundle = Bundle().apply {
                        putString("car_ads_id", carAdId)
                    }
                    navController.navigate(R.id.addCarStepTwo, bundle)
                }

            }

        }
        step3.setOnClickListener {
            if (!carAdId.equals("") && !carAdId.isNullOrEmpty()) {
                stepTextViewAddCar.setText("Add car step-3")
                viewLineStep1.setBackgroundResource(R.color.green)
                viewLineStep2.setBackgroundResource(R.color.green)
                viewLineStep3.setBackgroundResource(R.color.yellow_color)
                viewLineStep4.setBackgroundResource(R.color.green)
                val bundle = Bundle().apply {
                    putString("car_ads_id", carAdId)
                }
                navController.navigate(R.id.addCarStepThree, bundle)
            }


        }
        step4.setOnClickListener {
            if (!carAdId.isNullOrEmpty()) {
                stepTextViewAddCar.setText("Add car step-4")
                viewLineStep1.setBackgroundResource(R.color.green)
                viewLineStep2.setBackgroundResource(R.color.green)
                viewLineStep3.setBackgroundResource(R.color.green)
                viewLineStep4.setBackgroundResource(R.color.yellow_color)

                if (!carAdId.equals("") && !carAdId.isNullOrEmpty()) {
                    val bundle = Bundle().apply {
                        putString("car_ads_id", carAdId)
                    }
                    navController.navigate(R.id.addCarStepFour, bundle)
                }

            }
        }


    }

}