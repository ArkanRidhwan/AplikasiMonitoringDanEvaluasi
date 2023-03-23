package com.example.aplikasimonitoringdanevaluasi.ui.extra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Course
import com.example.aplikasimonitoringdanevaluasi.model.Evaluation
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddEvaluationLinkViewModel : ViewModel() {

    private val database = Firebase.database
    private val collEvaluation = database.getReference(Constant.COLL_EVALUATION)

    fun saveEvaluation(data: Evaluation): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val logbook = Evaluation.saveEvaluation(
            id = data.id,
            timestamp = data.timestamp,
            courseId = data.courseId,
            link = data.link,
        )
        collEvaluation.child(data.id).setValue(logbook)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }
}