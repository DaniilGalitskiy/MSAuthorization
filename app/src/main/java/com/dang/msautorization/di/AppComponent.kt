package com.dang.msautorization.di

import android.content.Context
import com.dang.msautorization.App
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

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}