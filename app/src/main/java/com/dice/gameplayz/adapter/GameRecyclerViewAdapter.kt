package com.dice.gameplayz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dice.core.domain.model.Game
import com.dice.core.utils.StringExtensions.toLocalDate
import com.dice.gameplayz.R
import com.dice.gameplayz.databinding.ItemGameCardBinding
import com.dice.gameplayz.databinding.ItemGameCardLoadingBinding

@SuppressLint("NotifyDataSetChanged")
class GameRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listGame = mutableListOf<Game?>()
    private var onClickAction: ((Game) -> Unit)? = null

    fun setData(listGame: List<Game>) {
        this.listGame.clear()
        this.listGame.addAll(listGame)
        notifyDataSetChanged()
    }

    fun addData(listGame: List<Game>) {
        this.listGame.addAll(listGame)
        notifyItemRangeInserted(this.listGame.size - 1, listGame.size)
    }

    fun showLoading() {
        if (isLoading().not()) {
            this.listGame.add(null)
            notifyItemRangeInserted(this.listGame.size - 1, 1)
        }
    }

    fun hideLoading() {
        this.listGame.remove(null)
        notifyItemRemoved(this.listGame.size - 1)
    }

    fun isLoading() = listGame.contains(null)

    fun addOnClickAction(onClickAction: ((Game) -> Unit)) {
        this.onClickAction = onClickAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_GAME -> GameViewHolder(ItemGameCardBinding.inflate(inflater, parent, false))
            else -> LoadingViewHolder(ItemGameCardLoadingBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GameViewHolder -> listGame[position]?.let { holder.bind(it, onClickAction) }
            is LoadingViewHolder -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listGame[position] == null) {
            TYPE_LOADING
        } else {
            TYPE_GAME
        }
    }

    override fun getItemCount(): Int = listGame.size

    internal class GameViewHolder(private val binding: ItemGameCardBinding) :
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

    internal class LoadingViewHolder(binding: ItemGameCardLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val TYPE_GAME = 0
        private const val TYPE_LOADING = 1
    }
}