package com.dang.msautorization.view.home

import androidx.lifecycle.ViewModel
import com.dang.msautorization.Screens
import ru.terrakok.cicerone.Router

class HomeViewModel(private val router: Router) : ViewModel(), IHomeViewModel{

    override fun onAccountImageClick() {
        router.navigateTo(Screens.LoginScreen())
    }

}