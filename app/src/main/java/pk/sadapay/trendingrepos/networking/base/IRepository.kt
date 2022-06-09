package pk.sadapay.trendingrepos.networking.base

import retrofit2.Response

internal interface IRepository {
    suspend fun <T : BaseResponse> executeSafely(call: suspend () -> Response<T>): ApiResponse<T>
}