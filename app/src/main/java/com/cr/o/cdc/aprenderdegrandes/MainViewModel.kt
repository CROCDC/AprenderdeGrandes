package com.cr.o.cdc.aprenderdegrandes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: CardsRepository) : ViewModel() {

    private val notViewedCards: MutableStateFlow<List<CardEntity>?> = MutableStateFlow(null)

    lateinit var cards: Flow<Resource<Cards?>>

    private val _showCard = MutableStateFlow<CardEntity?>(null)
    val showCard: Flow<CardEntity?> = _showCard
    val notMoreCards: Flow<Boolean> = notViewedCards.mapNotNull {
        it?.isEmpty()
    }

    init {
        viewModelScope.launch {
            cards = repository.getCards().stateIn(viewModelScope)
            cards.collect { r ->
                r.data?.cards?.firstOrNull()?.let {
                    selectCard(it, r.data?.cards ?: listOf())
                    if (r is Resource.Success){
                        this.cancel()
                    }
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
                selectCard(anotherCard, notViewedCardsValue)
            }
        }
    }

    private suspend fun viewCard(viewedCardEntity: CardEntity) {
        repository.viewCard(viewedCardEntity)
    }

    private suspend fun selectCard(cardEntity: CardEntity?, notViewCard: List<CardEntity>) {
        this._showCard.value = cardEntity?.copy(viewedTimes = cardEntity.viewedTimes.inc())
        cardEntity?.let {
            viewCard(it)
            notViewedCards.value = notViewCard.minus(cardEntity)
        }
    }
}