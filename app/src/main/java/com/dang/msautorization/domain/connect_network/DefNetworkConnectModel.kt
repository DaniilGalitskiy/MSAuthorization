package com.dang.msautorization.domain.connect_network

import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject

class DefNetworkConnectModel : NetworkConnectModel {

    override val isNetworkConnectedObservable = BehaviorSubject.createDefault(true)

    override fun setNetworkConnected(isConnected: Boolean) = Completable.fromAction {
        isNetworkConnectedObservable.onNext(isConnected)
    }
}