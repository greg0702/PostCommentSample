package my.com.postcommentsample.base.mvp

import io.reactivex.disposables.Disposable
import net.grandcentrix.thirtyinch.TiView
import retrofit2.Retrofit

interface BaseMvp {

    interface FirstView: TiView{

        fun showLoading(msg: String)

        fun hideLoading()

        fun showToast (msg: String)

    }

    interface FirstPresenter{

        fun disposableManager(disposable: Disposable)

        fun getRetrofitInstance(): Retrofit

    }

}