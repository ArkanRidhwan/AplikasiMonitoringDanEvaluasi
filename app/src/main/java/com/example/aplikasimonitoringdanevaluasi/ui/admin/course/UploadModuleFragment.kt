package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentUploadModuleBinding
import com.example.aplikasimonitoringdanevaluasi.ui.storage.StorageActivity
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import com.example.aplikasimonitoringdanevaluasi.utils.visible
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import org.koin.core.component.getScopeName
import java.io.File


class UploadModuleFragment : Fragment() {

    var documentUri: Uri? = null

    private lateinit var file: File
    private lateinit var binding: FragmentUploadModuleBinding

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
            }

            btnUploadModule.setOnClickListener {
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