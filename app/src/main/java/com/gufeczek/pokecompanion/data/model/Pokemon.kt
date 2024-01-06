package com.gufeczek.pokecompanion.data.model

import com.gufeczek.pokecompanion.data.local.entity.PokemonEntity

data class Pokemon(
    val id: Int,
    val name: String,
    val type: PokemonType,
    val imageUrl: String
) {
    fun asEntity() = PokemonEntity(
        id = this.id,
        name = this.name,
        type = this.type,
        imageUrl = this.imageUrl
    )
}