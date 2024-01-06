package com.gufeczek.pokecompanion.data.model

data class PagedResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T> = listOf()
)