package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.app.DatePickerDialog
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
import com.example.aplikasimonitoringdanevaluasi.utils.*
import java.util.*


class AddLogbookFragment : Fragment() {

    private lateinit var binding: FragmentAddLogbookBinding
    private val uploadLogbookViewModel: AddLogbookViewModel by viewModels()
    private var companyId = ""
    private var studentApprovalStatus = ""
    private var imageProfileStudentLogbook = ""
    private var dateText = ""
    private var dateTextCheck = ""

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

            uploadLogbookViewModel.getLogbookData(logbookUserId).observe(viewLifecycleOwner){
                dateTextCheck = it?.activityDate.toString()
            }

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            btnPickDate.setOnClickListener {
                DatePickerDialog(
                    requireActivity(),
                    { view, myear, mmonth, mday ->
                        tvDate.text = ": $mday/${mmonth}/$myear"
                        dateText = "$mday/${mmonth}/$myear"
                    },
                    year,
                    month,
                    day
                ).show()
                tvDate.visible()
            }

            btnSave.setOnClickListener {
                hideKeyboard()
                val content = etContentLogbook.text.toString()
                val activityDate = dateText
                val name = getInstance(requireContext()).getString(Constant.NAME)
                val logbookId = UUID.randomUUID().toString()

                if (activityDate.isEmpty()) {
                    requireActivity().showToast("Silahkan pilih tanggal terlebih dahulu")
                } else if (content.isEmpty()) {
                    etContentLogbook.error("Email tidak boleh kosong")
                    etContentLogbook.requestFocus()
                } else {
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
                    if (studentApprovalStatus != "2") {
                        requireContext().showToast("Silahkan ajukan lamaran ke perusahaan terlebih dahulu")
                    } else if (dateText == dateTextCheck) {
                        requireContext().showToast("Logbook hari ini sudah dibuat")
                    } else {
                        uploadLogbookViewModel.saveLogbook(logbook)
                            .observe(viewLifecycleOwner) { data ->
                                if (data == true) {
                                    requireActivity().onBackPressed()
                                    requireContext().showToast("Berhasil menyimpan logbook")
                                } else {
                                    requireContext().showToast("Menyimpan logbook gagal")
                                }
                            }
                    }
                }
            }
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}