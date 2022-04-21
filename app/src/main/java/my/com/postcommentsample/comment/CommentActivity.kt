package my.com.postcommentsample.comment

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import my.com.postcommentsample.R
import my.com.postcommentsample.adapter.CommentAdapter
import my.com.postcommentsample.base.BaseActivity
import my.com.postcommentsample.databinding.ActivityCommentBinding
import my.com.postcommentsample.model.Comment
import my.com.postcommentsample.remote.helper.NetworkChecker

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

        loadPost()

        setAdapter()

        val haveInternet = NetworkChecker.haveInternet(this)

        if (haveInternet){
            callLoadComment()
        }else{
            binding.rvComment.isVisible = false
            binding.txtNoComment.isVisible = true
            binding.btnRefreshComment.isVisible = true

            binding.txtNoComment.text = getString(R.string.no_internet)
            binding.btnRefreshComment.text = getString(R.string.refresh)

            binding.btnRefreshComment.setOnClickListener {

                showLoading("Refreshing...")
                val isConnected = NetworkChecker.haveInternet(this)

                if (isConnected){
                    callLoadComment()
                    binding.rvComment.isVisible = true
                    binding.txtNoComment.isVisible = false
                    binding.btnRefreshComment.isVisible = false
                }else{
                    hideLoading()
                }

            }
        }

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

    override fun getLoadedComment(list: List<Comment>) { adapter.setComment(list) }

    override fun doneLoadComment() { hideLoading() }

    override fun noComment(status: String) {

        binding.rvComment.isVisible = false
        binding.txtNoComment.isVisible = true
        binding.btnRefreshComment.isVisible = true

        if (status == presenter.API_ERROR){
            binding.txtNoComment.text = getString(R.string.error_occurred)
            binding.btnRefreshComment.text = getString(R.string.retry_button)
        }else{
            binding.txtNoComment.text = getString(R.string.no_comments_found)
            binding.btnRefreshComment.text = getString(R.string.refresh)
        }

        binding.btnRefreshComment.setOnClickListener {
            callLoadComment()

            binding.rvComment.isVisible = true
            binding.txtNoComment.isVisible = false
            binding.btnRefreshComment.isVisible = false
        }

    }

}