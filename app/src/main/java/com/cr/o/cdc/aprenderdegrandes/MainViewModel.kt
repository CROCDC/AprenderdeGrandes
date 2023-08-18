package com.cr.o.cdc.aprenderdegrandes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card

class MainViewModel : ViewModel() {

    private val repository = CardsRepository()

    private val cards: LiveData<List<Card>> = repository.getCards()

    private val _showCard = MutableLiveData<Card?>()
    val showCard: LiveData<Card?> = _showCard

    private var isSet = false

    init {
        observeSourceLiveData()
    }

    fun anotherCard() {
        _showCard.value = cards.value?.random()
    }

    private fun observeSourceLiveData() {
        if (!isSet) {
            cards.observeForever(object : Observer<List<Card>> {
                override fun onChanged(value: List<Card>) {
                    _showCard.value = value.first()
                    cards.removeObserver(this)
                        isSet = true
                }
            })
        }
    }
}