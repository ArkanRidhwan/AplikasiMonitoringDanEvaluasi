package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailRequestStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.loadCircleImageFromUrl
import com.example.aplikasimonitoringdanevaluasi.utils.showToast

class DetailRequestStudentFragment : Fragment() {

    private lateinit var binding: FragmentDetailRequestStudentBinding
    private val args: DetailRequestStudentFragmentArgs by navArgs()
    private val detailRequestStudentViewModel: DetailRequestStudentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailRequestStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val id = args.requestStudent.id
            val studentId = args.requestStudent.studentId
            val companyId = args.requestStudent.companyId
            val studentName = args.requestStudent.studentName
            val studentEmail = args.requestStudent.studentEmail
            val studentImage = args.requestStudent.image
            detailRequestStudentViewModel.getStudentById(studentId).observe(viewLifecycleOwner) {
                tvStudentNameEmail.text = it?.email.toString()
                tvStudentName.text = it?.name.toString()
                tvStudentCompanyName.text = it?.job.toString()
                tvStudentJob.text = it?.job.toString()
                tvStudentClassName.text = it?.className.toString()
                tvStudentPhoneNumber.text = it?.phoneNumber.toString()
                tvStudentSchoolMajor.text = it?.studentMajor.toString()
                if (it?.image?.isEmpty() == true) {
                    ivProfilePicture.setImageResource(R.drawable.img_no_image)
                } else {
                    if (it != null) {
                        ivProfilePicture.loadCircleImageFromUrl(it.image)
                    }
                }
            }
            btnAccept.setOnClickListener {
                val student = RequestStudent(
                    id = id,
                    status = "2",
                    companyId = companyId,
                    studentId = studentId,
                    studentName = studentName,
                    studentEmail = studentEmail,
                    image = studentImage
                )
                detailRequestStudentViewModel.updateRequestStatusAccepted(student, id)
                    .observe(viewLifecycleOwner) {
                        if (it == true) {
                            requireActivity().onBackPressed()
                            requireContext().showToast("Update berhasil")
                        } else {
                            requireContext().showToast("Update gagal")
                        }
                    }
            }
            btnReject.setOnClickListener {
                detailRequestStudentViewModel.updateRequestStatusRejected(id).observe(viewLifecycleOwner){
                    if (it == true) {
                        requireActivity().onBackPressed()
                        requireContext().showToast("Update berhasil")
                    } else {
                        requireContext().showToast("Update gagal")
                    }
                }
            }
        }
    }
}