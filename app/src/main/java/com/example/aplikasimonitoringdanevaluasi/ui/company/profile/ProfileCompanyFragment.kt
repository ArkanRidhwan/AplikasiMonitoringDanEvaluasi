package com.example.aplikasimonitoringdanevaluasi.ui.company.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentProfileCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.ui.main.MainActivity
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.loadCircleImageFromUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileCompanyFragment : Fragment() {

    private lateinit var binding: FragmentProfileCompanyBinding
    private lateinit var auth: FirebaseAuth
    private val companyProfileViewModel: ProfileCompanyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = getInstance(requireContext()).getString(Constant.ID)
        auth = Firebase.auth
        binding.apply {
            companyProfileViewModel.companyProfile(userId).observe(viewLifecycleOwner) {
                tvCompanyName.text = it?.companyName
                tvCompanyAddress.text = it?.companyAddress
                tvContactName.text = it?.contactName
                tvContactEmail.text = it?.email
                tvContactPhoneNumber.text = it?.contactPhoneNumber
                if (it?.image?.isEmpty() == true) {
                    ivCompanyProfile.setImageResource(R.drawable.ic_image_no_image)
                } else {
                    Glide.with(requireContext())
                        .load(it?.image)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivCompanyProfile)
                }
            }
            ivLogout.setOnClickListener {
                logout()
            }
            btnEditProfile.setOnClickListener {
                val action =
                    ProfileCompanyFragmentDirections.actionProfileCompanyFragmentToEditProfileCompanyFragment()
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