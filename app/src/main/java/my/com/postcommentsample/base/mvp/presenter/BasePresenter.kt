package my.com.postcommentsample.base.mvp.presenter

import io.reactivex.disposables.Disposable
import my.com.postcommentsample.base.mvp.BaseMvp
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler

class BasePresenter<V: BaseMvp.FirstView> : TiPresenter<V>(), BaseMvp.FirstPresenter {

    private val disposableHandler = RxTiPresenterDisposableHandler(this)

    override fun disposableManager(disposable: Disposable) {
        if (disposable != null){
            disposableHandler.manageDisposable(disposable)
        }
    }

}