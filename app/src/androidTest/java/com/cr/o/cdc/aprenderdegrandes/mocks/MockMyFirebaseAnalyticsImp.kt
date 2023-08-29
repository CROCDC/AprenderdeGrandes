package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.analitycs.MyFirebaseAnalytics
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity

class MockMyFirebaseAnalyticsImp : MyFirebaseAnalytics {

    override fun trackEvent(name: String) {
        events.add(name to null)
    }

    override fun voteCard(name: String, id: Int) {
        events.add(name to id)
    }

    override fun trackViewCard(entity: CardEntity) {
        viewedCardEntity = entity
    }

    companion object {
        fun contains(name: String) = events.contains(name to null)

        fun contains(name: String, id: Int) = events.contains(name to id)

        val events: MutableList<Pair<String, Any?>> = mutableListOf()
        var viewedCardEntity: CardEntity? = null

        fun clean() {
            events.clear()
            viewedCardEntity = null
        }
    }
}