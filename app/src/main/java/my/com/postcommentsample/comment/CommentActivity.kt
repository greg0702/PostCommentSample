package my.com.postcommentsample.comment

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import my.com.postcommentsample.R
import my.com.postcommentsample.adapter.CommentAdapter
import my.com.postcommentsample.base.BaseActivity
import my.com.postcommentsample.databinding.ActivityCommentBinding
import my.com.postcommentsample.model.Comment

class CommentActivity : BaseActivity<CommentMvp.CommentView, CommentPresenter>(), CommentMvp.CommentView {

    private var authorId: Int = 0
    private var postId: Int = 0
    private var postTitle: String = ""
    private var postBody: String = ""

    private lateinit var adapter: CommentAdapter

    private lateinit var binding: ActivityCommentBinding

    override fun layout(): Int { return R.layout.activity_comment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBarSetting()

        authorId = intent.getIntExtra("userId", 0)
        postId = intent.getIntExtra("id",0)
        postTitle = intent.getStringExtra("title") ?: ""
        postBody = intent.getStringExtra("body") ?: ""

        setAdapter()

        callLoadComment()

    }

    private fun setAdapter(){

        adapter = CommentAdapter()
        binding.rvComment.adapter = adapter
        binding.rvComment.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

    }

    private fun loadPost() {

        binding.txtAuthor.text = authorId.toString()
        binding.txtPostId.text = postId.toString()
        binding.txtPostTitle.text = postTitle
        binding.txtPostBody.text = postBody

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