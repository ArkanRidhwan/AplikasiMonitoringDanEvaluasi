package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListStudentViewModel : ViewModel() {
    private val database = Firebase.database
    private val collStudent = database.getReference(Constant.COLL_STUDENT)

    fun getStudent(): LiveData<List<Student>?> {
        val dataStudent = MutableLiveData<List<Student>?>()
        val student = ArrayList<Student>()
        collStudent.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    student.clear()
                    for (i in snapshot.children) {
                        i.getValue(Student::class.java)?.let {
                            student.add(it)
                        }
                    }
                    dataStudent.value = student
                } else {
                    dataStudent.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataStudent.value = null
            }

        })
        return dataStudent
    }

    fun getFilter(): LiveData<List<Student>?> {
        val dataStudent = MutableLiveData<List<Student>?>()
        val student = ArrayList<Student>()
        collStudent.orderByChild("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    student.clear()
                    for (i in snapshot.children) {
                        i.getValue(Student::class.java)?.let {
                            student.add(it)
                        }
                    }
                    dataStudent.value = student
                } else {
                    dataStudent.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataStudent.value = null
            }

        })
        return dataStudent
    }
}