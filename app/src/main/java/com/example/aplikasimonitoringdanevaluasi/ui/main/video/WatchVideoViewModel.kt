package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WatchVideoViewModel : ViewModel() {

    private val database = Firebase.database
    private val collVideo = database.getReference(Constant.COLL_VIDEO)

    fun getVideoById(id: String) {
        collVideo.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    i.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}