package com.example.aplikasimonitoringdanevaluasi.ui.company.profile

import android.app.Activity
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
import androidx.fragment.app.viewModels
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentEditProfileCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import java.io.File


class EditProfileCompanyFragment : Fragment() {

    var encodedImage: String? = ""
    var urlDownload: String? = ""

    private lateinit var binding: FragmentEditProfileCompanyBinding
    private lateinit var file: File
    private val editProfileCompanyViewModel: EditProfileCompanyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val userId = getInstance(requireContext()).getString(Constant.ID)
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            editProfileCompanyViewModel.getCompanyById(userId).observe(viewLifecycleOwner) {
                etCompanyName.setText(it?.companyName)
                etEmailCompany.setText(it?.email)
                etCompanyPassword.setText(it?.password)
                etCompanyAddress.setText(it?.companyAddress)
                etCompanyPICName.setText(it?.contactName)
                etCompanyPICPhoneNumber.setText(it?.contactPhoneNumber)
                if(it?.image?.isEmpty() == true){
                    ivProfile.setImageResource(R.drawable.img_no_image)
                } else {
                    if (it != null) {
                        ivProfile.loadCircleImageFromUrl(it.image)
                    }
                }
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
                ImagePicker.with(this@EditProfileCompanyFragment)
                    .crop()
                    .compress(1024)
                    .createIntent {
                        startForImageResult.launch(it)
                    }
                ivProfileLoading.visible()
                progressBarImage.visible()
            }

            btnSaveProfile.setOnClickListener {
                val email = etEmailCompany.text.toString()
                val password = etCompanyPassword.text.toString()
                val companyName = etCompanyName.text.toString()
                val companyAddress = etCompanyAddress.text.toString()
                val contactName = etCompanyPICName.text.toString()
                val contactPhoneNumber = etCompanyPICPhoneNumber.text.toString()
                val userId = getInstance(requireContext()).getString(Constant.ID)

                progressBar.visible()
                tvProgress.visible()
                ivProfile.uploadImage(file)
                    .addOnFailureListener {
                        progressBar.gone()
                        tvProgress.gone()
                        requireContext().showToast("Upload Image Failed!")
                    }
                    .addOnSuccessListener { task ->
                        progressBar.gone()
                        tvProgress.gone()
                        requireContext().showToast("Upload Image Success!")
                        task.storage.downloadUrl.addOnSuccessListener { url ->
                            Log.d(TAG, "downloadUri: $url")
                            urlDownload = url.toString()
                            val admin = Company(
                                id = userId,
                                email = email,
                                password = password,
                                companyName = companyName,
                                companyAddress = companyAddress,
                                contactName = contactName,
                                contactPhoneNumber = contactPhoneNumber,
                                image = urlDownload.toString()
                            )
                            editProfileCompanyViewModel.updateCompanyById(admin, userId)
                                .observe(viewLifecycleOwner) { data ->
                                    if (data == true) {
                                        requireActivity().onBackPressed()
                                        requireContext().showToast("Update berhasil")
                                    } else {
                                        requireContext().showToast("Update gagal")
                                    }
                                }
                        }
                    }
                    .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                        val progress = (100.0 * bytesTransferred) / totalByteCount
                        tvProgress.text = "${progress.toInt()} %"
                    }
            }
        }
    }

    companion object {
        private const val TAG = "EditProfileCompanyFragment"
    }
}