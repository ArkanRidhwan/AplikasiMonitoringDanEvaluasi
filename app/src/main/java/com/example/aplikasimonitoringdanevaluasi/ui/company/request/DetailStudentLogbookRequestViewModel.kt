package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailStudentLogbookRequestViewModel : ViewModel() {

    private val database = Firebase.database
    private val collLogbook= database.getReference(Constant.COLL_LOGBOOK)

    fun updateLogbookStatus(data: Logbook, logbookId: String, statusLogbook: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val logbook = Logbook.saveLogbook(
            id = logbookId,
            logbookUserId = data.logbookUserId,
            companyId = data.companyId,
            name = data.name,
            activityDate = data.activityDate,
            content = data.content,
            date = data.date,
            timestamp = data.timestamp,
            status = statusLogbook,
            image = data.image,
        )
        collLogbook.child(logbookId).setValue(logbook)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun updateLogbookStatusRejected(id: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        collLogbook.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
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