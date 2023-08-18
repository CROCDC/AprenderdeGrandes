package com.cr.o.cdc.aprenderdegrandes.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.StringReader

class CardsRepository {
    val database = Firebase.database
    val myRef = database.getReference("cards")

    fun getCards(): LiveData<List<Card>> {
        val mutableLiveData = MutableLiveData<List<Card>>()
        myRef.get().addOnCompleteListener {
            mutableLiveData.value = Gson().fromJson<List<Card>>(
                Gson().toJson(it.result.value),
                object : TypeToken<List<Card>>() {}.type
            )
        }
        return mutableLiveData
    }
}