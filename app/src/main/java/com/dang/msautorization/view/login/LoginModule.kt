package com.dang.msautorization.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class LoginModule(private val fragment: LoginFragment) {

    @Provides
    fun vm(router: Router): IMainViewModel = ViewModelProviders.of(fragment, object : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = LoginViewModel(router) as T

    })[LoginViewModel::class.java]
}