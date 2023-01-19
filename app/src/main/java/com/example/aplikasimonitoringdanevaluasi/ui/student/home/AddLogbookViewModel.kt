package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddLogbookViewModel : ViewModel() {

    private val database = Firebase.database
    private val collLogbook = database.getReference(Constant.COLL_LOGBOOK)

    fun saveLogbook(data: Logbook): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val logbook = Logbook.saveLogbook(
            id = data.id,
            logbookUserId = data.logbookUserId,
            name = data.name,
            content = data.content,
            date = data.date,
            status = data.status
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
}