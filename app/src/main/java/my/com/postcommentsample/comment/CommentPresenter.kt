package my.com.postcommentsample.comment

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import my.com.postcommentsample.base.mvp.presenter.BasePresenter
import my.com.postcommentsample.model.Comment
import my.com.postcommentsample.remote.ApiCall
import my.com.postcommentsample.remote.RetrofitService
import my.com.postcommentsample.remote.helper.ApiCallState
import my.com.postcommentsample.remote.helper.Status

class CommentPresenter: BasePresenter<CommentMvp.CommentView>(), CommentMvp.CommentPresenter {

    private val TAG = "COMMENT_PRESENTER_ERROR"

    val API_ERROR = "API_ERROR"
    val NO_COMMENT = "NO_COMMENT"

    private var getCommentList: List<Comment> = ArrayList()

    private val apiState = MutableStateFlow(ApiCallState(Status.LOADING, getCommentList, ""))

    override fun loadComment(postId: Int) {

        val retrofitIns = RetrofitService.getRetrofitInstance()

        val requestApi = retrofitIns.create(ApiCall:: class.java)

//        val disposable: Disposable = requestApi.getComments(postId)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe {
//                sendToView { v ->
//                    v.showLoading("Loading Comments...")
//                }
//            }.doOnComplete {
//                sendToView { f ->
//                    f.getLoadedComment(getCommentList)
//                    f.doneLoadComment()
//                }
//            }.doOnError {
//                Log.e(TAG,"Comment Presenter Error", it)
//                sendToView { e ->
//                    e.hideLoading()
//                    e.showToast("An error occurred while retrieving comments. Please try again later.")
//                }
//            }.subscribe { getCommentList = it }
//
//        disposableManager(disposable)

        apiState.value = ApiCallState.loading()

        sendToView { v ->
            v.showLoading("Loading Comments...")
        }

        GlobalScope.launch(Dispatchers.Main) {
            getComment(requestApi, postId)
                .catch {
                    apiState.value = ApiCallState.error(it.message.toString())
                    Log.e(TAG, it.message.toString())
                    sendToView { e ->
                        e.hideLoading()
                        e.noComment(API_ERROR)
                        e.showToast("Error encountered!")
                    }
                }.collect {
                    apiState.value = ApiCallState.success(it.data)
                    if (it.data != null){
                        getCommentList = it.data
                        sendToView { c ->
                            c.doneLoadComment()
                            c.getLoadedComment(getCommentList)
                        }
                    }else{
                        Log.d(TAG,"No Comment Found!")
                        sendToView { d ->
                            d.hideLoading()
                            d.noComment(NO_COMMENT)
                        }
                    }
                }
        }

    }

    private suspend fun getComment(apiCall: ApiCall, postId: Int): Flow<ApiCallState<Comment>>{

        return flow {

            val commentList = apiCall.getComments(postId)

            emit(ApiCallState.success(commentList))

        }.flowOn(Dispatchers.IO)

    }

}