package com.example.aplikasimonitoringdanevaluasi.ui.storage

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import com.example.aplikasimonitoringdanevaluasi.databinding.ActivityStorageBinding
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2

class StorageActivity : AppCompatActivity(), View.OnClickListener {

    var type: String? = ""
    var encodedImage: String? = ""
    var videoUri: Uri? = null
    var documentUri: Uri? = null

    private lateinit var file: File
    private lateinit var binding: ActivityStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnImage.setOnClickListener(this)
        binding.btnVideo.setOnClickListener(this)
        binding.btnDocument.setOnClickListener(this)
        binding.btnUpload.setOnClickListener(this)
    }

    private val startForImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val imageUri = data?.data
                    file = imageUri?.toFile() as File
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(ImageDecoder.createSource(file))
                    } else {
                        MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            imageUri
                        )
                    }
                    encodedImage = bitmap.encodeImage()
                    binding.btnUpload.visible()
                    binding.imgPreview.apply {
                        visible()
                        loadCircleImageFromUri(imageUri)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    showToast(ImagePicker.getError(data))
                }
                else -> {
                    showToast("Dibatalkan")
                }
            }
        }

    private val startForVideoResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == RESULT_OK && data?.data != null) {
                videoUri = data.data
                file = File(videoUri.toString())
                binding.btnUpload.visible()
                binding.vidPreview.apply {
                    visible()
                    setVideoURI(videoUri)
                    start()
                }
            }
        }

    private val startForDocumentResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == RESULT_OK && data?.data != null) {
                documentUri = data.data
                file = File(documentUri.toString())
                binding.docPreview.apply {
                    visible()
                    text = file.name
                }
                binding.btnUpload.visible()
            }
        }


    override fun onClick(v: View?) {
        when (v) {
            binding.btnImage -> {
                type = "Image"
                ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .createIntent {
                        startForImageResult.launch(it)
                    }
            }

            binding.btnVideo -> {
                type = "Video"
                val intent = Intent().apply {
                    type = "video/*"
                    action = Intent.ACTION_GET_CONTENT
                }
                startForVideoResult.launch(intent)
            }

            binding.btnDocument -> {
                type = "Document"
                val intent = Intent().apply {
                    type = "application/pdf"
                    action = Intent.ACTION_GET_CONTENT
                }
                startForDocumentResult.launch(intent)
            }

            binding.btnUpload -> {
                binding.progressCircular.visible()
                binding.tvProgress.visible()

                when (type) {
                    "Image" -> {
                        binding.imgPreview.uploadImage(file)
                            .addOnFailureListener {
                                binding.progressCircular.gone()
                                binding.tvProgress.gone()
                                showToast("Upload Image Failed!")
                            }
                            .addOnSuccessListener { task ->
                                binding.progressCircular.gone()
                                binding.tvProgress.gone()
                                showToast("Upload Image Success!")

                                task.storage.downloadUrl.addOnSuccessListener { url ->
                                    Log.d(TAG, "downloadUri: $url")
                                }
                            }
                            .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                                val progress = (100.0 * bytesTransferred) / totalByteCount
                                binding.tvProgress.text = "${progress.toInt()} %"
                            }
                    }
                    "Video" -> {
                        binding.vidPreview.stopPlayback()
                        val storageRef = Firebase.storage.reference
                        val videosRef = storageRef.child("videos/${file.name}")
                        videoUri?.let { uri ->
                            videosRef.putFile(uri)
                                .addOnFailureListener {
                                    binding.progressCircular.gone()
                                    binding.tvProgress.gone()
                                    showToast("Upload Video Failed!")
                                }
                                .addOnSuccessListener { task ->
                                    binding.progressCircular.gone()
                                    binding.tvProgress.gone()
                                    showToast("Upload Video Success!")

                                    task.storage.downloadUrl.addOnSuccessListener { url ->
                                        Log.d(TAG, "downloadUri: $url")
                                    }
                                }
                                .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                                    val progress = (100.0 * bytesTransferred) / totalByteCount
                                    binding.tvProgress.text = "${progress.toInt()} %"
                                }
                        }
                    }
                    "Document" -> {
                        val storageRef = Firebase.storage.reference
                        val documentsRef = storageRef.child("documents/${file.name}")
                        documentUri?.let { uri ->
                            documentsRef.putFile(uri)
                                .addOnFailureListener {
                                    binding.progressCircular.gone()
                                    binding.tvProgress.gone()
                                    showToast("Upload Document Failed!")
                                }
                                .addOnSuccessListener { task ->
                                    binding.progressCircular.gone()
                                    binding.tvProgress.gone()
                                    showToast("Upload Document Success!")

                                    task.storage.downloadUrl.addOnSuccessListener { url ->
                                        Log.d(TAG, "Document: $url")
                                    }
                                }
                                .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                                    val progress = (100.0 * bytesTransferred) / totalByteCount
                                    binding.tvProgress.text = "${progress.toInt()} %"
                                }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "StorageActivity"
    }
}