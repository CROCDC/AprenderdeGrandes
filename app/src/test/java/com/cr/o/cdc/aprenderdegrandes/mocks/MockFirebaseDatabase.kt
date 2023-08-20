package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabase

object MockFirebaseDatabase : FirebaseDatabase {
    override fun getCards(): ArrayList<HashMap<String, Any>> = arrayListOf(
        hashMapOf("text" to "pregunta", "number" to 1L, "type" to "GOLD")
    )
}