package pk.sadapay.trendingrepos.networking.base

import pk.sadapay.trendingrepos.networking.base.error.ApiError
import retrofit2.Response
import com.google.gson.stream.MalformedJsonException as MalformedJsonException1

const val MALFORMED_JSON_EXCEPTION_CODE = 0

abstract class BaseRepository : IRepository {

    override suspend fun <T : BaseResponse> executeSafely(call: suspend () -> Response<T>): ApiResponse<T> {
        try {
            val response: Response<T> = call.invoke()
            if (response.isSuccessful) {
                return ApiResponse.Success(response.code(), response.body()!!)
            }

            return ApiResponse.Error(detectError(response))

        } catch (exception: MalformedJsonException1) {
            return ApiResponse.Error(
                ApiError(
                    MALFORMED_JSON_EXCEPTION_CODE,
                    exception.localizedMessage ?: ""
                )
            )
        } catch (exception: Exception) {
            return ApiResponse.Error(
                ApiError(
                    getDefaultCode(),
                    exception.localizedMessage ?: ""
                )
            )
        }
    }

    private fun <T : BaseResponse> detectError(response: Response<T>): ApiError {
        return when (response.code()) {
            403 -> getApiError(mapError(NetworkErrors.Forbidden, response.code()))
            404 -> getApiError(mapError(NetworkErrors.NotFound, response.code()))
            502 -> getApiError(mapError(NetworkErrors.BadGateway, response.code()))
            504 -> getApiError(mapError(NetworkErrors.NoInternet, response.code()))
            in 400..500 -> getApiError(mapError(NetworkErrors.InternalServerError, response.code()))
            -1009 -> getApiError(mapError(NetworkErrors.NoInternet, response.code()))
            -1001 -> getApiError(mapError(NetworkErrors.RequestTimedOut, response.code()))
            else -> {
                getApiError(mapError(NetworkErrors.UnknownError(), response.code()))
            }
        }
    }


    private fun getApiError(error: ServerError): ApiError {
        return ApiError(
            error.code ?: getDefaultCode(),
            error.message ?: getDefaultMessage()
        )
    }

    private fun mapError(error: NetworkErrors, code: Int = 0): ServerError {
        return when (error) {

            is NetworkErrors.NoInternet -> ServerError(
                code,
                "It seems you're offline. Please try to reconnect and refresh to continue"
            )
            is NetworkErrors.RequestTimedOut -> ServerError(
                code,
                "It seems you're offline. Please try to reconnect and refresh to continue"
            )
            is NetworkErrors.BadGateway -> ServerError(code, "Bad Gateway")
            is NetworkErrors.NotFound -> ServerError(code, "Not Found")
            is NetworkErrors.Forbidden -> ServerError(
                code,
                "You don't have access to this information"
            )
            is NetworkErrors.InternalServerError -> ServerError(code, getDefaultMessage())
            is NetworkErrors.UnknownError -> ServerError(code, getDefaultMessage())
        }
    }

    private fun getDefaultMessage(): String {
        return "Something went wrong."
    }

    private fun getDefaultCode(): Int {
        return 0
    }


    data class ServerError(val code: Int?, val message: String?)

    sealed class NetworkErrors {
        object NoInternet : NetworkErrors()
        object RequestTimedOut : NetworkErrors()
        object BadGateway : NetworkErrors()
        object NotFound : NetworkErrors()
        object Forbidden : NetworkErrors()
        object InternalServerError : NetworkErrors()
        open class UnknownError : NetworkErrors()
    }
}