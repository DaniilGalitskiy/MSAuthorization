package com.dang.msautorization.view.home

import com.dang.msautorization.domain.user_info.entity.DynamicUser
import io.reactivex.Observable

interface IHomeViewModel {

    val circleAvatarUrl: Observable<String>

    val isBottomAccountSheetVisible: Observable<Boolean>
    val signedUsers: Observable<List<DynamicUser>>

    val isLogOutDialogShow: Observable<Boolean>



    fun onAccountPictureClick()
    fun onChangeAccountBottomSheetClick(dynamicUser: DynamicUser)
    fun onLogoutBottomSheetClick(dynamicUser: DynamicUser)
    fun onAccountBottomSheetDialogDismiss()
    fun onAddAccountBottomSheetClick()
}