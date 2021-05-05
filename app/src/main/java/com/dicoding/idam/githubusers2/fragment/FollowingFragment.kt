package com.dicoding.idam.githubusers2.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.idam.githubusers2.DetailActivity
import com.dicoding.idam.githubusers2.viewmodel.DetailViewModel
import com.dicoding.idam.githubusers2.GithubUser
import com.dicoding.idam.githubusers2.adapter.ListUserAdapter
import com.dicoding.idam.githubusers2.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowing.adapter = adapter

        return binding.root
    }

    companion object {
        private val ARG_USERNAME = "username"

        @JvmStatic
        fun newInstance(param1: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, param1)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getString(ARG_USERNAME)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        if (user != null) {
            detailViewModel.setFollowers(user)
        }

        detailViewModel.getFollowers().observe(viewLifecycleOwner) {
            adapter.setData(it)

        }
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, data)
                startActivity(intent)
            }
        })
    }
}