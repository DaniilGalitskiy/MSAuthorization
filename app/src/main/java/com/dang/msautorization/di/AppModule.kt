package com.dang.msautorization.di

import com.dang.msautorization.repository.pref.DefSharedPrefsRepo
import com.dang.msautorization.repository.pref.SharedPrefsRepo
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun sharedPrefsRepo(defSharedPrefsRepo: DefSharedPrefsRepo): SharedPrefsRepo
}