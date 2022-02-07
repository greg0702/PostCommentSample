package my.com.postcommentsample.post

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.com.postcommentsample.base.mvp.presenter.BasePresenter
import my.com.postcommentsample.model.Post
import my.com.postcommentsample.post.util.PostAdapter
import my.com.postcommentsample.request.ApiCall
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PostPresenter: BasePresenter<PostMvp.PostView>(), PostMvp.PostPresenter {

    private val TAG = "PRESENTER_ERROR"

    private val disposables = CompositeDisposable()

    private var getPostList: List<Post> = ArrayList()

    override fun loadPost() {

        val getRetrofitIns = getRetrofitInstance()

        val requestApi = getRetrofitIns.create(ApiCall:: class.java)

        requestApi.getPost()
            .flatMap { posts ->

                getPostList = posts

                return@flatMap Observable.fromIterable(posts)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                sendToView { v ->
                    v.showLoading("Loading Posts...")
                }
            }.subscribe (object : Observer<Post>{
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: Post) {}

                override fun onError(e: Throwable) {
                    Log.e(TAG,"Presenter Error", e)
                    sendToView { f ->
                        f.hideLoading()
                        f.showToast(e.toString())
                    }
                }

                override fun onComplete() {
                    view?.getPostLoaded(getPostList)
                    view?.doneLoadPost()
                }

            })


    }

    private fun getRetrofitInstance(): Retrofit {

        val BASE_URL = "https://jsonplaceholder.typicode.com"

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

}