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
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterCompanyByEmailPasswordBinding
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class RegisterCompanyByEmailPasswordFragment : Fragment() {

    private lateinit var binding: FragmentRegisterCompanyByEmailPasswordBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentRegisterCompanyByEmailPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnRegister.setOnClickListener {
                val emailFiltered = etCompanyEmail.text.toString().replace(".", "").replace("#", "")
                    .replace("$", "")
                    .replace("[", "").replace("]", "")
                val email = etCompanyEmail.text.toString()
                val password = etCompanyPassword.text.toString()
                val passwordVerification = etCompanyPasswordVerification.text.toString()
                val companyName = etCompanyName.text.toString()
                val companyAddress = etCompanyAddress.text.toString()
                val contactName = etCompanyContactName.text.toString()
                val contactPhoneNumber = etCompanyContactPhoneNumber.text.toString()

                if (email.isEmpty()) {
                    etCompanyEmail.error("Email Tidak boleh kosong")
                    etCompanyEmail.requestFocus()
                } else if (password.isEmpty()) {
                    etCompanyPassword.error("Password tidak boleh kosong")
                    etCompanyPassword.requestFocus()
                } else if (companyName.isEmpty()) {
                    etCompanyName.error("Nama perusahaan tidak boleh kosong")
                    etCompanyName.requestFocus()
                } else if (companyAddress.isEmpty()) {
                    etCompanyAddress.error("Alamat perusahaan tidak boleh kosong")
                    etCompanyAddress.requestFocus()
                } else if (contactName.isEmpty()) {
                    etCompanyContactName.error("Nama PIC tidak boleh kosong")
                    etCompanyContactName.requestFocus()
                } else if (contactPhoneNumber.isEmpty()) {
                    etCompanyContactPhoneNumber.error("Nomor telepon  PIC tidak boleh kosong")
                    etCompanyContactPhoneNumber.requestFocus()
                } else if (passwordVerification.isEmpty()) {
                    etCompanyPasswordVerification.error("Konfirmasi password tidak boleh kosong")
                    etCompanyPasswordVerification.requestFocus()
                } else if (passwordVerification != password) {
                    etCompanyPasswordVerification.error("Password tidak sesuai")
                    etCompanyPasswordVerification.requestFocus()
                } else {
                    val passHash =
                        BCrypt.withDefaults().hashToString(4, password.toCharArray())
                    btnRegister.gone()
                    progressBarCompanyRegister.visible()
                    progressBarCompanyRegister.playAnimation()
                    val company = Company(
                        id = UUID.randomUUID().toString(),
                        email = email,
                        password = passHash,
                        companyName = companyName,
                        companyAddress = companyAddress,
                        contactName = contactName,
                        contactPhoneNumber = contactPhoneNumber
                    )

                    val database = Firebase.database.getReference(Constant.COLL_COMPANY)
                    database.child(emailFiltered).get()
                        .addOnSuccessListener {
                            if (it.value == null) {
                                registerViewModel.saveCompany(company)
                                    .observe(viewLifecycleOwner) { data ->
                                        if (data == true) {
                                            getInstance(requireContext()).apply {
                                                putString(Constant.ID, company.id)
                                                putString(Constant.NAME, company.companyName)
                                                putString(
                                                    Constant.ROLE,
                                                    getString(R.string.company)
                                                )
                                            }
                                            findNavController().navigate(
                                                RegisterCompanyByEmailPasswordFragmentDirections.actionRegisterCompanyByEmailPasswordToHomeCompanyFragment(
                                                    getString(R.string.company)
                                                )
                                            )
                                            requireContext().showToast("Regitrasi berhasil")
                                        } else {
                                            btnRegister.visible()
                                            progressBarCompanyRegister.gone()
                                            requireContext().showToast("Regitrasi gagal")
                                        }
                                    }
                            } else {
                                requireActivity().showToast("Email sudah terdaftar")
                                btnRegister.visible()
                                progressBarCompanyRegister.gone()
                            }
                        }
                        .addOnFailureListener {
                            requireActivity().showToast("Database error")
                        }
                    /*registerViewModel.getCompanyByEmail(email)
                        .observe(viewLifecycleOwner) { dataCompany ->
                            if (dataCompany != null) {
                                requireContext().showToast("Email yang anda masukkan sudah terdaftar")
                                progressBarCompanyRegister.gone()
                                btnRegister.visible()
                            } else {
                                registerViewModel.saveCompany(company)
                                    .observe(viewLifecycleOwner) { data ->
                                        if (data == true) {
                                            getInstance(requireContext()).apply {
                                                putString(Constant.ID, company.id)
                                                putString(Constant.NAME, company.companyName)
                                                putString(Constant.ROLE, getString(R.string.company))
                                            }
                                            findNavController().navigate(
                                                RegisterCompanyByEmailPasswordFragmentDirections.actionRegisterCompanyByEmailPasswordToHomeCompanyFragment(
                                                    getString(R.string.company)
                                                )
                                            )
                                            requireContext().showToast("Regitrasi berhasil")
                                        } else {
                                            btnRegister.visible()
                                            progressBarCompanyRegister.gone()
                                            requireContext().showToast("Regitrasi gagal")
                                        }
                                    }
                            }
                        }*/

                    /*registerViewModel.getCompanyByEmail(email)
                        .observe(viewLifecycleOwner) {
                            if (it != null) {
                                btnRegister.visible()
                                progressBarCompanyRegister.gone()
                                requireContext().showToast("Email yang anda masukkan sudah terdaftar")
                                        } else {
                                registerViewModel.saveCompany(company)
                                    .observe(viewLifecycleOwner) { it ->
                                        if (it == true) {
                                            registerViewModel.getCompany(contactPhoneNumber)
                                                .observe(viewLifecycleOwner) {
                                                    getInstance(requireContext()).putString(
                                                        Constant.ID,
                                                        it?.id
                                                    )
                                                    getInstance(requireContext()).putString(
                                                        Constant.NAME,
                                                        it?.contactName
                                                    )
                                                    getInstance(requireContext()).putString(
                                                        Constant.ROLE,
                                                        getString(R.string.company)
                                                    )
                                                    findNavController().navigate(
                                                        RegisterCompanyByEmailPasswordFragmentDirections.actionRegisterCompanyByEmailPasswordToHomeCompanyFragment()
                                                    )
                                                }
                                            btnRegister.visible()
                                            progressBarCompanyRegister.gone()
                                            requireContext().showToast("Registrasi gagal")
                                        }
                                    }
                            } else {
                                btnRegister.visible()
                                progressBarCompanyRegister.gone()
                                requireContext().showToast("Email yang anda masukkan sudah terdaftar")
                            }
                        }*/
                }
            }
        }
    }
}