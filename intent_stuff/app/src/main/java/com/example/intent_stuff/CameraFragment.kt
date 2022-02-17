package com.example.intent_stuff

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.intent_stuff.databinding.FragmentCameraBinding

class CameraFragment() : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private lateinit var observer: LifeCycleObserver


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observer = LifeCycleObserver(requireActivity().activityResultRegistry)
        lifecycle.addObserver(observer)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        if (isAdded) {
            binding.btnCaptureImage.setOnClickListener {
                observer.setImage(binding.imageView)
            }
        }
        return binding.root
    }
}

class LifeCycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {
    private lateinit var getContent: ActivityResultLauncher<String>

    private var uri: Uri? = null
    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, ActivityResultContracts.GetContent()) {
            uri = it
        }
    }

    fun setImage(v: ImageView) {
        getContent.launch("image/*")
        v.setImageURI(uri)
    }
}

