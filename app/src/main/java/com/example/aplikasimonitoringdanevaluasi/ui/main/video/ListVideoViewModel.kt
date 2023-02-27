package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListVideoViewModel : ViewModel() {

    private val database = Firebase.database
    private val collVideo = database.getReference(Constant.COLL_VIDEO)

    fun getAllVideo(): LiveData<List<Video>?> {
        val dataVideo = MutableLiveData<List<Video>?>()
        val video = ArrayList<Video>()

        collVideo.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    video.clear()
                    for (i in snapshot.children) {
                        i.getValue(Video::class.java)?.let {
                            video.add(it)
                        }
                    }
                    dataVideo.value = video
                } else {
                    dataVideo.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                video.clear()
            }

        })
        return dataVideo

        /*collVideo.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    video.clear()
                    for (i in snapshot.children) {
                        i.getValue(Video::class.java)?.let {
                            video.add(it)
                        }
                    }
                    dataVideo.value = video
                } else {
                    dataVideo.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataVideo.value = null
            }

        })
        return dataVideo*/
    }
}