package my.com.postcommentsample.post

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import my.com.postcommentsample.R
import my.com.postcommentsample.base.BaseActivity
import my.com.postcommentsample.comment.CommentActivity
import my.com.postcommentsample.model.Post
import my.com.postcommentsample.post.util.PostAdapter

class MainActivity : BaseActivity<PostMvp.PostView, PostPresenter>(), PostMvp.PostView {

    private var rvPost: RecyclerView? = null

    private lateinit var adapter: PostAdapter

    override fun layout(): Int { return R.layout.activity_main }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBarSetting()

        rvPost = findViewById(R.id.rvPost)

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

                intent.putExtra("userId", post.getUserId())
                intent.putExtra("id", post.getId())
                intent.putExtra("title", post.getTitle())
                intent.putExtra("body", post.getBody())

                startActivity(intent)

            }
        }
        rvPost!!.adapter = adapter
        rvPost!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL) )

    }

    override fun getPostLoaded(list: List<Post>) { adapter.setPosts(list) }

    override fun doneLoadPost() { hideLoading() }

}