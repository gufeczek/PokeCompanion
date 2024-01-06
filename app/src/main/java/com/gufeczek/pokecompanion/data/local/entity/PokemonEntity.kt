package com.gufeczek.pokecompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gufeczek.pokecompanion.data.model.Pokemon
import com.gufeczek.pokecompanion.data.model.PokemonType

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: PokemonType,
    val imageUrl: String
) {
    fun asDomain() = Pokemon(
        id = this.id,
        name = this.name,
        type = this.type,
        imageUrl = this.imageUrl
    )
}