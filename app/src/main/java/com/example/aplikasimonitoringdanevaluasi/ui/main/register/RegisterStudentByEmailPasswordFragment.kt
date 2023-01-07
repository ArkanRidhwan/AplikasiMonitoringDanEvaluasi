package com.example.aplikasimonitoringdanevaluasi.ui.main.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterStudentByEmailPasswordBinding
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.*

class RegisterStudentByEmailPasswordFragment : Fragment() {

    private lateinit var binding: FragmentRegisterStudentByEmailPasswordBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentRegisterStudentByEmailPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnRegister.setOnClickListener {
                val email = etStudentEmail.text.toString()
                val password = etStudentPassword.text.toString()
                val name = etStudentName.text.toString()
                val job = etStudentJob.text.toString()
                val companyName = etStudentCompanyName.text.toString()
                val className = etStudentClassName.text.toString()
                val phoneNumber = etStudentPhoneNumber.text.toString()
                val major = etStudentSchoolMajor.text.toString()

                if (email.isEmpty()) {
                    etStudentEmail.error("Email tidak boleh kosong")
                    etStudentEmail.requestFocus()
                } else if (password.isEmpty()) {
                    etStudentPassword.error("Password tidak boleh kosong")
                    etStudentPassword.requestFocus()
                } else if (name.isEmpty()) {
                    etStudentName.error("Nama tidak boleh kosong")
                    etStudentName.requestFocus()
                } else if (job.isEmpty()) {
                    etStudentJob.error("Pekerjaan tidak boleh kosong")
                    etStudentJob.requestFocus()
                } else if (companyName.isEmpty()) {
                    etStudentCompanyName.error("Nama perusahaan tidak boleh kosong")
                    etStudentCompanyName.requestFocus()
                } else if (className.isEmpty()) {
                    etStudentClassName.error("Nama kelas tidak boleh kosong")
                    etStudentClassName.requestFocus()
                } else if (phoneNumber.isEmpty()) {
                    etStudentPhoneNumber.error("Nomor telepon tidak boleh kosong")
                    etStudentPhoneNumber.requestFocus()
                } else if (major.isEmpty()) {
                    etStudentSchoolMajor.error("Jurusan tidak boleh kosong")
                    etStudentSchoolMajor.requestFocus()
                } else {
                    btnRegister.gone()
                    progressBarStudentRegister.visible()
                    progressBarStudentRegister.playAnimation()
                    val student = Student(
                        email = email,
                        password = password,
                        name = name,
                        job = job,
                        companyName = companyName,
                        className = className,
                        phoneNumber = phoneNumber,
                        studentMajor = major
                    )

                    registerViewModel.getStudentByEmail(email)
                        .observe(viewLifecycleOwner) { dataStudent ->
                            if (dataStudent != null) {
                                requireContext().showToast("Email yang anda masukkan sudah terdaftar")
                                progressBarStudentRegister.gone()
                                btnRegister.visible()
                            } else {
                                registerViewModel.saveStudent(student)
                                    .observe(viewLifecycleOwner) { data ->
                                        if (data == true) {
                                            getInstance(requireContext()).apply {
                                                putString(Constant.ID, student.id)
                                                putString(Constant.NAME, student.name)
                                                putString(
                                                    Constant.ROLE,
                                                    getString(R.string.student)
                                                )
                                            }
                                            findNavController().navigate(
                                                RegisterStudentByEmailPasswordFragmentDirections.actionRegisterStudentByEmailPasswordToHomeStudentFragment(
                                                    getString(R.string.student)
                                                )
                                            )
                                            requireContext().showToast("Regitrasi berhasil")
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
    }
}
