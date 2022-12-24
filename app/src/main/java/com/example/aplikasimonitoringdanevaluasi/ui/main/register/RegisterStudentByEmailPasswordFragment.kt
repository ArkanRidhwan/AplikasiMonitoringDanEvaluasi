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
                val email = etStudentEmail.toString()
                val password = etStudentPassword.toString()
                val name = etStudentName.text.toString()
                val job = etStudentJob.text.toString()
                val companyName = etStudentCompanyName.toString()
                val className = etStudentClassName.toString()
                val phoneNumber = etStudentPhoneNumber.toString()

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
                        phoneNumber = phoneNumber
                    )

                    registerViewModel.studentEmailRegistrationValidation(email)
                        .observe(viewLifecycleOwner) {
                            if (it == true) {
                                registerViewModel.saveStudent(student)
                                    .observe(viewLifecycleOwner) { it ->
                                        if (it == true) {
                                            registerViewModel.getStudent(phoneNumber)
                                                .observe(viewLifecycleOwner) {
                                                    getInstance(requireContext()).putString(
                                                        Constant.ID,
                                                        it?.id
                                                    )
                                                    getInstance(requireContext()).putString(
                                                        Constant.NAME,
                                                        it?.name
                                                    )
                                                    getInstance(requireContext()).putString(
                                                        Constant.ROLE, getString(R.string.student)
                                                    )
                                                    findNavController().navigate(
                                                        RegisterStudentByEmailPasswordFragmentDirections.actionRegisterStudentByEmailPasswordToHomeStudentFragment(
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
                            } else {
                                requireContext().showToast("Email yang anda masukkan sudah terdaftar")
                                btnRegister.visible()
                                progressBarStudentRegister.gone()
                            }
                        }
                }
            }
        }
    }
}
