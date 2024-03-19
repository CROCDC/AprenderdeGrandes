package com.cr.o.cdc.aprenderdegrandes.datasource

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface FirebaseDatabase {
    suspend fun getCards(): ArrayList<HashMap<String, Any>>
    suspend fun getCardsByVolume(id: Int): ArrayList<HashMap<String, Any>>
}

class FirebaseDatabaseImp : FirebaseDatabase {
    @Suppress("UNCHECKED_CAST")
    override suspend fun getCards(): ArrayList<HashMap<String, Any>> =
        Firebase.database.getReference("cards").get()
            .await().value as ArrayList<HashMap<String, Any>>

    @Suppress("UNCHECKED_CAST")
    override suspend fun getCardsByVolume(id: Int): ArrayList<HashMap<String, Any>> =
        ((Firebase.database.getReference("volume$id").get()
            .await().value as? HashMap<String, Any>)?.get("cards") as? ArrayList<HashMap<String, Any>>)
            ?: ArrayList()

}