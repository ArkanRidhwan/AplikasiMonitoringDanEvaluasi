package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentHomeStudentBinding
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.CooursePagerAdminAdapter
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.CourseAdminFragment
import com.example.aplikasimonitoringdanevaluasi.ui.main.chat.ListAdminAdapter
import com.example.aplikasimonitoringdanevaluasi.ui.main.chat.ListAdminViewModel
import com.example.aplikasimonitoringdanevaluasi.ui.main.chat.ListContactChatFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible
import com.google.android.material.tabs.TabLayoutMediator


class HomeStudentFragment : Fragment() {

    private lateinit var binding: FragmentHomeStudentBinding
    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.homeAdminAdapter1,
            R.string.homeAdminAdapter2
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val sectionsPagerAdapter = HomeStudentPagerAdapter(requireActivity() as AppCompatActivity)
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            fabChat.setOnClickListener {
                findNavController().navigate(
                    HomeStudentFragmentDirections.actionHomeStudentFragmentToListContactChatFragment(
                        getString(R.string.student)
                    )
                )
            }
        }
    }

}