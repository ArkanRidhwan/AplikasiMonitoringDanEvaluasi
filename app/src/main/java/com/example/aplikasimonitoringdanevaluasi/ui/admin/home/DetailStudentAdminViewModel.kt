package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

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

class  DetailStudentAdminViewModel : ViewModel() {

    private val database = Firebase.database
    private val collStudent = database.getReference(Constant.COLL_STUDENT)
    private val collRequestStudent = database.getReference(Constant.COLL_REQUEST_STUDENT)

    fun deleteStudent(email: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        collStudent.child(email.replace(".", "").replace("#", "").replace("$", "")
            .replace("[", "").replace("]", "")).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    i.ref.removeValue()
                }
                status.value = true
            }

            override fun onCancelled(error: DatabaseError) {
                status.value = false
            }
        })
        return status
    }

    fun getRequestStudentById(id: String): LiveData<RequestStudent?> {
        val dataReport = MutableLiveData<RequestStudent?>()
        var report: RequestStudent? = null
        collRequestStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(RequestStudent::class.java)
                        if (valueStudent?.studentId == id) {
                            report = valueStudent
                        }
                    }
                    dataReport.value = report
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataReport.value = null
            }
        })
        return dataReport
    }
}