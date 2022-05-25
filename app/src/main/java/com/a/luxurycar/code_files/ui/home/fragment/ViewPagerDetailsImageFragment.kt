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

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
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
        scaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener())
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            imageView.scaleX = scaleFactor
            imageView.scaleY = scaleFactor
            return true
        }
    }

}
