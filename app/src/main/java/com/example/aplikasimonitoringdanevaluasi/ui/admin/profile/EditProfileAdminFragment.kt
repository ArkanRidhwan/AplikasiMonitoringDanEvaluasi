package com.example.aplikasimonitoringdanevaluasi.ui.admin.profile

import android.app.Activity
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentEditProfileAdminBinding
import com.example.aplikasimonitoringdanevaluasi.utils.encodeImage
import com.example.aplikasimonitoringdanevaluasi.utils.loadCircleImageFromUri
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import com.example.aplikasimonitoringdanevaluasi.utils.visible
import com.github.dhaval2404.imagepicker.ImagePicker
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
                            binding.btnSaveProfile.visible()
                            binding.ivProfile.apply {
                                visible()
                                loadCircleImageFromUri(imageUri)
                            }
                        }
                        ImagePicker.RESULT_ERROR -> {
                            context?.showToast(ImagePicker.getError(data))
                        }
                        else -> {
                            context?.showToast("Dibatalkan")
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
            }

        }
    }

}