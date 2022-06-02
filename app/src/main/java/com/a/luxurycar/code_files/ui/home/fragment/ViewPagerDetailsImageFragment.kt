package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import com.a.luxurycar.R
import kotlin.math.max
import kotlin.math.min
import com.a.luxurycar.databinding.FragmentViewPagerDetailsImageBinding


class ViewPagerDetailsImageFragment : Fragment() {

    var _binding: FragmentViewPagerDetailsImageBinding? = null
    val binding get() = _binding!!;

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentViewPagerDetailsImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageView)

    }



}
