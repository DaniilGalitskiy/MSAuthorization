package com.dang.msautorization.view.home

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dang.msautorization.App
import com.dang.msautorization.R
import com.dang.msautorization.adapter.SignedUsersAdapter
import com.dang.msautorization.core.MVVMFragment
import com.dang.msautorization.utilities.OnSwipeTouchListener
import com.dang.msautorization.view.home.AccountPictureSwipeState.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : MVVMFragment() {

    @Inject
    lateinit var homeViewModel: IHomeViewModel


    private lateinit var logoutAlertDialog: AlertDialog
    private val signedUsersAdapter =
            SignedUsersAdapter(
                    { user -> homeViewModel.onAccountBottomSheetClick(user) },
                    { user -> homeViewModel.onLogoutBottomSheetClick(user) }
            )

    private var swipeAnimationState = EMPTYSWIPE

    companion object {
        const val DURATION_SWIPE = 500L
    }

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


        val adb: AlertDialog.Builder = AlertDialog.Builder(
                ContextThemeWrapper(
                        activity!!,
                        R.style.AlertDialog_AppCompat_MyDialog
                )
        )
                .setCancelable(true)
                .setPositiveButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog!!.cancel(); dialog.dismiss()
                }
                .setNegativeButton(R.string.logout) { dialog, _ ->
                    dialog!!.dismiss()
                    homeViewModel.onLogoutAlertDialogClick()
                }
                .setOnDismissListener {
                    homeViewModel.onDismissLogoutAlertDialogClick()
                }
        logoutAlertDialog = adb.create()

        accountImage.setOnTouchListener(object : OnSwipeTouchListener() {
            override fun onSwipeTop(): Boolean {
                swipeAnimationState = TOPSWIPE
                homeViewModel.onAccountPictureSwipeTop()
                return true
            }

            override fun onSwipeBottom(): Boolean {
                swipeAnimationState = BOTTOMSWIPE
                homeViewModel.onAccountPictureSwipeBottom()
                return true
            }
        })
    }

    private fun picassoAnimFadePictureStart(avatarUrl: String?) {

        val fadePictureAnimation = ScaleAnimation(
                0f, 1f, 0f, 1f,
                (accountImage.width / 2).toFloat(),
                (homeToolbar.height / 2).toFloat()
        ).apply {
            duration = DURATION_SWIPE
        }
        if (avatarUrl == null) {
            accountImage.setImageDrawable(
                    ContextCompat.getDrawable(
                            this.activity!!,
                            R.drawable.ic_account_unknown
                    )
            )
        } else {
            Picasso.get()
                    .load(avatarUrl)
                    .into(accountImage)
        }
        accountImage.startAnimation(fadePictureAnimation)
    }


    private val bottomSheetDialog by lazy {

        val sheetView: View = activity!!.layoutInflater
                .inflate(R.layout.dialog_bottom_sheet_home, null)

        sheetView.findViewById<LinearLayout>(R.id.addAccountBottomSheetLinear)
                .setOnClickListener { homeViewModel.onAddAccountBottomSheetClick() }

        val bottomHomeRecyclerView =
                sheetView.findViewById(R.id.bottomHomeRecyclerView) as RecyclerView

        bottomHomeRecyclerView.layoutManager = LinearLayoutManager(activity)
        bottomHomeRecyclerView.adapter = signedUsersAdapter

        BottomSheetDialog(activity!!).apply {
            setContentView(sheetView)
            setOnDismissListener { homeViewModel.onAccountBottomSheetDialogDismiss() }
        }
    }


    override fun subscribe(): Disposable = CompositeDisposable(
            homeViewModel.circleAvatarUrl.subscribe { avatarUrl ->
                when (swipeAnimationState) {
                    EMPTYSWIPE -> {
                        picassoAnimFadePictureStart(avatarUrl.value)
                    }
                    else -> {
                        accountImage.startAnimation(TranslateAnimation(
                                0F, 0F, 0F,
                                if (swipeAnimationState == TOPSWIPE) -homeToolbar.width.toFloat() else homeToolbar.width.toFloat()
                        ).apply {
                            duration = DURATION_SWIPE
                            setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationEnd(animation: Animation) {
                                    picassoAnimFadePictureStart(avatarUrl.value)
                                    swipeAnimationState = EMPTYSWIPE
                                }

                                override fun onAnimationRepeat(animation: Animation) {}
                                override fun onAnimationStart(animation: Animation) {}
                            })
                        })
                    }
                }
            },

            homeViewModel.isBottomAccountSheetVisible.subscribe { isVisible ->
                if (isVisible)
                    bottomSheetDialog.show() else
                    bottomSheetDialog.dismiss()
            },
            homeViewModel.signedUsers.subscribe { listUsers ->
                signedUsersAdapter.submitList(listUsers)
            },
            homeViewModel.logOutDialogUser.subscribe { userOptional ->
                if (userOptional.value != null) {
                    logoutAlertDialog.setMessage(SpannableString(
                            getString(
                                    R.string.do_you_really_want_to_log_out_from,
                                    userOptional.value.name
                            )
                    ).apply {
                        this.setSpan(
                                StyleSpan(Typeface.BOLD),
                                0,
                                this.length,
                                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                    })
                    logoutAlertDialog.show()
                } else logoutAlertDialog.dismiss()
            }
    )
}