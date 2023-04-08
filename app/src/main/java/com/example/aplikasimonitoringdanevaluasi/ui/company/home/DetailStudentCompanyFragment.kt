package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailStudentCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.utils.showToast


class DetailStudentCompanyFragment : Fragment() {

    private lateinit var binding: FragmentDetailStudentCompanyBinding
    private val args: DetailStudentCompanyFragmentArgs by navArgs()
    private val detailStudentCompanyViewModel: DetailStudentCompanyViewModel by viewModels()
    private var studentId = ""
    private var tittle = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailStudentCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            studentId = args.requestStudent.studentId
            detailStudentCompanyViewModel.getStudentById(studentId).observe(viewLifecycleOwner) { it ->
                tvStudentName.text = it?.name.toString()
                tvStudentEmail.text = it?.email.toString()
                tvStudentPhoneNumber.text = it?.phoneNumber.toString()
                tvStudentJob.text = it?.job.toString()
                tvStudentClassName.text = it?.className.toString()
                tvStudentMajorName.text = it?.studentMajor.toString()
                if (it?.image?.isEmpty() == true) {
                    ivProfilePicture.setImageResource(R.drawable.ic_image_no_image)
                } else {
                    Glide.with(requireContext())
                        .load(it?.image)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivProfilePicture)
                }

                detailStudentCompanyViewModel.getReportById(args.requestStudent.studentId)
                    .observe(viewLifecycleOwner) { i ->
                        tittle = i?.reportStatus.toString()
                    }

                btnAddReport.setOnClickListener {
                    if (tittle == "Laporan 4") {
                        requireContext().showToast("Semua laporan sudah terisi")
                    } else {
                        val action =
                            DetailStudentCompanyFragmentDirections.actionDetailStudentCompanyFragmentToAddReportFragment(
                                args.requestStudent
                            )
                        findNavController().navigate(action)
                    }

                }

                btnStudentLogbook.setOnClickListener {
                    val action =
                        DetailStudentCompanyFragmentDirections.actionDetailStudentCompanyFragmentToListLogbookFragment(
                            studentId
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }
}