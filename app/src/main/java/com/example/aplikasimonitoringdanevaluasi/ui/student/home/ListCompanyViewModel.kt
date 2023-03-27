package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListCompanyViewModel : ViewModel() {
    private val database = Firebase.database
    private val collCompany = database.getReference(Constant.COLL_COMPANY)

    fun getCompany(): LiveData<List<Company>?> {
        val dataCompany = MutableLiveData<List<Company>?>()
        val company = ArrayList<Company>()
        collCompany.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    company.clear()
                    for (i in snapshot.children) {
                        i.getValue(Company::class.java)?.let {
                            company.add(it)
                        }
                    }
                    dataCompany.value = company
                } else {
                    dataCompany.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataCompany.value = null
            }

        })
        return dataCompany
    }

    fun getFilter(): LiveData<List<Company>?> {
        val dataCompany = MutableLiveData<List<Company>?>()
        val company = ArrayList<Company>()
        collCompany.orderByChild("companyName").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    company.clear()
                    for (i in snapshot.children) {
                        i.getValue(Company::class.java)?.let {
                            company.add(it)
                        }
                    }
                    dataCompany.value = company
                } else {
                    dataCompany.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataCompany.value = null
            }

        })
        return dataCompany
    }
}