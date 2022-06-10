package pk.sadapay.trendingrepos.data.base
import pk.sadapay.trendingrepos.data.base.error.ApiError

sealed class ApiResponse<out T : BaseResponse> {
    data class Success<out T : BaseResponse>(val code: Int, val data: T) : ApiResponse<T>()
    data class Error(val error: ApiError) : ApiResponse<Nothing>()
}