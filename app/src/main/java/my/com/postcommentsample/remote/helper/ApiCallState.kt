package my.com.postcommentsample.remote.helper

data class ApiCallState<out T> (val status: Status,  val data: List<T>?, val message: String?){

    companion object{

        //if success, set status as success and data as response
        fun <T> success(data: List<T>?): ApiCallState<T>{ return ApiCallState(Status.SUCCESS, data, null) }

        //if error, set status as error and add error message as message, set data to null
        fun <T> error(msg: String): ApiCallState<T>{ return ApiCallState(Status.ERROR, null, msg) }

        //when loading, set status as loading and rest as null
        fun <T> loading(): ApiCallState<T>{ return ApiCallState(Status.LOADING, null, null) }

    }

}

enum class Status{

    SUCCESS,
    ERROR,
    LOADING

}