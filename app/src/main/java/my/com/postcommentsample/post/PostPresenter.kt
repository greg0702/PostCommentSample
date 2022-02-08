package my.com.postcommentsample.post

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.com.postcommentsample.base.mvp.presenter.BasePresenter
import my.com.postcommentsample.model.Post
import my.com.postcommentsample.remote.ApiCall
import my.com.postcommentsample.remote.RetrofitService

class PostPresenter: BasePresenter<PostMvp.PostView>(), PostMvp.PostPresenter {

    private val TAG = "POST_PRESENTER_ERROR"

    private var getPostList: List<Post> = ArrayList()

    override fun loadPost() {

        val getRetrofitIns = RetrofitService.getRetrofitInstance()

        val requestApi = getRetrofitIns.create(ApiCall:: class.java)

        val disposables: Disposable = requestApi.getPost()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                sendToView { v ->
                    v.showLoading("Loading Posts...")
                }
            }.doOnComplete {
                sendToView { f ->
                    f.getPostLoaded(getPostList)
                    f.doneLoadPost()
                }
            }.doOnError {
                Log.e(TAG,"Post Presenter Error", it)
                sendToView { e ->
                    e.hideLoading()
                    e.showToast("An error occurred while retrieving posts. Please try again later.")
                }
            }.subscribe { getPostList = it }

        disposableManager(disposables)

    }

}