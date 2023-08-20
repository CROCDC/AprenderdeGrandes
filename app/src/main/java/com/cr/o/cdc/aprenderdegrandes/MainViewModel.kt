package com.cr.o.cdc.aprenderdegrandes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(repository: CardsRepository) : ViewModel() {

    private val viewedCards: MutableLiveData<List<Card>> = MutableLiveData()

    private val notViewedCards: Flow<Resource<Cards?>> = repository.getCards()

    private val _showCard = MutableStateFlow<Card?>(null)
    val showCard: Flow<Card?> = _showCard

    init {
        viewModelScope.launch {
            notViewedCards.collect { r ->
                r.data?.cards?.first()?.let {
                    _showCard.value = it
                    viewedCards.value = listOf(it)
                }
            }
        }
    }

    suspend fun anotherCard() {
        val viewedCardsValue = viewedCards.value!!
        val notViewedCardsValue = notViewedCards.firstOrNull()?.data?.cards
        var anotherCard = notViewedCardsValue?.random()
        if (anotherCard != null && viewedCardsValue.size < (notViewedCardsValue?.size ?: 0)) {
            while (anotherCard in viewedCardsValue) {
                anotherCard = notViewedCardsValue?.random()
            }
            _showCard.value = anotherCard
            viewedCards.value = viewedCardsValue.plus(anotherCard!!)
        } else {
            _showCard.value = null
        }
    }
}