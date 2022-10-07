package com.example.aplikasimonitoringdanevaluasi.ui.main.login

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

class LoginViewModel() : ViewModel() {

    private val database = Firebase.database
    private val collStudent = database.getReference(Constant.COLL_STUDENT)
    private val collAdmin = database.getReference(Constant.COLL_ADMIN)
    private val collCompany = database.getReference(Constant.COLL_COMPANY)

    fun loginAdminByEmailPassword(email: String, password: String): LiveData<Admin?> {
        val dataAdmin = MutableLiveData<Admin?>()
        var admin: Admin? = null
        collAdmin.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueAdmin = i.getValue(Admin::class.java)
                        if (valueAdmin?.email == email && valueAdmin.password == password) {
                            admin = valueAdmin
                        }
                        dataAdmin.value = admin
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                admin = null
            }

        })
        return dataAdmin
    }

    fun loginCompanyByEmailPassword(email: String, password: String): LiveData<Company?> {
        val dataCompany = MutableLiveData<Company?>()
        var company: Company? = null
        collCompany.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueCompany = i.getValue(Company::class.java)
                        if (valueCompany?.email == email && valueCompany.password == password) {
                            company = valueCompany
                        }
                    }
                    dataCompany.value = company
                }
            }

            override fun onCancelled(error: DatabaseError) {
                company = null
            }

        })
        return dataCompany
    }

    fun loginStudentByEmailPassword(email: String, password: String): LiveData<Student?> {
        val dataStudent = MutableLiveData<Student?>()
        var student: Student? = null
        collStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(Student::class.java)
                        if(valueStudent?.email == email && valueStudent.password == password) {
                            student = valueStudent
                        }
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

    fun loginCompanyByGoogleAuth(email: String): LiveData<Company?> {
        val dataCompany = MutableLiveData<Company?>()
        var company: Company? = null
        collCompany.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueCompany = i.getValue(Company::class.java)
                        if (valueCompany?.email == email) {
                            company = valueCompany
                        }
                    }
                    dataCompany.value = company
                } else {
                    dataCompany.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataCompany.value = null
            }

        })
        return dataCompany
    }

    fun loginStudentByGoogleAuth(email: String): LiveData<Student?> {
        val dataStudent = MutableLiveData<Student?>()
        var student: Student? = null
        collStudent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueStudent = i.getValue(Student::class.java)
                        if (valueStudent?.email == email) {
                            student = valueStudent
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