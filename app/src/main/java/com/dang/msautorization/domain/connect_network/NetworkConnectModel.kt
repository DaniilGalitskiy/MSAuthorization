package com.dang.msautorization.domain.connect_network

import io.reactivex.Completable
import io.reactivex.Observable


interface NetworkConnectModel {

    val isNetworkConnectedObservable: Observable<Boolean>

    fun setNetworkConnected(isConnected: Boolean): Completable
}