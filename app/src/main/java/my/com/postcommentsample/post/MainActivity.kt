package my.com.postcommentsample.post

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import my.com.postcommentsample.R
import my.com.postcommentsample.adapter.PostAdapter
import my.com.postcommentsample.base.BaseActivity
import my.com.postcommentsample.comment.CommentActivity
import my.com.postcommentsample.databinding.ActivityMainBinding
import my.com.postcommentsample.model.Post

class MainActivity : BaseActivity<PostMvp.PostView, PostPresenter>(), PostMvp.PostView {

    private lateinit var adapter: PostAdapter

    private lateinit var binding: ActivityMainBinding

    override fun layout(): Int { return R.layout.activity_main }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBarSetting()

        setAdapter()

        callLoadPost()

    }

    private fun actionBarSetting(){ this.title = getString(R.string.post) }

    private fun callLoadPost(){ presenter.loadPost() }

    override fun providePresenter(): PostPresenter { return PostPresenter() }

    private fun setAdapter(){

        adapter = PostAdapter { holder, post ->
            holder.root.setOnClickListener {

                val intent = Intent(this, CommentActivity::class.java)

                intent.putExtra("userId", post.userId)
                intent.putExtra("id", post.id)
                intent.putExtra("title", post.title)
                intent.putExtra("body", post.body)

                startActivity(intent)

            }
        }
        binding.rvPost.adapter = adapter
        binding.rvPost.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL) )

    }

    override fun getPostLoaded(list: List<Post>) { adapter.setPosts(list) }

    override fun doneLoadPost() { hideLoading() }

}