package com.dang.msautorization.view.login

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface ILoginViewModel {

    val state: BehaviorSubject<ScreenLoginState>

    val nextButtonEnabled: Observable<Boolean>
    val loginButtonEnabled: Observable<Boolean>

    val usernameHintColor: Observable<Int>
    val passwordHintColor: Observable<Int>

    val connectNetworkFailedVisible: Observable<Boolean>

    val loginFailedTextException: Observable<Int>


    fun onBackButtonClick()
    fun onNextButtonClick()
    fun onSkipButtonClick()
    fun onLoginButtonClick()

    fun onNextActionKeyboardClick()
    fun onGoActionKeyboardClick()

    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
}