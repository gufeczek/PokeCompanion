package com.gufeczek.pokecompanion.data.remote.response

import com.gufeczek.pokecompanion.data.model.PokemonType

data class PokemonInfoResponse(
    val types: List<PokemonTypes>
) {
    fun getPrimaryType() = PokemonType.valueOf(types[0].type.name.uppercase())
}

data class PokemonTypes(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String,
    val url: String
)