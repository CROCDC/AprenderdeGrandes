package com.cr.o.cdc.aprenderdegrandes

import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSourceImp
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.junit.Test

class CardsDataSourceImpTest {
    private val dataSource = CardsDataSourceImp()

    @Test
     fun getCards() {
        val json = dataSource.getCards()
        JsonParser.parseString(json)
    }
}