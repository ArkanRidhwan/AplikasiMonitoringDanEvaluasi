package com.example.aplikasimonitoringdanevaluasi.ui.main.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterCompanyByEmailPasswordBinding
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.utils.*

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
                val email = etCompanyEmail.text.toString()
                val password = etCompanyPassword.text.toString()
                val name = etCompanyName.text.toString()
                val phoneNumber = etCompanyPhoneNumber.text.toString()
                val npwp = etCompanyNPWP.text.toString()

                if (email.isEmpty()) {
                    etCompanyEmail.error("Email Tidak boleh kosong")
                    etCompanyEmail.requestFocus()
                } else if (password.isEmpty()) {
                    etCompanyPassword.error("Password tidak boleh kosong")
                    etCompanyPassword.requestFocus()
                } else if (name.isEmpty()) {
                    etCompanyName.error("Nama tidak boleh kosong")
                    etCompanyName.requestFocus()
                } else if (npwp.isEmpty()) {
                    etCompanyPhoneNumber.error("NPWP tidak boleh kosong")
                    etCompanyPhoneNumber.requestFocus()
                } else if (phoneNumber.isEmpty()) {
                    etCompanyNPWP.error("Nomor telepon tidak boleh kosong")
                    etCompanyNPWP.requestFocus()
                } else {
                    btnRegister.gone()
                    progressBarCompanyRegister.visible()
                    progressBarCompanyRegister.playAnimation()
                    val company = Company(
                        name = name,
                        npwp = npwp,
                        email = email,
                        password = password,
                        phoneNumber = phoneNumber
                    )
                    registerViewModel.companyEmailRegistrationValidation(email)
                        .observe(viewLifecycleOwner) {
                            if ( it == true ) {
                                registerViewModel.saveCompany(company)
                                    .observe(viewLifecycleOwner) { it ->
                                        if (it == true) {
                                            registerViewModel.getCompany(phoneNumber)
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
                                                        Constant.ROLE,
                                                        getString(R.string.company)
                                                    )
                                                    findNavController().navigate(
                                                        RegisterCompanyByEmailPasswordFragmentDirections.actionRegisterCompanyByEmailPasswordToHomeCompanyFragment()
                                                    )
                                                }
                                        } else {
                                            btnRegister.visible()
                                            progressBarCompanyRegister.gone()
                                            requireContext().showToast("Registrasi gagal")
                                        }
                                    }
                            } else {
                                requireContext().showToast("Email yang anda masukkan sudah terdaftar")
                                btnRegister.visible()
                                progressBarCompanyRegister.gone()
                            }
                        }
                }
            }
        }
    }
}