package com.example.intent_stuff

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.intent_stuff.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
//
//            binding.btnCaptureImage.setOnClickListener {
//                btnCaptureClickListener(binding.videoView).launch("video/*")
//            }
//            binding.btnCaptureImage.setOnClickListener {
//                btnCaptureClickListener(binding.imageView).launch("image/*")
//        }
        return binding.root
    }
//    val btnCaptureClickListener = fun(v: View) =
//        registerForActivityResult(ActivityResultContracts.GetContent()) {
//            when (v) {
//                is ImageView -> v.setImageURI(it)
//                is VideoView -> v.setVideoURI(it)
//            }
//        }
}