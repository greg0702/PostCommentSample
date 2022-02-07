package my.com.postcommentsample.comment.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.com.postcommentsample.R
import my.com.postcommentsample.model.Comment

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private var comments: List<Comment> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder (view: View): RecyclerView.ViewHolder(view){

        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPostIdComment: TextView = view.findViewById(R.id.txtPostIdComment)
        val txtCommentBody: TextView = view.findViewById(R.id.txtCommentBody)

    }

    fun setComment(comments: List<Comment>){
        this.comments = comments
        this.comments.forEach { notifyItemInserted(this.comments.indexOf(it)) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtName.text = comments[position].getName()
        holder.txtPostIdComment.text = "Replying to Post: " + comments[position].getPostId().toString()
        holder.txtCommentBody.text = comments[position].getBody()

    }

    override fun getItemCount(): Int { return comments.size }

}