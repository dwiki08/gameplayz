package com.dice.gameplayz.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dice.core.abstraction.BaseFragment
import com.dice.core.adapter.GameRecyclerViewAdapter
import com.dice.core.vo.Result
import com.dice.gameplayz.R
import com.dice.gameplayz.databinding.FragmentSearchBinding
import com.dice.gameplayz.ui.detail.DetailGameActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun getViewBinding(): FragmentSearchBinding =
        FragmentSearchBinding.inflate(layoutInflater)

    private val viewModel: SearchViewModel by viewModels()
    private val gamesAdapter by lazy { GameRecyclerViewAdapter() }
    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupSearchView()
        observeGameResult()
    }

    private fun setupView() {
        gamesAdapter.addOnClickAction { navigateToDetailGame(it.id) }
        binding.rvGames.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = gamesAdapter
        }
    }

    private fun setupSearchView() {
        val searchEditText =
            binding.searchBar.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        searchEditText.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setHintTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                delayedSearchGames(query)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                delayedSearchGames(query)
                return true
            }
        })
    }

    private fun navigateToDetailGame(gameId: Int) {
        DetailGameActivity.startActivity(requireContext(), gameId)
    }

    private fun delayedSearchGames(query: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            viewModel.searchGames(query)
        }, SEARCH_DELAY)
    }

    private fun observeGameResult() {
        binding.run {
            viewModel.gameList.observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Success -> {
                        snake.isVisible = it.data.isEmpty()
                        loading.isVisible = false
                        error.root.isVisible = false
                        rvGames.isVisible = true
                        gamesAdapter.submitList(it.data)
                    }
                    is Result.Error -> {
                        snake.isVisible = true
                        loading.isVisible = false
                        error.root.isVisible = true
                        rvGames.isVisible = false
                    }
                    is Result.Loading -> {
                        snake.isVisible = false
                        loading.isVisible = true
                        error.root.isVisible = false
                        rvGames.isVisible = false
                    }
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DELAY = 700L
    }

}