package com.example.aplikasimonitoringdanevaluasi.ui.main.module

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasimonitoringdanevaluasi.model.Module
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListModuleViewModel : ViewModel() {

    private val database = Firebase.database
    private val collModule = database.getReference(Constant.COLL_MODULE)

    fun getAllModule(): LiveData<List<Module>?> {
        val dataModule = MutableLiveData<List<Module>?>()
        val module = ArrayList<Module>()
        collModule.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    module.clear()
                    for (i in snapshot.children) {
                        i.getValue(Module::class.java)?.let {
                            module.add(it)
                        }
                    }
                    dataModule.value = module
                } else {
                    dataModule.value = null
                }
            }

            override fun onCancelled(error: DatabaseError) {
                dataModule.value = null
            }

        })
        return dataModule
    }
}