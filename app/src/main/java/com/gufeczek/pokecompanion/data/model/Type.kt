package com.gufeczek.pokecompanion.data.model

import com.gufeczek.pokecompanion.R

enum class PokemonType {
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    STELLAR,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK,
    FAIRY;

    fun getColor(): Int = when (this) {
        NORMAL -> R.color.normal
        FIGHTING -> R.color.fighting
        FLYING -> R.color.flying
        POISON -> R.color.poison
        GROUND -> R.color.ground
        ROCK -> R.color.rock
        BUG -> R.color.bug
        GHOST -> R.color.ghost
        STEEL -> R.color.steel
        STELLAR -> R.color.stellar
        FIRE -> R.color.fire
        WATER -> R.color.water
        GRASS -> R.color.grass
        ELECTRIC -> R.color.electric
        PSYCHIC -> R.color.psychic
        ICE -> R.color.ice
        DRAGON -> R.color.dragon
        DARK -> R.color.dark
        FAIRY -> R.color.fairy
    }
}
