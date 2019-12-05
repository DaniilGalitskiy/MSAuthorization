package com.dang.msautorization.view.login

import com.dang.msautorization.di.AppComponent
import com.dang.msautorization.view.ModuleScope
import dagger.Component

@ModuleScope
@Component(modules = [(LoginModule::class)], dependencies = [(AppComponent::class)])
interface LoginComponent {
    fun inject(fragment: LoginFragment)
}