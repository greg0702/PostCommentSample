package my.com.postcommentsample.post

import my.com.postcommentsample.base.mvp.BaseMvp
import my.com.postcommentsample.model.Post

interface PostMvp {

    interface PostView: BaseMvp.FirstView{

        fun getPostLoaded(list: List<Post>)

        fun doneLoadPost()

        fun noPost(status: String)

    }

    interface PostPresenter: BaseMvp.FirstPresenter{

        fun loadPost()

    }

}