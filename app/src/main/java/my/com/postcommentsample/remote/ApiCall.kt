package my.com.postcommentsample.remote

import io.reactivex.Observable
import my.com.postcommentsample.model.Comment
import my.com.postcommentsample.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCall {

    @GET("posts")
    fun getPost(): Observable<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Int): Observable<List<Comment>>

}