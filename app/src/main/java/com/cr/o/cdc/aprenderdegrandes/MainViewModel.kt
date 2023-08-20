package com.cr.o.cdc.aprenderdegrandes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card

class MainViewModel(repository: CardsRepository) : ViewModel() {

    private val viewedCards: MutableLiveData<List<Card>> = MutableLiveData()

    private val notViewedCards: LiveData<List<Card>> = repository.getCards()

    private val _showCard = MutableLiveData<Card?>()
    val showCard: LiveData<Card?> = _showCard

    private var isSet = false

    init {
        observeSourceLiveData()
    }

    fun anotherCard() {
        val viewedCardsValue = viewedCards.value!!
        val notViewedCardsValue = notViewedCards.value
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

    private fun observeSourceLiveData() {
        if (!isSet) {
            notViewedCards.observeForever(object : Observer<List<Card>> {
                override fun onChanged(value: List<Card>) {
                    val first = value.first()
                    _showCard.value = first
                    viewedCards.value = listOf(first)
                    notViewedCards.removeObserver(this)
                    isSet = true
                }
            })
        }
    }
}