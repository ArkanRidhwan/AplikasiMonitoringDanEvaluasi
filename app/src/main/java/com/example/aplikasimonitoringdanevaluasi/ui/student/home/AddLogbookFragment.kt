package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddLogbookBinding
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getDateNow
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import java.util.*


class AddLogbookFragment : Fragment() {

    private lateinit var binding: FragmentAddLogbookBinding
    private val uploadLogbookViewModel: AddLogbookViewModel by viewModels()
    private var companyId = ""
    private var studentApprovalStatus = ""
    private var imageProfileStudentLogbook = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLogbookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val logbookUserId = getInstance(requireContext()).getString(Constant.ID)
            uploadLogbookViewModel.getRequestStudentById(logbookUserId)
                .observe(viewLifecycleOwner) {
                    companyId = it?.companyId.toString()
                    studentApprovalStatus = it?.status.toString()
                    imageProfileStudentLogbook = it?.image.toString()
                }

            btnSave.setOnClickListener {
                hideKeyboard()
                val content = etContentLogbook.text.toString()
                val activityDate = etActivityDateLogbook.text.toString()
                val name = getInstance(requireContext()).getString(Constant.NAME)
                val logbookId = UUID.randomUUID().toString()
                val logbook = Logbook(
                    id = logbookId,
                    logbookUserId = logbookUserId,
                    companyId = companyId,
                    name = name,
                    activityDate = activityDate,
                    content = content,
                    date = getDateNow(),
                    timestamp = System.currentTimeMillis().toString(),
                    status = "1",
                    image = imageProfileStudentLogbook
                )
                if (studentApprovalStatus == "2") {
                    uploadLogbookViewModel.saveLogbook(logbook)
                        .observe(viewLifecycleOwner) { data ->
                            if (data == true) {
                                requireActivity().onBackPressed()
                                requireContext().showToast("Berhasil menyimpan logbook")
                            } else {
                                requireContext().showToast("Menyimpan logbook gagal")
                            }
                        }
                } else {
                    requireContext().showToast("Silahkan ajukan lamaran ke perusahaan terlebih dahulu")
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}