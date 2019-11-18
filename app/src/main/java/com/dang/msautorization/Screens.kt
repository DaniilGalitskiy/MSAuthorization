package com.dang.msautorization

import androidx.fragment.app.Fragment
import com.dang.msautorization.view.home.HomeFragment
import com.dang.msautorization.view.login.LoginFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class LoginScreen : SupportAppScreen() {

        override fun getFragment(): Fragment {
            return LoginFragment.getNewInstance()
        }
    }

    class HomeScreen : SupportAppScreen(){

        override fun getFragment(): Fragment {
            return HomeFragment.getNewInstance()
        }
    }
}