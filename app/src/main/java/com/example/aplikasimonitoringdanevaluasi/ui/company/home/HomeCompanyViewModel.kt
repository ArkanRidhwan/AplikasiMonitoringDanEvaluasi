package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeCompanyViewModel : ViewModel() {

    private val database = Firebase.database
    private val collRequestStudent = database.getReference(Constant.COLL_REQUEST_STUDENT)
    private val collStudent = database.getReference(Constant.COLL_STUDENT)

    fun getApprovedStudent(status: String, companyId: String): LiveData<List<RequestStudent>?> {
        val dataRequestStudent = MutableLiveData<List<RequestStudent>?>()
        val requestStudent = ArrayList<RequestStudent>()
        collRequestStudent.orderByChild("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    requestStudent.clear()
                    for (i in snapshot.children) {
                        i.getValue(RequestStudent::class.java)?.let {
                            if (it.status == status && it.companyId == companyId){
                                requestStudent.add(it)
                            }
                        }
                    }
                    dataRequestStudent.value = requestStudent
                } else {
                    dataRequestStudent.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataRequestStudent.value = null
            }

        })
        return dataRequestStudent
    }

    fun getStudentById(id: String): LiveData<Student?> {
        val dataStudent = MutableLiveData<Student?>()
        var student: Student? = null
        collStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(Student::class.java)
                        if (valueStudent?.id == id) {
                            student = valueStudent
                        }
                    }
                    dataStudent.value = student
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataStudent.value = null
            }
        })
        return dataStudent
    }

    fun getFilter(status: String, companyId: String): LiveData<List<RequestStudent>?> {
        val dataRequestStudent = MutableLiveData<List<RequestStudent>?>()
        val requestStudent = ArrayList<RequestStudent>()
        collRequestStudent.orderByChild("studentName").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    requestStudent.clear()
                    for (i in snapshot.children) {
                        i.getValue(RequestStudent::class.java)?.let {
                            if (it.status == status && it.companyId == companyId){
                                requestStudent.add(it)
                            }
                        }
                    }
                    dataRequestStudent.value = requestStudent
                } else {
                    dataRequestStudent.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataRequestStudent.value = null
            }

        })
        return dataRequestStudent
    }
}