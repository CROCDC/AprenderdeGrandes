package com.cr.o.cdc.aprenderdegrandes.datasource

import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

interface FirebaseDatabase {
    fun getCards(): ArrayList<HashMap<String, Any>>
}

class FirebaseDatabaseImp : FirebaseDatabase {
    @Suppress("UNCHECKED_CAST")
    override fun getCards(): ArrayList<HashMap<String, Any>> =
        Tasks.await(
            Firebase.database.getReference("cards").get()
        ).value as ArrayList<HashMap<String, Any>>
}