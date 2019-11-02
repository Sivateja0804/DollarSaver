package com.sshd.dollarsaver

import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val x= inflater.inflate(R.layout.tab_layout,null)
        tabLayout= x.findViewById<View>(R.id.tabs) as TabLayout
        viewPager=x.findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.adapter = MyAdapter(childFragmentManager)
        tabLayout.post{ tabLayout.setupWithViewPager(viewPager) }
        return x
    }

    internal inner class MyAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
        override fun getItem(position: Int): Fragment {
            when(position) {
                0 ->return DashboardFragment()
                1 ->return SuggestionsFragment()
            }
            return DashboardFragment()
        }

        override fun getCount(): Int {
          return int_items
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when(position) {
                0 ->return "Dashboard"
                1 ->return "Suggestions"
            }
            return null
        }
    }
    companion object{
        lateinit var tabLayout : TabLayout
        lateinit var viewPager : ViewPager
        var int_items=3
    }
}