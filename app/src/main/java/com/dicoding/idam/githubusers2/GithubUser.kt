package com.dicoding.idam.githubusers2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    var avatar: String? = null, //avatar_url
    var login: String? = null, //login
    var username: String? = null, //name
    var followers: String? = null, //followers
    var following: String? = null, //folllowing
    var company: String? = null, //company
    var location: String? = null //company
) : Parcelable
