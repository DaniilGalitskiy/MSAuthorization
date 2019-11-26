package com.dang.msautorization.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.Screens
import com.dang.msautorization.domain.connect_network.NetworkConnectModel
import com.dang.msautorization.repository.pref.SharedPrefsScreen
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var sharedPrefsScreen: SharedPrefsScreen

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var networkConnectedModel: NetworkConnectModel

    private val navigator = object : SupportAppNavigator(this, R.id.mainContainer) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: androidx.fragment.app.Fragment?,
            nextFragment: androidx.fragment.app.Fragment?,
            fragmentTransaction: androidx.fragment.app.FragmentTransaction?
        ) {
            super.setupFragmentTransaction(
                command,
                currentFragment,
                nextFragment,
                fragmentTransaction
            )

            if (currentFragment != null) {
                when (command) {
                    is Replace -> fragmentTransaction!!.setCustomAnimations(
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    is Forward -> fragmentTransaction!!.setCustomAnimations(
                        R.anim.enter_from_bottom,
                        R.anim.exit_to_top,
                        R.anim.exit_to_bottom,
                        R.anim.enter_from_top
                    )
                }
            }
        }
    }

    private val isNextworkConnectedReceiver = object : BroadcastReceiver() {
        @Suppress("DEPRECATION")
        override fun onReceive(context: Context?, intent: Intent?) {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            networkConnectedModel.setNetworkConnected(
                connectivityManager?.activeNetworkInfo?.isConnected ?: false
            ).subscribe()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if (sharedPrefsScreen.isHome())
                router.newRootScreen(Screens.HomeScreen())
            else
                router.newRootScreen(Screens.LoginScreen())
        }

        registerReceiver(isNextworkConnectedReceiver, IntentFilter().apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
            priority = 100
        })
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        unregisterReceiver(isNextworkConnectedReceiver)
        super.onDestroy()
    }
}
