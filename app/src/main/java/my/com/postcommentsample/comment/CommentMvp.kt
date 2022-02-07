package my.com.postcommentsample.comment

import my.com.postcommentsample.base.mvp.BaseMvp
import my.com.postcommentsample.model.Comment

interface CommentMvp {

    interface CommentView: BaseMvp.FirstView{

        fun getLoadedComment(list: List<Comment>)

        fun doneLoadComment()

    }

    interface CommentPresenter: BaseMvp.FirstPresenter{

        fun loadComment(postId: Int)

    }

}