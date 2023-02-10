package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailStudentCompanyBinding


class DetailStudentCompanyFragment : Fragment() {

    private lateinit var binding: FragmentDetailStudentCompanyBinding
    private val args: DetailStudentCompanyFragmentArgs by navArgs()
    private val homeCompanyViewModel: DetailStudentCompanyViewModel by viewModels()
    private var studentId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailStudentCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            studentId = args.requestStudent.studentId
            homeCompanyViewModel.getStudentById(studentId).observe(viewLifecycleOwner) {
                tvStudentName.text = it?.name.toString()
                tvStudentEmail.text = it?.email.toString()
                tvStudentPhoneNumber.text = it?.phoneNumber.toString()
                tvStudentJob.text = it?.job.toString()
                tvStudentClassName.text = it?.className.toString()
                tvStudentMajorName.text = it?.studentMajor.toString()
            }
            btnAddReport.setOnClickListener {
                val action =
                    DetailStudentCompanyFragmentDirections.actionDetailStudentCompanyFragmentToAddReportFragment(
                        studentId,
                        args.requestStudent
                    )
                findNavController().navigate(action)
            }
        }
    }
}