package com.example.aplikasimonitoringdanevaluasi.ui.main.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class RegisterViewModel() : ViewModel() {

    private val database = Firebase.database
    private val collCompany = database.getReference(Constant.COLL_COMPANY)
    private val collStudent = database.getReference(Constant.COLL_STUDENT)
    private val collAdmin = database.getReference(Constant.COLL_ADMIN)

    fun saveAdmin(data: Admin): LiveData<Boolean> {
        val adminId = UUID.randomUUID().toString()
        val status = MutableLiveData<Boolean>()
        val admin = Admin.saveRegistrationAdmin(
            id = adminId,
            name = data.name,
            email = data.email,
            password = data.password
        )
        collAdmin.child(adminId).setValue(admin)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun saveCompany(data: Company): LiveData<Boolean> {
        val companyId = UUID.randomUUID().toString()
        val status = MutableLiveData<Boolean>()
        val company = Company.saveRegistrationCompany(
            id = companyId,
            name = data.name,
            npwp = data.npwp,
            phoneNumber = data.phoneNumber,
            email = data.email,
            password = data.password
        )
        collCompany.child(companyId).setValue(company)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun saveStudent(data: Student): LiveData<Boolean> {
        val studentId = UUID.randomUUID().toString()
        val status = MutableLiveData<Boolean>()
        val student = Student.saveRegistrationStudent(
            id = studentId,
            name = data.name,
            job = data.job,
            email = data.email,
            companyName = data.companyName,
            className = data.className,
            phoneNumber = data.phoneNumber,
            password = data.password
        )
        collStudent.child(studentId).setValue(student)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun getAdmin(email: String): LiveData<Admin?> {
        val dataAdmin = MutableLiveData<Admin?>()
        var company: Admin? = null
        collCompany.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueAdmin = i.getValue(Admin::class.java)
                        if (valueAdmin?.email == email) {
                            company = valueAdmin
                        }
                    }
                    dataAdmin.value = company
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataAdmin.value = null
            }
        })
        return dataAdmin
    }

    fun getCompany(phoneNumber: String): LiveData<Company?> {
        val dataCompany = MutableLiveData<Company?>()
        var company: Company? = null
        collCompany.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueCompany = i.getValue(Company::class.java)
                        if (valueCompany?.phoneNumber == phoneNumber) {
                            company = valueCompany
                        }
                    }
                    dataCompany.value = company
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataCompany.value = null
            }
        })
        return dataCompany
    }

    fun getStudent(phoneNumber: String): LiveData<Student?> {
        val dataStudent = MutableLiveData<Student?>()
        var student: Student? = null
        collStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(Student::class.java)
                        if (valueStudent?.phoneNumber == phoneNumber) {
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

    fun companyEmailRegistrationValidation(email: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        collCompany.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueCompany = i.getValue(Company::class.java)
                        if (valueCompany?.email == null || valueCompany.email != email) {
                            status.value = true
                        } else {
                            status.value = false
                        }
                    }
                } else {
                    status.value = true
                }
            }

            override fun onCancelled(error: DatabaseError) {
                status.value = false
            }

        })
        return status
    }

    fun studentEmailRegistrationValidation(email: String): LiveData<Student?> {
        val dataStudent = MutableLiveData<Student?>()
        var student: Student? = null
        collStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(Student::class.java)
                        student = valueStudent
                    }
                    dataStudent.value = student
                }
            }

            override fun onCancelled(error: DatabaseError) {
                student = null
            }

        })
        return dataStudent
    }
}