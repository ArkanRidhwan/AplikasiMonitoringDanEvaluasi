package com.example.aplikasimonitoringdanevaluasi.ui.main.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterAdminBinding
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class RegisterAdminFragment : Fragment() {

    private lateinit var binding: FragmentRegisterAdminBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentRegisterAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            auth = FirebaseAuth.getInstance()
            btnRegister.setOnClickListener {
                val adminName = etAdminName.text.toString()
                val phoneNumber = etAdminPhoneNumber.text.toString()
                val verificationNumber = etAdminVerificationNumber.text.toString()
                val email = auth.currentUser?.email

                if (adminName.isEmpty()) {
                    etAdminName.error("Nama tidak boleh kosong")
                    etAdminName.requestFocus()
                } else if (phoneNumber.isEmpty()) {
                    etAdminPhoneNumber.error("Nomor telepon tidak boleh kosong")
                    etAdminPhoneNumber.requestFocus()
                } else if (verificationNumber.isEmpty()) {
                    etAdminVerificationNumber.error("Nomor verifikasi tidak boleh kosong")
                    etAdminVerificationNumber.requestFocus()
                } else if (verificationNumber != getString(R.string.verificationAdmin)) {
                    etAdminVerificationNumber.error("Nomor verifikasi salah")
                    etAdminVerificationNumber.requestFocus()
                } else {
                    btnRegister.gone()
                    progressBarAdminRegister.visible()
                    progressBarAdminRegister.playAnimation()
                    val admin = Admin(
                        id = UUID.randomUUID().toString(),
                        email = email.toString(),
                        name = adminName,
                        phoneNumber = phoneNumber
                    )

                    registerViewModel.saveAdmin(admin).observe(viewLifecycleOwner) { it ->
                        if (it == true) {
                            registerViewModel.getAdmin(email).observe(viewLifecycleOwner) {
                                getInstance(requireContext()).putString(Constant.ID, it?.id)
                                getInstance(requireContext()).putString(
                                    Constant.NAME,
                                    it?.name
                                )
                                getInstance(requireContext()).putString(
                                    Constant.ROLE,
                                    getString(R.string.admin)
                                )
                                findNavController().navigate(
                                    RegisterAdminFragmentDirections.actionRegisterAdminFragmentToHomeAdminFragment(
                                        getString(R.string.admin)
                                    )
                                )
                            }
                        } else {
                            btnRegister.visible()
                            progressBarAdminRegister.gone()
                            requireContext().showToast("Registrasi gagal")
                        }
                    }
                }
            }
        }
    }
}