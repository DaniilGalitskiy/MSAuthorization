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
import com.dang.msautorization.view.ScreenLoginState.password
import com.dang.msautorization.view.ScreenLoginState.user
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : MVVMFragment() {

    @Inject
    lateinit var loginViewModel: ILoginViewModel

    companion object {
        private const val animDuration: Long = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerLoginComponent.builder()
            .appComponent(App.component)
            .loginModule(LoginModule(this))
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
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                loginViewModel.onUsernameChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
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
        loginViewModel.state.subscribe { screenLogin ->
            if (isResumed) {
                when (screenLogin!!) {
                    user -> {
                        userTextInputLayout.startAnimation(
                            TranslateAnimation(
                                -loginContainer.width.toFloat(), 0F, 0F, 0F
                            ).apply {
                                duration = animDuration
                            })
                        passwordTextInputLayout.startAnimation(
                            TranslateAnimation(
                                0F, loginContainer.width.toFloat(), 0F, 0F
                            ).apply {
                                duration = animDuration
                            })
                        userEditText.requestFocus()
                    }
                    password -> {
                        userTextInputLayout.startAnimation(
                            TranslateAnimation(
                                0F, -loginContainer.width.toFloat(), 0F, 0F
                            ).apply {
                                duration = animDuration
                            })
                        passwordTextInputLayout.startAnimation(
                            TranslateAnimation(
                                loginContainer.width.toFloat(), 0F, 0F, 0F
                            ).apply {
                                duration = animDuration
                            })
                        passwordEditText.requestFocus()
                    }
                }
            }

            userTextInputLayout.isVisible = screenLogin == user
            passwordTextInputLayout.isVisible = screenLogin == password

            nextButton.isVisible = screenLogin == user
            backButton.isVisible = screenLogin == password
            loginButton.isVisible = screenLogin == password
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

        loginViewModel.connectNetworkVisible.subscribe { connectNetworkVisible ->
            noConnectTextView.isVisible = connectNetworkVisible
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when (connectNetworkVisible) {
                    true -> {
                        activity!!.window.statusBarColor =
                            ContextCompat.getColor(this.activity!!, R.color.colorDarkRed)
                        activity!!.window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }
                    false -> {
                        activity!!.window.decorView.systemUiVisibility = 0
                        activity!!.window.statusBarColor =
                            ContextCompat.getColor(activity!!, R.color.blackTopPanel)
                    }

                }
            }
        }
    )

}