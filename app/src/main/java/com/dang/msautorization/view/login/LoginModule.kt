package com.dang.msautorization.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dang.msautorization.domain.authorization.UserAuthorizationModel
import com.dang.msautorization.domain.connect_network.NetworkConnectModel
import com.dang.msautorization.repository.pref.SharedPrefsScreen
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class LoginModule(private val fragment: LoginFragment, private val state: ScreenLoginState?) {

    @Provides
    fun vm(router: Router,
           sharedPrefsScreen: SharedPrefsScreen,
           networkConnectModel: NetworkConnectModel,
           userAuthorizationModel: UserAuthorizationModel): ILoginViewModel =
            ViewModelProviders.of(fragment, object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>) = LoginViewModel(
                        state,
                        router,
                        sharedPrefsScreen,
                        networkConnectModel,
                        userAuthorizationModel
                ) as T

            })[LoginViewModel::class.java]
}