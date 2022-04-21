package my.com.postcommentsample.remote

import io.reactivex.Observable
import my.com.postcommentsample.model.Comment
import my.com.postcommentsample.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCall {

    @GET("posts")
    suspend fun getPost(): List<Post>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): List<Comment>

}