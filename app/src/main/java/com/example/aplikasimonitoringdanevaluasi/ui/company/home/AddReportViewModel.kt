package com.example.aplikasimonitoringdanevaluasi.ui.company.home

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

class AddReportViewModel : ViewModel() {

    private val database = Firebase.database
    private val collReport = database.getReference(Constant.COLL_REPORT)
    private val collRequestStudent = database.getReference(Constant.COLL_REQUEST_STUDENT)

    fun saveReport(data: Report): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val report = Report.saveReport(
            id = data.id,
            companyId = data.companyId,
            studentId = data.studentId,
            studentName = data.studentName,
            tittle = data.tittle,
            date = data.date,
            timestamp = data.timestamp,
            answer1 = data.answer1,
            answer2 = data.answer2,
            answer3 = data.answer3,
            answer4 = data.answer4,
            answer5 = data.answer5,
            answer6 = data.answer6,
            answer7 = data.answer7,
            answer8 = data.answer8,
            answer9 = data.answer9,
            answer10 = data.answer10,
            answer11 = data.answer11,
            answer12 = data.answer12,
            answer13 = data.answer13,
            answer14 = data.answer14,
            answer15 = data.answer15,
            answer16 = data.answer16,
            answer17 = data.answer17,
            answer18 = data.answer18
        )
        collReport.child(data.id).setValue(report)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun getRequestStudentById(id: String): LiveData<RequestStudent?> {
        val dataReport = MutableLiveData<RequestStudent?>()
        var report: RequestStudent? = null
        collRequestStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(RequestStudent::class.java)
                        if (valueStudent?.studentId == id) {
                            report = valueStudent
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

    fun updateRequestStudentById(data: RequestStudent, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val student = RequestStudent(
            id = data.id,
            status = data.status,
            companyId = data.companyId,
            studentId = data.studentId,
            studentName = data.studentName,
            studentEmail = data.studentEmail,
            image = data.image,
            reportStatus = data.reportStatus
        )
        collRequestStudent.child(userId).setValue(student)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }
}