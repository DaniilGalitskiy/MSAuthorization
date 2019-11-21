package com.dang.msautorization.view.login

import androidx.lifecycle.ViewModel
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.Screens
import com.dang.msautorization.repository.pref.SharedPrefsRepo
import com.dang.msautorization.view.ScreenLogin
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class LoginViewModel() : ViewModel(), IMainViewModel {

    @Inject
    lateinit var sharedPrefsRepo: SharedPrefsRepo

    lateinit var router : Router

    constructor(router: Router) : this() {
        this.router = router
        App.component.inject(this)
    }

    private val stateBehaviorSubject =  BehaviorSubject.createDefault(ScreenLogin.user)

    private val usernameBehaviorSubject = BehaviorSubject.createDefault("")
    private val passwordBehaviorSubject = BehaviorSubject.createDefault("")

    private val isUsernameValidBehaviorSubject = BehaviorSubject.create<Boolean>().apply {
        usernameBehaviorSubject.map { username -> username.isNotEmpty() }.subscribe(this)
    }

    private val isPasswordValidBehaviorSubject = BehaviorSubject.create<Boolean>().apply {
        passwordBehaviorSubject.map { password -> password.isNotEmpty() }.subscribe(this)
    }

    private val usernameHintColorBehaviorSubject = BehaviorSubject.createDefault(R.color.colorAccent)
    private val passwordHintColorBehaviorSubject = BehaviorSubject.createDefault(R.color.colorAccent)

    override val state get()  = stateBehaviorSubject

    override val nextButtonEnabled get() = isUsernameValidBehaviorSubject
    override val loginButtonEnabled get() = isPasswordValidBehaviorSubject


    override val usernameHintColor = usernameHintColorBehaviorSubject.distinctUntilChanged()!!
    override val passwordHintColor = passwordHintColorBehaviorSubject.distinctUntilChanged()!!

    override fun onSkipButtonClick() {
        if (sharedPrefsRepo.isHomeFlag()){
            router.backTo(Screens.HomeScreen())
        } else {
            router.newRootScreen(Screens.HomeScreen())
            sharedPrefsRepo.setHomeFlag()
        }
    }

    override fun onLoginButtonClick() {
        if (sharedPrefsRepo.isHomeFlag()){
            router.backTo(Screens.HomeScreen())
        } else {
            router.newRootScreen(Screens.HomeScreen())
            sharedPrefsRepo.setHomeFlag()
        }
    }

    override fun onBackButtonClick() {
        state.onNext(ScreenLogin.user)
    }

    override fun onNextButtonClick() {
        state.onNext(ScreenLogin.password)
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