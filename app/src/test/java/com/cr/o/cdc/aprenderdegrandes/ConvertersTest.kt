package com.cr.o.cdc.aprenderdegrandes

import com.cr.o.cdc.aprenderdegrandes.database.Converters
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.google.gson.Gson
import com.google.gson.JsonParser
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ConvertersTest {
    private val converters = Converters()

    @Test
    fun jsonToCardList() {
        val list = listOf(Card("text", 1))
        val listFromJson = converters.jsonToCardList(Gson().toJson(list))
        assertEquals(list, listFromJson)
    }

    @Test
    fun cardListToJson() {
        val json = converters.cardListToJson(listOf(Card("text", 1)))
        assert(JsonParser.parseString(json).isJsonArray)
    }
}