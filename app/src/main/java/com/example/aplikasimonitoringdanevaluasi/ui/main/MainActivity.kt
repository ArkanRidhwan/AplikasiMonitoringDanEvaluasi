package com.example.aplikasimonitoringdanevaluasi.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.ActivityMainBinding
import com.example.aplikasimonitoringdanevaluasi.ui.admin.home.HomeAdminFragment
import com.example.aplikasimonitoringdanevaluasi.ui.admin.profile.ProfileAdminFragment
import com.example.aplikasimonitoringdanevaluasi.ui.company.home.HomeCompanyFragment
import com.example.aplikasimonitoringdanevaluasi.ui.company.profile.ProfileCompanyFragment
import com.example.aplikasimonitoringdanevaluasi.ui.company.request.RequestFragment
import com.example.aplikasimonitoringdanevaluasi.ui.company.request.StudentRequestFragment
import com.example.aplikasimonitoringdanevaluasi.ui.student.home.HomeStudentFragment
import com.example.aplikasimonitoringdanevaluasi.ui.student.profile.ProfileStudentFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val navController = findNavController(R.id.fragmentContainerView)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeAdminFragment, R.id.profileAdminFragment -> {
                        btmNavAdmin.visibility = View.VISIBLE
                        btmNavAdmin.setItemSelected(R.id.HomeAdmin, true)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, HomeAdminFragment()).commit()
                        bottomMenu()
                    }
                    R.id.homeCompanyFragment, R.id.profileCompanyFragment -> {
                        btmNavCompany.visibility = View.VISIBLE
                        btmNavCompany.setItemSelected(R.id.HomeCompany, true)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, HomeCompanyFragment()).commit()
                        bottomMenu()
                    }
                    R.id.homeStudentFragment, R.id.profileStudentFragment -> {
                        btmNavStudent.visibility = View.VISIBLE
                        btmNavStudent.setItemSelected(R.id.HomeStudent, true)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, HomeStudentFragment()).commit()
                        bottomMenu()
                    }
                    else -> {
                        binding.btmNavCompany.visibility = View.GONE
                        binding.btmNavAdmin.visibility = View.GONE
                        binding.btmNavStudent.visibility = View.GONE
                    }
                }
            }

        }
        /*val navController = findNavController(R.id.fragmentContainerView)
        binding.apply {
            btmNavCompany.setupWithNavController(navController)
            btmNavStudent.setupWithNavController(navController)
            btmNavAdmin.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeAdminFragment, R.id.profileAdminFragment -> {
                        btmNavAdmin.visibility = View.VISIBLE
                    }
                    R.id.homeCompanyFragment, R.id.profileCompanyFragment -> {
                        btmNavCompany.visibility = View.VISIBLE
                    }
                    R.id.homeStudentFragment, R.id.profileStudentFragment -> {
                        btmNavStudent.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.btmNavCompany.visibility = View.GONE
                        binding.btmNavAdmin.visibility = View.GONE
                        binding.btmNavStudent.visibility = View.GONE
                    }
                }
            }
        }*/
    }

    private fun bottomMenu() {
        binding.btmNavAdmin.setOnItemSelectedListener {
            when (it) {
                R.id.HomeAdmin -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeAdminFragment()).commit()
                }
                R.id.ProfileAdmin -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProfileAdminFragment()).commit()
                }
            }
        }
        binding.btmNavCompany.setOnItemSelectedListener{
            when(it){
                R.id.HomeCompany -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeCompanyFragment()).commit()
                }
                R.id.ProfileCompany -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProfileCompanyFragment()).commit()
                }
                R.id.StudentRequestCompany -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, RequestFragment()).commit()
                }
            }
        }
        binding.btmNavStudent.setOnItemSelectedListener {
            when(it){
                R.id.HomeStudent -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeStudentFragment()).commit()
                }
                R.id.ProfileStudent -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProfileStudentFragment()).commit()
                }
            }
        }
    }
}