package com.example.aplikasimonitoringdanevaluasi.ui.admin.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentProfileAdminBinding
import com.example.aplikasimonitoringdanevaluasi.ui.main.MainActivity
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.loadCircleImageFromUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileAdminFragment : Fragment() {

    private lateinit var binding: FragmentProfileAdminBinding
    private lateinit var auth: FirebaseAuth
    private val adminProfileViewModel: ProfileAdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val userId = getInstance(requireContext()).getString(Constant.ID)
        binding.apply {
            adminProfileViewModel.adminProfile(userId).observe(viewLifecycleOwner) {
                tvAdminName.text = it?.name
                tvAdminEmail.text = it?.email
                tvAdminPhoneNumber.text = it?.phoneNumber
                if (it?.image?.isEmpty() == true) {
                    ivAdminProfile.setImageResource(R.drawable.img_no_image)
                } else {
                    if (it != null) {
                        ivAdminProfile.loadCircleImageFromUrl(it.image)
                    }
                }
            }
            ivLogout.setOnClickListener {
                logout()
            }

            btnEditProfile.setOnClickListener {
                //val admin = Admin()
                val action =
                    ProfileAdminFragmentDirections.actionProfileAdminFragmentToEditProfileAdminFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun logout() {
        auth.signOut()
        getInstance(requireContext()).clearPreferences()
        requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
    }
}