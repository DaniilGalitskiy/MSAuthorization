package com.dang.msautorization.view.login

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.core.MVVMFragment
import com.dang.msautorization.view.ScreenLogin.password
import com.dang.msautorization.view.ScreenLogin.user
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : MVVMFragment() {

    @Inject
    lateinit var mainViewModel: IMainViewModel

    companion object {
        fun getNewInstance() = LoginFragment()
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


    override fun subscribe(): Disposable = CompositeDisposable(
        mainViewModel.state.subscribe { screenLogin ->
            if (isResumed) {
                when (screenLogin!!) {
                    user -> {
                        val animateUser =
                            TranslateAnimation(-loginContainer.width.toFloat(), 0F, 0F, 0F)
                        animateUser.duration = 200
                        userTextInputLayout.startAnimation(animateUser)
                        val animatePassword =
                            TranslateAnimation(0F, loginContainer.width.toFloat(), 0F, 0F)
                        animatePassword.duration = 200
                        passwordTextInputLayout.startAnimation(animatePassword)
                        userEditText.requestFocus()
                    }
                    password -> {
                        val animateUser =
                            TranslateAnimation(0F, -loginContainer.width.toFloat(), 0F, 0F)
                        animateUser.duration = 200
                        userTextInputLayout.startAnimation(animateUser)
                        val animatePassword =
                            TranslateAnimation(loginContainer.width.toFloat(), 0F, 0F, 0F)
                        animatePassword.duration = 200
                        passwordTextInputLayout.startAnimation(animatePassword)
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

        mainViewModel.nextButtonEnabled.subscribe { nextEnabled ->
            nextButton.isEnabled = nextEnabled == true

        },

        mainViewModel.loginButtonEnabled.subscribe { loginEnabled ->
            loginButton.isEnabled = loginEnabled == true
        },

        mainViewModel.usernameHintColor.subscribe { colorHint ->
            userTextInputLayout.hintTextColor =
                ContextCompat.getColorStateList(activity!!, colorHint)
        },

        mainViewModel.passwordHintColor.subscribe { colorHint ->
            passwordTextInputLayout.hintTextColor =
                ContextCompat.getColorStateList(activity!!, colorHint)
        }
    )

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity!!.window

            window.decorView.systemUiVisibility = 0
            window.statusBarColor = ContextCompat.getColor(activity!!, R.color.blackTopPanel)
        }

        skipButton.setOnClickListener {
            mainViewModel.onSkipButtonClick()
        }
        loginButton.setOnClickListener {
            mainViewModel.onLoginButtonClick()
        }
        nextButton.setOnClickListener {
            mainViewModel.onNextButtonClick()
        }
        backButton.setOnClickListener {
            mainViewModel.onBackButtonClick()
        }


        userEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mainViewModel.onUsernameChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mainViewModel.onPasswordChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        userEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                mainViewModel.onNextActionKeyboardClick()
            }
            false
        }

        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                mainViewModel.onGoActionKeyboardClick()
            }
            false
        }
    }

}