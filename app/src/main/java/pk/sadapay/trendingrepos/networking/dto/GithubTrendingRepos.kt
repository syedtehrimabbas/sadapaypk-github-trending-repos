package pk.sadapay.trendingrepos.networking.dto


import com.google.gson.annotations.SerializedName
import pk.sadapay.trendingrepos.networking.base.BaseResponse

data class GithubTrendingRepos(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null,
    @SerializedName("items")
    var repos: List<Repo>? = null,
    @SerializedName("total_count")
    var totalCount: Int? = null
) : BaseResponse()