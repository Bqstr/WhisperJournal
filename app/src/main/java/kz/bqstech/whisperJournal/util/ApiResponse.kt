package kz.bqstech.whisperJournal.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed interface ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>
    data class Failure(val exception: Exception) : ApiResponse<Nothing>
}

suspend inline fun <reified T> safeAPIRequestCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    isRefreshCall: Boolean = false,
    crossinline call: suspend () -> Response<T>,
): ApiResponse<T> {

    try {
        val response = call.invoke()


        when (response.code()) {
            in 200..299 -> {
                return withContext(dispatcher) { ApiResponse.Success(response.body()!!) }
            }

            400 -> {
                return withContext(dispatcher) {
                    ApiResponse.Failure(
                        Exceptions.UserInputException(
                            response.body().toString()
                        )
                    )
                }
            }

            401 -> {
                return withContext(dispatcher) { ApiResponse.Failure(Exceptions.UnauthorizedException()) }
            }


            in 500..511 -> {

                return withContext(dispatcher) { ApiResponse.Failure(Exceptions.ServerException()) }
            }

            else -> {

                return withContext(dispatcher) { ApiResponse.Failure(Exceptions.UnknownException()) }
            }
        }

    } catch (e: Exception) {

        return withContext(dispatcher) { ApiResponse.Failure(e) }
    }
}


inline fun <T> ApiResponse<T>.handleResponse(
    onSuccess: (T) -> Unit = {},
    onFailure: (Exception) -> Unit = {},
) {
    when (this) {
        is ApiResponse.Success -> onSuccess(data)
        is ApiResponse.Failure -> onFailure(exception)
    }
}
