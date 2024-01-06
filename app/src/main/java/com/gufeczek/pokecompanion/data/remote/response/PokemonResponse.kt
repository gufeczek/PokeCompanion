package com.gufeczek.pokecompanion.data.remote.response

import com.gufeczek.pokecompanion.core.PokeCompanionGlideModule
import com.gufeczek.pokecompanion.data.model.Pokemon
import com.gufeczek.pokecompanion.data.model.PokemonType
import dagger.hilt.android.plugin.util.capitalize

data class PokemonResponse(
    val name: String,
    val url: String
) {
    fun asDomain(id: Int, type: PokemonType) = Pokemon(
        id = id,
        name = this.name.capitalize(),
        type = type,
        imageUrl = getImageUrl(id)
    )

    private fun getImageUrl(pokemonId: Int): String {
        val paddedId = pokemonId.toString().padStart(3, '0')
        return "${PokeCompanionGlideModule.IMAGE_PROVIDER_BASE_URL}/$paddedId.png"
    }
}