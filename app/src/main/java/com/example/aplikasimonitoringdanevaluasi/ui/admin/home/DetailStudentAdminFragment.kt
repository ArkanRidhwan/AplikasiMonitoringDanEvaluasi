package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailStudentAdminBinding


class DetailStudentAdminFragment : Fragment() {

    private lateinit var binding: FragmentDetailStudentAdminBinding
    private val args: DetailStudentAdminFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailStudentAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvStudentName.text = args.student.name
            tvStudentNameEmail.text = args.student.email
            tvStudentClassName.text = args.student.className
            tvStudentCompanyName.text = args.student.companyName
            tvStudentJob.text = args.student.job
            tvStudentSchoolMajor.text = args.student.studentMajor
            tvStudentPhoneNumber.text = args.student.phoneNumber
            if (args.student.image.isEmpty()) {
                ivProfilePicture.setImageResource(R.drawable.ic_image_no_image)
            } else {
                Glide.with(requireContext())
                    .load(args.student.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                            .error(R.drawable.ic_image_error)
                    )
                    .into(ivProfilePicture)
            }

            btnLogbook.setOnClickListener {
                val action =
                    DetailStudentAdminFragmentDirections.actionDetailStudentAdminFragmentToListLogbookFragment(
                        args.student, null
                    )
                findNavController().navigate(action)
            }
        }
    }
}