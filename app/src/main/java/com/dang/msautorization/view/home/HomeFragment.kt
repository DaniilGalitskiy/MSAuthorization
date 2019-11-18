package com.dang.msautorization.view.home

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.dang.msautorization.App
import com.dang.msautorization.R
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeViewModel: IHomeViewModel

    companion object {
        fun getNewInstance(): HomeFragment {
            return HomeFragment()
        }
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
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.M)
    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity!!.window
            window.statusBarColor = resources.getColor(R.color.whiteTopPanel)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        homeToolbar.setTitle(R.string.app_name)
        activity?.menuInflater?.inflate(R.menu.fragment_home_menu, homeToolbar.menu)

        accountImage.setOnClickListener {
            homeViewModel.onAccountImageClick()
        }
    }
}