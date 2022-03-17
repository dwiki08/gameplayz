package com.dice.gameplayz.favorite

import android.content.Context
import com.dice.gameplayz.di.FavoriteModule
import com.dice.gameplayz.favorite.ui.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModule::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModule: FavoriteModule): Builder
        fun build(): FavoriteComponent
    }
}
