package com.dice.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dice.core.R
import com.dice.core.databinding.ItemGameCardBinding
import com.dice.core.domain.model.Game
import com.dice.core.utils.StringExtensions.toLocalDate

@SuppressLint("NotifyDataSetChanged")
class GameRecyclerViewAdapter : RecyclerView.Adapter<GameRecyclerViewAdapter.GameViewHolder>() {

    private val listGame = mutableListOf<Game>()
    private var onClickAction: ((Game) -> Unit)? = null

    fun setData(listGame: List<Game>) {
        this.listGame.clear()
        this.listGame.addAll(listGame)
        notifyDataSetChanged()
    }

    fun addOnClickAction(onClickAction: ((Game) -> Unit)) {
        this.onClickAction = onClickAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = ItemGameCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(listGame[position], onClickAction)
    }

    override fun getItemCount(): Int = listGame.size

    class GameViewHolder(private val binding: ItemGameCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game, onClickAction: ((Game) -> Unit)?) {
            Glide.with(binding.root.context)
                .load(game.posterImage)
                .skipMemoryCache(true)
                .apply(RequestOptions().override(binding.imgBanner.width, binding.imgBanner.height))
                .into(binding.imgBanner)
            binding.tvGenres.text = game.genres
            binding.tvTitle.text = game.name
            binding.tvReleaseDate.text = game.releaseDate?.toLocalDate()
            binding.tvRating.text = game.rating.toString()
            binding.tvRatingTop.text = binding.root.context.getString(
                R.string.rating_top_format,
                game.ratingTop.toString()
            )
            binding.icPlatformWindows.isVisible = game.platforms.contains(Game.Platform.PC)
            binding.icPlatformXbox.isVisible = game.platforms.contains(Game.Platform.XBOX)
            binding.icPlatformPlaystation.isVisible =
                game.platforms.contains(Game.Platform.PLAYSTATION)
            binding.icPlatformNintendo.isVisible = game.platforms.contains(Game.Platform.NINTENDO)
            binding.icPlatformMacOs.isVisible = game.platforms.contains(Game.Platform.MAC_OS)
            binding.card.setOnClickListener { onClickAction?.invoke(game) }
        }
    }
}