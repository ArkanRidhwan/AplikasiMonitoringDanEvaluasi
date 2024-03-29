package com.example.aplikasimonitoringdanevaluasi.ui.extra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Course
import com.example.aplikasimonitoringdanevaluasi.model.Report
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListCourseViewModel : ViewModel() {

    private val database = Firebase.database
    private val collCourse = database.getReference(Constant.COLL_COURSE)

    fun getAllCourse(): LiveData<List<Course>?> {
        val dataCourse = MutableLiveData<List<Course>?>()
        val course = ArrayList<Course>()

        collCourse.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    course.clear()
                    for (i in snapshot.children) {
                        i.getValue(Course::class.java)?.let {
                            course.add(it)
                        }
                    }
                    dataCourse.value = course
                } else {
                    dataCourse.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                course.clear()
            }

        })
        return dataCourse
    }

    fun getFilter(): LiveData<List<Course>?> {
        val dataCourse = MutableLiveData<List<Course>?>()
        val course = ArrayList<Course>()
        collCourse.orderByChild("tittle").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    course.clear()
                    for (i in snapshot.children) {
                        i.getValue(Course::class.java)?.let {
                            course.add(it)
                        }
                    }
                    dataCourse.value = course
                } else {
                    dataCourse.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataCourse.value = null
            }

        })
        return dataCourse
    }
}