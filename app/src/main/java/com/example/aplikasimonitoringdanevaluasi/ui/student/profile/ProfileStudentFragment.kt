package com.example.aplikasimonitoringdanevaluasi.ui.student.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentProfileStudentBinding
import com.example.aplikasimonitoringdanevaluasi.ui.main.MainActivity
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.loadCircleImageFromUrl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileStudentFragment : Fragment() {

    private lateinit var binding: FragmentProfileStudentBinding
    private lateinit var auth: FirebaseAuth
    private val studentProfileViewModel: ProfileStudentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val userId = getInstance(requireContext()).getString(Constant.ID)
        binding.apply {
            studentProfileViewModel.studentProfile(userId).observe(viewLifecycleOwner) {
                tvStudentName.text = it?.name
                tvEmailStudent.text = it?.email
                if(it?.companyName?.isEmpty() == true){
                    tvStudentCompanyName.text = "Belum terdaftar"
                } else {
                    tvStudentCompanyName.text = it?.companyName
                }
                tvStudentJob.text = it?.job
                tvStudentPhoneNumber.text = it?.phoneNumber
                tvStudentClassName.text = it?.className
                tvStudentMajor.text = it?.studentMajor
                if (it?.image?.isEmpty() == true) {
                    ivProfile.setImageResource(R.drawable.ic_image_no_image)
                } else {
                    Glide.with(requireContext())
                        .load(it?.image)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivProfile)
                }
            }
            ivLogout.setOnClickListener {
                logout()
            }

            btnEditProfile.setOnClickListener {
                val action =
                    ProfileStudentFragmentDirections.actionProfileStudentFragmentToEditProfileStudentFragment()
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