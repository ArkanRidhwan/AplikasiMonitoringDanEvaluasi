package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailStudentCompanyViewModel : ViewModel() {

    private val database = Firebase.database
    private val collStudent = database.getReference(Constant.COLL_STUDENT)
    private val collRequestStudent = database.getReference(Constant.COLL_REQUEST_STUDENT)

    fun getStudentById(id: String): LiveData<Student?> {
        val dataStudent = MutableLiveData<Student?>()
        var student: Student? = null
        collStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(Student::class.java)
                        if (valueStudent?.id == id) {
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

    fun getReportById(id: String): LiveData<RequestStudent?> {
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