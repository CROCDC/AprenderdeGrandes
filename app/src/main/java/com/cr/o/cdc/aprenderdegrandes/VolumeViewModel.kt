package com.cr.o.cdc.aprenderdegrandes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeWithCards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.states.VolumeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolumeViewModel @Inject constructor(
    private val repository: CardsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<VolumeUIState> = MutableStateFlow(VolumeUIState.Loading)
    val uiState: StateFlow<VolumeUIState>
        get() = _uiState

    private val notViewedCards: MutableStateFlow<List<CardEntity>?> = MutableStateFlow(null)

    private val volume: Flow<Resource<VolumeWithCards?>> = repository.getVolume(
        checkNotNull(
            savedStateHandle.get<Int>(VolumeActivity.ARG_VOLUME_ID)
        )
    ).stateIn(viewModelScope, SharingStarted.Eagerly, Resource.Loading())

    init {
        viewModelScope.launch {
            volume.collect { r ->
                r.data?.cards?.firstOrNull()?.let {
                    selectCard(it, r.data?.cards ?: listOf())
                    if (r is Resource.Success) {
                        this.cancel()
                    }
                }
            }
        }
    }

    fun anotherCard() {
        viewModelScope.launch {
            val notViewedCardsValue = notViewedCards.firstOrNull()
            var anotherCard = notViewedCardsValue?.takeIf { it.isNotEmpty() }?.random()
            if (anotherCard != null) {
                while (anotherCard !in notViewedCardsValue!!) {
                    anotherCard = notViewedCardsValue.random()
                }
                if (anotherCard != null) {
                    selectCard(anotherCard, notViewedCardsValue)
                } else {
                    //TODO NO MORE CARDS
                }
            } else {
                //TODO NO MORE CARDS
            }
        }
    }

    private suspend fun viewCard(viewedCardEntity: CardEntity) {
        repository.viewCard(viewedCardEntity)
    }

    private suspend fun selectCard(cardEntity: CardEntity, notViewCard: List<CardEntity>) {
        this._uiState.value = VolumeUIState.ShowCard(
            cardEntity.copy(viewedTimes = cardEntity.viewedTimes.inc())
        )
        cardEntity.let {
            viewCard(it)
            notViewedCards.value = notViewCard.minus(cardEntity)
        }
    }
}