package com.gufeczek.pokecompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_remote_key")
data class PokemonRemoteKey(
    @PrimaryKey
    val id: Int,
    val nextOffset: Int?
)
