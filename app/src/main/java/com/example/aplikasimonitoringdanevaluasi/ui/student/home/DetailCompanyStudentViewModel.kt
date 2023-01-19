package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailCompanyStudentViewModel : ViewModel() {

    private val database = Firebase.database
    private val collStudent = database.getReference(Constant.COLL_STUDENT)

    fun getCompanyApproval(data: Student, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val student = Student.getCompanyApproval(
            id = userId,
            companyApproval = data.companyApproval,
            ApprovalStatus = data.ApprovalStatus.toString()
        )
        collStudent.child(userId).setValue(student)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }
}