package com.cr.o.cdc.aprenderdegrandes.repos.model

data class Card(
    val text: String,
    val type: Type,
    val number: Int
)

enum class Type {
    GOLD, CUP, SWORD, COARSE
}