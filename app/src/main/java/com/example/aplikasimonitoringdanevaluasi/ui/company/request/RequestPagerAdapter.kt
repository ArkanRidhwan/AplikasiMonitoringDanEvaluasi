package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RequestPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = StudentLogbookRequestFragment()
            1 -> fragment = StudentRequestFragment()
        }
        return fragment as Fragment
    }
}