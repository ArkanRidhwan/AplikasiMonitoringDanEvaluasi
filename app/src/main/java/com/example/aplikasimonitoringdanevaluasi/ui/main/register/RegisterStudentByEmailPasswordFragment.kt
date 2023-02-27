package com.example.aplikasimonitoringdanevaluasi.ui.main.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterStudentByEmailPasswordBinding
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

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
                val emailFiltered = etStudentEmail.text.toString().replace(".", "").replace("#", "")
                    .replace("$", "")
                    .replace("[", "").replace("]", "")
                val email = etStudentEmail.text.toString()
                val password = etStudentPassword.text.toString()
                val passwordVerification = etStudentPasswordVerification.text.toString()
                val name = etStudentName.text.toString()
                val job = etStudentJob.text.toString()
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
                } else if (className.isEmpty()) {
                    etStudentClassName.error("Nama kelas tidak boleh kosong")
                    etStudentClassName.requestFocus()
                } else if (phoneNumber.isEmpty()) {
                    etStudentPhoneNumber.error("Nomor telepon tidak boleh kosong")
                    etStudentPhoneNumber.requestFocus()
                } else if (major.isEmpty()) {
                    etStudentSchoolMajor.error("Jurusan tidak boleh kosong")
                    etStudentSchoolMajor.requestFocus()
                } else if (passwordVerification.isEmpty()) {
                    etStudentPasswordVerification.error("Konfirmasi password tidak boleh kosong")
                    etStudentPasswordVerification.requestFocus()
                } else if (password != passwordVerification) {
                    etStudentPasswordVerification.error("Password tidak sesuai")
                    etStudentPasswordVerification.requestFocus()
                } else {
                    val passHash =
                        BCrypt.withDefaults().hashToString(4, password.toCharArray())
                    btnRegister.gone()
                    progressBarStudentRegister.visible()
                    progressBarStudentRegister.playAnimation()
                    val student = Student(
                        id = UUID.randomUUID().toString(),
                        email = email,
                        password = encrypt(password).toString(),
                        name = name,
                        job = job,
                        className = className,
                        phoneNumber = phoneNumber,
                        studentMajor = major
                    )

                    val database = Firebase.database.getReference(Constant.COLL_STUDENT)
                    database.child(emailFiltered).get()
                        .addOnSuccessListener {
                            if (it.value == null) {
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
                            } else {
                                requireActivity().showToast("Email sudah terdaftar")
                                btnRegister.visible()
                                progressBarStudentRegister.gone()
                            }
                        }
                        .addOnFailureListener {
                            requireActivity().showToast("Database error")
                        }
                    /*registerViewModel.getStudentByEmail(email)
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
                        }*/
                }
            }
        }
    }
}
