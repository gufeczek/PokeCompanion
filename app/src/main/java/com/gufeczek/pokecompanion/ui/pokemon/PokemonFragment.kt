package com.gufeczek.pokecompanion.ui.pokemon

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gufeczek.pokecompanion.databinding.FragmentPokemonBinding
import com.gufeczek.pokecompanion.ui.pokemon.adapter.PokemonAdapter
import com.gufeczek.pokecompanion.ui.pokemon.adapter.PokemonLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PokemonFragment : Fragment() {
    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: PokemonViewModel by viewModels()

    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var footerAdapter: PokemonLoadStateAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialisePagingAdapter()

        initialiseLayoutManager()
        initialiseRecyclerView()

        binding.rvPokemon.apply {
            this.layoutManager = gridLayoutManager
            this.adapter = pokemonAdapter.withLoadStateFooter(footerAdapter)
        }

        lifecycleScope.launch {
            viewmodel.pokemons.collectLatest { pagingData ->
                pokemonAdapter.submitData(pagingData)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialisePagingAdapter() {
        pokemonAdapter = PokemonAdapter()
        pokemonAdapter.hasStableIds()

        footerAdapter = PokemonLoadStateAdapter()
        lifecycleScope.launch {
            pokemonAdapter.loadStateFlow.collect { loadState ->
                footerAdapter.loadState = loadState.mediator?.refresh?.takeIf {
                    pokemonAdapter.itemCount > 0
                } ?: loadState.append
            }
        }
    }

    private fun initialiseLayoutManager() {
        gridLayoutManager = GridLayoutManager(context, NUMBER_OF_COLUMNS)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == pokemonAdapter.itemCount && footerAdapter.itemCount > 0) {
                    NUMBER_OF_COLUMNS
                } else {
                    1
                }
            }
        }
    }

    private fun initialiseRecyclerView() {
        binding.rvPokemon.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                view.layoutParams.apply {
                    height = parent.width / NUMBER_OF_COLUMNS
                }
                with(outRect) {
                    top = ITEM_MARGIN
                    bottom = ITEM_MARGIN
                    left = ITEM_MARGIN
                    right = ITEM_MARGIN
                }
            }
        })
    }

    companion object {
        const val NUMBER_OF_COLUMNS = 3
        const val ITEM_MARGIN = 10
    }
}
