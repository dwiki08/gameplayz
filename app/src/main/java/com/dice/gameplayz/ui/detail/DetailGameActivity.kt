package com.dice.gameplayz.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dice.core.abstraction.BaseActivity
import com.dice.core.domain.model.Game
import com.dice.core.utils.StringExtensions.toLocalDate
import com.dice.core.vo.Result
import com.dice.gameplayz.R
import com.dice.gameplayz.databinding.ActivityDetailGameBinding
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailGameActivity : BaseActivity<ActivityDetailGameBinding>() {
    override fun getViewBinding(): ActivityDetailGameBinding =
        ActivityDetailGameBinding.inflate(layoutInflater)

    private val viewModel: DetailGameViewModel by viewModels()
    private var game: Game? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
        setupToolbar()
        observeFavoriteResult()
    }

    private fun handleArguments() {
        intent?.getIntExtra(EXTRA_GAME, 0)?.let {
            getDetailGame(it)
            checkIsFavorite(it)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = game?.name
        }
    }

    private fun setupView(game: Game) {
        binding.tvTitle.text = game.name
        binding.tvOverview.text = game.description
        binding.tvDateRelease.text = game.releaseDate?.toLocalDate()
        Glide.with(this)
            .load(game.posterImage)
            .into(binding.imgBackdrop)
        Glide.with(this)
            .load(game.posterImage)
            .into(binding.imgPoster)
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            val menuFavorite = binding.toolbar.menu.findItem(R.id.menu_add_favorite)
            if (offset < -500) {
                binding.collapsingToolbar.title = game.name
                binding.collapsingToolbar.setCollapsedTitleTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                menuFavorite.isVisible = true
            } else {
                binding.collapsingToolbar.title = ""
                menuFavorite.isVisible = false
            }
        })
        binding.error.root.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.primaryDark
            )
        )
        binding.fabAddFavorite.setOnClickListener { addOrDeleteFavorite() }
    }

    private fun getDetailGame(id: Int) {
        viewModel.getDetailGame(id).observe(this) {
            when (it) {
                is Result.Success -> {
                    game = it.data
                    binding.fabAddFavorite.isVisible = true
                    binding.error.root.isVisible = false
                    binding.loading.isVisible = false
                    setupView(it.data)
                }
                is Result.Error -> {
                    binding.error.root.isVisible = true
                    binding.loading.isVisible = false
                }
                is Result.Loading -> {
                    binding.error.root.isVisible = false
                    binding.loading.isVisible = true
                }
            }
        }
    }

    private fun checkIsFavorite(id: Int) {
        viewModel.checkIsFavorite(id)
    }

    private fun observeFavoriteResult() {
        viewModel.isFavorite.observe(this) {
            isFavorite = it
            val menuFavorite = binding.toolbar.menu.findItem(R.id.menu_add_favorite)
            val icon = if (isFavorite) ContextCompat.getDrawable(
                this,
                R.drawable.ic_favorite_star_filled
            )
            else ContextCompat.getDrawable(this, R.drawable.ic_favorite_star)
            menuFavorite.icon = icon
            binding.fabAddFavorite.setImageDrawable(icon)
        }
    }

    private fun addOrDeleteFavorite() {
        game?.let { game ->
            if (isFavorite) {
                viewModel.deleteFavorite(game)
            } else {
                viewModel.addFavorite(game)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_game, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_add_favorite -> addOrDeleteFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_GAME = "extra_game_id"

        fun startActivity(context: Context, gameId: Int) {
            val intent = Intent(context, DetailGameActivity::class.java).apply {
                putExtra(EXTRA_GAME, gameId)
            }
            context.startActivity(intent)
        }
    }
}