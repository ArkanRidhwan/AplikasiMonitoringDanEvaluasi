package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddReportBinding
import com.example.aplikasimonitoringdanevaluasi.model.Report
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.*
import java.util.*


class AddReportFragment : Fragment() {

    private lateinit var binding: FragmentAddReportBinding
    private val addReportFragmentViewModel: AddReportViewModel by viewModels()
    private val args: AddReportFragmentArgs by navArgs()
    private var tittle = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddReportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addReportFragmentViewModel.getReportById(args.studentId).observe(viewLifecycleOwner) {
                tittle = it?.reportStatus.toString()
                if (tittle.isEmpty()){
                    tvTittleReport.text = getString(R.string.Report1)
                } else if (tittle == "Laporan1"){
                    tvTittleReport.text = getString(R.string.Report2)
                } else if (tittle == "Laporan2"){
                    tvTittleReport.text = getString(R.string.Report3)
                } else {
                    tvTittleReport.text = getString(R.string.Report4)
                }
            }

            btnSave.setOnClickListener {
                val reportUserId = getInstance(requireContext()).getString(Constant.ID)
                val answer1 = etReportAnswer1.text.toString()
                val answer2 = etReportAnswer2.text.toString()
                val answer3 = etReportAnswer3.text.toString()
                val answer4 = etReportAnswer4.text.toString()
                val answer5 = etReportAnswer5.text.toString()

                val answer6: String = if (rb11.isChecked) {
                    "Tidak baik"
                } else if (rb12.isChecked) {
                    "Kurang baik"
                } else if (rb13.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer7: String = if (rb21.isChecked) {
                    "Tidak baik"
                } else if (rb22.isChecked) {
                    "Kurang baik"
                } else if (rb23.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer8 = if (rb31.isChecked) {
                    "Tidak baik"
                } else if (rb32.isChecked) {
                    "Kurang baik"
                } else if (rb33.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                tittle = if (tittle.isEmpty()) {
                    "Laporan1"
                } else if (tittle == "Laporan1") {
                    "Laporan2"
                } else if (tittle == "Laporan2") {
                    "Laporan3"
                } else {
                    "Laporan4"
                }


                if (answer1.isEmpty()) {
                    etReportAnswer1.error("Jawaban tidak boleh kosong")
                    etReportAnswer1.requestFocus()
                } else if (answer2.isEmpty()) {
                    etReportAnswer2.error("Jawaban tidak boleh kosong")
                    etReportAnswer2.requestFocus()
                } else if (answer3.isEmpty()) {
                    etReportAnswer3.error("Jawaban tidak boleh kosong")
                    etReportAnswer3.requestFocus()
                } else if (answer4.isEmpty()) {
                    etReportAnswer4.error("Jawaban tidak boleh kosong")
                    etReportAnswer4.requestFocus()
                } else if (answer5.isEmpty()) {
                    etReportAnswer5.error("Jawaban tidak boleh kosong")
                    etReportAnswer5.requestFocus()
                } else {
                    val report = Report(
                        id = UUID.randomUUID().toString(),
                        companyId = reportUserId,
                        studentId = args.studentId,
                        date = getDateNow(),
                        tittle = tittle,
                        answer1 = answer1,
                        answer2 = answer2,
                        answer3 = answer3,
                        answer4 = answer4,
                        answer5 = answer5,
                        answer6 = answer6,
                        answer7 = answer7,
                        answer8 = answer8
                    )

                    addReportFragmentViewModel.saveReport(report).observe(viewLifecycleOwner) {
                        if (it == true) {
                            requireActivity().showToast("Berhasil disimpan")
                            val action =
                                AddReportFragmentDirections.actionAddReportFragmentToHomeCompanyFragment(
                                    getInstance(requireContext()).getString(Constant.ROLE)
                                )
                            findNavController().navigate(action)
                        } else {
                            requireActivity().showToast("Gagal disimpan")
                        }
                    }

                    val requestStudent = RequestStudent(
                        id = args.requestStudent.id,
                        status = args.requestStudent.status,
                        companyId = args.requestStudent.companyId,
                        studentId = args.requestStudent.studentId,
                        studentName = args.requestStudent.studentName,
                        studentEmail = args.requestStudent.studentEmail,
                        image = args.requestStudent.image,
                        reportStatus = tittle
                    )
                    addReportFragmentViewModel.updateRequestStudent(
                        requestStudent,
                        args.requestStudent.id
                    ).observe(viewLifecycleOwner) {
                        if (it == true) {
                            requireActivity().showToast("Berhasil diupdate")
                            requireActivity().onBackPressed()
                        } else {
                            requireActivity().showToast("Gagal disimpan")
                        }
                    }
                }
            }
        }
    }
}

