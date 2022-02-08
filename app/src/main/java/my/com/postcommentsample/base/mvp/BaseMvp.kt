package my.com.postcommentsample.base.mvp

import io.reactivex.disposables.Disposable
import net.grandcentrix.thirtyinch.TiView

interface BaseMvp {

    interface FirstView: TiView{

        fun showLoading(msg: String)

        fun hideLoading()

        fun showToast (msg: String)

    }

    interface FirstPresenter{

        fun disposableManager(disposable: Disposable)

    }

}