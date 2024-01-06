package com.gufeczek.pokecompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gufeczek.pokecompanion.data.local.entity.PokemonRemoteKey

@Dao
interface PokemonRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PokemonRemoteKey>)

    @Query("SELECT * FROM pokemon_remote_key WHERE id = :id")
    suspend fun selectById(id: Int): PokemonRemoteKey?

    @Query("DELETE FROM pokemon_remote_key")
    suspend fun clearRemoteKeys()
}