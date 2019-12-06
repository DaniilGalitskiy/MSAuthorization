package com.dang.msautorization.view.home

import androidx.lifecycle.ViewModel
import com.dang.msautorization.R
import com.dang.msautorization.Screens
import com.dang.msautorization.domain.authorization.UserAuthorizationModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.terrakok.cicerone.Router

class HomeViewModel(private val router: Router,
                    userAuthorizationModel: UserAuthorizationModel,
                    private var isAuthorized: Boolean) : ViewModel(),
        IHomeViewModel {

    init {
        isAuthorized = userAuthorizationModel.isSignedUser() > 0
    }


    private val circleImageViewBehaviorSubject =
            BehaviorSubject.createDefault(if (isAuthorized) R.drawable.ic_account_unknown else R.drawable.ic_account_unknown)

    private val logOutDialogVisibleBehaviourSubject = BehaviorSubject.createDefault(false)

    private val bottomAccountSheetVisibleBehaviourSubject = BehaviorSubject.createDefault(false)


    override val circleImageUser: Observable<Int>
        get() = circleImageViewBehaviorSubject

    override val logOutDialogVisible: Observable<Boolean>
        get() = logOutDialogVisibleBehaviourSubject

    override val bottomAccountSheetVisible: Observable<Boolean>
        get() = bottomAccountSheetVisibleBehaviourSubject


    override fun onAccountPictureClick() {
        if (isAuthorized)
            router.navigateTo(Screens.LoginScreen())//logOutDialogVisibleBehaviourSubject.onNext(true)
        else
            router.navigateTo(Screens.LoginScreen())
    }
}