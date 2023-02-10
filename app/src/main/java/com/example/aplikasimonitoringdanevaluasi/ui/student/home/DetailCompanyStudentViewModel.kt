package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailCompanyStudentViewModel : ViewModel() {

    private val database = Firebase.database
    private val collStudent = database.getReference(Constant.COLL_STUDENT)
    private val collRequestStudent = database.getReference(Constant.COLL_REQUESTSTUDENT)

    fun saveRequestStudent(data: RequestStudent): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val student = RequestStudent(
            id = data.id,
            status = data.status,
            companyId = data.companyId,
            studentId = data.studentId,
            studentName = data.studentName,
            studentEmail = data.studentEmail,
            image = data.image
        )
        collRequestStudent.child(data.id).setValue(student)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

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

    fun getRequestStudentById(id: String): LiveData<RequestStudent?> {
        val dataRequestStudent = MutableLiveData<RequestStudent?>()
        var requestStudent: RequestStudent? = null
        collRequestStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(RequestStudent::class.java)
                        if (valueStudent?.studentId == id) {
                            requestStudent = valueStudent
                        }
                    }
                    dataRequestStudent.value = requestStudent
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataRequestStudent.value = null
            }
        })
        return dataRequestStudent
    }
}