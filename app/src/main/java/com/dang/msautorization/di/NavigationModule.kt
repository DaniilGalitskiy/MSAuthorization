package com.dang.msautorization.di

import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class])
class NavigationModule {

    //cicerone
    val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }
}