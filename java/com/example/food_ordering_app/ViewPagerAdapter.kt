package com.example.food_ordering_app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter( fm: FragmentManager) : FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Kitchen()
            1 -> MyOrders()
            2 -> Profile()
            else -> Kitchen()
        }
    }
    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Kitchen"
            1 -> return "My orders"
            2 -> return "Profile"
        }
        return super.getPageTitle(position)
    }


}

//