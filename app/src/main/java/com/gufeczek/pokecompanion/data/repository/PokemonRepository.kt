package com.gufeczek.pokecompanion.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gufeczek.pokecompanion.data.local.PokeCompanionDatabase
import com.gufeczek.pokecompanion.data.local.entity.PokemonEntity
import com.gufeczek.pokecompanion.data.paging.PokemonRemoteMediator
import com.gufeczek.pokecompanion.data.remote.source.PokemonRemoteDataSource
import com.gufeczek.pokecompanion.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokemonRepository @Inject constructor(
    private val db: PokeCompanionDatabase,
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
) {
    fun getPokemons(): Flow<PagingData<PokemonEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
            ),
            remoteMediator = PokemonRemoteMediator(
                db = db,
                pokemonRemoteDataSource = pokemonRemoteDataSource
            ),
            pagingSourceFactory = {
                db.pokemonDao().pagingSource()
            }
        ).flow
    }
}