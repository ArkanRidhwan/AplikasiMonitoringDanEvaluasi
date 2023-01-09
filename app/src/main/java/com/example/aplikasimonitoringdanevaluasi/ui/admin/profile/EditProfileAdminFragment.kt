package com.example.aplikasimonitoringdanevaluasi.ui.admin.profile

import android.app.Activity
import android.content.ContentValues.TAG
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentEditProfileAdminBinding
import com.example.aplikasimonitoringdanevaluasi.ui.storage.StorageActivity
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import java.io.File


class EditProfileAdminFragment : Fragment() {

    var encodedImage: String? = ""
    var type: String? = ""

    private lateinit var binding: FragmentEditProfileAdminBinding
    private lateinit var file: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            val startForImageResult =
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
                                    requireActivity().contentResolver,
                                    imageUri
                                )
                            }
                            encodedImage = bitmap.encodeImage()
                            ivProfile.apply {
                                visible()
                                loadCircleImageFromUri(imageUri)
                            }
                            progressBarImage.gone()
                            ivProfileLoading.gone()
                        }
                        ImagePicker.RESULT_ERROR -> {
                            context?.showToast(ImagePicker.getError(data))
                        }
                        else -> {
                            context?.showToast("Dibatalkan")
                            progressBarImage.gone()
                            ivProfileLoading.gone()
                        }
                    }
                }

            tvChengePicture.setOnClickListener {
                type = "Image"
                ImagePicker.with(this@EditProfileAdminFragment)
                    .crop()
                    .compress(1024)
                    .createIntent {
                        startForImageResult.launch(it)
                    }
                ivProfileLoading.visible()
                progressBarImage.visible()
            }

            btnSaveProfile.setOnClickListener {
                progressBar.visible()
                tvProgress.visible()
                btnSaveProfile.gone()
                ivProfile.uploadImage(file)
                    .addOnFailureListener {
                        progressBar.gone()
                        tvProgress.gone()
                        requireContext().showToast("Upload Image Failed!")
                        btnSaveProfile.visible()
                    }
                    .addOnSuccessListener { task ->
                        progressBar.gone()
                        tvProgress.gone()
                        requireContext().showToast("Upload Image Success!")
                        task.storage.downloadUrl.addOnSuccessListener { url ->
                            Log.d(TAG, "downloadUri: $url")
                        }
                        btnSaveProfile.visible()
                    }
                    .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                        val progress = (100.0 * bytesTransferred) / totalByteCount
                        tvProgress.text = "${progress.toInt()} %"
                        btnSaveProfile.visible()
                    }
            }
        }
    }

}