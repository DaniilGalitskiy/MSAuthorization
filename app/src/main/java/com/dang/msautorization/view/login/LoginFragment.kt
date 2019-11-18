package com.dang.msautorization.view.login

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.view.ScreenLogin.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var mainViewModel: IMainViewModel

    private var disposable: Disposable? = null

    companion object {
        fun getNewInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerLoginComponent.builder()
            .appComponent(App.component)
            .loginModule(LoginModule(this))
            .build()
            .inject(this)

    }

    override fun onStart() {
        super.onStart()
        if (disposable?.isDisposed == true) {
            disposable = subscribe()
        }
    }

    override fun onResume() {
        super.onResume()
        disposable = subscribe()
    }

    override fun onStop() {
        disposable?.dispose()
        super.onStop()
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


    private fun subscribe(): Disposable = CompositeDisposable(
        mainViewModel.state.subscribe { screenLogin ->
            userTextInputLayout.isVisible = screenLogin == user
            passwordTextInputLayout.isVisible = screenLogin == password

            nextButton.isVisible = screenLogin == user
            backButton.isVisible = screenLogin == password
            loginButton.isVisible = screenLogin == password
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

        userEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                mainViewModel.onNextButtonClick()
            }
            false
        }

        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                mainViewModel.onLoginButtonClick()
            }
            false
        }
    }

}