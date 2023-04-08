package com.example.aplikasimonitoringdanevaluasi.ui.company.request

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
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailRequestStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.loadCircleImageFromUrl
import com.example.aplikasimonitoringdanevaluasi.utils.showToast

class DetailRequestStudentFragment : Fragment() {

    private lateinit var binding: FragmentDetailRequestStudentBinding
    private val args: DetailRequestStudentFragmentArgs by navArgs()
    private val detailRequestStudentViewModel: DetailRequestStudentViewModel by viewModels()
    private var oldCompanyName = ""
    private var newCompanyName = ""
    private var studentName = ""
    private var email = ""
    private var password = ""
    private var job = ""
    private var className = ""
    private var phoneNumber = ""
    private var studentMajor = ""
    private var image = ""
    private var timestamp = ""

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
            detailRequestStudentViewModel.getCompanyById(args.requestStudent.companyId)
                .observe(viewLifecycleOwner) {
                    newCompanyName = it?.companyName.toString()
                }

            val id = args.requestStudent.id
            val studentId = args.requestStudent.studentId
            val companyId = args.requestStudent.companyId
            val studentEmail = args.requestStudent.studentEmail
            val studentImage = args.requestStudent.image

            detailRequestStudentViewModel.getStudentById(studentId).observe(viewLifecycleOwner) {
                email = it?.email.toString()
                password = it?.password.toString()
                job = it?.job.toString()
                className = it?.className.toString()
                phoneNumber = it?.phoneNumber.toString()
                studentMajor = it?.studentMajor.toString()
                image = it?.image.toString()
                timestamp = it?.timestamp.toString()
                studentName = it?.name.toString()
                oldCompanyName = it?.companyName.toString()

                tvStudentNameEmail.text = email
                tvStudentName.text = studentName
                tvStudentCompanyName.text = oldCompanyName
                tvStudentJob.text = job
                tvStudentClassName.text = className
                tvStudentPhoneNumber.text = phoneNumber
                tvStudentSchoolMajor.text = studentMajor
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
            }

            btnAccept.setOnClickListener {
                val requestStudent = RequestStudent(
                    id = id,
                    status = "2",
                    companyId = companyId,
                    companyName = newCompanyName,
                    studentId = studentId,
                    studentName = studentName,
                    studentEmail = studentEmail,
                    image = studentImage,
                    timestamp = System.currentTimeMillis().toString()
                )
                detailRequestStudentViewModel.updateRequestStatusAccepted(requestStudent, id)
                    .observe(viewLifecycleOwner) {
                        if (it == true) {
                            val action =
                                DetailRequestStudentFragmentDirections.actionDetailRequestStudentFragmentToRequestFragment(
                                )
                            findNavController().navigate(action)
                            requireContext().showToast("Lamaran berhasil diproses")
                        } else {
                            requireContext().showToast("Lamaran gagal diproses")
                        }
                    }

                val student = Student(
                    id = studentId,
                    email = studentEmail,
                    password = password,
                    name = studentName,
                    companyName = newCompanyName,
                    job = job,
                    className = className,
                    phoneNumber = phoneNumber,
                    studentMajor = studentMajor,
                    image = image,
                    timestamp = timestamp
                )
                detailRequestStudentViewModel.updateStudentById(student, studentId)
                    .observe(viewLifecycleOwner) {

                    }
            }

            btnReject.setOnClickListener {
                detailRequestStudentViewModel.updateRequestStatusRejected(id)
                    .observe(viewLifecycleOwner) {
                        if (it == true) {
                            val action =
                                DetailRequestStudentFragmentDirections.actionDetailRequestStudentFragmentToRequestFragment(
                                )
                            findNavController().navigate(action)
                            requireContext().showToast("Lamaran berhasil diproses")
                        } else {
                            requireContext().showToast("Lamaran gagal diproses")
                        }
                    }
            }

            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}