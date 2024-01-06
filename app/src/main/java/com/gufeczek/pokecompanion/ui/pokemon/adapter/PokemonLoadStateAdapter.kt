package com.gufeczek.pokecompanion.ui.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gufeczek.pokecompanion.R
import com.gufeczek.pokecompanion.databinding.ItemLoadingBinding

class PokemonLoadStateAdapter : LoadStateAdapter<PokemonLoadStateAdapter.LoadStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder = LoadStateViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun displayLoadStateAsItem(loadState: LoadState) = loadState is LoadState.NotLoading

    override fun getStateViewType(loadState: LoadState): Int = 0

    class LoadStateViewHolder(
        private val binding: ItemLoadingBinding
    ) : ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.NotLoading
        }

        companion object {
            fun create(parent: ViewGroup): LoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
                val binding = ItemLoadingBinding.bind(view)
                return LoadStateViewHolder(binding)
            }
        }
    }
}
