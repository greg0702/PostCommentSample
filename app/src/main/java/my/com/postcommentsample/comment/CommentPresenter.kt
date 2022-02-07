package my.com.postcommentsample.comment

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.com.postcommentsample.base.mvp.presenter.BasePresenter
import my.com.postcommentsample.model.Comment
import my.com.postcommentsample.request.ApiCall

class CommentPresenter: BasePresenter<CommentMvp.CommentView>(), CommentMvp.CommentPresenter {

    private val TAG = "COMMENT_PRESENTER_ERROR"

    private val disposables = CompositeDisposable()

    private var getCommentList: List<Comment> = ArrayList()

    override fun loadComment(postId: Int) {

        val retrofitIns = getRetrofitInstance()

        val requestApi = retrofitIns.create(ApiCall:: class.java)

        requestApi.getComments(postId)
            .flatMap { comment ->
                getCommentList = comment
                return@flatMap Observable.fromIterable(comment)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                sendToView { v ->
                    v.showLoading("Loading Comments...")
                }
            }.subscribe(object: Observer<Comment>{
                override fun onSubscribe(d: Disposable) { disposables.add(d) }

                override fun onNext(t: Comment) {}

                override fun onError(e: Throwable) {
                    Log.e(TAG,"Comment Presenter Error", e)
                    sendToView { f ->
                        f.hideLoading()
                        f.showToast("An unexpected error occurred. Please try again later.")
                    }
                }

                override fun onComplete() {
                    viewOrThrow.getLoadedComment(getCommentList)
                    viewOrThrow.doneLoadComment()
                }

            })

        disposableManager(disposables)

    }

}