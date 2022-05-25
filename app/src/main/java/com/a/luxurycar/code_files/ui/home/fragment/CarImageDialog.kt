package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.a.luxurycar.R

class CarImageDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView:View = inflater.inflate(R.layout.image_dialog,container,false)

         return rootView
    }

}