package my.com.postcommentsample.comment

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import my.com.postcommentsample.R
import my.com.postcommentsample.base.BaseActivity
import my.com.postcommentsample.comment.util.CommentAdapter
import my.com.postcommentsample.model.Comment

class CommentActivity : BaseActivity<CommentMvp.CommentView, CommentPresenter>(), CommentMvp.CommentView {

    private var txtAuthor: TextView? = null
    private var txtPostId: TextView? = null
    private var txtPostTitle: TextView? = null
    private var txtPostBody: TextView? = null
    private var rvComment: RecyclerView? = null

    private var authorId: Int = 0
    private var postId: Int = 0
    private var postTitle: String = ""
    private var postBody: String = ""

    private lateinit var adapter: CommentAdapter

    override fun layout(): Int { return R.layout.activity_comment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        actionBarSetting()

        txtAuthor = findViewById(R.id.txtAuthor)
        txtPostId = findViewById(R.id.txtPostId)
        txtPostTitle = findViewById(R.id.txtPostTitle)
        txtPostBody = findViewById(R.id.txtPostBody)
        rvComment = findViewById(R.id.rvComment)

        authorId = intent.getIntExtra("userId", 0)
        postId = intent.getIntExtra("id",0)
        postTitle = intent.getStringExtra("title") ?: ""
        postBody = intent.getStringExtra("body") ?: ""

        setAdapter()

        callLoadComment()

    }

    private fun setAdapter(){

        adapter = CommentAdapter()
        rvComment!!.adapter = adapter
        rvComment!!.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

    }

    private fun loadPost() {

        txtAuthor?.text = authorId.toString()
        txtPostId?.text = postId.toString()
        txtPostTitle?.text = postTitle
        txtPostBody?.text = postBody

    }

    private fun callLoadComment(){ presenter.loadComment(postId) }

    private fun actionBarSetting() {

        this.title = getString(R.string.comment)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun providePresenter(): CommentPresenter { return CommentPresenter() }

    override fun getLoadedComment(list: List<Comment>) {

        loadPost()

        adapter.setComment(list)

    }

    override fun doneLoadComment() { hideLoading() }

}