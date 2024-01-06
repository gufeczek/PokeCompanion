package com.gufeczek.pokecompanion.ui.pokemon

import androidx.lifecycle.ViewModel
import com.gufeczek.pokecompanion.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    pokemonRepository: PokemonRepository
) : ViewModel() {
    val pokemons = pokemonRepository.getPokemons()
}