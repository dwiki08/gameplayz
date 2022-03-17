package com.dice.gameplayz.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dice.core.vo.Result
import com.dice.gameplayz.di.FavoriteModule
import com.dice.gameplayz.favorite.DaggerFavoriteComponent
import com.dice.gameplayz.favorite.R
import com.dice.gameplayz.favorite.ViewModelFactory
import com.dice.gameplayz.favorite.databinding.FragmentFavoriteBinding
import com.dice.core.adapter.GameRecyclerViewAdapter
import com.dice.gameplayz.ui.detail.DetailGameActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels { factory }
    private val gamesAdapter by lazy { GameRecyclerViewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModule::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
    }

    override fun onStart() {
        super.onStart()
        getFavoriteGames()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.title = getString(R.string.favorite_list)
        }
    }

    private fun setupView() {
        gamesAdapter.addOnClickAction { navigateToDetailGame(it.id) }
        binding.rvGames.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = gamesAdapter
        }
    }

    private fun getFavoriteGames() {
        viewModel.getFavoriteGames().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.snake.isVisible = it.data.isEmpty()
                    binding.loading.isVisible = false
                    gamesAdapter.setData(it.data.asReversed())
                }
                is Result.Error -> {
                    binding.snake.isVisible = true
                    binding.loading.isVisible = false
                }
                is Result.Loading -> {
                    binding.snake.isVisible = false
                    binding.loading.isVisible = true
                }
            }
        }
    }

    private fun navigateToDetailGame(gameId: Int) {
        DetailGameActivity.startActivity(requireContext(), gameId)
    }
}