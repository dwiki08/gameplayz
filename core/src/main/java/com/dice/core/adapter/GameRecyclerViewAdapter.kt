package com.dice.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dice.core.R
import com.dice.core.databinding.ItemGameCardBinding
import com.dice.core.domain.model.Game
import com.dice.core.utils.StringExtensions.toLocalDate

class GameRecyclerViewAdapter :
    ListAdapter<Game, GameRecyclerViewAdapter.GameViewHolder>(DiffCallback) {

    private var onClickAction: ((Game) -> Unit)? = null

    private object DiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }

    fun addOnClickAction(onClickAction: ((Game) -> Unit)) {
        this.onClickAction = onClickAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = ItemGameCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position), onClickAction)
    }

    override fun getItemCount(): Int = currentList.size

    class GameViewHolder(private val binding: ItemGameCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game, onClickAction: ((Game) -> Unit)?) {
            binding.run {
                Glide.with(root.context)
                    .load(game.posterImage)
                    .apply(RequestOptions().override(imgBanner.width, imgBanner.height))
                    .into(imgBanner)
                tvGenres.text = game.genres
                tvTitle.text = game.name
                tvReleaseDate.text = game.releaseDate?.toLocalDate()
                tvRating.text = game.rating.toString()
                tvRatingTop.text = root.context.getString(
                    R.string.rating_top_format,
                    game.ratingTop.toString()
                )
                icPlatformWindows.isVisible = game.platforms.contains(Game.Platform.PC)
                icPlatformXbox.isVisible = game.platforms.contains(Game.Platform.XBOX)
                icPlatformPlaystation.isVisible =
                    game.platforms.contains(Game.Platform.PLAYSTATION)
                icPlatformNintendo.isVisible = game.platforms.contains(Game.Platform.NINTENDO)
                icPlatformMacOs.isVisible = game.platforms.contains(Game.Platform.MAC_OS)
                card.setOnClickListener { onClickAction?.invoke(game) }
            }
        }
    }
}