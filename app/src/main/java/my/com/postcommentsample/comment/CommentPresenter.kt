package my.com.postcommentsample.comment

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.com.postcommentsample.base.mvp.presenter.BasePresenter
import my.com.postcommentsample.model.Comment
import my.com.postcommentsample.remote.ApiCall
import my.com.postcommentsample.remote.RetrofitService

class CommentPresenter: BasePresenter<CommentMvp.CommentView>(), CommentMvp.CommentPresenter {

    private val TAG = "COMMENT_PRESENTER_ERROR"

    private var getCommentList: List<Comment> = ArrayList()

    override fun loadComment(postId: Int) {

        val retrofitIns = RetrofitService.getRetrofitInstance()

        val requestApi = retrofitIns.create(ApiCall:: class.java)

        val disposable: Disposable = requestApi.getComments(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                sendToView { v ->
                    v.showLoading("Loading Comments...")
                }
            }.doOnComplete {
                sendToView { f ->
                    f.getLoadedComment(getCommentList)
                    f.doneLoadComment()
                }
            }.doOnError {
                Log.e(TAG,"Comment Presenter Error", it)
                sendToView { e ->
                    e.hideLoading()
                    e.showToast("An error occurred while retrieving comments. Please try again later.")
                }
            }.subscribe { getCommentList = it }

        disposableManager(disposable)

    }

}