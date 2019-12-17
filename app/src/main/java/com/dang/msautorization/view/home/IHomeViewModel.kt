package com.dang.msautorization.view.home

import com.dang.msautorization.domain.user_info.entity.DynamicUser
import com.dang.msautorization.utilities.Optional
import io.reactivex.Observable

interface IHomeViewModel {

    val circleAvatarUrl: Observable<Optional<String>>

    val isBottomAccountSheetVisible: Observable<Boolean>
    val signedUsers: Observable<List<DynamicUser>>

    val logOutDialogUser: Observable<Optional<DynamicUser>>



    fun onAccountPictureClick()

    fun onAccountBottomSheetClick(dynamicUser: DynamicUser)
    fun onLogoutBottomSheetClick(dynamicUser: DynamicUser)
    fun onAddAccountBottomSheetClick()

    fun onAccountBottomSheetDialogDismiss()

    fun onDismissLogoutAlertDialogClick()
    fun onLogoutAlertDialogClick()

    fun onAccountPictureSwipeTop()
    fun onAccountPictureSwipeBottom()
}