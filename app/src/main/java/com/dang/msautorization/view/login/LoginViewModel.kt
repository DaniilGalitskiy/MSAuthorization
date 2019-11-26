package com.dang.msautorization.view.login

import androidx.lifecycle.ViewModel
import com.dang.msautorization.R
import com.dang.msautorization.Screens
import com.dang.msautorization.domain.connect_network.NetworkConnectModel
import com.dang.msautorization.repository.pref.SharedPrefsScreen
import com.dang.msautorization.view.ScreenLoginState
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router

class LoginViewModel(
    private val router: Router, private val sharedPrefsScreen: SharedPrefsScreen,
    networkConnectModel:
    NetworkConnectModel
) : ViewModel(), ILoginViewModel {

    private val stateBehaviorSubject = BehaviorSubject.createDefault(ScreenLoginState.user)

    private val usernameBehaviorSubject = BehaviorSubject.createDefault("")
    private val passwordBehaviorSubject = BehaviorSubject.createDefault("")

    private val isUsernameValidBehaviorSubject = BehaviorSubject.create<Boolean>().apply {
        usernameBehaviorSubject.map { username -> username.isNotEmpty() }.subscribe(this)
    }

    private val isPasswordValidBehaviorSubject = BehaviorSubject.create<Boolean>().apply {
        passwordBehaviorSubject.map { password -> password.isNotEmpty() }.subscribe(this)
    }

    private val usernameHintColorBehaviorSubject =
        BehaviorSubject.createDefault(R.color.colorAccent)
    private val passwordHintColorBehaviorSubject =
        BehaviorSubject.createDefault(R.color.colorAccent)

    override val state get() = stateBehaviorSubject

    override val nextButtonEnabled get() = isUsernameValidBehaviorSubject
    override val loginButtonEnabled get() = isPasswordValidBehaviorSubject

    override val usernameHintColor = usernameHintColorBehaviorSubject.distinctUntilChanged()!!
    override val passwordHintColor = passwordHintColorBehaviorSubject.distinctUntilChanged()!!

    override val connectNetworkVisible = networkConnectModel.isNetworkConnectedObservable.map { !it }!!


    override fun onSkipButtonClick() {
        if (sharedPrefsScreen.isHome()) {
            router.backTo(Screens.HomeScreen())
        } else {
            router.newRootScreen(Screens.HomeScreen())
            sharedPrefsScreen.setHome()
        }
    }

    override fun onLoginButtonClick() {
        if (sharedPrefsScreen.isHome()) {
            router.backTo(Screens.HomeScreen())
        } else {
            router.newRootScreen(Screens.HomeScreen())
            sharedPrefsScreen.setHome()
        }
    }

    override fun onBackButtonClick() {
        state.onNext(ScreenLoginState.user)
    }

    override fun onNextButtonClick() {
        state.onNext(ScreenLoginState.password)
    }


    override fun onNextActionKeyboardClick() {
        val isUsernameValid = isUsernameValidBehaviorSubject.value ?: return
        if (!isUsernameValid) usernameHintColorBehaviorSubject.onNext(R.color.colorRed)
        else onNextButtonClick()
    }

    override fun onGoActionKeyboardClick() {
        val isPasswordValid = isPasswordValidBehaviorSubject.value ?: return
        if (!isPasswordValid) passwordHintColorBehaviorSubject.onNext(R.color.colorRed)
        else onLoginButtonClick()
    }

    override fun onUsernameChanged(username: String) {
        usernameBehaviorSubject.onNext(username)
        usernameHintColorBehaviorSubject.onNext(R.color.colorAccent)
    }

    override fun onPasswordChanged(password: String) {
        passwordBehaviorSubject.onNext(password)
        passwordHintColorBehaviorSubject.onNext(R.color.colorAccent)
    }
}