package my.com.postcommentsample.request

import io.reactivex.Observable
import my.com.postcommentsample.model.Post
import retrofit2.http.GET

interface ApiCall {

    @GET("posts")
    fun getPost(): Observable<List<Post>>

}