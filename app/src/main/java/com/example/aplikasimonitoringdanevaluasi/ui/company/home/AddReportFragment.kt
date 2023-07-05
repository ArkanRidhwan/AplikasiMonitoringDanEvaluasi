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
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            addReportFragmentViewModel.getRequestStudentById(args.requestStudent.studentId)
                .observe(viewLifecycleOwner) {
                    tittle = it?.reportStatus.toString()
                    if (tittle.isEmpty()) {
                        tvTittleReport.text = getString(R.string.Report1)
                    } else if (tittle == "Laporan 1") {
                        tvTittleReport.text = getString(R.string.Report2)
                    } else if (tittle == "Laporan 2") {
                        tvTittleReport.text = getString(R.string.Report3)
                    } else {
                        tvTittleReport.text = getString(R.string.Report4)
                    }
                }

            btnSave.setOnClickListener {
                val reportUserId = getInstance(requireContext()).getString(Constant.ID)
                val answer18 = etAnswerReportQuestionSummary.text.toString()

                val answer1: String = if (rb11.isChecked) {
                    "Tidak baik"
                } else if (rb12.isChecked) {
                    "Kurang baik"
                } else if (rb13.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer2: String = if (rb21.isChecked) {
                    "Tidak baik"
                } else if (rb22.isChecked) {
                    "Kurang baik"
                } else if (rb23.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer3 = if (rb31.isChecked) {
                    "Tidak baik"
                } else if (rb32.isChecked) {
                    "Kurang baik"
                } else if (rb33.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer4 = if (rb41.isChecked) {
                    "Tidak baik"
                } else if (rb42.isChecked) {
                    "Kurang baik"
                } else if (rb44.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer5 = if (rb51.isChecked) {
                    "Tidak baik"
                } else if (rb52.isChecked) {
                    "Kurang baik"
                } else if (rb53.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer6 = if (rb61.isChecked) {
                    "Tidak baik"
                } else if (rb62.isChecked) {
                    "Kurang baik"
                } else if (rb63.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer7 = if (rb71.isChecked) {
                    "Tidak baik"
                } else if (rb72.isChecked) {
                    "Kurang baik"
                } else if (rb73.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer8 = if (rb81.isChecked) {
                    "Tidak baik"
                } else if (rb82.isChecked) {
                    "Kurang baik"
                } else if (rb83.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer9 = if (rb91.isChecked) {
                    "Tidak baik"
                } else if (rb92.isChecked) {
                    "Kurang baik"
                } else if (rb93.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer10 = if (rb101.isChecked) {
                    "Tidak baik"
                } else if (rb102.isChecked) {
                    "Kurang baik"
                } else if (rb103.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer11 = if (rb111.isChecked) {
                    "Tidak baik"
                } else if (rb112.isChecked) {
                    "Kurang baik"
                } else if (rb113.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer12 = if (rb121.isChecked) {
                    "Tidak baik"
                } else if (rb122.isChecked) {
                    "Kurang baik"
                } else if (rb123.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer13 = if (rb131.isChecked) {
                    "Tidak baik"
                } else if (rb132.isChecked) {
                    "Kurang baik"
                } else if (rb133.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer14 = if (rb141.isChecked) {
                    "Tidak baik"
                } else if (rb142.isChecked) {
                    "Kurang baik"
                } else if (rb143.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer15 = if (rb151.isChecked) {
                    "Tidak baik"
                } else if (rb152.isChecked) {
                    "Kurang baik"
                } else if (rb153.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer16 = if (rb161.isChecked) {
                    "Tidak baik"
                } else if (rb162.isChecked) {
                    "Kurang baik"
                } else if (rb163.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                val answer17 = if (rb171.isChecked) {
                    "Tidak baik"
                } else if (rb172.isChecked) {
                    "Kurang baik"
                } else if (rb173.isChecked) {
                    "Baik"
                } else {
                    "Sangat baik"
                }

                tittle = if (tittle.isEmpty()) {
                    "Laporan 1"
                } else if (tittle == "Laporan 1") {
                    "Laporan 2"
                } else if (tittle == "Laporan 2") {
                    "Laporan 3"
                } else {
                    "Laporan 4"
                }


                if (answer18.isEmpty()) {
                    etAnswerReportQuestionSummary.error("Jawaban tidak boleh kosong")
                    etAnswerReportQuestionSummary.requestFocus()
                } else {
                    val report = Report(
                        id = UUID.randomUUID().toString(),
                        companyId = reportUserId,
                        studentId = args.requestStudent.studentId,
                        studentName = args.requestStudent.studentName,
                        tittle = tittle,
                        date = getDateNow(),
                        timestamp = System.currentTimeMillis().toString(),
                        answer1 = answer1,
                        answer2 = answer2,
                        answer3 = answer3,
                        answer4 = answer4,
                        answer5 = answer5,
                        answer6 = answer6,
                        answer7 = answer7,
                        answer8 = answer8,
                        answer9 = answer9,
                        answer10 = answer10,
                        answer11 = answer11,
                        answer12 = answer12,
                        answer13 = answer13,
                        answer14 = answer14,
                        answer15 = answer15,
                        answer16 = answer16,
                        answer17 = answer17,
                        answer18 = answer18
                    )

                    addReportFragmentViewModel.saveReport(report).observe(viewLifecycleOwner) {
                        if (it == true) {
                            requireActivity().showToast("Berhasil disimpan")
                            requireActivity().onBackPressed()
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
                    addReportFragmentViewModel.updateRequestStudentById(
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

