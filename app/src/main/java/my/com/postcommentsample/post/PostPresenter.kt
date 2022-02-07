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
import my.com.postcommentsample.request.ApiCall

class PostPresenter: BasePresenter<PostMvp.PostView>(), PostMvp.PostPresenter {

    private val TAG = "POST_PRESENTER_ERROR"

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
                override fun onSubscribe(d: Disposable) { disposables.add(d) }

                override fun onNext(t: Post) {}

                override fun onError(e: Throwable) {
                    Log.e(TAG,"Post Presenter Error", e)
                    sendToView { f ->
                        f.hideLoading()
                        f.showToast("An unexpected error occurred. Please try again later.")
                    }
                }

                override fun onComplete() {
                    viewOrThrow.getPostLoaded(getPostList)
                    viewOrThrow.doneLoadPost()
                }

            })

        disposableManager(disposables)

    }

}