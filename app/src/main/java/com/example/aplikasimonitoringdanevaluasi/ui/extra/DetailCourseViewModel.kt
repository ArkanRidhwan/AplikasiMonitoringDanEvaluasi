package com.example.aplikasimonitoringdanevaluasi.ui.extra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.*
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailCourseViewModel : ViewModel() {

    private val database = Firebase.database
    private val collVideo = database.getReference(Constant.COLL_VIDEO)
    private val collModule = database.getReference(Constant.COLL_MODULE)
    private val collEvaluation = database.getReference(Constant.COLL_EVALUATION)
    private val collCourse = database.getReference(Constant.COLL_COURSE)

    fun updateCourseById(data: Course, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val course = Course.saveCourse(
            id = data.id,
            adminId =  data.adminId,
            studentId = data.studentId,
            tittle = data.tittle,
            number = data.number,
            timestamp = data.timestamp,
            check1 = data.check1,
            check2 = data.check2,
            check3 = data.check3,
            finalCheck = data.finalCheck
        )
        collCourse.child(userId).setValue(course)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun getCourseById(id: String): LiveData<Course?> {
        val dataCourse = MutableLiveData<Course?>()
        var course: Course? = null
        collCourse.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueEvaluation = i.getValue(Course::class.java)
                        if (valueEvaluation?.id == id) {
                            course = valueEvaluation
                        }
                    }
                    dataCourse.value = course
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataCourse.value = null
            }
        })
        return dataCourse
    }

    fun getVideoById(id: String): LiveData<Video?> {
        val dataVideo = MutableLiveData<Video?>()
        var video: Video? = null
        collVideo.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueVideo = i.getValue(Video::class.java)
                        if (valueVideo?.courseId == id) {
                            video = valueVideo
                        }
                    }
                    dataVideo.value = video
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataVideo.value = null
            }
        })
        return dataVideo
    }

    fun getModuleById(id: String): LiveData<Module?> {
        val dataModule = MutableLiveData<Module?>()
        var module: Module? = null
        collModule.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueModule = i.getValue(Module::class.java)
                        if (valueModule?.courseId == id) {
                            module = valueModule
                        }
                    }
                    dataModule.value = module
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataModule.value = null
            }
        })
        return dataModule
    }

    fun getEvaluationById(id: String): LiveData<Evaluation?> {
        val dataEvaluation = MutableLiveData<Evaluation?>()
        var evaluation: Evaluation? = null
        collEvaluation.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueEvaluation = i.getValue(Evaluation::class.java)
                        if (valueEvaluation?.courseId == id) {
                            evaluation = valueEvaluation
                        }
                    }
                    dataEvaluation.value = evaluation
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataEvaluation.value = null
            }
        })
        return dataEvaluation
    }
}