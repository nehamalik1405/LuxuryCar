package com.a.luxurycar.code_files.ui.add_car.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarRepository
import com.a.luxurycar.code_files.view_model.AddCarViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.utils.SetTextColor
import com.a.luxurycar.databinding.FragmentAddCarStepThreeBinding


class AddCarStepThreeFragment :
    BaseFragment<AddCarViewModel, FragmentAddCarStepThreeBinding, AddCarRepository>() {

    override fun getViewModel() = AddCarViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAddCarStepThreeBinding.inflate(inflater, container, false)

    override fun getRepository() = AddCarRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListenerEvent()
    }

    private fun manageClickListenerEvent() {

        binding.cardViewBasicPlan.setOnClickListener {
            // change card view color on card selection
            binding.consLayoutBasicPlan.setBackgroundResource(R.color.green)
            binding.consLayoutPremiumPlan.setBackgroundResource(R.color.cardview_color)

            binding.consLayoutBasicCurve.setBackgroundResource(R.drawable.drawable_arc_for_yellow_background)
            binding.consLayoutPremiumCurve.setBackgroundResource(R.drawable.drawable_arc)

            binding.consLayoutFree.setBackgroundResource(R.color.white)
            binding.txtViewFree.SetTextColor(R.color.green)
            binding.consLayoutAEDPrice.setBackgroundResource(R.color.color_background_black)
            binding.txtViewAEDPrice.SetTextColor(R.color.white)

            // button color change on card selection
            binding.btnSelectBasic.setBackgroundResource(R.drawable.drawable_selected_premium_background)
            binding.btnSelectPremium.setBackgroundResource(R.drawable.drawable_background_border)

            // right check color change
            binding.imgViewRightCheck.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.imgViewYourListingWillBeRecoverByThousandOfNicheHNWBuyersFromAroundWorld.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.imgViewFeaturedFor7DaysToPromotingYourListing.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.txtViewActiveFor30Days.SetTextColor(R.color.white)
            binding.txtViewYourListingWillBeRecoverByThousandOfNicheHNWBuyersFromAroundWorld.SetTextColor(R.color.white)
            binding.txtViewFeaturedFor7DaysToPromotingYourListing.SetTextColor(R.color.white)

            binding.imgViewActiveFor60Days.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.imgViewYourListingWillBeRecoverByThousandOfNicheHNWBuyersFromAroundWorldINSEcondCardView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.imgViewListingToBeShowingOnLandingPage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.imgViewListingtobeaddedtodedicatedspacepinned.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.imgViewWeeklyemailblasttoatargetedaudiencepromotingyourvehicle.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.imgView1xsocialmediapostacrossallchannels.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.txtViewActiveFor60Days.SetTextColor(R.color.description_color)
            binding.txtViewYourListingWillBeRecoverByThousandOfNicheHNWBuyersFromAroundWorldINSecondCardView.SetTextColor(R.color.description_color)
            binding.txtViewListingToBeShowingOnLandingPage.SetTextColor(R.color.description_color)
            binding.txtViewListingtobeaddedtodedicatedspacepinned.SetTextColor(R.color.description_color)
            binding.txtViewWeeklyemailblasttoatargetedaudiencepromotingyourvehicle.SetTextColor(R.color.description_color)
            binding.txtView1xsocialmediapostacrossallchannels.SetTextColor(R.color.description_color)

        }
        binding.cardViewpremiumPlan.setOnClickListener {
            binding.consLayoutBasicPlan.setBackgroundResource(R.color.cardview_color)
            binding.consLayoutPremiumPlan.setBackgroundResource(R.color.green)

            binding.consLayoutBasicCurve.setBackgroundResource(R.drawable.drawable_arc)
            binding.consLayoutPremiumCurve.setBackgroundResource(R.drawable.drawable_arc_for_yellow_background)

            binding.consLayoutFree.setBackgroundResource(R.color.color_background_black)
            binding.txtViewFree.SetTextColor(R.color.white)
            binding.consLayoutAEDPrice.setBackgroundResource(R.color.white)
            binding.txtViewAEDPrice.SetTextColor(R.color.green)

            // button color change on card selection
            binding.btnSelectBasic.setBackgroundResource(R.drawable.drawable_background_border)
            binding.btnSelectPremium.setBackgroundResource(R.drawable.drawable_selected_premium_background)

            // right check color change
            binding.imgViewRightCheck.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.imgViewYourListingWillBeRecoverByThousandOfNicheHNWBuyersFromAroundWorld.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.imgViewFeaturedFor7DaysToPromotingYourListing.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            binding.txtViewActiveFor30Days.SetTextColor(R.color.description_color)
            binding.txtViewYourListingWillBeRecoverByThousandOfNicheHNWBuyersFromAroundWorld.SetTextColor(R.color.description_color)
            binding.txtViewFeaturedFor7DaysToPromotingYourListing.SetTextColor(R.color.description_color)

            binding.imgViewActiveFor60Days.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.imgViewYourListingWillBeRecoverByThousandOfNicheHNWBuyersFromAroundWorldINSEcondCardView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.imgViewListingToBeShowingOnLandingPage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.imgViewListingtobeaddedtodedicatedspacepinned.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.imgViewWeeklyemailblasttoatargetedaudiencepromotingyourvehicle.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.imgView1xsocialmediapostacrossallchannels.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white));
            binding.txtViewActiveFor60Days.SetTextColor(R.color.white)
            binding.txtViewYourListingWillBeRecoverByThousandOfNicheHNWBuyersFromAroundWorldINSecondCardView.SetTextColor(R.color.white)
            binding.txtViewListingToBeShowingOnLandingPage.SetTextColor(R.color.white)
            binding.txtViewListingtobeaddedtodedicatedspacepinned.SetTextColor(R.color.white)
            binding.txtViewWeeklyemailblasttoatargetedaudiencepromotingyourvehicle.SetTextColor(R.color.white)
            binding.txtView1xsocialmediapostacrossallchannels.SetTextColor(R.color.white)
        }
    }
}