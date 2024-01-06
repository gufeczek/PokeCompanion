package com.gufeczek.pokecompanion.data.remote.source


import com.gufeczek.pokecompanion.core.PokeCompanionGlideModule
import com.gufeczek.pokecompanion.data.model.Page
import com.gufeczek.pokecompanion.data.model.PagedResponse
import com.gufeczek.pokecompanion.data.model.Pokemon
import com.gufeczek.pokecompanion.data.model.PokemonType
import com.gufeczek.pokecompanion.data.remote.client.PokemonClient
import com.gufeczek.pokecompanion.data.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val pokemonClient: PokemonClient
) {
    suspend fun getPokemonPage(offset: Int): Page<Pokemon>? {
        pokemonClient.getPokemonPageResponse(offset = offset).body()?.let { response ->
            return Page(
                count = response.count,
                nextOffset = getNextOffset(response = response),
                results = createPokemons(response.results)
            )
        } ?: return null
    }

    private suspend fun getPokemonType(id: Int): PokemonType? {
        pokemonClient.getPokemonInfo(id = id).body()?.let { pokemonInfo ->
            return pokemonInfo.getPrimaryType()
        } ?: return null
    }

    private suspend fun createPokemons(responses: List<PokemonResponse>): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            val deferredPokemons = responses.map { pokemonResponse ->
                async {
                    val pokemonId = getPokemonId(url = pokemonResponse.url)
                    val type = getPokemonType(id = pokemonId)

                    pokemonResponse.asDomain(
                        id = pokemonId,
                        type = type ?: PokemonType.NORMAL
                    )
                }
            }
            deferredPokemons.awaitAll().toMutableList()
        }
    }

    private fun getNextOffset(response: PagedResponse<PokemonResponse>): Int? {
        return response.next?.substringAfter("?offset=")?.substringBefore("&")?.toIntOrNull()
    }

    private fun getPokemonId(url: String): Int {
        return url.split("/").last { it.isNotEmpty() }.toInt()
    }

    private fun getImageUrl(pokemonId: Int): String {
        val paddedId = pokemonId.toString().padStart(3, '0')
        return "${PokeCompanionGlideModule.IMAGE_PROVIDER_BASE_URL}/$paddedId.png"
    }
}