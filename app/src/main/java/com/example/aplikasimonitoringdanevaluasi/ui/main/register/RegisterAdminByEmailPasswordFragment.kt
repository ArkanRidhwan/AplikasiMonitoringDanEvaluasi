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
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterAdminByEmailPasswordBinding
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*


class RegisterAdminByEmailPasswordFragment : Fragment() {

    private lateinit var binding: FragmentRegisterAdminByEmailPasswordBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private var availableEmail = ""
    private var step = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentRegisterAdminByEmailPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnRegister.setOnClickListener {
                val emailFiltered =
                    etAdminEmail.text.toString().replace(".", "").replace("#", "").replace("$", "")
                        .replace("[", "").replace("]", "")
                val email = etAdminEmail.text.toString()
                val password = etAdminPassword.text.toString()
                val passwordVerification = etAdminPasswordVerification.text.toString()
                val name = etAdminName.text.toString()
                val phoneNumber = etAdminPhoneNumber.text.toString()
                val verification = etAdminVerification.text.toString()

                if (email.isEmpty()) {
                    etAdminEmail.error("Email Tidak boleh kosong")
                    etAdminEmail.requestFocus()
                } else if (password.isEmpty()) {
                    etAdminPassword.error("Password tidak boleh kosong")
                    etAdminPassword.requestFocus()
                } else if (name.isEmpty()) {
                    etAdminName.error("Nama tidak boleh kosong")
                    etAdminName.requestFocus()
                } else if (phoneNumber.isEmpty()) {
                    etAdminPhoneNumber.error("Nomor telepon tidak boleh kosong")
                    etAdminPhoneNumber.requestFocus()
                } else if (verification.isEmpty()) {
                    etAdminVerification.error("Nomor verifikasi tidak boleh kosong")
                    etAdminVerification.requestFocus()
                } else if (verification.isEmpty()) {
                    etAdminVerification.error("Nomor verifikasi tidak boleh kosong")
                    etAdminVerification.requestFocus()
                } else if (verification != getString(R.string.verification)) {
                    etAdminVerification.error("Nomor verifikasi salah")
                    etAdminVerification.requestFocus()
                } else if (passwordVerification.isEmpty()) {
                    etAdminPasswordVerification.error("Konfirmasi password tidak boleh kosong")
                    etAdminPasswordVerification.requestFocus()
                } else if (password != passwordVerification) {
                    etAdminPasswordVerification.error("Password tidak sesuai")
                    etAdminPasswordVerification.requestFocus()
                } else {
                    val passHash =
                        BCrypt.withDefaults().hashToString(4, password.toCharArray())
                    btnRegister.gone()
                    progressBarAdminRegister.visible()
                    progressBarAdminRegister.playAnimation()
                    val admin = Admin(
                        id = UUID.randomUUID().toString(),
                        email = email,
                        password = passHash,
                        name = name,
                        phoneNumber = phoneNumber,
                    )

                    val database = Firebase.database.getReference(Constant.COLL_ADMIN)
                    database.child(emailFiltered).get()
                        .addOnSuccessListener {
                            if (it.value == null) {
                                registerViewModel.saveAdmin(admin)
                                    .observe(viewLifecycleOwner) { data ->
                                        if (data == true) {
                                            getInstance(requireContext()).apply {
                                                putString(Constant.ID, admin.id)
                                                putString(Constant.NAME, admin.name)
                                                putString(
                                                    Constant.ROLE,
                                                    getString(R.string.admin)
                                                )
                                            }
                                            findNavController().navigate(
                                                RegisterAdminByEmailPasswordFragmentDirections.actionRegisterAdminByEmailPasswordFragmentToHomeAdminFragment(
                                                    getString(R.string.admin)
                                                )
                                            )
                                            requireContext().showToast("Regitrasi berhasil")
                                        } else {
                                            btnRegister.visible()
                                            progressBarAdminRegister.gone()
                                            requireContext().showToast("Regitrasi gagal")
                                        }
                                    }
                            } else {
                                requireActivity().showToast("Email sudah terdaftar")
                                btnRegister.visible()
                                progressBarAdminRegister.gone()
                            }
                        }
                        .addOnFailureListener {
                            requireActivity().showToast("Database error")
                        }
                }

                /*if (step == 2) {
                    val email = etAdminEmail.text.toString()
                    val password = etAdminPassword.text.toString()
                    val name = etAdminName.text.toString()
                    val phoneNumber = etAdminPhoneNumber.text.toString()
                    val verification = etAdminVerification.text.toString()

                    if (email.isEmpty()) {
                        etAdminEmail.error("Email Tidak boleh kosong")
                        etAdminEmail.requestFocus()
                    } else if (password.isEmpty()) {
                        etAdminPassword.error("Password tidak boleh kosong")
                        etAdminPassword.requestFocus()
                    } else if (name.isEmpty()) {
                        etAdminName.error("Nama tidak boleh kosong")
                        etAdminName.requestFocus()
                    } else if (phoneNumber.isEmpty()) {
                        etAdminPhoneNumber.error("Nomor telepon tidak boleh kosong")
                        etAdminPhoneNumber.requestFocus()
                    } else if (verification.isEmpty()) {
                        etAdminVerification.error("Nomor verifikasi tidak boleh kosong")
                        etAdminVerification.requestFocus()
                    } else if (verification != getString(R.string.verification)) {
                        requireContext().showToast("Kode verifikasi salah")
                    } else {
                        val passHash =
                            BCrypt.withDefaults().hashToString(12, password.toCharArray())
                        btnRegister.gone()
                        progressBarAdminRegister.visible()
                        progressBarAdminRegister.playAnimation()
                        val admin = Admin(
                            id = UUID.randomUUID().toString(),
                            email = email,
                            password = passHash,
                            name = name,
                            phoneNumber = phoneNumber,
                        )
                        registerViewModel.saveAdmin(admin)
                            .observe(viewLifecycleOwner) { data ->
                                if (data == true) {
                                    getInstance(requireContext()).apply {
                                        putString(Constant.ID, admin.id)
                                        putString(Constant.NAME, admin.name)
                                        putString(
                                            Constant.ROLE,
                                            getString(R.string.admin)
                                        )
                                    }
                                    findNavController().navigate(
                                        RegisterAdminByEmailPasswordFragmentDirections.actionRegisterAdminByEmailPasswordFragmentToHomeAdminFragment(
                                            getString(R.string.admin)
                                        )
                                    )
                                    requireContext().showToast("Regitrasi berhasil")
                                } else {
                                    btnRegister.visible()
                                    progressBarAdminRegister.gone()
                                    requireContext().showToast("Regitrasi gagal")
                                }
                            }
                    }
                } else {
                    val email = etAdminEmail.text.toString().replace(".", "")
                    val database = Firebase.database.getReference(Constant.COLL_ADMIN)
                    database.child(email).get()
                        .addOnSuccessListener {
                            if (it.value == null) {
                                step = 2
                                etAdminName.visible()
                                etAdminPassword.visible()
                                etAdminPhoneNumber.visible()
                                etAdminVerification.visible()
                            } else {
                                requireActivity().showToast("Email sudah terdaftar")
                            }
                        }
                        .addOnFailureListener {
                            requireActivity().showToast("Database error")
                        }
                }*/
            }
        }
    }
}
