package com.example.aplikasimonitoringdanevaluasi.ui.student.profile

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
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentEditProfileStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import java.io.File

class EditProfileStudentFragment : Fragment() {

    var encodedImage: String? = ""
    var urlDownload: String? = ""

    private lateinit var binding: FragmentEditProfileStudentBinding
    private lateinit var file: File
    private val editProfileStudentViewModel: EditProfileStudentViewModel by viewModels()
    private var timestamp = ""
    private var companyName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            val userId = getInstance(requireContext()).getString(Constant.ID)
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            editProfileStudentViewModel.getStudentById(userId).observe(viewLifecycleOwner) {
                companyName = it?.companyName.toString()
                timestamp = it?.timestamp.toString()
                etStudentName.setText(it?.name)
                etEmailStudent.setText(it?.email)
                etStudentPassword.setText(decrypt(it?.password.toString()))
                etStudentJob.setText(it?.job)
                etStudentPhoneNumber.setText(it?.phoneNumber)
                etStudentClassName.setText(it?.className)
                etStudentMajor.setText(it?.studentMajor)
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
                ImagePicker.with(this@EditProfileStudentFragment)
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

                val email = etEmailStudent.text.toString()
                val password = encrypt(etStudentPassword.text.toString())
                val name = etStudentName.text.toString()
                val job = etStudentJob.text.toString()
                val className = etStudentClassName.text.toString()
                val phoneNumber = etStudentPhoneNumber.text.toString()
                val studentMajor = etStudentMajor.text.toString()
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
                            val student = Student(
                                id = userId,
                                email = email,
                                password = password.toString(),
                                name = name,
                                companyName = companyName,
                                job = job,
                                className = className,
                                phoneNumber = phoneNumber,
                                studentMajor = studentMajor,
                                image = urlDownload.toString(),
                                timestamp = timestamp
                            )
                            editProfileStudentViewModel.updateStudentById(student, userId)
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
        private const val TAG = "EditProfileStudentFragment"
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}