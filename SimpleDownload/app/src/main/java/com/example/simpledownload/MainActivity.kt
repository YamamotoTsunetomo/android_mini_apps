package com.example.simpledownload

import android.app.DownloadManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.simpledownload.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var request: DownloadManager.Request

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val isImage =
            fun(url: String) = url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".jpeg")

        val withProtocol =
            fun(url: String) = if (url.startsWith("https://") || url.startsWith("http://")) url
            else "https://${url}"

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val hasInternet = connectivityManager.activeNetwork != null

        binding.btnGo.setOnClickListener {
            when {
                binding.etSearch.text.isBlank() -> Toast.makeText(
                    this,
                    "No URL!",
                    Toast.LENGTH_SHORT
                ).show()
                hasInternet -> {
                    val url = withProtocol(binding.etSearch.text.toString())
                    val fileName = when {
                        url.startsWith("https://") -> url.substring(8)
                        url.startsWith("http://") -> url.substring(7)
                        else -> url
                    }
                    val uri = Uri.parse(url)

                    if (isImage(url)) {
                        binding.tvFileTitle.visibility = View.INVISIBLE
                        binding.ivFileImage.visibility = View.VISIBLE

                        Glide.with(this).load(uri).into(binding.ivFileImage)
                    } else {
                        binding.tvFileTitle.visibility = View.VISIBLE
                        binding.ivFileImage.visibility = View.INVISIBLE

                        val text = "${fileName.substring(0, 10)}..."
                        binding.tvFileTitle.text = text
                    }

                    request = DownloadManager.Request(uri)
                    request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        fileName
                    )
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                }
                else -> Toast.makeText(this, "No Internet!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDownload.setOnClickListener {
            if (binding.etSearch.text.isNotBlank()) {
                (getSystemService(DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
                binding.etSearch.text.clear()
            } else Toast.makeText(this, "No File Selected!", Toast.LENGTH_SHORT).show()
        }
    }
}