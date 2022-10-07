package com.example.aplikasimonitoringdanevaluasi.ui.main.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.google.firebase.auth.FirebaseAuth


class RegisterCompanyFragment : Fragment() {

    private lateinit var binding: FragmentRegisterCompanyBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            auth = FirebaseAuth.getInstance()
            btnRegister.setOnClickListener {
                val name = etCompanyName.text.toString()
                val npwp = etCompanyNPWP.text.toString()
                val phoneNumber = etCompanyPhoneNumber.text.toString()
                val email = auth.currentUser?.email

                if (name.isEmpty()) {
                    etCompanyName.error("Nama tidak boleh kosong")
                    etCompanyName.requestFocus()
                } else if (npwp.isEmpty()) {
                    etCompanyNPWP.error("NPWP tidak boleh kosong")
                    etCompanyNPWP.requestFocus()
                } else if (phoneNumber.isEmpty()) {
                    etCompanyPhoneNumber.error("Nomor telepon tidak boleh kosong")
                    etCompanyPhoneNumber.requestFocus()
                } else {
                    btnRegister.gone()
                    progressBarCompanyRegister.visible()
                    progressBarCompanyRegister.playAnimation()
                    val company = Company(
                        email = email.toString(),
                        name = name,
                        npwp = npwp,
                        phoneNumber = phoneNumber
                    )

                    registerViewModel.saveCompany(company).observe(viewLifecycleOwner) { it ->
                        if (it == true) {
                            registerViewModel.getCompany(phoneNumber).observe(viewLifecycleOwner) {
                                getInstance(requireContext()).putString(Constant.ID, it?.id)
                                getInstance(requireContext()).putString(Constant.NAME, it?.name)
                                getInstance(requireContext()).putString(Constant.ROLE, getString(R.string.company))
                                findNavController().navigate(RegisterCompanyFragmentDirections.actionRegisterCompanyFragmentToHomeCompanyFragment())
                            }
                        } else {
                            btnRegister.visible()
                            progressBarCompanyRegister.gone()
                            requireContext().showToast("Registrasi gagal")
                        }
                    }
                }
            }
        }
    }
}