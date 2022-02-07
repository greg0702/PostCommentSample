package my.com.postcommentsample.model

class Comment {

    private var postId: Int = 0
    private var id: Int = 0
    private var name: String = ""
    private var email: String = ""
    private var body: String = ""

    fun Comment(postId: Int, id: Int, name: String, email: String, body: String){

        this.postId = postId
        this.id = id
        this.name = name
        this.email = email
        this.body = body

    }

    fun Comment(){}

    fun getPostId(): Int {return postId}

    fun setPostId(postId: Int) {this.postId = postId}

    fun getId(): Int {return id}

    fun setId(id: Int) {this.id = id}

    fun getName(): String {return name}

    fun setName(name: String) {this.name = name}

    fun getEmail(): String {return email}

    fun setEmail(email: String) {this.email = email}

    fun getBody(): String {return body}

    fun setBody(body: String) {this.body = body}

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