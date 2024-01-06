package com.gufeczek.pokecompanion.data.model

data class Page<T>(
    val count: Int,
    val nextOffset: Int?,
    val results: List<T> = listOf()
)
