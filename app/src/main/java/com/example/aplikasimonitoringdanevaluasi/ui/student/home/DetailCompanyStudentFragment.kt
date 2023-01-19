package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailCompanyStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.showToast


class DetailCompanyStudentFragment : Fragment() {

    private lateinit var binding: FragmentDetailCompanyStudentBinding
    private val args: DetailCompanyStudentFragmentArgs by navArgs()
    private val detailCompanyStudentViewModel: DetailCompanyStudentViewModel by viewModels()

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
            val userId = args.company.id
            val companyName = args.company.companyName
            tvCompanyName.text = args.company.companyName
            tvCompanyAddress.text = args.company.companyAddress
            tvCompanyContactName.text = args.company.contactName
            tvCompanyContactPhoneNumber.text = args.company.contactPhoneNumber
            tvCompanyContactEmail.text = args.company.contactEmail
            val admin = Student(
                id = userId,
                name = companyName
            )
            btnApplyCompany.setOnClickListener {
                detailCompanyStudentViewModel.getCompanyApproval(admin, userId).observe(viewLifecycleOwner){
                    if (it == true) {
                        requireActivity().onBackPressed()
                        requireContext().showToast("Lamaran berhasil terkirim")
                    } else {
                        requireContext().showToast("Lamaran gagal terkirim")
                    }
                }
            }
        }
    }
}