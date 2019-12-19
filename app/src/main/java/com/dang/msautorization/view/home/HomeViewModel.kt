package com.dang.msautorization.view.home

import androidx.lifecycle.ViewModel
import com.dang.msautorization.Screens
import com.dang.msautorization.domain.user_info.UserInfoModel
import com.dang.msautorization.domain.user_info.entity.DynamicUser
import com.dang.msautorization.utilities.Optional
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



    private val logOutDialogUserBehaviourSubject = BehaviorSubject.create<Optional<DynamicUser>>()


    private val signedUsersBehaviorSubject = BehaviorSubject.create<List<DynamicUser>>()
            .apply {
                userInfoModel.getAllUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this)
            }

    override val circleAvatarUrl = BehaviorSubject.create<Optional<String>>()
            .apply {
                signedUsersBehaviorSubject
                        .map { userList ->
                            Optional(userList.find { it.isActive }?.avatar)
                        }
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this)
            }

    private val bottomAccountSheetVisibleBehaviourSubject = BehaviorSubject.create<Boolean>()


    override val logOutDialogUser = logOutDialogUserBehaviourSubject

    override val isBottomAccountSheetVisible = bottomAccountSheetVisibleBehaviourSubject

    override val signedUsers = signedUsersBehaviorSubject


    override fun onAccountPictureClick() {
        if (signedUsers.value!!.isNotEmpty())
            isBottomAccountSheetVisible.onNext(true)
        else
            router.navigateTo(Screens.LoginScreen())
    }

    override fun onAccountBottomSheetDialogDismiss() {
        bottomAccountSheetVisibleBehaviourSubject.onNext(false)
    }

    override fun onAccountBottomSheetClick(dynamicUser: DynamicUser) {
        userInfoModel.updateClearAndSetCurrentUserById(dynamicUser.id)
                .subscribeOn(Schedulers.io())
                .subscribe()
        bottomAccountSheetVisibleBehaviourSubject.onNext(false)
    }

    override fun onAddAccountBottomSheetClick() {
        bottomAccountSheetVisibleBehaviourSubject.onNext(false)
        router.navigateTo(Screens.LoginScreen())
    }

    override fun onLogoutBottomSheetClick(dynamicUser: DynamicUser) {
        logOutDialogUserBehaviourSubject.onNext(Optional(dynamicUser))
        bottomAccountSheetVisibleBehaviourSubject.onNext(false)
    }

    override fun onDismissLogoutAlertDialogClick() {
        logOutDialogUserBehaviourSubject.onNext(Optional(null))
    }

    override fun onLogoutAlertDialogClick() {
        userInfoModel.deleteUser(logOutDialogUserBehaviourSubject.value!!.value!!)
                .subscribeOn(Schedulers.io())
                .subscribe()
        logOutDialogUserBehaviourSubject.onNext(Optional(null))
    }

    override fun onAccountPictureSwipeTop() {
        if (signedUsersBehaviorSubject.value!!.size > 1) {
            val prevIndex =
                    if (signedUsersBehaviorSubject.value!!.indexOfFirst { dynamicUser -> dynamicUser.isActive } - 1 < 0)
                        signedUsersBehaviorSubject.value!!.lastIndex
                    else signedUsersBehaviorSubject.value!!.indexOfFirst { dynamicUser -> dynamicUser.isActive } - 1
            userInfoModel.updateClearAndSetCurrentUserById(signedUsersBehaviorSubject.value!![prevIndex].id)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }
    }

    override fun onAccountPictureSwipeBottom() {
        if (signedUsersBehaviorSubject.value!!.size > 1) {
            val nextIndex =
                    if (signedUsersBehaviorSubject.value!!.indexOfFirst { dynamicUser -> dynamicUser.isActive } + 1 > signedUsersBehaviorSubject.value!!.lastIndex)
                        0
                    else signedUsersBehaviorSubject.value!!.indexOfFirst { dynamicUser -> dynamicUser.isActive } + 1
            userInfoModel.updateClearAndSetCurrentUserById(signedUsersBehaviorSubject.value!![nextIndex].id)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }
    }
}