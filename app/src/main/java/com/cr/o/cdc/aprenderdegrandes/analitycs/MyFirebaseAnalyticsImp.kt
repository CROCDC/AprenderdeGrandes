package com.cr.o.cdc.aprenderdegrandes.analitycs

import android.content.Context
import androidx.core.os.bundleOf
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.google.firebase.analytics.FirebaseAnalytics

interface MyFirebaseAnalytics {
    fun trackEvent(name: String)
    fun trackViewCard(entity: CardEntity)

    fun voteCard(name: String, id: Int)
}

class MyFirebaseAnalyticsImp(context: Context) : MyFirebaseAnalytics {
    private val firebase = FirebaseAnalytics.getInstance(context)
    override fun trackEvent(name: String) {
        firebase.logEvent(name, null)
    }

    override fun voteCard(name: String, id: Int) {
        firebase.logEvent(
            name,
            bundleOf("id" to id)
        )
    }

    override fun trackViewCard(entity: CardEntity) {
        firebase.logEvent(
            FirebaseEvent.VIEW_CARD,
            bundleOf(
                "id" to entity.id,
                "viewed_times" to entity.viewedTimes
            )
        )
    }

    companion object {
        fun create(context: Context): MyFirebaseAnalytics = MyFirebaseAnalyticsImp(context)
    }
}