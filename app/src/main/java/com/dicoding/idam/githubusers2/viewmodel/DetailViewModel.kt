package com.dicoding.idam.githubusers2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.idam.githubusers2.GithubUser
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    private val detailUser = MutableLiveData<GithubUser>()
    private val listFollowing = MutableLiveData<ArrayList<GithubUser>>()
    private val listFollowers = MutableLiveData<ArrayList<GithubUser>>()

    fun setDetailUser(users: String) {
        val url = "https://api.github.com/users/$users"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d("ViewModel", result)
                try {
                    val responObjects = JSONObject(result)
                    val githubUser = GithubUser()
                    githubUser.login = responObjects.getString("login")
                    githubUser.username = responObjects.getString("name")
                    githubUser.avatar = responObjects.getString("avatar_url")
                    githubUser.company = responObjects.getString("company")
                    githubUser.location = responObjects.getString("location")
                    githubUser.followers = responObjects.getString("followers")
                    githubUser.following = responObjects.getString("following")
                    detailUser.postValue(githubUser)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }
    fun getDetailUser(): LiveData<GithubUser> {
        return detailUser
    }

    fun setFollowers(users: String?) {
        val listItemFollowers = ArrayList<GithubUser>()
        val url = "https://api.github.com/users/$users/followers"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d("ViewModel", result)
                    val responseObjects = JSONArray(result)
                    for (i in 0 until responseObjects.length()) {
                        val item = responseObjects.getJSONObject(i)
                        val githubUser = GithubUser()
                        githubUser.login = item.getString("login")
                        githubUser.avatar = item.getString("avatar_url")
                        listItemFollowers.add(githubUser)
                    }
                    listFollowers.postValue(listItemFollowers)
                } catch (e: Exception) {
                    Log.d("Exception", e.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error!!.message.toString())
            }
        })
    }
    fun getFollowers(): LiveData<ArrayList<GithubUser>> {
        return listFollowers
    }

    fun setFollowing(users: String?) {
        val listItemFollowing = ArrayList<GithubUser>()
        val url = "https://api.github.com/users/$users/following"
        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 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")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d("ViewModel", result)
                    val response = JSONArray(result)
                    for (i in 0 until response.length()) {
                        val item = response.getJSONObject(i)
                        val githubUser = GithubUser()
                        githubUser.login = item.getString("login")
                        githubUser.avatar = item.getString("avatar_url")
                        listItemFollowing.add(githubUser)
                    }
                    listFollowing.postValue(listItemFollowing)
                } catch (e: Exception) {
                    Log.d("Exception", e.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("Data error", error!!.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<GithubUser>> {
        return listFollowing
    }
}