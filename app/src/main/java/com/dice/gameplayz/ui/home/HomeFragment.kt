package com.dice.gameplayz.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dice.core.abstraction.BaseFragment
import com.dice.core.adapter.GameRecyclerViewAdapter
import com.dice.core.vo.Result
import com.dice.gameplayz.R
import com.dice.gameplayz.databinding.FragmentHomeBinding
import com.dice.gameplayz.ui.detail.DetailGameActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private val viewModel: HomeViewModel by viewModels()
    private val gamesAdapter by lazy { GameRecyclerViewAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
        getGames()
        observeGamesResult()
    }

    override fun onDestroyView() {
        binding.rvGames.adapter = null
        super.onDestroyView()
    }

    private fun setupToolbar() {
        binding.toolbar.root.title = getString(R.string.popular_games)
    }

    private fun setupView() {
        gamesAdapter.addOnClickAction { navigateToDetailGame(it.id) }
        binding.rvGames.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = gamesAdapter
        }
        binding.error.root.setOnClickListener {
            getGames(true)
        }
    }

    private fun navigateToDetailGame(gameId: Int) {
        DetailGameActivity.startActivity(requireContext(), gameId)
    }

    private fun getGames(refresh: Boolean = false) {
        viewModel.getBestGames(refresh)
    }

    private fun observeGamesResult() {
        viewModel.gameList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.loading.isVisible = false
                    binding.error.root.isVisible = false
                    gamesAdapter.submitList(it.data)
                }
                is Result.Error -> {
                    binding.loading.isVisible = false
                    binding.error.root.isVisible = true
                }
                is Result.Loading -> {
                    binding.loading.isVisible = true
                    binding.error.root.isVisible = false
                }
            }
        }
    }

}