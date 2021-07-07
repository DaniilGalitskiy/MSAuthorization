package com.dang.msautorization.di

import android.content.Context
import com.dang.msautorization.domain.authorization.UserAuthorizationModel
import com.dang.msautorization.domain.connect_network.NetworkConnectModel
import com.dang.msautorization.domain.user_info.UserInfoModel
import com.dang.msautorization.repository.pref.SharedPrefsScreen
import com.dang.msautorization.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class])
interface AppComponent {


    fun inject(mainActivity: MainActivity)

    fun router(): Router

    fun sharedPrefs(): SharedPrefsScreen

    fun networkConnectModel(): NetworkConnectModel

    fun userAuthorizationModel(): UserAuthorizationModel

    fun userInfoModel(): UserInfoModel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}