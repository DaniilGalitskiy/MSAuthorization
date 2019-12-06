package com.dang.msautorization.view.home

import io.reactivex.Observable

interface IHomeViewModel {

    val circleImageUser: Observable<Int>
    val logOutDialogVisible: Observable<Boolean>
    val bottomAccountSheetVisible: Observable<Boolean>


    fun onAccountPictureClick()
}