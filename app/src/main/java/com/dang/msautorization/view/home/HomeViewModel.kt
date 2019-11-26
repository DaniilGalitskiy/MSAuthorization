package com.dang.msautorization.view.home

import androidx.lifecycle.ViewModel
import com.dang.msautorization.Screens
import ru.terrakok.cicerone.Router

class HomeViewModel(private val router: Router) : ViewModel(), IHomeViewModel{

    override fun onAccountPictureClick() {
        router.navigateTo(Screens.LoginScreen())
    }

}