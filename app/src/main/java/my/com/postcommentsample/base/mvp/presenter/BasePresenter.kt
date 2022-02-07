package my.com.postcommentsample.base.mvp.presenter

import io.reactivex.disposables.Disposable
import my.com.postcommentsample.base.mvp.BaseMvp
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

open class BasePresenter<V: BaseMvp.FirstView> : TiPresenter<V>(), BaseMvp.FirstPresenter {

    override fun disposableManager(disposable: Disposable) {

        val disposableHandler = RxTiPresenterDisposableHandler(this)

        if (disposable != null){ disposableHandler.manageDisposable(disposable) }
    }

    override fun getRetrofitInstance(): Retrofit {
        val BASE_URL = "https://jsonplaceholder.typicode.com"

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}