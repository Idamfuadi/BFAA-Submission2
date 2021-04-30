package com.dicoding.idam.githubusers2

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String? =null
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.newInstance(username.toString())
            1 -> fragment = FollowingFragment.newInstance(username.toString())
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}