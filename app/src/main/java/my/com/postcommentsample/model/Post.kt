package my.com.postcommentsample.model

data class Post(

    var userId: Int = 0,
    var id: Int = 0,
    var title: String = "",
    var body: String = ""

) {

    override fun toString(): String {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}'
    }

}