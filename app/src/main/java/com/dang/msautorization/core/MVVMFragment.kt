package com.dang.msautorization.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.Disposable

abstract class MVVMFragment : Fragment() {

    private var disposable: Disposable? = null

    protected open fun subscribe(): Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable?.dispose()
        disposable = subscribe()
    }

    override fun onStart() {
        super.onStart()

        if (disposable?.isDisposed != false)
            disposable = subscribe()
    }

    override fun onStop() {
        disposable?.dispose()
        super.onStop()
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}