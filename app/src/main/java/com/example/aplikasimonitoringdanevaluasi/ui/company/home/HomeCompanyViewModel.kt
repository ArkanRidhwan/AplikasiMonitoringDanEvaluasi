package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeCompanyViewModel : ViewModel() {

    private val database = Firebase.database
    private val collRequestStudent = database.getReference(Constant.COLL_REQUESTSTUDENT)

    fun getRequestStudent(status: String, companyId: String): LiveData<List<RequestStudent>?> {
        val dataRequestStudent = MutableLiveData<List<RequestStudent>?>()
        val requestStudent = ArrayList<RequestStudent>()
        collRequestStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    requestStudent.clear()
                    for (i in snapshot.children) {
                        i.getValue(RequestStudent::class.java)?.let {
                            if (it.status == status && it.companyId == companyId){
                                requestStudent.add(it)
                            }
                        }
                    }
                    dataRequestStudent.value = requestStudent
                } else {
                    dataRequestStudent.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataRequestStudent.value = null
            }

        })
        return dataRequestStudent
    }
}