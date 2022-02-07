package my.com.postcommentsample.model

class Post {

    private var userId: Int = 0
    private var id: Int = 0
    private var title: String = ""
    private var body: String = ""

    fun Post(userId: Int, id: Int, title: String, body: String){

        this.userId = userId
        this.id = id
        this.title = title
        this.body = body

    }

    fun Post(){}

    fun getUserId(): Int { return userId }

    fun setUserId(userId: Int) { this.userId = userId }

    fun getId(): Int { return id }

    fun setId(id: Int) { this.id = id }

    fun getTitle(): String { return title }

    fun setTitle(title: String) { this.title = title }

    fun getBody(): String { return body }

    fun setBody(body: String) { this.body = body }

    override fun toString(): String {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}'
    }

}