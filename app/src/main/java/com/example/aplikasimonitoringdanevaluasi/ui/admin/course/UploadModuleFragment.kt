package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentUploadModuleBinding
import com.example.aplikasimonitoringdanevaluasi.model.Module
import com.example.aplikasimonitoringdanevaluasi.utils.getDateNow
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import com.example.aplikasimonitoringdanevaluasi.utils.visible
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*


class UploadModuleFragment : Fragment() {

    private lateinit var binding: FragmentUploadModuleBinding
    private val uploadModuleViewModel: UploadModuleViewModel by viewModels()
    private lateinit var file: File
    private val args: UploadModuleFragmentArgs by navArgs()

    var documentUri: Uri? = null
    var urlDownload: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadModuleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }


            val startForDocumentResult =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                    val resultCode = result.resultCode
                    val data = result.data
                    if (resultCode == AppCompatActivity.RESULT_OK && data?.data != null) {
                        documentUri = data.data
                        file = File(documentUri.toString())
                        tvModuleName.apply {
                            visible()
                            text = file.name
                        }
                        tvChooseModule.gone()
                        btnUploadModule.visible()
                    }
                }

            ivChooseModule.setOnClickListener {
                val intent = Intent().apply {
                    type = "application/pdf"
                    action = Intent.ACTION_GET_CONTENT
                }
                startForDocumentResult.launch(intent)

                Handler().postDelayed({
                    ivChooseModule.gone()
                    ivChangeModule.visible()
                    tvChangeModule.visible()
                }, 1000)
            }

            ivChangeModule.setOnClickListener {
                val intent = Intent().apply {
                    type = "application/pdf"
                    action = Intent.ACTION_GET_CONTENT
                }
                startForDocumentResult.launch(intent)
            }

            btnUploadModule.setOnClickListener {
                val tittle = etUploadModuleTittle.text.toString()
                val description = etUploadModuleContent.text.toString()

                tvChangeModule.gone()
                ivChangeModule.gone()
                tvModuleName.gone()
                progressBar.visible()
                tvProgress.visible()
                val storageRef = Firebase.storage.reference
                val documentsRef = storageRef.child("documents/${file.name}")
                documentUri?.let { uri ->
                    documentsRef.putFile(uri)
                        .addOnFailureListener {
                            progressBar.gone()
                            tvProgress.gone()
                            requireContext().showToast("Upload Document Failed!")
                        }
                        .addOnSuccessListener { task ->
                            progressBar.gone()
                            tvProgress.gone()
                            requireContext().showToast("Upload Document Success!")

                            task.storage.downloadUrl.addOnSuccessListener { url ->
                                Log.d(TAG, "Document: $url")
                                urlDownload = url.toString()
                                val id = UUID.randomUUID().toString()
                                val module = Module(
                                    id = id,
                                    tittle = tittle,
                                    description = description,
                                    date = getDateNow(),
                                    timestamp = System.currentTimeMillis().toString(),
                                    courseId = args.tittleCourseId.toString(),
                                    link = urlDownload.toString(),
                                )
                                uploadModuleViewModel.saveModule(module)
                                    .observe(viewLifecycleOwner) { data ->
                                        if (data == true) {
                                            requireActivity().onBackPressed()
                                            requireContext().showToast("Menyimpan modul berhasil")
                                        } else {
                                            requireContext().showToast("Menyimpan modul gagal")
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
    }

    companion object {
        private const val TAG = "StorageActivity"
    }
}