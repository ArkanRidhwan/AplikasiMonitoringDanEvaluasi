package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailCompanyStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.Company


class DetailCompanyStudentFragment : Fragment() {

    private lateinit var binding: FragmentDetailCompanyStudentBinding
    private val data = Company()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCompanyStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            Glide.with(requireActivity())
                .load(data.image)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                        .error(R.drawable.ic_image_error)
                )
                .into(ivProfilePicture)
            tvCompanyName.text = data.companyName
            tvCompanyAddress.text = data.companyAddress
            tvCompanyContactName.text = data.contactName
            tvCompanyContactPhoneNumber.text = data.contactPhoneNumber
            tvCompanyContactEmail.text = data.contactEmail
        }
    }
}