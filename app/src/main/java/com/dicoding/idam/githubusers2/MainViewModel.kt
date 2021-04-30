package com.dicoding.idam.githubusers2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    val listUsers = MutableLiveData<ArrayList<GithubUser>>()

    fun setUserNames(username: String) {
        val listUser = ArrayList<GithubUser>()
        val url = "https://api.github.com/search/users?q=$username"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d("TAG", result)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val githubUser = GithubUser()
                        githubUser.login = item.getString("login")
                        githubUser.avatar = item.getString("avatar_url")
                        listUser.add(githubUser)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d("Failure", error?.message.toString())
            }

        })
    }

    fun getUsers(): LiveData<ArrayList<GithubUser>> {
        return listUsers
    }
}