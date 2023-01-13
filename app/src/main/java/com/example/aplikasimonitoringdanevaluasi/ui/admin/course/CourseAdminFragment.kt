package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentCourseAdminBinding
import com.google.android.material.tabs.TabLayoutMediator


class CourseAdminFragment : Fragment() {

    private lateinit var binding: FragmentCourseAdminBinding

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.courseAdminAdapter1,
            R.string.courseAdminAdapter2
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            val sectionsPagerAdapter =
                CourseAdminPagerAdapter(requireActivity() as AppCompatActivity)
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }
}