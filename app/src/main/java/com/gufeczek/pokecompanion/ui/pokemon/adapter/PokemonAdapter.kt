package com.gufeczek.pokecompanion.ui.pokemon.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gufeczek.pokecompanion.R
import com.gufeczek.pokecompanion.data.local.entity.PokemonEntity
import javax.inject.Inject

class PokemonAdapter @Inject constructor() :
    PagingDataAdapter<PokemonEntity, PokemonAdapter.ItemViewHolder>(PokemonComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { pokemon ->
            holder.bind(pokemon)
        }
    }

    override fun getItemViewType(position: Int): Int = position

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val layout: LinearLayout = view.findViewById(R.id.layout)
        private val ivPokemon: ImageView = view.findViewById(R.id.ivPokemon)

        fun bind(pokemon: PokemonEntity) {
            tvName.text = pokemon.name
            layout.background = createLayoutDrawable(pokemon)
            Glide.with(itemView.context).load(pokemon.imageUrl)
                .placeholder(R.drawable.img_item_pokemon_placeholder).fitCenter().into(ivPokemon)
        }

        private fun createLayoutDrawable(pokemon: PokemonEntity): GradientDrawable {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.shape = GradientDrawable.RECTANGLE
            gradientDrawable.cornerRadius = CORNER_RADIUS
            gradientDrawable.setStroke(STROKE_WIDTH, STROKE_COLOR)
            gradientDrawable.color = ColorStateList.valueOf(
                ContextCompat.getColor(
                    ivPokemon.context, pokemon.type.getColor()
                )
            )
            return gradientDrawable
        }
    }

    object PokemonComparator : DiffUtil.ItemCallback<PokemonEntity>() {
        override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean =
            oldItem == newItem

    }

    companion object {
        const val CORNER_RADIUS = 20f
        const val STROKE_WIDTH = 6
        const val STROKE_COLOR = Color.WHITE
    }
}

