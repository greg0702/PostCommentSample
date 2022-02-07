package my.com.postcommentsample.post.util

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import my.com.postcommentsample.R
import my.com.postcommentsample.model.Post

class PostAdapter(
    val fn : (ViewHolder, Post) -> Unit = { _,_ -> }
    ): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var posts: List<Post> = ArrayList()

    class ViewHolder (view: View): RecyclerView.ViewHolder(view){

        val root = view
        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtBody: TextView = view.findViewById(R.id.txtBody)

    }

    fun setPosts(posts: List<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtTitle.text = posts[position].getTitle()
        holder.txtBody.text = posts[position].getBody()

        fn(holder,posts[position])

    }

    override fun getItemCount(): Int {
        return posts.size
    }

}