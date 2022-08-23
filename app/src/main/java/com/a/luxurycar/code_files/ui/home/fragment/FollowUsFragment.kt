package com.a.luxurycar.code_files.ui.home.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.FollowUsRepository
import com.a.luxurycar.code_files.view_model.FollowUsViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentFollowUsBinding
import kotlinx.android.synthetic.main.fragment_seller_home.*


class FollowUsFragment : BaseFragment<FollowUsViewModel,FragmentFollowUsBinding,FollowUsRepository>() {

   override fun getViewModel()=FollowUsViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentFollowUsBinding.inflate(inflater,container,false)
    override fun getRepository() = FollowUsRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListener()
    }

    private fun manageClickListener() {
        binding.btnBookNo.setOnClickListener {
            findNavController().navigate(R.id.nav_book_an_appointment)
        }
        binding.btnEnquire.setOnClickListener {
            findNavController().navigate(R.id.nav_new_enquiry_form)
        }
        binding.imgViewYoutube.setOnClickListener{
            val url = "https://www.youtube.com/"
            val i =  Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i)
        }
        binding.imgViewFacebook.setOnClickListener{
            val url = "https://www.facebook.com/login/";
            val i =  Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i)
        }
        binding.imgViewLinkedIn.setOnClickListener{
            val url = "https://in.linkedin.com/"
            val i =  Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i)
        }
        binding.imgViewInstagram.setOnClickListener{
            val url = "https://www.instagram.com/"
            val i =  Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i)
        }
    }

}