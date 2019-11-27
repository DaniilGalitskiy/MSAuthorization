package com.dang.msautorization.view.login

import com.dang.msautorization.view.ScreenLoginState
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface ILoginViewModel {

    val state: BehaviorSubject<ScreenLoginState>

    val nextButtonEnabled: Observable<Boolean>
    val loginButtonEnabled: Observable<Boolean>

    val usernameHintColor: Observable<Int>
    val passwordHintColor: Observable<Int>

    val connectNetworkFailedVisible: Observable<Boolean>


    fun onBackButtonClick()
    fun onNextButtonClick()
    fun onSkipButtonClick()
    fun onLoginButtonClick()

    fun onNextActionKeyboardClick()
    fun onGoActionKeyboardClick()

    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
}