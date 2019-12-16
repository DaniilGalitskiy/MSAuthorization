package com.dang.msautorization.view.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.adapter.SignedUsersAdapter
import com.dang.msautorization.core.MVVMFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : MVVMFragment() {

    @Inject
    lateinit var homeViewModel: IHomeViewModel

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var alertDialog: AlertDialog
    private val signedUsersAdapter =
            SignedUsersAdapter(
                    { user -> homeViewModel.onChangeAccountBottomSheetClick(user) },
                    { user -> homeViewModel.onLogoutBottomSheetClick(user) }
            )

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

        bottomSheetDialog = BottomSheetDialog(activity!!)
        val sheetView: View = activity!!.layoutInflater
                .inflate(R.layout.dialog_bottom_sheet_home, null)
        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.setOnCancelListener { homeViewModel.onAccountBottomSheetDialogDismiss() }

        val bottomHomeRecyclerView =
                sheetView.findViewById(R.id.bottomHomeRecyclerView) as RecyclerView
        bottomHomeRecyclerView.layoutManager = LinearLayoutManager(activity)
        bottomHomeRecyclerView.itemAnimator = DefaultItemAnimator()
        bottomHomeRecyclerView.adapter = signedUsersAdapter

        sheetView.findViewById<LinearLayout>(R.id.addAccountBottomSheetLinear)
                .setOnClickListener { homeViewModel.onAddAccountBottomSheetClick() }

        val adb: AlertDialog.Builder = AlertDialog.Builder(activity!!)
                .setCancelable(true)
                .setMessage(getString(R.string.do_you_really_want_to_log_out_from, "tester"))
                .setPositiveButton(getString(R.string.logout)) { dialog, _ -> dialog!!.dismiss() }
                .setNegativeButton("cancel") { dialog, _ -> dialog!!.cancel(); dialog.dismiss() }
        alertDialog = adb.create()
    }

    override fun subscribe(): Disposable = CompositeDisposable(
            homeViewModel.circleAvatarUrl.subscribe { avatarUrl ->
                Picasso.get()
                        .load(avatarUrl)
                        .placeholder(R.drawable.ic_account_unknown)
                        .into(accountImage)
            },
            homeViewModel.isBottomAccountSheetVisible.subscribe { isVisible ->
                if (isVisible)
                    bottomSheetDialog.show() else
                    bottomSheetDialog.dismiss()
            },
            homeViewModel.signedUsers.subscribe { listUsers ->
                signedUsersAdapter.submitList(listUsers)
            }
    )
}