package com.dang.msautorization.view.home

import com.dang.msautorization.di.AppComponent
import com.dang.msautorization.view.ModuleScope
import dagger.Component

@ModuleScope
@Component(modules = [HomeModule::class], dependencies = [AppComponent::class])
interface HomeComponent {

    fun inject(fragment: HomeFragment)
}