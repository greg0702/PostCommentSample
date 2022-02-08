package my.com.postcommentsample.base.mvp.presenter

import io.reactivex.disposables.Disposable
import my.com.postcommentsample.base.mvp.BaseMvp
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler

open class BasePresenter<V: BaseMvp.FirstView> : TiPresenter<V>(), BaseMvp.FirstPresenter {

    override fun disposableManager(disposable: Disposable) {

        val disposableHandler = RxTiPresenterDisposableHandler(this)

        if (disposable != null){ disposableHandler.manageDisposable(disposable) }
    }

}