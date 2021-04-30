package com.dicoding.idam.githubusers2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.idam.githubusers2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

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
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailUserViewModel = DetailUserViewModel()
        detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        val userData = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser

        detailUserViewModel.getDetailUser().observe(this, {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(it.avatar)
                    .into(userDetailAvatar)
                tvUsername.text = it.login
                tvDetailLocation.text = it.location
            }
            showLoading(false)
        })
        initTabLayout(userName = String())

        userData.login?.let {
            initTabLayout(it)
            if (it != null) {
                detailUserViewModel.setDetailUser(it)
            }}
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
            binding.progressBar.visibility = View.GONE
        }
    }
}