package com.example.aplikasimonitoringdanevaluasi.ui.extra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Course
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CourseTittleViewModel : ViewModel() {

    private val database = Firebase.database
    private val collCourse = database.getReference(Constant.COLL_COURSE)

    fun saveCourse(data: Course): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val logbook = Course.saveCourse(
            id = data.id,
            adminId = data.adminId,
            studentId = data.studentId,
            tittle = data.tittle,
            number = data.number,
            timestamp = data.timestamp,
            check1 = data.check1,
            check2 = data.check2,
            check3 = data.check3,
            finalCheck = data.finalCheck
        )
        collCourse.child(data.id).setValue(logbook)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }
}