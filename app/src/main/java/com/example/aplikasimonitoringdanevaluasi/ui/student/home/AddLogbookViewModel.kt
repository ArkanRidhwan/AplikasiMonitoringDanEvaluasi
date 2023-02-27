package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddLogbookViewModel : ViewModel() {

    private val database = Firebase.database
    private val collLogbook = database.getReference(Constant.COLL_LOGBOOK)
    private val collRequestStudent = database.getReference(Constant.COLL_REQUESTSTUDENT)

    fun saveLogbook(data: Logbook): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val logbook = Logbook.saveLogbook(
            id = data.id,
            logbookUserId = data.logbookUserId,
            companyId = data.companyId,
            name = data.name,
            activityDate = data.activityDate,
            content = data.content,
            date = data.date,
            timestamp = data.timestamp,
            status = data.status,
            image = data.image
        )
        collLogbook.child(data.id).setValue(logbook)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun getRequestStudentById(id: String): LiveData<RequestStudent?> {
        val dataStudent = MutableLiveData<RequestStudent?>()
        var student: RequestStudent? = null
        collRequestStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(RequestStudent::class.java)
                        if (valueStudent?.studentId == id) {
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