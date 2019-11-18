package com.dang.msautorization.view.login

import com.dang.msautorization.view.ScreenLogin
import io.reactivex.Observable

interface IMainViewModel {

    val state: Observable<ScreenLogin>

    fun onBackButtonClick()
    fun onNextButtonClick()
    fun onSkipButtonClick()
    fun onLoginButtonClick()
}