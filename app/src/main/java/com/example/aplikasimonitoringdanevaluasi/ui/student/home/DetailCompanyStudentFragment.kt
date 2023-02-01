package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailCompanyStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import java.util.*


class DetailCompanyStudentFragment : Fragment() {

    private lateinit var binding: FragmentDetailCompanyStudentBinding
    private val args: DetailCompanyStudentFragmentArgs by navArgs()
    private val detailCompanyStudentViewModel: DetailCompanyStudentViewModel by viewModels()
    private var name = ""
    private var email = ""
    private var status = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCompanyStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            /*Glide.with(requireActivity())
                .load(data.image)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                        .error(R.drawable.ic_image_error)
                )
                .into(ivProfilePicture)*/
            tvCompanyName.text = args.company.companyName
            tvCompanyName.text = args.company.companyName
            tvCompanyAddress.text = args.company.companyAddress
            tvCompanyContactName.text = args.company.contactName
            tvCompanyContactPhoneNumber.text = args.company.contactPhoneNumber
            tvCompanyContactEmail.text = args.company.email
            val studentId = getInstance(requireContext()).getString(Constant.ID)

            detailCompanyStudentViewModel.getStudentById(studentId)
                .observe(viewLifecycleOwner) {
                    name = it?.name.toString()
                    email = it?.email.toString()
                }

            detailCompanyStudentViewModel.getRequestStudentById(studentId)
                .observe(viewLifecycleOwner) {
                    status = it?.status.toString()
                }

            btnApplyCompany.setOnClickListener {
                val requestStudent = RequestStudent(
                    id = UUID.randomUUID().toString(),
                    status = "2",
                    companyId = args.company.id,
                    studentId = studentId,
                    studentName = name,
                    studentEmail = email
                )

                if (status.isEmpty()) {
                    detailCompanyStudentViewModel.saveRequestStudent(requestStudent)
                        .observe(viewLifecycleOwner) {
                            if (it == true) {
                                requireActivity().showToast("Lamaran berhasil")
                                requireActivity().onBackPressed()
                            } else {
                                requireActivity().showToast("Lamaran gagal")
                            }
                        }
                } else if (status == "2") {
                    requireActivity().showToast("Sedang diproses")
                } else {
                    requireActivity().showToast("Sudah terdaftar")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }
}