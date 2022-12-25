package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aplikasimonitoringdanevaluasi.ui.main.logbook.ListLogbookFragment

class HomeStudentPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ListCompanyFragment()
            1 -> fragment = ListLogbookFragment()
        }
        return fragment as Fragment
    }
}