package com.dice.gameplayz.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dice.core.abstraction.BaseFragment
import com.dice.core.abstraction.PaginationScrollListener
import com.dice.gameplayz.adapter.GameRecyclerViewAdapter
import com.dice.core.utils.ViewExtensions.hide
import com.dice.core.utils.ViewExtensions.show
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
        viewModel.getGames()
        observeGamesResult()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar.root)
            supportActionBar?.title = getString(R.string.popular_games)
        }
    }

    private fun setupView() {
        gamesAdapter.addOnClickAction { navigateToDetailGame(it.id) }
        binding.rvGames.apply {
            val linearLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            layoutManager = linearLayoutManager
            adapter = gamesAdapter
            addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.getNextGames()
                }

                override fun isLastPage(): Boolean {
                    return false
                }

                override fun isLoading(): Boolean {
                    return gamesAdapter.isLoading()
                }
            })
        }
        binding.error.root.setOnClickListener {
            viewModel.getGames(true)
        }
    }

    private fun navigateToDetailGame(gameId: Int) {
        DetailGameActivity.startActivity(requireContext(), gameId)
    }

    private fun observeGamesResult() {
        viewModel.gameList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    binding.error.root.hide()
                    gamesAdapter.hideLoading()
                    gamesAdapter.addData(it.data)
                }
                is Result.Error -> {
                    binding.error.root.show()
                    gamesAdapter.hideLoading()
                }
                is Result.Loading -> {
                    binding.error.root.hide()
                    gamesAdapter.showLoading()
                }
            }
        }
    }

}