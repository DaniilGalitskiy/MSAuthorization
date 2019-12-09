package com.dang.msautorization.view.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.core.MVVMFragment
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : MVVMFragment() {

    @Inject
    lateinit var homeViewModel: IHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerHomeComponent.builder()
                .appComponent(App.component)
                .homeModule(HomeModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window: Window = activity!!.window
            window.statusBarColor = ContextCompat.getColor(activity!!, R.color.whiteTopPanel)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        homeToolbar.setTitle(R.string.app_name)
        activity!!.menuInflater.inflate(R.menu.fragment_home_menu, homeToolbar.menu)

        accountImage.setOnClickListener {
            homeViewModel.onAccountPictureClick()
        }
    }

    private fun showDialog(logoutUserName: String) {
        /*if (visible){
            val adb: AlertDialog.Builder = AlertDialog.Builder(activity!!)
                    .setCancelable(true)
                    .setMessage(getString(R.string.do_you_really_want_to_log_out_from, "tester"))
                    .setPositiveButton(getString(R.string.logout)) { dialog, _ -> dialog!!.dismiss() }
                    .setNegativeButton("cancel") { dialog, _ -> dialog!!.cancel(); dialog.dismiss() }

            val alertDialog = adb.create()
            alertDialog.show()
        }*/
    }

    override fun subscribe(): Disposable = CompositeDisposable(
            homeViewModel.circleAvatarUrl.subscribe { avatarUrl ->
                Picasso.get()
                        .load(avatarUrl)
                        .placeholder(R.drawable.ic_account_unknown)
                        .into(accountImage)
            }
    )
}