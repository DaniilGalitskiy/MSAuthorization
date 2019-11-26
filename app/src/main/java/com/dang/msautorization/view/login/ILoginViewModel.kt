package com.dang.msautorization.view.login

import com.dang.msautorization.view.ScreenLogin
import io.reactivex.Observable

interface IMainViewModel {

    val state: Observable<ScreenLogin>

    val nextButtonEnabled: Observable<Boolean>
    val loginButtonEnabled: Observable<Boolean>

    val usernameHintColor: Observable<Int>
    val passwordHintColor: Observable<Int>


    fun onBackButtonClick()
    fun onNextButtonClick()
    fun onSkipButtonClick()
    fun onLoginButtonClick()

    fun onNextActionKeyboardClick()
    fun onGoActionKeyboardClick()

    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
}