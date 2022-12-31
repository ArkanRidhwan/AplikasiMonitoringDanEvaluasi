package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailCompanyStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.ui.main.chat.ChatFragmentArgs


class DetailCompanyStudentFragment : Fragment() {

    private lateinit var binding: FragmentDetailCompanyStudentBinding
    private val args: DetailCompanyStudentFragmentArgs by navArgs()

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
            tvCompanyAddress.text = args.company.companyAddress
            tvCompanyContactName.text = args.company.contactName
            tvCompanyContactPhoneNumber.text = args.company.contactPhoneNumber
            tvCompanyContactEmail.text = args.company.contactEmail
            btnLogin.setOnClickListener {
                Toast.makeText(requireActivity(), "Mantap", Toast.LENGTH_SHORT).show()
            }
        }
    }
}