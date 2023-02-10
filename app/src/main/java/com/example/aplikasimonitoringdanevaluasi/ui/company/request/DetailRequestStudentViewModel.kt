package com.example.aplikasimonitoringdanevaluasi.ui.company.request

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

class DetailRequestStudentViewModel : ViewModel() {

    private val database = Firebase.database
    private val collStudent = database.getReference(Constant.COLL_STUDENT)
    private val collRequestStudent = database.getReference(Constant.COLL_REQUESTSTUDENT)

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

    fun updateRequestStatusAccepted(data: RequestStudent, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val student = RequestStudent.processStudentRequest(
            id = userId,
            status = data.status,
            companyId = data.companyId,
            studentId = data.studentId,
            studentEmail = data.studentEmail,
            studentName = data.studentName,
            image = data.image,
            reportStatus = data.reportStatus
        )
        collRequestStudent.child(userId).setValue(student)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun updateRequestStatusRejected(id: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        collRequestStudent.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
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
}