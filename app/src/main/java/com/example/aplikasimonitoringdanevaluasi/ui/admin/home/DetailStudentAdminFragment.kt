package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailStudentAdminBinding
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.ui.main.chat.ListContactChatFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.loadCircleImageFromUrl


class DetailStudentAdminFragment : Fragment() {

    private lateinit var binding: FragmentDetailStudentAdminBinding
    private val args : DetailStudentAdminFragmentArgs by navArgs()

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
                ivProfilePicture.setImageResource(R.drawable.img_no_image)
            } else {
                ivProfilePicture.loadCircleImageFromUrl(args.student.image)
            }

            btnLogbook.setOnClickListener {
                val action =
                    DetailStudentAdminFragmentDirections.actionDetailStudentAdminFragmentToListLogbookFragment(args.student)
                findNavController().navigate(action)
            }
        }
    }
}