package com.gufeczek.pokecompanion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gufeczek.pokecompanion.data.local.dao.PokemonDao
import com.gufeczek.pokecompanion.data.local.dao.PokemonRemoteKeyDao
import com.gufeczek.pokecompanion.data.local.entity.PokemonEntity
import com.gufeczek.pokecompanion.data.local.entity.PokemonRemoteKey

@Database(
    entities = [PokemonEntity::class, PokemonRemoteKey::class],
    version = 7,
    exportSchema = true
)
abstract class PokeCompanionDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonRemoteKeyDao(): PokemonRemoteKeyDao
}