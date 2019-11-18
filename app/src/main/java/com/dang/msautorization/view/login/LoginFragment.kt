package com.dang.msautorization.view.login

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.view.ScreenLogin.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_main.*
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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
    private fun subscribe(): Disposable = CompositeDisposable(
        mainViewModel.state.subscribe {
            when (it) {
                user -> {
                    userConstrainElements.visibility = View.VISIBLE
                    passwordConstrainElements.visibility = View.GONE
                }
                password -> {
                    userConstrainElements.visibility = View.GONE
                    passwordConstrainElements.visibility = View.VISIBLE
                }
            }
        }
    )

    @SuppressLint("ResourceAsColor")
    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity!!.window

            window.decorView.systemUiVisibility = 0
            @Suppress("DEPRECATION")
            window.statusBarColor = resources.getColor(R.color.blackTopPanel)
        }

        skipButton.setOnClickListener {
            mainViewModel.onSkipButtonClick()
        }
        loginButton.setOnClickListener{
            mainViewModel.onLoginButtonClick()
        }
        nextButton.setOnClickListener {
            mainViewModel.onNextButtonClick()
        }
        backButton.setOnClickListener {
            mainViewModel.onBackButtonClick()
        }
    }

}