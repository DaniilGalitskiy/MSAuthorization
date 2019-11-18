package com.dang.msautorization.view.login

import com.dang.msautorization.Screens
import com.dang.msautorization.base.BaseViewModel
import com.dang.msautorization.view.ScreenLogin
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router


class LoginViewModel(private val router: Router) : BaseViewModel(), IMainViewModel {

    override val state = BehaviorSubject.createDefault(ScreenLogin.user)

    override fun onSkipButtonClick() {
        router.navigateTo(Screens.HomeScreen())
    }

    override fun onLoginButtonClick() {
        router.navigateTo(Screens.HomeScreen())
    }

    override fun onBackButtonClick() {
        state.onNext(ScreenLogin.user)
    }

    override fun onNextButtonClick() {
        state.onNext(ScreenLogin.password)
    }
}