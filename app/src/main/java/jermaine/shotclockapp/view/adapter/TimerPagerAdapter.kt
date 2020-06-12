package jermaine.shotclockapp.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import jermaine.shotclockapp.view.fragment.Timer14Fragment
import jermaine.shotclockapp.view.fragment.Timer24Fragment


class TimerPagerAdapter(
    fragmentManager: FragmentManager,
    private val list: List<Int>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
        when (list[position]) {
            Timer24Fragment.TIMER_24 -> Timer24Fragment.newInstance()
            Timer14Fragment.TIMER_14 -> Timer14Fragment.newInstance()
            else -> Timer24Fragment.newInstance()
        }

    override fun getCount(): Int = list.size

    fun getItems() = list
}