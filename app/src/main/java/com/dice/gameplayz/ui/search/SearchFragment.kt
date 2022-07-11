package com.dice.gameplayz.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dice.core.abstraction.BaseFragment
import com.dice.gameplayz.adapter.GameRecyclerViewAdapter
import com.dice.core.utils.ViewExtensions.hide
import com.dice.core.utils.ViewExtensions.show
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
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchGames(query)
                return true
            }
        })
    }

    private fun navigateToDetailGame(gameId: Int) {
        DetailGameActivity.startActivity(requireContext(), gameId)
    }

    private fun searchGames(query: String) {
        viewModel.searchGames(query)
    }

    private fun observeGameResult() {
        viewModel.gameList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.snake.hide()
                    binding.loading.hide()
                    binding.error.root.hide()
                    binding.rvGames.show()
                    gamesAdapter.setData(it.data)
                }
                is Result.Error -> {
                    binding.snake.show()
                    binding.loading.hide()
                    binding.error.root.show()
                    binding.rvGames.hide()
                    Log.d("GamesResult", "Error: ${it.code} - ${it.errorMessage}")
                }
                is Result.Loading -> {
                    binding.snake.hide()
                    binding.loading.show()
                    binding.error.root.hide()
                    binding.rvGames.hide()
                }
            }
        }
    }

}