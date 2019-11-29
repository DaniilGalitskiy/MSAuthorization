package com.dang.msautorization.view.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.dang.msautorization.R
import com.dang.msautorization.Screens
import com.dang.msautorization.domain.authorization.UserAuthorizationModel
import com.dang.msautorization.domain.connect_network.NetworkConnectModel
import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.net.model.UserLogin
import com.dang.msautorization.repository.pref.SharedPrefsScreen
import com.dang.msautorization.view.ScreenLoginState
import io.reactivex.subjects.BehaviorSubject
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.terrakok.cicerone.Router


class LoginViewModel(
        state: ScreenLoginState?,
        private val router: Router,
        private val sharedPrefsScreen: SharedPrefsScreen,
        networkConnectModel: NetworkConnectModel,
        private val userAuthorizationModel: UserAuthorizationModel) : ViewModel(), ILoginViewModel {

    private val stateBehaviorSubject = BehaviorSubject.createDefault(state ?: ScreenLoginState.USER)

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

    override val connectNetworkFailedVisible =
            networkConnectModel.isNetworkConnectedObservable.map { !it }!!


    override fun onSkipButtonClick() {
        if (sharedPrefsScreen.isHome()) {
            router.backTo(Screens.HomeScreen())
        } else {
            router.newRootScreen(Screens.HomeScreen())
            sharedPrefsScreen.setHome()
        }
    }

    @SuppressLint("CheckResult")
    override fun onLoginButtonClick() {
        val credential = Credentials.basic(
                usernameBehaviorSubject.value.toString(),
                passwordBehaviorSubject.value.toString()
        )
        val call: Call<AuthorizationResult> = userAuthorizationModel.setAuthorizationLogin(
                credential,
                UserLogin(
                        "testovoe",
                        arrayOf()
                )
        )
        call.enqueue(object : Callback<AuthorizationResult> {
            override fun onResponse(call: Call<AuthorizationResult>,
                                    response: Response<AuthorizationResult>) {
                if (response.isSuccessful)
                    Log.d("TOKEN_TEST", response.body()!!.token)
                else
                    Log.d("TOKEN_TEST", "fail")
            }

            override fun onFailure(call: Call<AuthorizationResult>, t: Throwable) {
                Log.d("TOKEN_TEST", t.toString())
            }

        })
        /*if (sharedPrefsScreen.isHome()) {
            router.backTo(Screens.HomeScreen())
        } else {
            router.newRootScreen(Screens.HomeScreen())
            sharedPrefsScreen.setHome()
        }*/
    }

    override fun onBackButtonClick() {
        state.onNext(ScreenLoginState.USER)
    }

    override fun onNextButtonClick() {
        state.onNext(ScreenLoginState.PASSWORD)
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