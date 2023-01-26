package com.example.aplikasimonitoringdanevaluasi.ui.admin.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileAdminViewModel : ViewModel() {

    private val database = Firebase.database
    private val collAdmin = database.getReference(Constant.COLL_ADMIN)


    fun adminProfile(userId: String): LiveData<Admin?> {
        val dataAdmin = MutableLiveData<Admin?>()
        var admin: Admin? = null
        collAdmin.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val valueAdmin = i.getValue(Admin::class.java)
                        if (userId == valueAdmin?.id) {
                            admin = valueAdmin
                        }
                    }
                    dataAdmin.value = admin
                }
            }

            override fun onCancelled(error: DatabaseError) {
                admin = null
            }

        })
        return dataAdmin
    }

}