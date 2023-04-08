package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailCompanyStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import java.util.*


class DetailCompanyStudentFragment : Fragment() {

    private lateinit var binding: FragmentDetailCompanyStudentBinding
    private val detailCompanyStudentViewModel: DetailCompanyStudentViewModel by viewModels()
    private val args: DetailCompanyStudentFragmentArgs by navArgs()
    private var companyImage = ""
    private var studentImage = ""
    private var name = ""
    private var email = ""
    private var status = ""
    private var getRequestStudentId = ""
    private var studentCompanyName = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCompanyStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            tvCompanyName.text = args.company.companyName
            tvCompanyName.text = args.company.companyName
            tvCompanyAddress.text = args.company.companyAddress
            tvCompanyContactName.text = args.company.contactName
            tvCompanyContactPhoneNumber.text = args.company.contactPhoneNumber
            tvCompanyContactEmail.text = args.company.email
            companyImage = args.company.image
            if (companyImage.isEmpty()) {
                ivProfilePicture.setImageResource(R.drawable.ic_image_no_image)
            } else {
                Glide.with(requireContext())
                    .load(companyImage)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                            .error(R.drawable.ic_image_error)
                    )
                    .into(ivProfilePicture)
            }
            val studentId = getInstance(requireContext()).getString(Constant.ID)

            detailCompanyStudentViewModel.getStudentById(studentId)
                .observe(viewLifecycleOwner) {
                    name = it?.name.toString()
                    email = it?.email.toString()
                    studentImage = it?.image.toString()
                    studentCompanyName = it?.companyName.toString()
                }

            detailCompanyStudentViewModel.getRequestStudentById(studentId)
                .observe(viewLifecycleOwner) {
                    status = it?.status.toString()
                    getRequestStudentId = it?.studentId.toString()
                }

            btnApplyCompany.setOnClickListener {
                val requestStudent = RequestStudent(
                    id = UUID.randomUUID().toString(),
                    status = "1",
                    companyId = args.company.id,
                    studentId = studentId,
                    studentName = name,
                    studentEmail = email,
                    image = studentImage
                )

                if (getRequestStudentId == studentId) {
                    when (status) {
                        "1" -> {
                            requireActivity().showToast("Sedang diproses")
                        }
                        "2" -> {
                            requireActivity().showToast("Sudah terdaftar")
                        }
                    }
                } else {
                    detailCompanyStudentViewModel.saveRequestStudent(requestStudent)
                        .observe(viewLifecycleOwner) {
                            if (it == true) {
                                requireActivity().showToast("Lamaran berhasil dikirim")
                                requireActivity().onBackPressed()
                            } else {
                                requireActivity().showToast("Lamaran gagal diproses")
                            }
                        }
                }
            }
            
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}