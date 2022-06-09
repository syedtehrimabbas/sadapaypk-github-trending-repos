package pk.sadapay.trendingrepos.networking.dto

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    @SerializedName("events_url")
    var ownerEventsUrl: String? = null,
    @SerializedName("followers_url")
    var followersUrl: String? = null,
    @SerializedName("following_url")
    var followingUrl: String? = null,
    @SerializedName("gists_url")
    var gistsUrl: String? = null,
    @SerializedName("gravatar_id")
    var gravatarId: String? = null,
    @SerializedName("html_url")
    var ownerHtmlUrl: String? = null,
    @SerializedName("id")
    var ownerId: Int? = null,
    @SerializedName("login")
    var login: String? = null,
    @SerializedName("node_id")
    var ownerNodeId: String? = null,
    @SerializedName("organizations_url")
    var organizationsUrl: String? = null,
    @SerializedName("received_events_url")
    var receivedEventsUrl: String? = null,
    @SerializedName("repos_url")
    var reposUrl: String? = null,
    @SerializedName("site_admin")
    var siteAdmin: Boolean? = null,
    @SerializedName("starred_url")
    var starredUrl: String? = null,
    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String? = null,
    @SerializedName("type")
    var ownerType: String? = null,
    @SerializedName("url")
    var ownerUrl: String? = null
)