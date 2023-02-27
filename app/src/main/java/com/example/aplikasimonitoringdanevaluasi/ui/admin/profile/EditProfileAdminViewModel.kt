package com.example.aplikasimonitoringdanevaluasi.ui.admin.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditProfileAdminViewModel : ViewModel() {

    private val database = Firebase.database
    private val collAdmin = database.getReference(Constant.COLL_ADMIN)

    fun updateAdminByEmail(data: Admin, userId: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        val admin = Admin.saveRegistrationAdmin(
            id = userId,
            email = data.email,
            password = data.password,
            name = data.name,
            phoneNumber = data.phoneNumber,
            image = data.image
        )
        collAdmin.child(
            data.email.replace(".", "").replace("#", "").replace("$", "")
                .replace("[", "").replace("]", "")
        ).setValue(admin)
            .addOnCompleteListener {
                status.value = true
            }
            .addOnFailureListener {
                status.value = false
            }
        return status
    }

    fun getAdminById(id: String): LiveData<Admin?> {
        val dataAdmin = MutableLiveData<Admin?>()
        var admin: Admin? = null
        collAdmin.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueAdmin = i.getValue(Admin::class.java)
                        if (valueAdmin?.id == id) {
                            admin = valueAdmin
                        }
                    }
                    dataAdmin.value = admin
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataAdmin.value = null
            }
        })
        return dataAdmin
    }
}

