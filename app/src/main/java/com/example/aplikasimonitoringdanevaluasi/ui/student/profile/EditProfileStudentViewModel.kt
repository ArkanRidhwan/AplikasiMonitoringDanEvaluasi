package com.example.aplikasimonitoringdanevaluasi.ui.student.profile

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

class EditProfileStudentViewModel : ViewModel() {

    private val database = Firebase.database
    private val collStudent = database.getReference(Constant.COLL_STUDENT)

    fun updateStudentById(data: Student, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val student = Student.saveRegistrationStudent(
            id = userId,
            email = data.email,
            password = data.password,
            name = data.name,
            companyName = data.companyName,
            job = data.job,
            className = data.className,
            phoneNumber = data.phoneNumber,
            studentMajor = data.studentMajor,
            image = data.image
        )
        collStudent.child(
            data.email.replace(".", "").replace("#", "").replace("$", "")
                .replace("[", "").replace("]", "")
        ).setValue(student)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
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
}