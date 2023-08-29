package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.analitycs.MyFirebaseAnalytics
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity

class MockMyFirebaseAnalyticsImp : MyFirebaseAnalytics {

    override fun trackEvent(name: String) {
        events.add(name)
    }

    override fun trackViewCard(entity: CardEntity) {
        viewedCardEntity = entity
    }

    companion object {
        val events: MutableList<String> = mutableListOf()
        var viewedCardEntity: CardEntity? = null

        fun clean() {
            events.clear()
            viewedCardEntity = null
        }
    }
}