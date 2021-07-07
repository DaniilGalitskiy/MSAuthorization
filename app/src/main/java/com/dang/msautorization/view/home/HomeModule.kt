package com.dang.msautorization.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dang.msautorization.domain.user_info.UserInfoModel
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class HomeModule(private val fragment: HomeFragment) {

    @Provides
    fun vm(router: Router, userInfoModel: UserInfoModel): IHomeViewModel =
            ViewModelProviders.of(fragment, object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>) =
                        HomeViewModel(router, userInfoModel) as T

            })[HomeViewModel::class.java]
}