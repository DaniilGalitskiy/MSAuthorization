package com.dang.msautorization.view.home

import io.reactivex.Observable

interface IHomeViewModel {

    val logOutDialogVisible: Observable<Boolean>
    val bottomAccountSheetVisible: Observable<Boolean>
    val circleAvatarUrl: Observable<String>


    fun onAccountPictureClick()
}