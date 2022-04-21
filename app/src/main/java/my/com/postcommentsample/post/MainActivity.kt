package my.com.postcommentsample.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import my.com.postcommentsample.R
import my.com.postcommentsample.adapter.PostAdapter
import my.com.postcommentsample.base.BaseActivity
import my.com.postcommentsample.comment.CommentActivity
import my.com.postcommentsample.databinding.ActivityMainBinding
import my.com.postcommentsample.model.Post
import my.com.postcommentsample.remote.helper.NetworkChecker


class MainActivity : BaseActivity<PostMvp.PostView, PostPresenter>(), PostMvp.PostView {

    private val TAG = "POST_ACTIVITY_TAG"

    private lateinit var adapter: PostAdapter

    private lateinit var binding: ActivityMainBinding

    override fun layout(): Int { return R.layout.activity_main }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBarSetting()

        setAdapter()

        val haveInternet = NetworkChecker.haveInternet(this)

        if (haveInternet){
            callLoadPost()
        }else{
            binding.rvPost.isVisible = false
            binding.txtNoPost.isVisible = true
            binding.btnRefresh.isVisible = true

            binding.txtNoPost.text = getString(R.string.no_internet)
            binding.btnRefresh.text = getString(R.string.refresh)

            binding.btnRefresh.setOnClickListener {

                showLoading("Refreshing...")
                val isConnected = NetworkChecker.haveInternet(this)

                if (isConnected){
                    callLoadPost()
                    binding.rvPost.isVisible = true
                    binding.txtNoPost.isVisible = false
                    binding.btnRefresh.isVisible = false
                }else{
                    hideLoading()
                }

            }
        }

    }

    private fun actionBarSetting(){ this.title = getString(R.string.post) }

    private fun callLoadPost(){ presenter.loadPost() }

    override fun providePresenter(): PostPresenter { return PostPresenter() }

    private fun setAdapter(){

        adapter = PostAdapter { navigateToComment(it) }
        binding.rvPost.adapter = adapter
        binding.rvPost.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL) )

    }

    private fun navigateToComment(item: Post){

        Log.d(TAG, item.toString())

        val intent = Intent(this, CommentActivity::class.java)

        intent.putExtra("userId", item.userId)
        intent.putExtra("id", item.id)
        intent.putExtra("title", item.title)
        intent.putExtra("body", item.body)

        startActivity(intent)

    }

    override fun getPostLoaded(list: List<Post>) { adapter.setPosts(list) }

    override fun doneLoadPost() { hideLoading() }

    override fun noPost(status: String) {

        binding.rvPost.isVisible = false
        binding.txtNoPost.isVisible = true
        binding.btnRefresh.isVisible = true

        if (status == presenter.API_ERROR){
            binding.txtNoPost.text = getString(R.string.error_occurred)
            binding.btnRefresh.text = getString(R.string.retry_button)
        }else{
            binding.txtNoPost.text = getString(R.string.no_post_found)
            binding.btnRefresh.text = getString(R.string.refresh)
        }

        binding.btnRefresh.setOnClickListener {
            callLoadPost()

            binding.rvPost.isVisible = true
            binding.txtNoPost.isVisible = false
            binding.btnRefresh.isVisible = false
        }

    }

}