package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UploadVideoViewModel : ViewModel() {

    private val database = Firebase.database
    private val collVideo = database.getReference(Constant.COLL_VIDEO)

    fun saveVideo(data: Video): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val admin = Video.saveVideo(
            id = data.id,
            tittle = data.tittle,
            description = data.description,
            date = data.date,
            timestamp = data.timestamp,
            link = data.link,
        )
        collVideo.child(data.id).setValue(admin)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }
}