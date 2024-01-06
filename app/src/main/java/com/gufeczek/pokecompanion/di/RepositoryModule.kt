package com.gufeczek.pokecompanion.di

import com.gufeczek.pokecompanion.data.local.PokeCompanionDatabase
import com.gufeczek.pokecompanion.data.remote.source.PokemonRemoteDataSource
import com.gufeczek.pokecompanion.data.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSummonerRepository(
        db: PokeCompanionDatabase,
        pokemonRemoteDataSource: PokemonRemoteDataSource
    ): PokemonRepository = PokemonRepository(
        db = db,
        pokemonRemoteDataSource = pokemonRemoteDataSource
    )
}