package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Report
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailReportViewModel : ViewModel() {

    private val database = Firebase.database
    private val collReport = database.getReference(Constant.COLL_REPORT)

    fun getReportById(id: String): LiveData<Report?> {
        val dataReport= MutableLiveData<Report?>()
        var report: Report? = null
        collReport.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueReport = i.getValue(Report::class.java)
                        if (valueReport?.studentId == id) {
                            report = valueReport
                        }
                    }
                    dataReport.value = report
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataReport.value = null
            }
        })
        return dataReport
    }
}