package my.com.postcommentsample.post

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import my.com.postcommentsample.base.mvp.presenter.BasePresenter
import my.com.postcommentsample.model.Post
import my.com.postcommentsample.remote.ApiCall
import my.com.postcommentsample.remote.RetrofitService
import my.com.postcommentsample.remote.helper.ApiCallState
import my.com.postcommentsample.remote.helper.Status

class PostPresenter: BasePresenter<PostMvp.PostView>(), PostMvp.PostPresenter {

    private val TAG = "POST_PRESENTER_ERROR"

    val API_ERROR = "API_ERROR"
    val NO_POST = "NO_POST"

    private var getPostList: List<Post> = ArrayList()

    private val apiState = MutableStateFlow(ApiCallState(Status.LOADING, getPostList, ""))

    override fun loadPost() {

        val getRetrofitIns = RetrofitService.getRetrofitInstance()

        val requestApi = getRetrofitIns.create(ApiCall:: class.java)

//        val disposables: Disposable = requestApi.getPost()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe {
//                sendToView { v ->
//                    v.showLoading("Loading Posts...")
//                }
//            }.doOnComplete {
//                sendToView { f ->
//                    f.getPostLoaded(getPostList)
//                    f.doneLoadPost()
//                }
//            }.doOnError {
//                Log.e(TAG,"Post Presenter Error", it)
//                sendToView { e ->
//                    e.hideLoading()
//                    e.showToast("An error occurred while retrieving posts. Please try again later.")
//                }
//            }.subscribe { getPostList = it }
//
//        disposableManager(disposables)

        apiState.value = ApiCallState.loading()

        sendToView { v ->
            v.showLoading("Loading Posts...")
        }

        GlobalScope.launch(Dispatchers.Main){
            getPost(requestApi)
                .catch {
                    apiState.value = ApiCallState.error(it.message.toString())
                    Log.e(TAG, it.message.toString())
                    sendToView { e ->
                        e.hideLoading()
                        e.noPost(API_ERROR)
                        e.showToast("Error encountered!")
                    }
                }
                .collect {
                    apiState.value = ApiCallState.success(it.data)
                    if (it.data != null){
                        getPostList = it.data
                        sendToView { c ->
                            c.doneLoadPost()
                            c.getPostLoaded(getPostList)
                        }
                    }else{
                        Log.d(TAG,"No Post Found!")
                        sendToView { d ->
                            d.hideLoading()
                            d.noPost(NO_POST)
                        }
                    }
                }
        }

    }

    private suspend fun getPost(apiCall: ApiCall): Flow<ApiCallState<Post>>{

        return flow {

            val postList = apiCall.getPost()

            emit(ApiCallState.success(postList))

        }.flowOn(Dispatchers.IO)

    }

}