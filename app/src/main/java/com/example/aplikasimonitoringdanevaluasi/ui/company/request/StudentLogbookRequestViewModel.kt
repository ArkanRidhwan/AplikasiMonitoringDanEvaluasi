package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.model.RequestLogbook
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StudentLogbookRequestViewModel : ViewModel() {

    private val database = Firebase.database
    private val collLogbook = database.getReference(Constant.COLL_LOGBOOK)

    fun getRequestLogbook(status: Boolean, companyId: String): LiveData<List<Logbook>?> {
        val dataRequesLogbook = MutableLiveData<List<Logbook>?>()
        val requestLogbook = ArrayList<Logbook>()
        collLogbook.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    requestLogbook.clear()
                    for (i in snapshot.children) {
                        i.getValue(Logbook::class.java)?.let {
                            if (it.status == status && it.companyId == companyId) {
                                requestLogbook.add(it)
                            }
                        }
                    }
                    dataRequesLogbook.value = requestLogbook
                } else {
                    dataRequesLogbook.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataRequesLogbook.value = null
            }

        })
        return dataRequesLogbook
    }
}