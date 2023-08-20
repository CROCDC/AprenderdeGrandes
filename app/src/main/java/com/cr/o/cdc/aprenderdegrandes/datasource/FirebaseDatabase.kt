package com.cr.o.cdc.aprenderdegrandes.datasource

import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

interface FirebaseDatabase {
    fun getCards(): Any?
}

class FirebaseDatabaseImp : FirebaseDatabase {

    override fun getCards(): Any? = Tasks.await(Firebase.database.getReference("cards").get()).value
}