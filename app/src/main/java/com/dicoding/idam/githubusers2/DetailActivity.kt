package com.dicoding.idam.githubusers2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.idam.githubusers2.adapter.SectionPagerAdapter
import com.dicoding.idam.githubusers2.databinding.ActivityDetailBinding
import com.dicoding.idam.githubusers2.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionbar = supportActionBar
        actionbar?.title = "User Information"
        actionbar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = DetailViewModel()
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        val userData = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser

        detailViewModel.getDetailUser().observe(this, {
            showLoading(true)
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(it.avatar)
                    .into(ivDetailUserPhoto)

                tvDetailLogin.text = it.login
                tvDetailUsername.text = if (it.username == null) "No Name" else it.username
                tvDetailLocation.text = if (it.location == null) "No Location" else it.location
                tvDetailCompany.text = if (it.company == null) "No Company" else it.company
                tvDetailFollowers.text = if (it.followers == null) "-" else it.followers
                tvDetailFollowing.text = if (it.following == null) "-" else it.following
            }
            showLoading(false)
        })

        initTabLayout(userName = String())

        userData.login?.let {
            initTabLayout(it)
            detailViewModel.setDetailUser(it)
        }
    }

    private fun initTabLayout(userName: String) {
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = userName

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(
                TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}