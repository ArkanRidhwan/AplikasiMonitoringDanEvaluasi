package com.example.aplikasimonitoringdanevaluasi.ui.admin.profile

import android.app.Activity
import android.content.Context
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentEditProfileAdminBinding
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import java.io.File


class EditProfileAdminFragment : Fragment() {

    var encodedImage: String? = ""
    var urlDownload: String? = ""

    private lateinit var binding: FragmentEditProfileAdminBinding
    private var file: File? = null
    private val editProfileAdminViewModel: EditProfileAdminViewModel by viewModels()

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
            val userId = getInstance(requireContext()).getString(Constant.ID)
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            editProfileAdminViewModel.getAdminById(userId).observe(viewLifecycleOwner) {
                etAdminEmail.setText(it?.email)
                etAdminName.setText(it?.name)
                etAdminPassword.setText(decrypt(it?.password.toString()))
                etAdminPhoneNumber.setText(it?.phoneNumber)
                if (it?.image?.isEmpty() == true) {
                    ivProfile.setImageResource(R.drawable.ic_image_no_image)
                } else {
                    Glide.with(requireContext())
                        .load(it?.image)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivProfile)
                }
            }


            val startForImageResult =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                    val resultCode = result.resultCode
                    val data = result.data
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            val imageUri = data?.data
                            imageUri?.toFile() as File
                            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                ImageDecoder.decodeBitmap(ImageDecoder.createSource(file!!))
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
                hideKeyboard()
                val email = etAdminEmail.text.toString()
                val password = encrypt(etAdminPassword.text.toString())
                val name = etAdminName.text.toString()
                val phoneNumber = etAdminPhoneNumber.text.toString()

                progressBar.visible()
                tvProgress.visible()
                ivProfile.uploadImage(file)
                    .addOnFailureListener {
                        progressBar.gone()
                        tvProgress.gone()
                    }
                    .addOnSuccessListener { task ->
                        progressBar.gone()
                        tvProgress.gone()
                        task.storage.downloadUrl.addOnSuccessListener { url ->
                            Log.d(TAG, "downloadUri: $url")
                            urlDownload = url.toString()
                            editProfileAdminViewModel.getAdminById(userId)
                                .observe(viewLifecycleOwner) {
                                    val admin = Admin(
                                        id = userId,
                                        email = email,
                                        password = password.toString(),
                                        name = name,
                                        phoneNumber = phoneNumber,
                                        image = urlDownload.toString()
                                    )
                                    editProfileAdminViewModel.updateAdminByEmail(admin, userId)
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
                    }
                    .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                        val progress = (100.0 * bytesTransferred) / totalByteCount
                        tvProgress.text = "${progress.toInt()} %"
                    }
            }
        }
    }

    companion object {
        private const val TAG = "EditProfileAdminFragment"
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}