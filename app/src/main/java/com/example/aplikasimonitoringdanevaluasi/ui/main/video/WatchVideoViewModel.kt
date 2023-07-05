package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WatchVideoViewModel : ViewModel() {

    private val database = Firebase.database
    private val collVideo = database.getReference(Constant.COLL_VIDEO)

    fun deleteVideo(id: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        collVideo.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
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