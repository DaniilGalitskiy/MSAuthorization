package com.dang.msautorization

import com.dang.msautorization.view.home.HomeFragment
import com.dang.msautorization.view.login.LoginFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class LoginScreen : SupportAppScreen() {
        override fun getFragment() = LoginFragment()
    }

    class HomeScreen : SupportAppScreen() {
        override fun getFragment() = HomeFragment()
    }
}