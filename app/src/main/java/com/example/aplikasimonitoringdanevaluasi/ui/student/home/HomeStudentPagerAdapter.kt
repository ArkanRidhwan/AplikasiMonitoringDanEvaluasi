package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aplikasimonitoringdanevaluasi.ui.main.logbook.ListLogbookStudentFragment

class HomeStudentPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ListCompanyFragment()
            1 -> fragment = ListLogbookStudentFragment()
        }
        return fragment as Fragment
    }
}