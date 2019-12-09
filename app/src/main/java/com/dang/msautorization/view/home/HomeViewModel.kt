package com.dang.msautorization.view.home

import androidx.lifecycle.ViewModel
import com.dang.msautorization.Screens
import com.dang.msautorization.domain.user_info.UserInfoModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router

class HomeViewModel(private val router: Router,
                    userInfoModel: UserInfoModel
) : ViewModel(),
        IHomeViewModel {


    init {
        userInfoModel.updateUsersInfo().subscribeOn(Schedulers.io()).subscribe({}, {})
    }


    private val logOutDialogVisibleBehaviourSubject = BehaviorSubject.createDefault(false)

    private val bottomAccountSheetVisibleBehaviourSubject = BehaviorSubject.createDefault(false)


    override val circleAvatarUrl = BehaviorSubject.create<String>()
            .apply {
                userInfoModel.getCurrentUser().map { user ->
                    user.avatarUrl
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this)
            }

    override val logOutDialogVisible: Observable<Boolean>
        get() = logOutDialogVisibleBehaviourSubject

    override val bottomAccountSheetVisible: Observable<Boolean>
        get() = bottomAccountSheetVisibleBehaviourSubject


    override fun onAccountPictureClick() {
        router.navigateTo(Screens.LoginScreen())
    }
}