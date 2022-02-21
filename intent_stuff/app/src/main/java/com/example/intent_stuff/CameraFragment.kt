package com.example.intent_stuff

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import com.example.intent_stuff.databinding.FragmentCameraBinding
import java.io.File

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!


    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val camera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            Log.d("CAMERA", it.toString())
            Log.d("CAMERA", uri.toString())
            if (it)
                binding.imageView.setImageURI(uri)
        }
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        binding.btnCaptureImage.setOnClickListener {
            val tmpFile = File.createTempFile("tmp_image_file", ".png", activity?.cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }
            uri = getUriForFile(requireContext(), "com.example.intent_stuff.fileprovider", tmpFile)
            camera.launch(uri)
        }
        return binding.root

    }
}

