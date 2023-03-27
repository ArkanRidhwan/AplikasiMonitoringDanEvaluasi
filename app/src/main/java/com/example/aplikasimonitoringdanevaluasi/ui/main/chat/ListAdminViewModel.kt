package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListAdminViewModel : ViewModel() {
    private val database = Firebase.database
    private val collAdmin = database.getReference(Constant.COLL_ADMIN)

    fun getAdmin(): LiveData<List<Admin>?> {
        val dataAdmin = MutableLiveData<List<Admin>?>()
        val admin = ArrayList<Admin>()
        collAdmin.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    admin.clear()
                    for (i in snapshot.children) {
                        i.getValue(Admin::class.java)?.let {
                            admin.add(it)
                        }
                    }
                    dataAdmin.value = admin
                } else {
                    dataAdmin.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataAdmin.value = null
            }

        })
        return dataAdmin
    }

    fun getFilter(): LiveData<List<Admin>?> {
        val dataAdmin = MutableLiveData<List<Admin>?>()
        val admin = ArrayList<Admin>()
        collAdmin.orderByChild("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) { admin.clear()
                    for (i in snapshot.children) {
                        i.getValue(Admin::class.java)?.let {
                            admin.add(it)
                        }
                    }
                    dataAdmin.value = admin
                } else {
                    dataAdmin.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataAdmin.value = null
            }

        })
        return dataAdmin
    }
}