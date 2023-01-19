package com.example.aplikasimonitoringdanevaluasi.ui.main.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class RegisterStudentFragment : Fragment() {

    private lateinit var binding: FragmentRegisterStudentBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            auth = FirebaseAuth.getInstance()
            btnRegister.setOnClickListener {
                val name = etStudentName.text.toString()
                val companyName = etStudentCompanyName.text.toString()
                val job = etStudentCompanyJob.text.toString()
                val className = etStudentClassName.text.toString()
                val phoneNumber = etStudentPhoneNumber.text.toString()
                val studentMajor = etStudentSchoolMajor.text.toString()
                val email = auth.currentUser?.email

                if (name.isEmpty()) {
                    etStudentName.error("Nama tidak boleh kosong")
                    etStudentName.requestFocus()
                } else if (job.isEmpty()) {
                    etStudentCompanyName.error("Nama perusahaan tidak boleh kosong")
                    etStudentCompanyName.requestFocus()
                } else if (companyName.isEmpty()) {
                    etStudentCompanyJob.error("Nama perusahaan tidak boleh kosong")
                    etStudentCompanyJob.requestFocus()
                } else if (className.isEmpty()) {
                    etStudentClassName.error("Nama kelas tidak boleh kosong")
                    etStudentClassName.requestFocus()
                } else if (phoneNumber.isEmpty()) {
                    etStudentPhoneNumber.error("Nomor telepon tidak boleh kosong")
                    etStudentPhoneNumber.requestFocus()
                } else if (studentMajor.isEmpty()) {
                    etStudentSchoolMajor.error("Jurusan sekolah tidak boleh kosong")
                    etStudentSchoolMajor.requestFocus()
                } else {
                    btnRegister.gone()
                    progressBarStudentRegister.visible()
                    progressBarStudentRegister.playAnimation()
                    val student = Student(
                        id = UUID.randomUUID().toString(),
                        email = email.toString(),
                        name = name,
                        companyName = companyName,
                        job = job,
                        className = className,
                        phoneNumber = phoneNumber,
                        studentMajor = studentMajor
                    )

                    registerViewModel.saveStudent(student).observe(viewLifecycleOwner) { it ->
                        if (it == true) {
                            registerViewModel.getStudent(phoneNumber).observe(viewLifecycleOwner) {
                                getInstance(requireContext()).putString(Constant.ID, it?.id)
                                getInstance(requireContext()).putString(Constant.NAME, it?.name)
                                getInstance(requireContext()).putString(
                                    Constant.ROLE,
                                    getString(R.string.student)
                                )
                                findNavController().navigate(
                                    RegisterStudentFragmentDirections.actionRegisterStudentFragmentToHomeStudentFragment(
                                        getString(R.string.student)
                                    )
                                )
                            }
                        } else {
                            btnRegister.visible()
                            progressBarStudentRegister.gone()
                            requireContext().showToast("Regitrasi gagal")
                        }
                    }
                }
            }
        }
    }
}