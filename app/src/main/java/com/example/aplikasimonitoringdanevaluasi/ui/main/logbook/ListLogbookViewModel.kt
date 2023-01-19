package com.example.aplikasimonitoringdanevaluasi.ui.main.logbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListLogbookViewModel : ViewModel() {

    private val database = Firebase.database
    private val collLogbook = database.getReference(Constant.COLL_LOGBOOK)

    fun getAllLogbook(): LiveData<List<Logbook>?> {
        val dataLogbook = MutableLiveData<List<Logbook>?>()
        val logbook = ArrayList<Logbook>()
        collLogbook.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    logbook.clear()
                    for (i in snapshot.children) {
                        i.getValue(Logbook::class.java)?.let {
                            logbook.add(it)
                        }
                    }
                    dataLogbook.value = logbook
                } else {
                    dataLogbook.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataLogbook.value = null
            }

        })
        return dataLogbook
    }

    fun getStudentLogbook(userId: String): LiveData<List<Logbook>?> {
        val dataLogbook = MutableLiveData<List<Logbook>?>()
        val logbook = ArrayList<Logbook>()
        collLogbook.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    logbook.clear()
                    for (i in snapshot.children) {
                        i.getValue(Logbook::class.java)?.let {
                            if (it.logbookUserId == userId)
                                logbook.add(it)
                        }
                    }
                    dataLogbook.value = logbook
                } else {
                    dataLogbook.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataLogbook.value = null
            }

        })
        return dataLogbook
    }

    fun getAdminLogbook(userId: String): LiveData<List<Logbook>?> {
        val dataLogbook = MutableLiveData<List<Logbook>?>()
        val logbook = ArrayList<Logbook>()
        collLogbook.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    logbook.clear()
                    for (i in snapshot.children) {
                        i.getValue(Logbook::class.java)?.let {
                            if (it.logbookUserId == userId)
                                logbook.add(it)
                        }
                    }
                    dataLogbook.value = logbook
                } else {
                    dataLogbook.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataLogbook.value = null
            }

        })
        return dataLogbook
    }
}