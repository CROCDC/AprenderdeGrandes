package com.cr.o.cdc.aprenderdegrandes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: CardsRepository) : ViewModel() {

    private val notViewedCards: MutableStateFlow<List<Card>?> = MutableStateFlow(null)

    lateinit var cards: Flow<Resource<Cards?>>

    private val _showCard = MutableStateFlow<Card?>(null)
    val showCard: Flow<Card?> = _showCard
    val notMoreCards: Flow<Boolean> = notViewedCards.mapNotNull {
        it?.isEmpty()
    }

    init {
        viewModelScope.launch {
            cards = repository.getCards().stateIn(viewModelScope)
            cards.collect { r ->
                r.data?.cards?.first()?.let {
                    _showCard.value = it
                    notViewedCards.value = r.data?.cards?.minus(it)
                }
            }
        }
    }

    fun anotherCard() {
        viewModelScope.launch {
            val notViewedCardsValue = notViewedCards.firstOrNull()
            var anotherCard = notViewedCardsValue?.random()
            if (anotherCard != null) {
                while (anotherCard !in notViewedCardsValue!!) {
                    anotherCard = notViewedCardsValue.random()
                }
                _showCard.value = anotherCard
                notViewedCards.value = notViewedCardsValue.minus(anotherCard!!)
            }
        }
    }
}