package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.model.RequestLogbook
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StudentLogbookRequestViewModel : ViewModel() {

    private val database = Firebase.database
    private val collLogbook = database.getReference(Constant.COLL_LOGBOOK)
    private val collRequestStudent = database.getReference(Constant.COLL_REQUESTSTUDENT)

    fun getRequestLogbook(companyId: String, status: String): LiveData<List<Logbook>?> {
        val dataRequestLogbook = MutableLiveData<List<Logbook>?>()
        val requestLogbook = ArrayList<Logbook>()
        collLogbook.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    requestLogbook.clear()
                    for (i in snapshot.children) {
                        i.getValue(Logbook::class.java)?.let {
                            if (it.companyId == companyId && it.status == status) {
                                requestLogbook.add(it)
                            }
                        }
                    }
                    dataRequestLogbook.value = requestLogbook
                } else {
                    dataRequestLogbook.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataRequestLogbook.value = null
            }

        })
        return dataRequestLogbook
    }

    fun getRequestStudentById(id: String): LiveData<RequestStudent?> {
        val dataStudent = MutableLiveData<RequestStudent?>()
        var student: RequestStudent? = null
        collRequestStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(RequestStudent::class.java)
                        if (valueStudent?.companyId == id) {
                            student = valueStudent
                        }
                    }
                    dataStudent.value = student
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataStudent.value = null
            }
        })
        return dataStudent
    }
}