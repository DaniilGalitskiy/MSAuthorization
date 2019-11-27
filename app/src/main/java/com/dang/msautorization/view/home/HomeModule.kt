package com.dang.msautorization.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class HomeModule(private val fragment: HomeFragment) {

    @Suppress("UNCHECKED_CAST")
    @Provides
    fun vm(router: Router): IHomeViewModel =
            ViewModelProviders.of(fragment, object : ViewModelProvider.Factory {

                override fun <T : ViewModel?> create(modelClass: Class<T>) =
                        HomeViewModel(router) as T

            })[HomeViewModel::class.java]

}