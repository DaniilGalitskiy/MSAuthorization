package com.dang.msautorization.view.home

import com.dang.msautorization.Screens
import com.dang.msautorization.base.BaseViewModel
import ru.terrakok.cicerone.Router

class HomeViewModel(private val router: Router) : BaseViewModel(), IHomeViewModel{

    override fun onAccountImageClick() {
        router.backTo(Screens.MainScreen())
    }

}