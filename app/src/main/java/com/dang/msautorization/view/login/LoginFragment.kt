package com.dang.msautorization.view.login

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.core.MVVMFragment
import com.dang.msautorization.view.ScreenLoginState
import com.dang.msautorization.view.ScreenLoginState.PASSWORD
import com.dang.msautorization.view.ScreenLoginState.USER
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : MVVMFragment() {

    @Inject
    lateinit var loginViewModel: ILoginViewModel

    companion object {
        private const val animDuration: Long = 200
        private const val STATE_KEY = "STATE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val state: ScreenLoginState? = if (savedInstanceState?.containsKey(STATE_KEY) == true)
            ScreenLoginState.values()[savedInstanceState.getInt(STATE_KEY)]
        else null

        DaggerLoginComponent.builder()
                .appComponent(App.component)
                .loginModule(LoginModule(this, state))
                .build()
                .inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(STATE_KEY, loginViewModel.state.value!!.ordinal)
    }

    private fun init() {
        skipButton.setOnClickListener {
            loginViewModel.onSkipButtonClick()
        }
        loginButton.setOnClickListener {
            loginViewModel.onLoginButtonClick()
        }
        nextButton.setOnClickListener {
            loginViewModel.onNextButtonClick()
        }
        backButton.setOnClickListener {
            loginViewModel.onBackButtonClick()
        }

        userEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                loginViewModel.onUsernameChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                loginViewModel.onPasswordChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        userEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                loginViewModel.onNextActionKeyboardClick()
            }
            false
        }

        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                loginViewModel.onGoActionKeyboardClick()
            }
            false
        }
    }

    override fun subscribe(): Disposable = CompositeDisposable(
            loginViewModel.state.subscribe { state ->
                if (isResumed) {
                    userTextInputLayout.startAnimation(
                            TranslateAnimation(
                                    if (state == USER) -loginContainer.width.toFloat() else 0F,
                                    if (state == USER) 0F else -loginContainer.width.toFloat(),
                                    0F,
                                    0F
                            ).apply { duration = animDuration }
                    )
                    passwordTextInputLayout.startAnimation(
                            TranslateAnimation(
                                    if (state == USER) 0F else loginContainer.width.toFloat(),
                                    if (state == USER) loginContainer.width.toFloat() else 0F,
                                    0F,
                                    0F
                            ).apply { duration = animDuration }
                    )
                    if (state == USER) userEditText.requestFocus() else passwordEditText.requestFocus()
                }

                userTextInputLayout.isVisible = state == USER
                passwordTextInputLayout.isVisible = state == PASSWORD

                nextButton.isVisible = state == USER
                backButton.isVisible = state == PASSWORD
                loginButton.isVisible = state == PASSWORD
            },

            loginViewModel.nextButtonEnabled.subscribe { nextEnabled ->
                nextButton.isEnabled = nextEnabled == true
            },

            loginViewModel.loginButtonEnabled.subscribe { loginEnabled ->
                loginButton.isEnabled = loginEnabled == true
            },

            loginViewModel.usernameHintColor.subscribe { colorHint ->
                userTextInputLayout.hintTextColor =
                        ContextCompat.getColorStateList(activity!!, colorHint)
            },

            loginViewModel.passwordHintColor.subscribe { colorHint ->
                passwordTextInputLayout.hintTextColor =
                        ContextCompat.getColorStateList(activity!!, colorHint)
            },

            loginViewModel.connectNetworkFailedVisible.subscribe { connectNetworkFailedVisible ->
                noConnectTextView.isVisible = connectNetworkFailedVisible
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity!!.window.statusBarColor = ContextCompat.getColor(
                            this.activity!!,
                            if (connectNetworkFailedVisible) R.color.colorDarkRed else R.color.blackTopPanel
                    )

                    if (connectNetworkFailedVisible) activity!!.window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    else
                        activity!!.window.decorView.systemUiVisibility = 0
                }
            }
    )

}