package com.dicoding.idam.githubusers2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.idam.githubusers2.GithubUser
import com.dicoding.idam.githubusers2.databinding.ItemRowUserBinding

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private val githubUserData = ArrayList<GithubUser>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<GithubUser>) {
        githubUserData.clear()
        githubUserData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(githubUserData[position])
    }

    override fun getItemCount(): Int = githubUserData.size

    inner class ListViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: GithubUser) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(githubUser.avatar)
                    .into(ivUserPhoto)

                tvUsername.text = githubUser.login
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(githubUser) }
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }
}