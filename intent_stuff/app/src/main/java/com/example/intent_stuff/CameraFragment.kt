package com.example.intent_stuff

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.intent_stuff.databinding.FragmentCameraBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.runBlocking
import java.io.File

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!


    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val model = ViewModelProvider(this).get(CameraViewModel::class.java)
            model.event.observe(viewLifecycleOwner){
                if (!it.hasBeenHandled()) {
                    binding.imageView.setImageURI(it.content)
                }
            }

        val camera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            Log.d("CAMERA", it.toString())
            Log.d("CAMERA", uri.toString())
            if (it) {
                model.setSavedStateHandle(uri)
            }
        }

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
