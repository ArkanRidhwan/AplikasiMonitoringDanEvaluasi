package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aplikasimonitoringdanevaluasi.ui.main.module.ListModuleFragment
import com.example.aplikasimonitoringdanevaluasi.ui.main.video.ListVideoFragment

class CooursePagerAdminAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ListVideoFragment()
            1 -> fragment = ListModuleFragment()
        }
        return fragment as Fragment
    }
}