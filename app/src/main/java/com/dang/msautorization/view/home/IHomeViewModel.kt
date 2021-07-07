package com.dang.msautorization.view.home

import com.dang.msautorization.domain.user_info.entity.DynamicUser
import com.dang.msautorization.utilities.Optional
import io.reactivex.Observable

interface IHomeViewModel {

    val signedUsers: Observable<List<DynamicUser>>

    val circleAvatarUrl: Observable<Optional<String>>

    val isBottomAccountSheetVisible: Observable<Boolean>

    val logOutDialogUser: Observable<Optional<DynamicUser>>


    fun onAccountPictureClick()
    fun onAccountPictureSwipeTop()
    fun onAccountPictureSwipeBottom()

    fun onAccountBottomSheetClick(dynamicUser: DynamicUser)
    fun onLogoutBottomSheetClick(dynamicUser: DynamicUser)
    fun onAddAccountBottomSheetClick()
    fun onAccountBottomSheetDialogDismiss()

    fun onDismissLogoutAlertDialogClick()
    fun onLogoutAlertDialogClick()

}