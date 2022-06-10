package pk.sadapay.trendingrepos.data.dto
import com.google.gson.annotations.SerializedName

data class License(
    @SerializedName("key")
    var key: String? = null,
    @SerializedName("name")
    var licenseName: String? = null,
    @SerializedName("node_id")
    var licenseNodeId: String? = null,
    @SerializedName("spdx_id")
    var spdxId: String? = null,
    @SerializedName("url")
    var licenseUrl: String? = null
)