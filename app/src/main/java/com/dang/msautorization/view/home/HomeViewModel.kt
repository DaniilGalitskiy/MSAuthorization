package com.dang.msautorization.view.home

import androidx.lifecycle.ViewModel
import com.dang.msautorization.Screens
import com.dang.msautorization.domain.user_info.UserInfoModel
import com.dang.msautorization.domain.user_info.entity.DynamicUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router

class HomeViewModel(private val router: Router, private val userInfoModel: UserInfoModel) :
        ViewModel(),
        IHomeViewModel {


    init {
        userInfoModel.run {
            updateClearAndInsertAuthorizations().subscribeOn(Schedulers.io()).subscribe({}, {})
        }
    }


    override val circleAvatarUrl = BehaviorSubject.create<String>()
            .apply {
                userInfoModel.getCurrentUser()
                        .filter { user->user.avatar != null }
                        .map { user -> user.avatar }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this)
            }

    private val logOutDialogVisibleBehaviourSubject = BehaviorSubject.create<Boolean>()

    private val signedUsersBehaviorSubject = BehaviorSubject.create<List<DynamicUser>>()
            .apply {
                userInfoModel.getAllUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this)
            }

    private val bottomAccountSheetVisibleBehaviourSubject = BehaviorSubject.create<Boolean>()


    override val isLogOutDialogShow = logOutDialogVisibleBehaviourSubject

    override val isBottomAccountSheetVisible = bottomAccountSheetVisibleBehaviourSubject

    override val signedUsers = signedUsersBehaviorSubject


    override fun onAccountPictureClick() {
        if (signedUsers.value!!.isNotEmpty())
            isBottomAccountSheetVisible.onNext(true)
        else
            router.navigateTo(Screens.LoginScreen())
    }

    override fun onAccountBottomSheetDialogDismiss() {
        isBottomAccountSheetVisible.onNext(false)
    }

    override fun onChangeAccountBottomSheetClick(dynamicUser: DynamicUser) {
        userInfoModel.updateClearAndSetCurrentUserById(dynamicUser.id)
                .subscribeOn(Schedulers.io())
                .subscribe()
        isBottomAccountSheetVisible.onNext(false)
    }

    override fun onLogoutBottomSheetClick(dynamicUser: DynamicUser) {
        userInfoModel.deleteUserById(dynamicUser)
                .subscribeOn(Schedulers.io())
                .subscribe()
        isBottomAccountSheetVisible.onNext(false)
    }

    override fun onAddAccountBottomSheetClick() {
        isBottomAccountSheetVisible.onNext(false)
        router.navigateTo(Screens.LoginScreen())
    }
}