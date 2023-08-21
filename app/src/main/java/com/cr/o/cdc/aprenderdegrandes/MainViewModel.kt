package com.cr.o.cdc.aprenderdegrandes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: CardsRepository) : ViewModel() {

    private val viewedCards: MutableLiveData<List<Card>> = MutableLiveData()

    lateinit var notViewedCards: Flow<Resource<Cards?>>

    private val _showCard = MutableStateFlow<Card?>(null)
    val showCard: Flow<Card?> = _showCard

    init {
        viewModelScope.launch {
            notViewedCards = repository.getCards().stateIn(viewModelScope)
            notViewedCards.collect { r ->
                r.data?.cards?.first()?.let {
                    _showCard.value = it
                    viewedCards.value = listOf(it)
                }
            }
        }
    }

    fun anotherCard() {
        viewModelScope.launch {
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
}