package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.model.Report
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListReportAdminViewModel : ViewModel() {
    private val database = Firebase.database
    private val collReport = database.getReference(Constant.COLL_REPORT)

    fun getReport(id: String): LiveData<List<Report>?> {
        val dataReport = MutableLiveData<List<Report>?>()
        val report = ArrayList<Report>()
        collReport.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    report.clear()
                    for (i in snapshot.children) {
                        i.getValue(Report::class.java)?.let {
                            if (it.studentId == id)
                                report.add(it)
                        }
                    }
                    dataReport.value = report
                } else {
                    dataReport.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataReport.value = null
            }

        })
        return dataReport
    }
}