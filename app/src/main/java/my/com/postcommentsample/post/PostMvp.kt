package my.com.postcommentsample.post

import my.com.postcommentsample.base.mvp.BaseMvp
import my.com.postcommentsample.model.Post
import my.com.postcommentsample.post.util.PostAdapter

interface PostMvp {

    interface PostView: BaseMvp.FirstView{

        fun getPostLoaded(list: List<Post>)

        fun doneLoadPost()

    }

    interface PostPresenter: BaseMvp.FirstPresenter{

        fun loadPost()

    }

}