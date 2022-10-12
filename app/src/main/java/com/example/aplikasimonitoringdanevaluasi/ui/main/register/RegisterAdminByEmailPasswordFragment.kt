package com.example.aplikasimonitoringdanevaluasi.ui.main.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentRegisterAdminByEmailPasswordBinding
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.utils.*


class RegisterAdminByEmailPasswordFragment : Fragment() {

    private lateinit var binding: FragmentRegisterAdminByEmailPasswordBinding
    private val registerViewModel: RegisterViewModel by viewModels()

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
                val email = etAdminEmail.text.toString()
                val password = etAdminPassword.text.toString()
                val name = etAdminName.text.toString()
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
                } else if (verification.isEmpty()) {
                    etAdminVerification.error("Nomor verifikasi tidak boleh kosong")
                    etAdminVerification.requestFocus()
                } else if (verification != getString(R.string.verification)) {
                    requireContext().showToast("Kode verifikasi salah")
                } else {
                    btnRegister.gone()
                    progressBarAdminRegister.visible()
                    progressBarAdminRegister.playAnimation()
                    val admin = Admin(
                        name = name,
                        email = email,
                        password = password
                    )
                    registerViewModel.saveAdmin(admin)
                        .observe(viewLifecycleOwner) {
                            if (it == true) {
                                registerViewModel.getAdmin(email)
                                    .observe(viewLifecycleOwner){
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
                                            getString(R.string.admin)
                                        )
                                        findNavController().navigate(
                                            RegisterAdminByEmailPasswordFragmentDirections.actionRegisterAdminByEmailPasswordFragmentToHomeAdminFragment()
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