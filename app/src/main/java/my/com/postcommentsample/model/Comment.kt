package my.com.postcommentsample.model

data class Comment(

    var postId: Int = 0,
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var body: String = ""

){

    override fun toString(): String {
        return "Comment{" +
                "postId=" + postId +
                ", id=" + id +
                ", name=" + name +
                ", email=" + email +
                ", body='" + body + '\'' +
                '}'
    }

}